	$.ajax
		 ({ 
		  type:"POST",
         url:"../assets/php/getStation_all.php",
         async:false,
		  error: function(){
             alert("请求超时!");  
         }, 
         success:function(result){json_receive =eval('(' + result + ')');} //接受json字符串并将json字符串转换为json对象
        }); 
		
	var count=Object.keys(json_receive).length;
		
	for(i=0;i<count;i++)
	{
		if((i+1)%2==0) document.write('<tr class="alt">');
		else document.write("<tr>");
		document.write('<td height="50">'+json_receive[i].station_number+"</td>");
		document.write("<td>"+json_receive[i].video_number+"</td>");
		document.write("<td>"+json_receive[i].abscissa+"</td>");
		document.write("<td>"+json_receive[i].ordinate+"</td>");
		document.write("<td>"+json_receive[i].length+"</td>");
		document.write("<td>"+json_receive[i].width+"</td>");
       document.write("</tr>");
	} 