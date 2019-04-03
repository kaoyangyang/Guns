/**
 * 初始化分数修改记录详情对话框
 */
var ScoreLogInfoDlg = {
    scoreLogInfoData : {}
};

/**
 * 清除数据
 */
ScoreLogInfoDlg.clearData = function() {
    this.scoreLogInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScoreLogInfoDlg.set = function(key, val) {
    this.scoreLogInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScoreLogInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ScoreLogInfoDlg.close = function() {
    parent.layer.close(window.parent.ScoreLog.layerIndex);
}

/**
 * 收集数据
 */
ScoreLogInfoDlg.collectData = function() {
    this
    .set('id')
    .set('oldcourse')
    .set('newcourse')
    .set('oldscore')
    .set('newscore')
    .set('olduser')
    .set('newuser')
    .set('oldclass')
    .set('newclass')
    .set('username')
    .set('datetime');
}

/**
 * 提交添加
 */
ScoreLogInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scoreLog/add", function(data){
        Feng.success("添加成功!");
        window.parent.ScoreLog.table.refresh();
        ScoreLogInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scoreLogInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ScoreLogInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/scoreLog/update", function(data){
        Feng.success("修改成功!");
        window.parent.ScoreLog.table.refresh();
        ScoreLogInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scoreLogInfoData);
    ajax.start();
}

$(function() {

});
