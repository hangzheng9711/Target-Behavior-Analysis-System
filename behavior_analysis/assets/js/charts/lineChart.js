/*----------------------折线图-----------------------*/
//折线图堆叠
(function(){
	 var today = new Date();//获得当前日期
     var year = today.getFullYear();//获得年份
	 var month=0;
	 var dateString=new Array(); 
    var abnormalTimeString=new Array(); 
	dateString.push(year+'.1',year+'.2',year+'.3',year+'.4',year+'.5',year+'.6',year+'.7',year+'.8',year+'.9',year+'.10',year+'.11',year+'.12');
	abnormalTimeString=getAbnormalTimeString(1,year,month);
	
	var myChart = echarts.init(document.getElementById("Stack"));
	option = {
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['工位1']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: dateString
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'工位1',
            type:'line',
            stack: '总量',
            data:abnormalTimeString
        },
    ]
};
myChart.setOption(option);
})();

function showChartLine()
{
	var dateString=new Array(); 
    var abnormalTimeString=new Array(); 
	
   var tempString=(temp.value).split(" ");
	var station_number=tempString[1].replace(/[^0-9]/ig,"");
	if(tempString[0].indexOf(".")!=-1)
	{
		var tempString1=(tempString[0]).split(".");
		var year=tempString1[0];
		var month=tempString1[1];
	}
	else {year=tempString[0]; month=0;}
	
	if(month==0)
	{
	dateString.push(year+'.1',year+'.2',year+'.3',year+'.4',year+'.5',year+'.6',year+'.7',year+'.8',year+'.9',year+'.10',year+'.11',year+'.12');
	abnormalTimeString=getAbnormalTimeString(station_number,year,month);
	}
	else
	{
		for(i=1;i<=31;i++)
		dateString.push(i);
	abnormalTimeString=getAbnormalTimeString(station_number,year,month);
	}
	
	var myChart = echarts.init(document.getElementById("Stack"));
	option = {
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:new Array(tempString[1])
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: dateString
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:tempString[1],
            type:'line',
            stack: '总量',
            data:abnormalTimeString
        },

    ]
};
myChart.setOption(option);
}





















////柱状图
//(function(){
//
//var myChart = echarts.init(document.getElementById("histogram"));
//
//option = {
//
//	title: {
//		text: "柱状图",
//		x:'center'
//	},
//
//  color: ['#3398DB'],
//  tooltip : {
//      trigger: 'axis',
//      axisPointer : {            // 坐标轴指示器，坐标轴触发有效
//          type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
//      }
//  },
//  toolbox: {
//      feature: {
//          saveAsImage: {}
//      }
//  },
//  grid: {
//      left: '3%',
//      right: '4%',
//      bottom: '3%',
//      containLabel: true
//  },
//  xAxis : [
//      {
//          type : 'category',
//          data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
//          axisTick: {
//              alignWithLabel: true
//          }
//      }
//  ],
//  yAxis : [
//      {
//          type : 'value'
//      }
//  ],
//  series : [
//      {
//          name:'直接访问',
//          type:'bar',
//          barWidth: '60%',
//          data:[10, 52, 200, 334, 390, 330, 220]
//      }
//  ]
//};
//
//myChart.setOption(option);
//})();
//
//
////折线图
//(function(){
//
//	var line = echarts.init(document.getElementById("line"));
//
//	option = {
//  title: {
//      text: '堆叠区域图',
//      x:'center'
//  },
//  tooltip : {
//      trigger: 'axis'
//  },
//
//  toolbox: {
//      feature: {
//          saveAsImage: {}
//      }
//  },
//  grid: {
//      left: '3%',
//      right: '4%',
//      bottom: '3%',
//      containLabel: true
//  },
//  xAxis : [
//      {
//          type : 'category',
//          boundaryGap : false,
//          data : ['周一','周二','周三','周四','周五','周六','周日']
//      }
//  ],
//  yAxis : [
//      {
//          type : 'value'
//      }
//  ],
//  series : [
//
//      {
//          name:'联盟广告',
//          type:'line',
//          stack: '总量',
//          areaStyle: {normal: {}},
//          data:[220, 182, 191, 234, 290, 330, 310]
//      },
//      {
//          name:'视频广告',
//          type:'line',
//          stack: '总量',
//          areaStyle: {normal: {}},
//          data:[150, 232, 201, 154, 190, 330, 410]
//      },
//      {
//          name:'直接访问',
//          type:'line',
//          stack: '总量',
//          areaStyle: {normal: {}},
//          data:[320, 332, 301, 334, 390, 330, 320]
//      },
//      {
//          name:'搜索引擎',
//          type:'line',
//          stack: '总量',
//          label: {
//              normal: {
//                  show: true,
//                  position: 'top'
//              }
//          },
//          areaStyle: {normal: {}},
//          data:[820, 932, 901, 934, 1290, 1330, 1320]
//      }
//  ]
//};
//
//line.setOption(option);
//})();
//
//
////饼状图
//(function(){
//
//	var pie = echarts.init(document.getElementById("pie"));
//
//	option = {
//  title : {
//      text: '饼状图',
//      subtext: '纯属虚构',
//      x:'center'
//  },
//  tooltip : {
//      trigger: 'item',
//      formatter: "{a} <br/>{b} : {c} ({d}%)"
//  },
//  toolbox: {
//      feature: {
//          saveAsImage: {}
//      }
//  },
//  legend: {
//      orient: 'vertical',
//      left: 'left',
//      data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
//  },
//  series : [
//      {
//          name: '访问来源',
//          type: 'pie',
//          radius : '55%',
//          center: ['50%', '60%'],
//          data:[
//              {value:335, name:'直接访问'},
//              {value:310, name:'邮件营销'},
//              {value:234, name:'联盟广告'},
//              {value:135, name:'视频广告'},
//              {value:1548, name:'搜索引擎'}
//          ],
//          itemStyle: {
//              emphasis: {
//                  shadowBlur: 10,
//                  shadowOffsetX: 0,
//                  shadowColor: 'rgba(0, 0, 0, 0.5)'
//              }
//          }
//      }
//  ]
//};
//
//
//pie.setOption(option);
//})();
//
//
////环形图
//(function(){
//
//	var annular = echarts.init(document.getElementById("annular"));
//
//
//	option = {
//
//		title: {
//			text: "环状图",
//			x:'center'
//		},
//
//	    tooltip: {
//	        trigger: 'item',
//	        formatter: "{a} <br/>{b}: {c} ({d}%)"
//	    },
//	    toolbox: {
//	        feature: {
//	            saveAsImage: {}
//	        }
//	    },
//	    legend: {
//	        orient: 'vertical',
//	        x: 'left',
//	        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
//	    },
//	    series: [
//	        {
//	            name:'访问来源',
//	            type:'pie',
//	            radius: ['50%', '70%'],
//	            avoidLabelOverlap: false,
//	            label: {
//	                normal: {
//	                    show: false,
//	                    position: 'center'
//	                },
//	                emphasis: {
//	                    show: true,
//	                    textStyle: {
//	                        fontSize: '30',
//	                        fontWeight: 'bold'
//	                    }
//	                }
//	            },
//	            labelLine: {
//	                normal: {
//	                    show: false
//	                }
//	            },
//	            data:[
//	                {value:335, name:'直接访问'},
//	                {value:310, name:'邮件营销'},
//	                {value:234, name:'联盟广告'},
//	                {value:135, name:'视频广告'},
//	                {value:1548, name:'搜索引擎'}
//	            ]
//	        }
//	    ]
//	};
//
//annular.setOption(option);
//})();
//
