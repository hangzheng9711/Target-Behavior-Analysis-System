function modify_password_1_data()
{
	if(password.value==""||password1.value=="")
	document.getElementById("txtHint").innerHTML="新密码不能为空！";
	else if(password.value!=password1.value)
	document.getElementById("txtHint").innerHTML="新密码输入不一致！";
	else modify_password_11_data();
}
function modify_password_11_data()
{
	var account =getCookie("account_number_cookie");
	var modifypassword={"chaccount":account,"chpassword":password.value};
   var jsonstr_send=JSON.stringify(modifypassword);//将json对象转换为json字符串

         $.ajax
		 ({ 
		  type:"POST",
         url:"../assets/php/chPassword.php",
         async:false,
         data:{'json':jsonstr_send},//发送json字符串
		    error: function(){
             document.getElementById("txtHint").innerHTML="请求超时！"  
         }, 
         success:function(result){json_receive =eval('(' + result + ')');} //接受json字符串并将json字符串转换为json对象
        });
		if(json_receive.status==0) procedure_modify_password();
		else document.getElementById("txtHint").innerHTML="修改密码失败！请重试！";
}