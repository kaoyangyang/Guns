var scoreList_table;
var scoreList_table_id = 'scoreList_table';                   //教育局管理者表id
var scoreList_loadData_url = '/score/showscore';
$(function(){
    userDetailList_loadData();
    refreshCharts();
    refreshRoundCharts();
});

//    其他人员
var userDetailList_table_columns =
    [
        {
            field: 'coursename',
            title: "课程名称",
            valign:"middle",
            align:"center",
        },
        { field: 'classname',
            title: "班级姓名",
            valign:"middle",
            align:"center",
        },
        {
            field: 'coursename',
            title: "学科",
            valign:"middle",
            align:"center",
        },
        {
            field: 'score',
            title: "分数",
            valign:"middle",
            align:"center",
        },
        {
            field: 'username',
            title: "学生",
            valign:"middle",
            align:"center",
        },
        {
            field: 'datetime',
            title: "时间",
            valign:"middle",
            align:"center",
        }

    ];

function userDetailList_loadData(){
    scoreList_table = $('#' + scoreList_table_id).bootstrapTable({
        method:'post',
        columns : userDetailList_table_columns,
        url: scoreList_loadData_url,
        showRefresh: true, //是否显示刷新功能
        showExport: true,
        exportFileName: "成绩单",
        exportDataType: "all",
        showColumns: true,
        showFooter:true,
        showPaginationNum:false,
        paginationPreText:"上一页",
        paginationNextText:"下一页",
        sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
        pageNumber:1,            //初始化加载第一页，默认第一页
        pageSize: 10,            //每页的记录行数（*）
        pageList: [10, 20, 50, 100],    //可供选择的每页的行数（*）
        onLoadSuccess: function(data){  //加载成功时执行
            //点击过一次查询按钮就不能再点击
            $("#searchBtn").removeAttr("disabled");
        }
    });
}

//刷新查询
function userDetailList_search(){
//        if($("#searchBtn").attr("disabled")){
//          //点击过一次查询按钮就不能再点击
//        }else{
//            $("#searchBtn").attr("disabled",true);
//            userDetailList_table.bootstrapTable('refreshOptions',{pageNumber:1,url: userDetailList_loadData_url});
//        }
    scoreList_table.bootstrapTable('refreshOptions',{pageNumber:1,url: scoreList_loadData_url});
}
optionRound = null;
var dom = document.getElementById("container");
var myChart = echarts.init(dom);
option = null;
//图标1
var dataKs =[];
var xAxisData =[];
function refreshCharts(){
    $.ajax({
        async : true,
        cache:false,
        type: 'POST',
        dataType : "json",
        url : "/score/showscore",
        success : function(json) {
            dataKs =[];
            xAxisData =[];
            for(var i=0;i < json.rows.length;i++){
                dataKs.push(json.rows[i].score);
                xAxisData.push(json.rows[i].coursename);
            }
            option.xAxis.data = xAxisData;// 这一步是设置X轴数据了
            option.series[0].data=dataKs;
            myChart.setOption(option);
        },
        error:function(){
            alert("数据加载失败！");
        }
    });
}

option = {
    title : {
        text: '分数统计'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        // data: ['建设量', '整体占比']
        data: dataKs,
    },
    calculable : true,
    xAxis: {
        data: xAxisData,
    },
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'分数',
            type:'bar',
            data:dataKs
        }
        //
        // ,{
        //     name:'整体占比',
        //     type:'line',
        //     data:dataKs,
        // }
    ]
};
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
//图标2
var courses =[];//学科
var allcourse =[];//学科
var domRound = document.getElementById("containerRound");
var myChartRound = echarts.init(domRound);
optionRound = null;
optionRound = {
    title : {
        text: '分数统计',
        subtext: '分数统计',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:allcourse
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {
                show: true,
                type: ['pie', 'funnel'],
                option: {
                    funnel: {
                        x: '25%',
                        width: '50%',
                        funnelAlign: 'left',
                        max: 1548
                    }
                }
            },
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'访问来源',
            type:'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data: [courses],
        }
    ]
};


function refreshRoundCharts(){
    $.ajax({
        async : true,
        cache:false,
        type: 'POST',
        dataType : "json",
        url : "/score/showscore",
        success : function(json) {
            courses =[];
            allcourse =[];
            for(var i=0;i < json.rows.length;i++){
                var obj = new Object();
                obj.value =json.rows[i].score;
                obj.name = json.rows[i].coursename;
                courses.push(obj);
                allcourse.push( json.rows[i].coursename)
            }
            optionRound.legend.data=allcourse;
            optionRound.series[0].data=courses;
            myChartRound.setOption(optionRound);
        },
        error:function(){
            alert("数据加载失败！");
        }
    });
}
if (optionRound && typeof optionRound === "object") {
    myChartRound.setOption(optionRound, true);
}
//图标2
var grade =[];//学科
var allscores =[];//分数
var domHis = document.getElementById("containerHis");
var myChartHis= echarts.init(domHis);
optionHis = null;
optionHis = {
    title : {
        text: '成绩分析',
        subtext: '历年数据'
    },
    tooltip : {
        trigger: 'axis'
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            name:"年份",
            type : 'category',
            boundaryGap : false,
            data : grade
        }
    ],
    yAxis : [
        {
            name:"分数",
            type : 'value',
            axisLabel : {
            },
            data : grade
        }
    ],
    series : [
        {
            name:'分数',
            type:'line',
            data:allscores
        },
    ]
};


function getHis(){
    var courseid =$ ("#courseid").val();
    $.ajax({
        async : true,
        cache:false,
        type: 'POST',
        dataType : "json",
        url : "/score/showscore",
        data:{"courseid":courseid},
        success : function(json) {
            grade =[];
            allcourse =[];
            for(var i=0;i < json.rows.length;i++){
                grade.push( json.rows[i].grade);
                allcourse.push( json.rows[i].score)
            }
            console.log(grade);
            optionHis.xAxis[0].data=grade;
            optionHis.series[0].data=allcourse;
            myChartHis.setOption(optionHis);
        },
        error:function(){
            alert("数据加载失败！");
        }
    });
}
if (optionHis && typeof optionHis === "object") {
    myChartHis.setOption(optionHis, true);
}
function getExcel(){
    window.open("/score/excel");
}
