var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: 'GET',
            url: '/api/v1/guest',
            data: caller.searchView.getData(),
            callback: function (res) {
                caller.gridView01.setData(res);
                caller.formView01.clear();
                caller.gridView02.clear();
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
    PAGE_SAVE: function (caller, act, data) {
        if (caller.formView01.validate()) {
            var item = caller.formView01.getData();
            if (!item.id) item.__created__ = true;
            axboot.ajax({
                type: 'POST',
                url: '/api/v1/guest',
                data: JSON.stringify(item),
                callback: function (res) {
                    axToast.push('저장 되었습니다');
                    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                    caller.formView01.clear();
                },
            });
        }
    },
    ITEM_CLICK: function (caller, act, data) {
        var id = data.id;

        axboot.ajax({
            type: 'GET',
            url: '/api/v1/guest/' + id,
            callback: function (res) {
                caller.formView01.setData(res);
                caller.gridView02.setData(res.rsvList);
            },
        });

        // axboot.ajax({
        //     type: 'GET',
        //     url: '/api/v1/guest/' + id + '/rsvList',
        //     callback: function (res) {
        //         caller.gridView02.setData(res);
        //     },
        // });
    },
    EXCEL_DOWN: function (caller, act, data) {
        var frm = $('.js-form').get(0);
        frm.action = '/api/v1/guest/excelDownload';
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
    this.formView01.initView();
    this.gridView02.initView();

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
        var _this = this;
        this.target = $(document['searchView0']);
        this.target.attr('onsubmit', 'return ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);');
        this.guestNm = $('.search_name');
        this.guestTel = $('.search_tel');
        this.email = $('.search_email');
    },
    getData: function () {
        return {
            pageNumber: this.pageNumber,
            pageSize: this.pageSize,
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
                { key: 'guestNm', label: '이름', width: 80, align: 'left' },
                { key: 'guestTel', label: '연락처', width: 100, align: 'left' },
                { key: 'email', label: '이메일', width: 100, align: 'center' },
                {
                    key: 'gender',
                    label: '성별',
                    width: 50,
                    align: 'center',
                    formatter: function formatter() {
                        return this.item.gender == null ? '' : this.item.gender == 'F' ? '여' : '남';
                    },
                },
                { key: 'birth', label: '생년월일', width: 100, align: 'center' },
                {
                    key: 'langCd',
                    label: '언어',
                    width: 80,
                    align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE['PMS_LANG'].map[this.value];
                    },
                },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, { selectedClear: true });
                    ACTIONS.dispatch(ACTIONS.ITEM_CLICK, this.item);
                },
            },
        });
    },
});

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
        axboot.buttonClick(this, 'data-form-view-01-btn', {
            'form-clear': function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
        });
    },
    initView: function () {
        var _this = this; // fnObj.formView01

        _this.target = $('.guestForm');

        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작

        this.initEvent();
    },
});

fnObj.gridView02 = axboot.viewExtend(axboot.gridView, {
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showRowSelector: true,
            frozenColumnIndex: 0,
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-02"]'),
            columns: [
                {
                    key: 'rsvDt',
                    label: '투숙일',
                    width: 100,
                    align: 'center',
                    formatter: function () {
                        return moment(this.item.arrDt).format('YY.M.D') + '-' + moment(this.item.depDt).format('YY.M.D');
                    },
                },
                { key: 'nightCnt', label: '숙박수', width: 50, align: 'center' },
                { key: 'roomNum', label: '객실번호', width: 80, align: 'center' },
                {
                    key: 'roomTypCd',
                    label: '객실타입',
                    width: 120,
                    align: 'center',
                    formatter: function formatter() {
                        return parent.COMMON_CODE['PMS_ROOM_TYPE'].map[this.value];
                    },
                    editor: {
                        type: 'select',
                        config: {
                            columnKeys: {
                                optionValue: 'code',
                                optionText: 'name',
                            },
                            options: parent.COMMON_CODE['PMS_ROOM_TYPE'],
                        },
                    },
                },
                { key: 'rsvNum', label: '투숙번호', width: 120, align: 'center' },
            ],
            body: {
                onClick: function () {
                    this.self.select(this.dindex, { selectedClear: true });
                },
            },
        });
    },
});
