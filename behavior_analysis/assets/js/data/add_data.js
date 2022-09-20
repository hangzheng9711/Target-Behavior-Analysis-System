function add()
{
	var addstation={"station_number":station_number.value,"length":len.value,"width":width.value,"abscissa":abscissa.value,"ordinate":ordinate.value,"video_number":video_number.value};
   var jsonstr_send=JSON.stringify(addstation);//将json对象转换为json字符串

         $.ajax
		 ({ 
		  type:"POST",
         url:"../assets/php/addStation.php",
         async:false,
         data:{'json':jsonstr_send},//发送json字符串
		  error: function(){
             alert("请求超时!");  
         }, 
         success:function(result){json_receive =eval('(' + result + ')');} //接受json字符串并将json字符串转换为json对象
        }); 
		if(json_receive.status==0) {window.open("station.html","_self")}
		else alert("添加失败!");
}