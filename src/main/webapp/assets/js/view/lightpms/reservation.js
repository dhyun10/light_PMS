var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SAVE: function (caller, act, data) {
        var item = caller.formView01.getData();
        if (item.rsvNum) {
            alert('저장된 예약건입니다.');
            return;
        }

        var memoList = [].concat(caller.gridView01.getData());
        item.memoList = memoList;

        console.log(item);

        axboot.ajax({
            type: 'POST',
            url: '/api/v1/reservation',
            data: JSON.stringify(item),
            callback: function (res) {
                axToast.push('저장 되었습니다');
                caller.formView01.setrsvNum(res.rsvNum);
            },
        });
    },
    MODAL_OPEN: function (caller, act, data) {
        data = caller.formView01.searchData();
        if (!data) data = {};

        axboot.modal.open({
            width: 780,
            height: 480,
            iframe: {
                url: 'guest-Modal.jsp',
                param: 'guestNm=' + data.guestNm + '&guestTel=' + data.guestTel + '&email=' + data.email,
            },
            header: { title: '투숙객 조회' },
            callback: function (data) {
                if (data) {
                    caller.formView01.setGuest(data);
                }
                this.close();
            },
        });
    },
    PAGE_CLEAR: function (caller, act, data) {
        caller.gridView01.clear();
        caller.formView01.clear();
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

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, 'data-page-btn', {
            save: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            clear: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_CLEAR);
            },
        });
    },
});

//== view 시작
fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return {
            advnYn: 'N',
            arrDt: moment().format('YYYY-MM-DD'),
        };
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
    setrsvNum: function (data) {
        this.model.set('rsvNum', data);
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
    validate: function () {
        var item = this.model.get();

        var rs = this.model.validate();

        if (rs.error) {
            alert(rs.error[0].jquery.attr('title') + '을(를) 입력해주세요.');
            rs.error[0].jquery.focus();
            return false;
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
                ACTIONS.dispatch(ACTIONS.MODAL_OPEN);
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
                axDialog.alert('숙박은 1일 이상 가능합니다.', function () {
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
