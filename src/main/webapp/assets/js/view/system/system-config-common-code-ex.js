var fnObj = {};
var ACTIONS = axboot.actionExtend(fnObj, {
    PAGE_SEARCH: function (caller, act, data) {
        axboot.ajax({
            type: 'GET',
            //url: ['menu'],
            url: '/api/v1/commonGroup',
            callback: function (res) {
                caller.treeView01.setData(res.list);
            },
            options: {
                // axboot.ajax 함수에 2번째 인자는 필수가 아닙니다. ajax의 옵션을 전달하고자 할때 사용합니다.
                onError: function (err) {
                    console.log(err);
                },
            },
        });
        return false;
        // var res = {
        //     page: {
        //         totalPages: 0,
        //         totalElements: 0,
        //         currentPage: 0,
        //         pageSize: 0,
        //     },
        //     list: [
        //         {
        //             name: 'Site Map',
        //             open: true,
        //             children: [
        //                 { name: 'google', target: '_blank' },
        //                 { name: 'baidu', target: '_blank' },
        //                 { name: 'sina', target: '_blank' },
        //             ],
        //         },
        //     ],
        // };
        // caller.treeView01.setData(res.list);
    },
    PAGE_SAVE: function (caller, act, data) {
        var saveList = [].concat(caller.gridView01.getData('modified'));
        saveList = saveList.concat(caller.gridView01.getData('deleted'));

        axboot.ajax({
            type: 'PUT',
            url: ['samples', 'parent'],
            data: JSON.stringify(saveList),
            callback: function (res) {
                ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
                axToast.push('저장 되었습니다');
            },
        });
    },
    ITEM_CLICK: function (caller, act, data) {},
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
    this.gridView01.initView();
    this.formView01.initView();
    this.treeView01.initView();

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
        });
    },
});

//== view 시작

/**
 * treeView
 */
fnObj.treeView01 = axboot.viewExtend(axboot.treeView, {
    initView: function () {
        var _this = this;
        this.target = axboot.treeBuilder(
            $('[data-z-tree="tree-view-01"]'),
            {
                view: {
                    dblClickExpand: false,
                    addHoverDom: function (treeId, treeNode) {
                        var sObj = $('#' + treeNode.tId + '_span');
                        if (treeNode.editNameFlag || $('#addBtn_' + treeNode.tId).length > 0) return;
                        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='add node' onfocus='this.blur();'></span>";
                        sObj.after(addStr);
                        var btn = $('#addBtn_' + treeNode.tId);
                        if (btn) {
                            btn.bind('click', function () {
                                _this.target.zTree.addNodes(treeNode, {
                                    id: '_isnew_' + ++_this.newCount,
                                    pId: treeNode.id,
                                    name: 'New Item',
                                    __created__: true,
                                    menuGrpCd: _this.param.menuGrpCd,
                                });
                                _this.target.zTree.selectNode(treeNode.children[treeNode.children.length - 1]);
                                _this.target.editName();
                                fnObj.treeView01.deselectNode();
                                return false;
                            });
                        }
                    },
                    removeHoverDom: function (treeId, treeNode) {
                        $('#addBtn_' + treeNode.tId)
                            .unbind()
                            .remove();
                    },
                },
                edit: {
                    enable: true,
                    editNameSelectAll: true,
                },
                callback: {
                    beforeDrag: function () {
                        //return false;
                    },
                    onClick: function (e, treeId, treeNode, isCancel) {
                        ACTIONS.dispatch(ACTIONS.TREEITEM_CLICK, treeNode);
                    },
                    onRename: function (e, treeId, treeNode, isCancel) {
                        treeNode.__modified__ = true;
                    },
                    onRemove: function (e, treeId, treeNode, isCancel) {
                        if (!treeNode.__created__) {
                            treeNode.__deleted__ = true;
                            _this.deletedList.push(treeNode);
                        }
                        fnObj.treeView01.deselectNode();
                    },
                },
            },
            []
        );
    },
    setData: function (_tree, _data) {
        var _this = this;
        this.target.setData(_tree);
        //console.l
        if (_data && typeof _data.id !== 'undefined') {
            // selectNode
            (function (_tree, _keyName, _key) {
                var nodes = _tree.getNodes();
                var findNode = function (_arr) {
                    var i = _arr.length;
                    while (i--) {
                        if (_arr[i][_keyName] == _key) {
                            _tree.selectNode(_arr[i]);
                        }
                        if (_arr[i].children && _arr[i].children.length > 0) {
                            findNode(_arr[i].children);
                        }
                    }
                };
                findNode(nodes);
            })(this.target.zTree, 'id', _tree.id);
        }
    },
});

fnObj.formView01 = axboot.viewExtend(axboot.formView, {
    getDefaultData: function () {
        return $.extend({}, axboot.formView.defaultData, {});
    },
    initView: function () {
        this.target = $('#formView01');
        this.model = new ax5.ui.binder();
        this.model.setModel(this.getDefaultData(), this.target);
        this.modelFormatter = new axboot.modelFormatter(this.model); // 모델 포메터 시작
        this.initEvent();

        axboot.buttonClick(this, 'data-form-view-01-btn', {
            'form-clear': function () {
                ACTIONS.dispatch(ACTIONS.FORM_CLEAR);
            },
        });
    },
    initEvent: function () {
        var _this = this;
    },
    getData: function () {
        var data = this.modelFormatter.getClearData(this.model.get()); // 모델의 값을 포멧팅 전 값으로 치환.
        return $.extend({}, data);
    },
    setData: function (data) {
        if (typeof data === 'undefined') data = this.getDefaultData();
        data = $.extend({}, data);

        this.target.find('[data-ax-path="key"]').attr('readonly', 'readonly');

        this.model.setModel(data);
        this.modelFormatter.formatting(); // 입력된 값을 포메팅 된 값으로 변경
    },
    validate: function () {
        var rs = this.model.validate();
        if (rs.error) {
            alert(LANG('ax.script.form.validate', rs.error[0].jquery.attr('title')));
            rs.error[0].jquery.focus();
            return false;
        }
        return true;
    },
    clear: function () {
        this.model.setModel(this.getDefaultData());
        this.target.find('[data-ax-path="key"]').removeAttr('readonly');
    },
});

fnObj.gridView01 = axboot.viewExtend(axboot.gridView, {
    initView: function () {
        var _this = this;

        this.target = axboot.gridBuilder({
            showLineNumber: false,
            showRowSelector: true,
            frozenColumnIndex: 0,
            multipleSelect: true,
            target: $('[data-ax5grid="grid-view-01"]'),
            columns: [
                { key: 'key', label: '분류코드', width: 120, align: 'left', editor: 'text' },
                { key: 'value', label: '분류명', width: 120, align: 'left', editor: 'text' },
                { key: 'etc1', label: '코드', width: 70, align: 'center', editor: 'text' },
                { key: 'etc2', label: '코드값', width: 100, align: 'center', editor: 'text' },
                { key: 'etc3', label: '정렬', width: 50, align: 'center', editor: 'text' },
                { key: 'etc4', label: '사용여부', width: 100, align: 'center', editor: 'text' },
                { key: 'etc4', label: '비고', width: 150, align: 'center', editor: 'text' },
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
                delete this.deleted;
                return this.key;
            });
        } else {
            list = _list;
        }
        return list;
    },
    addRow: function () {
        this.target.addRow({ __created__: true }, 'last');
    },
});
