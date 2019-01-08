/**
 * 成绩录入控制管理初始化
 */
var Control = {
    id: "ControlTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Control.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '开始时间', field: 'starttime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endtime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Control.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Control.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加成绩录入控制
 */
Control.openAddControl = function () {
    var index = layer.open({
        type: 2,
        title: '添加成绩录入控制',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/control/control_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看成绩录入控制详情
 */
Control.openControlDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '成绩录入控制详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/control/control_update/' + Control.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除成绩录入控制
 */
Control.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/control/delete", function (data) {
            Feng.success("删除成功!");
            Control.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("controlId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询成绩录入控制列表
 */
Control.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Control.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Control.initColumn();
    var table = new BSTable(Control.id, "/control/list", defaultColunms);
    table.setPaginationType("client");
    Control.table = table.init();
});
