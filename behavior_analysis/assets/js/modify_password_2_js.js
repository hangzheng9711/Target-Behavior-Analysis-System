function procedure_modify_password_2()
{
	var x=document.getElementById("second_div").innerHTML;
	if(x>0)
	{
		x--;
		document.getElementById("second_div").innerHTML=x;
	}
	else
	{
		window.location="login.html";
	}
	setTimeout("procedure_modify_password_2()",1000);
}