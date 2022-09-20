/*----------------------柱状图-----------------------*/
//坐标轴刻度与标签对齐
(function(){
	 var today = new Date();//获得当前日期
     var year = today.getFullYear();//获得年份
	 var month=0;
	 var stationString=new Array(); 
    var proportionString=new Array(); 
	
	json_ob=getProportionString(year,month);
	
	i=1;
	while(i<=json_ob.station_count)
	{
	stationString.push("工位"+i);
	i++;
	}
	ab_time=eval('(' + json_ob.abnormal_time + ')');
	to_time=eval('(' + json_ob.total_time + ')');

			for(i=1;i<=json_ob.station_count;i++)
			{
			proportionString.push(1-ab_time[i]/to_time[i]);
			}
			
var columnar1 = echarts.init(document.getElementById("columnar1"));

option = {
	title: {
		x:'left'
	},
    color: ['#9370DB'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : stationString,
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'直接访问',
            type:'bar',
            barWidth: '60%',
            data:proportionString
        }
    ]
};

columnar1.setOption(option);
})();

function showChartColumnar()
{
	var stationString=new Array(); 
    var proportionString=new Array(); 
	
    if((temp.value).indexOf(".")!=-1)
	{
		var tempString=(temp.value).split(".");
		var year=tempString[0];
		var month=tempString[1];
	}
	else {year=temp.value; month=0;}
	
	json_ob=getProportionString(year,month);
	
	i=1;
	while(i<=json_ob.station_count)
	{
	stationString.push("工位"+i);
	i++;
	}
	ab_time=eval('(' + json_ob.abnormal_time + ')');
	to_time=eval('(' + json_ob.total_time + ')');

			for(i=1;i<=json_ob.station_count;i++)
			{
			proportionString.push(1-ab_time[i]/to_time[i]);
			}
			
var columnar1 = echarts.init(document.getElementById("columnar1"));

option = {
	title: {
		x:'left'
	},
    color: ['#9370DB'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : stationString,
            axisTick: {
                alignWithLabel: true
            }
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'直接访问',
            type:'bar',
            barWidth: '60%',
            data:proportionString
        }
    ]
};

columnar1.setOption(option);
}