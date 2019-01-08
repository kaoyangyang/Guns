/**
 * 分数管理管理初始化
 */
var Score = {
    id: "ScoreTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Score.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '课程名称', field: 'coursename', visible: true, align: 'center', valign: 'middle'},
            {title: '分数', field: 'score', visible: true, align: 'center', valign: 'middle'},
            {title: '学生姓名', field: 'username', visible: true, align: 'center', valign: 'middle'},
            {title: '班级名称', field: 'classname', visible: true, align: 'center', valign: 'middle'},
            {title: '年级', field: 'grade', visible: true, align: 'center', valign: 'middle'},
            {title: '录入时间', field: 'datetime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Score.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Score.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加分数管理
 */
Score.openAddScore = function () {
    var index = layer.open({
        type: 2,
        title: '添加分数管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/score/score_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看分数管理详情
 */
Score.openScoreDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '分数管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/score/score_update/' + Score.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除分数管理
 */
Score.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/score/delete", function (data) {
            if(data.success){
                Feng.success("删除失败,不在录入时间!")
            }else{
                Feng.success("删除成功!")
            }
            Score.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("scoreId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询分数管理列表
 */
Score.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Score.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Score.initColumn();
    var table = new BSTable(Score.id, "/score/list", defaultColunms);
    table.setPaginationType("client");
    Score.table = table.init();
});
