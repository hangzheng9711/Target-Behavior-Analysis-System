function modify_password_data()
{
	var account =getCookie("account_number_cookie");
	var password  = document.getElementById('password');
	var modifypassword={"account":account,"password":password.value};
   var jsonstr_send=JSON.stringify(modifypassword);//将json对象转换为json字符串

         $.ajax
		 ({ 
		  type:"POST",
         url:"../assets/php/isPassword.php",
         async:false,
         data:{'json':jsonstr_send},//发送json字符串
		    error: function(){
             document.getElementById("txtHint").innerHTML="请求超时！"  
         }, 
         success:function(result){json_receive =eval('(' + result + ')');} //接受json字符串并将json字符串转换为json对象
        });
		if(json_receive.status==0) window.open("modify_password_1.html","_self");
		else document.getElementById("txtHint").innerHTML="旧密码输入错误！";
}