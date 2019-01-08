/**
 * 初始化成绩录入控制详情对话框
 */
var ControlInfoDlg = {
    controlInfoData : {}
};

/**
 * 清除数据
 */
ControlInfoDlg.clearData = function() {
    this.controlInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ControlInfoDlg.set = function(key, val) {
    this.controlInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ControlInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ControlInfoDlg.close = function() {
    parent.layer.close(window.parent.Control.layerIndex);
}

/**
 * 收集数据
 */
ControlInfoDlg.collectData = function() {
    this
    .set('id')
    .set('starttime')
    .set('endtime');
}

/**
 * 提交添加
 */
ControlInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/control/add", function(data){
        Feng.success("添加成功!");
        window.parent.Control.table.refresh();
        ControlInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.controlInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ControlInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/control/update", function(data){
        Feng.success("修改成功!");
        window.parent.Control.table.refresh();
        ControlInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.controlInfoData);
    ajax.start();
}

$(function() {

});
