var modalParams = modalParams || {};
var roomModal = new ax5.ui.modal();
var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_CLOSE: function (caller, act, data) {
        if (parent) {
            parent.axboot.modal.close(data);
        }
    },
    PAGE_OPEN: function (caller, act, data) {
        // data = parent.axboot.modal.getData();
        // if (!data.id) return false;
        if (!modalParams.id) return false;

        axboot.ajax({
            type: 'GET',
            url: '/api/v1/reservation/' + modalParams.id,
            callback: function (res) {
                caller.formView01.setData(res);
                caller.gridView01.setData(res.memoList);
            },
            options: {
                onError: function (err) {
                    console.log(err);
                },
            },
        });
        return false;
    },
    PAGE_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var item = caller.formView01.getData();

            var memoList = [].concat(caller.gridView01.getData());
            memoList = memoList.concat(caller.gridView01.getData('deleted'));

            item.memoList = memoList;

            item.__modified__ = true;
            if (data) item.sttusCd = data;

            axboot.ajax({
                type: 'PUT',
                url: '/api/v1/reservation/' + item.id,
                data: JSON.stringify(item),
                callback: function (res) {
                    axDialog.alert('변경 되었습니다', function () {
                        if (parent && parent.axboot && parent.axboot.modal) {
                            parent.axboot.modal.callback({ dirty: true });
                        }
                    });
                },
            });
        }
    },
    GUEST_OPEN: function (caller, act, data) {
        data = caller.formView01.searchData();
        if (!data) data = {};

        caller.guestModalView.open(data);
    },
    ROOM_OPEN: function (caller, act, data) {
        data = caller.formView01.getData();
        if (!data) data = {};

        caller.roomModalView.open(data);
    },
    ITEM_ADD: function (caller, act, data) {
        caller.gridView01.addRow();
    },
    ITEM_DEL: function (caller, act, data) {
        caller.gridView01.delRow('selected');
    },
    dispatch: function (caller, act, data) {
        var result = ACTIONS.exec(caller, act, data);
        if (result != 'error') {
            return result;
        } else {
            // 직접코딩
            return false;
        }
    },
});

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    this.pageButtonView.initView();
    this.formView01.initView();
    this.gridView01.initView();
    this.guestModalView.initView();
    this.roomModalView.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_OPEN);
};

fnObj.pageResize = function () {};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, 'data-page-btn', {
            save: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            checkIn: function () {
                var data = $('[data-page-btn="checkIn"]').data('value');
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE, data);
            },
            checkOut: function () {
                var data = $('[data-page-btn="checkOut"]').data('value');
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE, data);
            },
            cancelCheckIn: function () {
                var data = $('[data-page-btn="cancelCheckIn"]').data('value');
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE, data);
            },
            close: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
            },
        });
    },
});

fnObj.guestModalView = axboot.viewExtend({
    open: function (data) {
        var _this = this;
        if (!data) data = {};

        this.modal.open({
            width: 780,
            height: 500,
            iframe: {
                url: 'guest-Modal.jsp',
                param: 'guestNm=' + data.guestNm + '&guestTel=' + data.guestTel + '&email=' + data.email + '&modalView=guestModalView',
            },
            header: { title: '투숙객 조회' },
        });
    },
    close: function () {
        this.modal.close();
    },
    callback: function (data) {
        fnObj.formView01.setGuest(data);
        this.modal.close();
    },
    initView: function () {
        this.modal = new ax5.ui.modal();
    },
});

fnObj.roomModalView = axboot.viewExtend({
    open: function (data) {
        var _this = this;
        if (!data) data = {};

        this.modal.open({
            width: 700,
            height: 350,
            iframe: {
                url: 'room-Modal.jsp',
                param: 'roomTypCd=' + data.roomTypCd + '&modalView=roomModalView',
            },
            header: { title: '객실 조회' },
        });
    },
    close: function () {
        this.modal.close();
    },
    callback: function (data) {
        fnObj.formView01.setRoomNum(data.roomNum);
        this.modal.close();
    },
    initView: function () {
        this.modal = new ax5.ui.modal();
    },
});
//== view 시작

fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return {};
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setData: function (data) {
        if (typeof data === 'undefined') data = this.getDefaultData();
        data = $.extend({}, data);

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    },
    setGuest: function (data) {
        this.model.set('guestId', data.id);
        this.model.set('guestNm', data.guestNm);
        this.model.set('guestNmEng', data.guestNmEng);
        this.model.set('guestTel', data.guestTel);
        this.model.set('email', data.email);
        this.model.set('langCd', data.langCd);
        this.model.set('birth', data.birth);
        this.model.set('gender', data.gender);
    },
    setRoomNum: function (data) {
        this.model.set('roomNum', data);
    },
    validate: function () {
        var item = this.model.get();

        var rs = this.model.validate();

        if (rs.error) {
            alert(rs.error[0].jquery.attr('title') + '을(를) 입력해주세요.');
            rs.error[0].jquery.focus();
            return false;
        }

        if (modalParams.modalTyp == 'checkIn') {
            if (!item.roomNum) {
                axDialog.alert('객실을 배정해주세요', function () {
                    $('[data-ax-path="roomNum"]').focus();
                });
                return false;
            }
        }

        return true;
    },
    initEvent: function () {
        var _this = this;

        axboot.buttonClick(this, 'data-form-view-01-btn', {
            'form-clear': function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
            searchGuest: function () {
                ACTIONS.dispatch(ACTIONS.GUEST_OPEN);
            },
            searchRoom: function () {
                ACTIONS.dispatch(ACTIONS.ROOM_OPEN);
            },
        });

        $('.js-arrDt').on('change', function () {
            var arrDate = moment($('.js-arrDt').val());
            var nightCnt = $('.js-nightCnt').val();

            var depDate = arrDate.add(nightCnt, 'days');

            _this.model.set('depDt', depDate.format('YYYY-MM-DD'));
        });

        $('.js-nightCnt').on('change', function () {
            var arrDate = moment($('.js-arrDt').val());
            var nightCnt = $('.js-nightCnt').val();

            var depDate = arrDate.add(nightCnt, 'days');

            _this.model.set('depDt', depDate.format('YYYY-MM-DD'));
        });

        $('.js-depDt').on('change', function () {
            var arrDate = moment($('.js-arrDt').val());
            var depDate = moment($('.js-depDt').val());

            var count = depDate.diff(arrDate, 'days');

            if (count < 0) {
                axDialog.alert('숙박 일수는 1일 이상 가능합니다.', function () {
                    $('[data-ax-path="depDt"]').focus();
                });
                return 0;
            }

            _this.model.set('nightCnt', count);
        });
    },
    initView: function () {
        var _this = this; // fnObj.formView01

        _this.target = $('.reservationForm');

        this.guestNm = $('.js-guestNm');
        this.guestTel = $('.js-guestTel');
        this.email = $('.js-email');

        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작

        this.initEvent();
    },
    searchData: function () {
        return {
            guestNm: this.guestNm.val(),
            guestTel: this.guestTel.val(),
            email: this.email.val(),
        };
    },
});

/**
 * gridView
 */
fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: true,
            frozenColumnIndex: 0,
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {
                    key: 'memoDtti',
                    label: '작성일',
                    width: 100,
                    align: 'center',
                    formatter: function () {
                        return moment(this.item.memoDtti).format('YYYY-MM-DD');
                    },
                },
                { key: 'memoCn', label: '메모', width: 650, align: 'left', editor: 'text' },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, { selectedClear: true });
                },
            },
        });

        axboot.buttonClick(this, 'data-grid-view-01-btn', {
            add: function () {
                ACTIONS.dispatch(ACTIONS.ITEM_ADD);
            },
            delete: function () {
                ACTIONS.dispatch(ACTIONS.ITEM_DEL);
            },
        });
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == 'modified' || _type == 'deleted') {
            list = ax5.util.filter(_list, function () {
                return this.id;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({ __created__: true, memoDtti: moment().format('YYYY-MM-DD') }, 'last');
    },
});
