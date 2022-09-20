/*----------------------饼状图-----------------------*/

//饼状图
(function(){

var pie1 = echarts.init(document.getElementById("pie1"));
var ab_proportion=get_ab_proportion();
if(ab_proportion==-1)
{document.getElementById("hint").innerHTML="无数据！";}
else
{
option = {
    title : {
        text: '所有工位总工作效率',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['异常行为时间','正常工作时间']
    },
    series : [
        {
            name: '所有工位总工作效率',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            data:[
                {value:ab_proportion, name:'异常行为时间'},
                {value:1-ab_proportion, name:'正常工作时间'}
            ],

        }
    ]
};

pie1.setOption(option);
}
})();
