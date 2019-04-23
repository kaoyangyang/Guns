/**
 * 请假模块管理初始化
 */
var Item = {
    id: "ItemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Item.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '原因', field: 'cotent', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人id', field: 'userId', visible: false, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'userName', visible: true, align: 'center', valign: 'middle'},
            {title: '审批人id', field: 'passId', visible: false, align: 'center', valign: 'middle'},
            {title: '审批人', field: 'passName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '附件', visible: true, align: 'center', valign: 'middle', formatter: function (value, row, index) {
                return '<a href="/item/download?id=' + row.id + '" >'+row.fileName+'</a>';
            }
        },
            {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Item.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Item.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加请假模块
 */
Item.openAddItem = function () {
    var index = layer.open({
        type: 2,
        title: '添加请假模块',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/item/item_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看请假模块详情
 */
Item.openItemDetail = function () {
    if (this.check()) {
        $.ajax({
            url : Feng.ctxPath+"/item/update",
            type : "POST",
            data : {"id":Item.seItem.id},
            success : function(data) {
                if(data.code==200){
                    Feng.success("审批成功!");
                    window.Item.table.refresh();
                }else{
                    Feng.error(data.message);
                }
            },
            error : function(data) {
                $( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
            }
        });

        //
        //
        //
        //
        //
        // var index = layer.open({
        //     type: 2,
        //     title: '请假模块详情',
        //     area: ['800px', '420px'], //宽高
        //     fix: false, //不固定
        //     maxmin: true,
        //     content: Feng.ctxPath + '/item/item_update/' + Item.seItem.id
        // });
        // this.layerIndex = index;
    }
};

/**
 * 删除请假模块
 */
Item.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/item/delete", function (data) {
            Feng.success("删除成功!");
            Item.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("itemId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询请假模块列表
 */
Item.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Item.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Item.initColumn();
    var table = new BSTable(Item.id, "/item/list", defaultColunms);
    table.setPaginationType("client");
    Item.table = table.init();
});
