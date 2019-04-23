/**
 * 初始化请假模块详情对话框
 */
var ItemInfoDlg = {
    itemInfoData : {}
};

/**
 * 清除数据
 */
ItemInfoDlg.clearData = function() {
    this.itemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ItemInfoDlg.set = function(key, val) {
    this.itemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ItemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ItemInfoDlg.close = function() {
    parent.layer.close(window.parent.Item.layerIndex);
}

/**
 * 收集数据
 */
ItemInfoDlg.collectData = function() {
    this
    .set('id')
    .set('type')
    .set('cotent')
    .set('startTime')
    .set('endTime')
    .set('userId')
    .set('userName')
    .set('passId')
    .set('passName')
    .set('path')
    .set('status');
}

/**
 * 提交添加
 */
$('#myForm').click(function() {
    var formData = new FormData(document.getElementById("postForm"));//表单id
    $.ajax({
        url : Feng.ctxPath+"/item/add",
        type : "POST",
        data : formData,
        processData: false,
        contentType: false,
        success : function(data) {
            if(data.code==200){
                Feng.success("添加成功!");
                window.parent.Item.table.refresh();
                ItemInfoDlg.close();
            }else{
                Feng.error(data.message);
                // ItemInfoDlg.close();
            }
            // $( '#serverResponse').html(data);
        },
        error : function(data) {
            $( '#serverResponse').html(data.status + " : " + data.statusText + " : " + data.responseText);
        }
    });
});
// ItemInfoDlg.addSubmit = function() {
//
//     this.clearData();
//     this.collectData();
//
//     //提交信息
//     var ajax = new $ax(Feng.ctxPath + "/item/add", function(data){
//         Feng.success("添加成功!");
//         window.parent.Item.table.refresh();
//         ItemInfoDlg.close();
//     },function(data){
//         Feng.error("添加失败!" + data.responseJSON.message + "!");
//     });
//     ajax.set(this.itemInfoData);
//     ajax.start();
// }

/**
 * 提交修改
 */
ItemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/item/update", function(data){
        Feng.success("修改成功!");
        window.parent.Item.table.refresh();
        ItemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.itemInfoData);
    ajax.start();
}

$(function() {

});
