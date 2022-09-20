function getAbnormalTimeString(year,month)
{
	var abnormalTimeString=new Array(); 
	var abnormal_data={"year":year,"month":month};
   var jsonstr_send=JSON.stringify(abnormal_data);//将json对象转换为json字符串
         $.ajax
		 ({ 
		  type:"POST",
         url:"../assets/php/getGraphData2.php",
         async:false,
		 data:{'json':jsonstr_send},//发送json字符串
		  error: function(){
             document.getElementById("hint").innerHTML="请求超时！"  
         }, 
         success:function(result){json_receive =eval('(' + result + ')');} //接受json字符串并将json字符串转换为json对象
        }); 
		return json_receive;
}