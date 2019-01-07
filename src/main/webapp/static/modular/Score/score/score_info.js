/**
 * 初始化分数管理详情对话框
 */
var ScoreInfoDlg = {
    scoreInfoData : {}
};

/**
 * 清除数据
 */
ScoreInfoDlg.clearData = function() {
    this.scoreInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScoreInfoDlg.set = function(key, val) {
    this.scoreInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ScoreInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ScoreInfoDlg.close = function() {
    parent.layer.close(window.parent.Score.layerIndex);
}

/**
 * 收集数据
 */
ScoreInfoDlg.collectData = function() {
    this
    .set('id')
    .set('coursename')
    .set('courseid')
    .set('socre')
    .set('username')
    .set('userid')
    .set('classcode')
    .set('classname')
    .set('grade')
    .set('datetime');
}

/**
 * 提交添加
 */
ScoreInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/score/add", function(data){
        Feng.success("添加成功!");
        window.parent.Score.table.refresh();
        ScoreInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scoreInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ScoreInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/score/update", function(data){
        Feng.success("修改成功!");
        window.parent.Score.table.refresh();
        ScoreInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.scoreInfoData);
    ajax.start();
}

$(function() {
    getStu();
});
function getStu(){
    $("#username").empty();
    var classid=$("#classcode option:selected").val();
    $.ajax({
        type: 'POST',
        url: Feng.ctxPath + "/mgr/getStu",  	//默认使用form的action
        data : {'classid':classid},	//序列化表单
        dataType: 'JSON',
        success: function(data){
            for (var i=0;i<data.length;i++){
                $("#userid").append('<option  value="'+data[i].id+'">'+data[i].name+'</option>');
            }

        },
        error: function(XmlHttpRequest, textStatus, errorThrown){
        }
    });
}