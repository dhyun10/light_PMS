var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: 'GET',
            url: '/api/v1/reservation/sales',
            data: caller.searchView.getData(),
            callback: function (res) {
                caller.gridView01.setData(res);
            },
            options: {
                // axboot.ajax 함수에 2번째 인자는 필수가 아닙니다. ajax의 옵션을 전달하고자 할때 사용합니다.
                onError: function (err) {
                    console.log(err);
                },
            },
        });

        return false;
    },
    EXCEL_DOWNLOAD: function (caller, act, data) {
        var frm = $('.js-form').get(0);
        frm.action = '/api/v1/reservation/sales/excelDownload';
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
            save: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
            },
            excel: function () {
                ACTIONS.dispatch(ACTIONS.EXCEL_DOWNLOAD);
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
        this.rsvDtStart = $('.js-rsvDtStart');
        this.rsvDtEnd = $('.js-rsvDtEnd');

        var picker = new ax5.ui.picker();

        picker.bind({
            target: $('[data-ax5picker="rsvDt"]'),
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

        $('button[name=period]').on('click', function () {
            var period = $(this).data('value');

            var date = [];
            date = period.split('-');
            var startDate = moment().subtract(date[0], date[1]).format('YYYY-MM-DD');

            $('.js-rsvDtStart').val(startDate);
            $('.js-rsvDtEnd').val(moment().format('YYYY-MM-DD'));
        });
    },
    getData: function () {
        return {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
            rsvDtStart: this.rsvDtStart.val(),
            rsvDtEnd: this.rsvDtEnd.val(),
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
            frozenRowIndex: 1,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                {
                    key: 'rsvDt',
                    label: '날짜',
                    width: 160,
                    align: 'center',
                    formatter: function formatter() {
                        return this.item.rsvDt == null ? '합    계' : this.item.rsvDt;
                    },
                },
                { key: 'rsvCnt', label: '투숙건수', width: 120, align: 'center' },
                { key: 'salePrc', label: '판매금액', width: 160, align: 'center', formatter: 'money' },
                { key: 'svcPrc', label: '서비스금액', width: 140, align: 'center', formatter: 'money' },
                {
                    key: 'sumPrc',
                    label: '합계',
                    width: 180,
                    align: 'center',
                    formatter: function () {
                        return ax5.util.number(this.item.salePrc + this.item.svcPrc, { money: true });
                    },
                },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, { selectedClear: true });
                },
            },
        });
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
});
