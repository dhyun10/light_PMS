var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        data = caller.searchView.getData();
        axboot.ajax({
            type: 'GET',
            url: '/api/v1/reservation/list/all',
            data: caller.searchView.getData(),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                onError: function (err) {
                    console.log(err);
                },
            },
        });

        return false;
    },
    MODAL_OPEN: function (caller, act, data) {
        if (!data) data = {};

        data.modalTyp = 'All';

        axboot.modal.open({
            width: 1200,
            height: 620,
            iframe: {
                url: 'reservation-Modal.jsp',
                param: 'id=' + data.id + '&modalTyp=' + data.modalTyp,
            },
            header: { title: '예약 조회' },
            callback: function (data) {
                if (data && data.dirty) {
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                }
                this.close();
            },
        });
    },
    SEARCH_CLEAR: function (caller, act, data) {
        caller.searchView.defaultSearch();
    },
    CHANGE_STATUS: function (caller, act, data) {
        axDialog.confirm({ msg: '선택한 항목의 상태를 변경하시겠습니까?' }, function () {
            if (this.key == 'ok') {
                var items = caller.gridView01.getData('selected');
                if (!items.length) {
                    axDialog.alert('변경할 데이터가 없습니다.');
                    return false;
                }
                var ids = items.map(function (value) {
                    return value.id;
                });

                items.__modified__ = true;
                items.sttusCd = $('.js-sttusCd').val();

                axboot.ajax({
                    type: 'PUT',
                    url: '/api/v1/reservation?ids=' + ids.join(',') + '&sttusCd=' + items.sttusCd,
                    callback: function (res) {
                        axDialog.alert('변경 되었습니다');
                        ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    },
                });
            }
        });
    },
    EXCEL_DOWN: function (caller, act, data) {
        var frm = $('.js-form').get(0);
        frm.action = '/api/v1/reservation/excelDownload/All';
        frm.enctype = 'application/x-www-form-urlencoded';
        frm.submit();
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
    this.searchView.initView();
    this.gridView01.initView();

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, 'data-page-btn', {
            search: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            clear: function () {
                ACTIONS.dispatch(ACTIONS.SEARCH_CLEAR);
            },
            excel: function () {
                ACTIONS.dispatch(ACTIONS.EXCEL_DOWN);
            },
        });
    },
});

//== view 시작
/**
 * searchView
 */

fnObj.searchView = axboot.viewExtend(axboot.searchView, {
    initView: function () {
        this.target = $(document['searchView0']);
        this.target.attr('onsubmit', 'return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);');

        $('.js-sttusAll').prop('checked', true);

        this.filter = $('.js-filter');
        this.rsvNum = $('.js-rsvNum');
        this.roomTypCd = $('.js-roomTypCd');
        this.rsvDtStart = $('.js-rsvDtStart');
        this.rsvDtEnd = $('.js-rsvDtEnd');
        this.arrDtStart = $('.js-arrDtStart');
        this.arrDtEnd = $('.js-arrDtEnd');
        this.depDtStart = $('.js-depDtStart');
        this.depDtEnd = $('.js-depDtEnd');

        function calender(target) {
            var picker = new ax5.ui.picker();
            picker.bind({
                target: target,
                direction: 'top',
                content: {
                    width: 270,
                    margin: 10,
                    type: 'date',
                    config: {
                        control: {
                            left: '<i class="fa fa-chevron-left"></i>',
                            yearTmpl: '%s',
                            monthTmpl: '%s',
                            right: '<i class="fa fa-chevron-right"></i>',
                        },
                        lang: {
                            yearTmpl: '%s년',
                            months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
                            dayTmpl: '%s',
                        },
                    },
                },
                onStateChanged: function () {},
            });
        }

        calender($('[data-ax5picker="rsvDt"]'));
        calender($('[data-ax5picker="arrDt"]'));
        calender($('[data-ax5picker="depDt"]'));

        $('input:checkbox[name=sttusCd]').on('change', function () {
            $('.js-sttusAll').prop('checked', false);
        });

        $('.js-sttusAll').on('change', function () {
            $('[data-ax-path="sttusCd"]').prop('checked', false);
        });
    },
    defaultSearch: function () {
        $('input:text').val('');
        $('[data-ax-path="sttusCd"]').prop('checked', false);
        $('.js-sttusAll').prop('checked', true);
        $('.js-roomTypCd').val('');
    },
    getData: function () {
        var sttusCdArr = [];

        $('input:checkbox[name=sttusCd]:checked').each(function () {
            sttusCdArr.push(this.value);
        });

        this.sttusCd = sttusCdArr.join(',');

        return {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            filter: this.filter.val(),
            rsvNum: this.rsvNum.val(),
            roomTypCd: this.roomTypCd.val(),
            sttusCd: this.sttusCd,
            rsvDtStart: this.rsvDtStart.val(),
            rsvDtEnd: this.rsvDtEnd.val(),
            arrDtStart: this.arrDtStart.val(),
            arrDtEnd: this.arrDtEnd.val(),
            depDtStart: this.depDtStart.val(),
            depDtEnd: this.depDtEnd.val(),
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
                { key: 'rsvNum', label: '예약번호', width: 100, align: 'center' },
                { key: 'rsvDt', label: '예약일', width: 100, align: 'center' },
                { key: 'guestNm', label: '투숙객', width: 100, align: 'center' },
                {
                    key: 'roomTypCd',
                    label: '객실타입',
                    width: 100,
                    align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE['PMS_ROOM_TYPE'].map[this.value];
                    },
                },
                { key: 'roomNum', label: '객실번호', width: 100, align: 'center' },
                { key: 'arrDt', label: '도착일', width: 100, align: 'center' },
                { key: 'depDt', label: '출발일', width: 100, align: 'center' },
                {
                    key: 'srcCd',
                    label: '예약경로',
                    width: 100,
                    align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE['PMS_RESERVATION_ROUTE'].map[this.value];
                    },
                },
                {
                    key: 'saleTypCd',
                    label: '판매유형',
                    width: 100,
                    align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE['PMS_SALE_TYPE'].map[this.value];
                    },
                },
                {
                    key: 'sttusCd',
                    label: '상태',
                    width: 100,
                    align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE['PMS_STAY_STATUS'].map[this.value];
                    },
                },
                { key: 'salePrc', label: '결제금액', width: 100, align: 'center', formatter: 'money' },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, { selectedClear: true });
                },
                onDBLClick: function () {
                    ACTIONS.dispatch(ACTIONS.MODAL_OPEN, this.item);
                },
            },
        });

        axboot.buttonClick(this, 'data-grid-view-01-btn', {
            statusChange: function () {
                ACTIONS.dispatch(ACTIONS.CHANGE_STATUS);
            },
        });

        this.sttusCd = $('.js-sttusCd');
    },
    getData: function (_type) {
        var list = [];
        var _list = this.target.getList(_type);

        if (_type == 'modified' || _type == 'deleted') {
            list = ax5.util.filter(_list, function () {
                delete this.deleted;
                return this.key;
            });
        } else {
            list = _list;
        }
        return list;
    },
    statusData: function () {
        return {
            sttusCd: this.sttusCd.val(),
        };
    },
});
