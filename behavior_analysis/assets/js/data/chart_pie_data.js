function get_ab_proportion()
{
         $.ajax
		 ({ 
		  type:"POST",
         url:"../assets/php/getPieData.php",
         async:false,
		  error: function(){
             document.getElementById("hint").innerHTML="请求超时！"  
         }, 
         success:function(result){json_receive =eval('(' + result + ')');} //接受json字符串并将json字符串转换为json对象
        }); 
		return json_receive.ab_proportion;
}