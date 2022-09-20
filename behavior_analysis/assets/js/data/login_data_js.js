function login_data(str1,str2)
{ 
   var login={"account":str1,"password":str2};
   var jsonstr_send=JSON.stringify(login);//将json对象转换为json字符串

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
		
		if(json_receive.status==0) {setCookie("account_number_cookie",str1); window.open("index.html","_self");}
		else if(json_receive.status==1) document.getElementById("txtHint").innerHTML="密码输入错误！";
		else document.getElementById("txtHint").innerHTML="账号不存在！";
}