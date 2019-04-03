/**
 * 分数修改记录管理初始化
 */
var ScoreLog = {
    id: "ScoreLogTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ScoreLog.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '旧学科', field: 'oldcourse', visible: true, align: 'center', valign: 'middle'},
            {title: '新学科', field: 'newcourse', visible: true, align: 'center', valign: 'middle'},
            {title: '旧分数', field: 'oldscore', visible: true, align: 'center', valign: 'middle'},
            {title: '新分数', field: 'newscore', visible: true, align: 'center', valign: 'middle'},
            {title: '旧学生', field: 'olduser', visible: true, align: 'center', valign: 'middle'},
            {title: '新学生', field: 'newuser', visible: true, align: 'center', valign: 'middle'},
            {title: '旧班级', field: 'oldclass', visible: true, align: 'center', valign: 'middle'},
            {title: '新班级', field: 'newclass', visible: true, align: 'center', valign: 'middle'},
            {title: '修改人', field: 'username', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'datetime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ScoreLog.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ScoreLog.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加分数修改记录
 */
ScoreLog.openAddScoreLog = function () {
    var index = layer.open({
        type: 2,
        title: '添加分数修改记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/scoreLog/scoreLog_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看分数修改记录详情
 */
ScoreLog.openScoreLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '分数修改记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/scoreLog/scoreLog_update/' + ScoreLog.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除分数修改记录
 */
ScoreLog.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/scoreLog/delete", function (data) {
            Feng.success("删除成功!");
            ScoreLog.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("scoreLogId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询分数修改记录列表
 */
ScoreLog.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ScoreLog.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ScoreLog.initColumn();
    var table = new BSTable(ScoreLog.id, "/scoreLog/list", defaultColunms);
    table.setPaginationType("client");
    ScoreLog.table = table.init();
});
