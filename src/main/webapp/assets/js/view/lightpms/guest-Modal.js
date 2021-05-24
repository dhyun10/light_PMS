var modalParams = modalParams || {};
var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_CLOSE: function (caller, act, data) {
        var modal = fnObj.getModal();
        if (modal) modal.close();
        if (opener) window.close();
    },
    PAGE_SEARCH: function (caller, act, data) {
        var paramObj = $.extend(caller.gridView01.searchData(), data);
        if (!paramObj.guestNm || !paramObj.filter) return false;
        axboot.ajax({
            type: 'GET',
            url: '/api/v1/guest',
            data: paramObj,
            callback: function (res) {
                caller.gridView01.setData(res);
            },
        });
    },
    GUEST_SELETE: function (caller, act, data) {
        if (!data) {
            var list = caller.gridView01.getData('selected');
            if (list.length > 0) data = list[0];
        }

        if (data) {
            var modal = fnObj.getModal();
            if (modal) modal.callback(data);
            if (opener) window.close();
        }
    },
    ITEM_CLICK: function (caller, act, data) {
        caller.formView01.setData(data);
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

fnObj.getModal = function () {
    var modalView;
    if (parent && modalParams.modalView && (modalView = parent[axboot.def.pageFunctionName][modalParams.modalView])) {
        return modalView;
    } else if (opener && modalParams.modalView && (modalView = opener[axboot.def.pageFunctionName][modalParams.modalView])) {
        return modalView;
    } else if (parent && parent.axboot && parent.axboot.modal) {
        return parent.axboot.modal;
    }
};

// fnObj 기본 함수 스타트와 리사이즈
fnObj.pageStart = function () {
    this.pageButtonView.initView();
    this.formView01.initView();
    this.gridView01.initView();

    this.gridView01.guestNm.val(modalParams.guestNm);
    if (modalParams.guestTel) this.gridView01.filter.val(modalParams.guestTel);
    if (modalParams.email) this.gridView01.filter.val(modalParams.email);

    ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
};

fnObj.pageResize = function () {};

fnObj.pageButtonView = axboot.viewExtend({
    initView: function () {
        axboot.buttonClick(this, 'data-page-btn', {
            search: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
            },
            select: function () {
                ACTIONS.dispatch(ACTIONS.GUEST_SELETE);
            },
            close: function () {
                ACTIONS.dispatch(ACTIONS.PAGE_CLOSE);
            },
        });
    },
});

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
                    key: 'guestNm',
                    label: '이름',
                    width: 100,
                    align: 'center',
                },
                { key: 'guestTel', label: '연락처', width: 130, align: 'center' },
                { key: 'email', label: '이메일', width: 130, align: 'center' },
                {
                    key: 'gender',
                    label: '성별',
                    width: 80,
                    align: 'center',
                    formatter: function formatter() {
                        return this.item.gender == null ? '' : this.item.gender == 'F' ? '여' : '남';
                    },
                },
                { key: 'birth', label: '생년월일', width: 130, align: 'center' },
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
                onDBLClick: function () {
                    ACTIONS.dispatch(ACTIONS.GUEST_SELETE, this.item);
                },
            },
        });

        this.guestNm = $('.js-guestNm');
        this.filter = $('.js-filter');
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
    searchData: function () {
        return {
            guestNm: this.guestNm.val(),
            filter: this.filter.val(),
        };
    },
});
/**
 * formView
 */
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
            axDialog.alert(LANG('ax.script.form.validate', rs.error[0].jquery.attr('title')), function () {
                rs.error[0].jquery.focus();
            });
            return false;
        }

        return true;
    },
    initEvent: function () {
        axboot.buttonClick(this, 'data-form-view-01-btn', {
            formClear: function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
        });
    },
    initView: function () {
        var _this = this; // fnObj.formView01

        _this.target = $('.js-form');

        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작

        this.initEvent();
    },
});
