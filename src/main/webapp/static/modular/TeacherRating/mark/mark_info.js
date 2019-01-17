/**
 * 初始化教师评分详情对话框
 */
var MarkInfoDlg = {
    markInfoData : {}
};

/**
 * 清除数据
 */
MarkInfoDlg.clearData = function() {
    this.markInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MarkInfoDlg.set = function(key, val) {
    this.markInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MarkInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MarkInfoDlg.close = function() {
    parent.layer.close(window.parent.Mark.layerIndex);
}

/**
 * 收集数据
 */
MarkInfoDlg.collectData = function() {
    this
    .set('id')
    .set('level')
    .set('morality')
    .set('style')
    .set('code')
    .set('totle')
    .set('userid')
    .set('username')
    .set('markid')
    .set('datetime');
}

/**
 * 提交添加
 */
MarkInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mark/add", function(data){
        Feng.success("添加成功!");
        window.parent.Mark.table.refresh();
        MarkInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.markInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
MarkInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mark/update", function(data){
        Feng.success("修改成功!");
        window.parent.Mark.table.refresh();
        MarkInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.markInfoData);
    ajax.start();
}

$(function() {

});
