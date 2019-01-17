/**
 * 教师评分管理初始化
 */
var Mark = {
    id: "MarkTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Mark.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '水平', field: 'level', visible: true, align: 'center', valign: 'middle'},
            {title: '师德', field: 'morality', visible: true, align: 'center', valign: 'middle'},
            {title: '工作作风', field: 'style', visible: true, align: 'center', valign: 'middle'},
            {title: '行为规范', field: 'code', visible: true, align: 'center', valign: 'middle'},
            {title: '总分', field: 'totle', visible: true, align: 'center', valign: 'middle'},
            {title: '教师姓名', field: 'username', visible: true, align: 'center', valign: 'middle'},
            {title: '评分时间', field: 'datetime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Mark.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Mark.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加教师评分
 */
Mark.openAddMark = function () {
    var index = layer.open({
        type: 2,
        title: '添加教师评分',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mark/mark_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看教师评分详情
 */
Mark.openMarkDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教师评分详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mark/mark_update/' + Mark.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除教师评分
 */
Mark.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/mark/delete", function (data) {
            Feng.success("删除成功!");
            Mark.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("markId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询教师评分列表
 */
Mark.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Mark.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Mark.initColumn();
    var table = new BSTable(Mark.id, "/mark/list", defaultColunms);
    table.setPaginationType("client");
    Mark.table = table.init();
});
