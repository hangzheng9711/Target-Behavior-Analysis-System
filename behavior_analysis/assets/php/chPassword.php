<?php
if ($_POST['json'])//接受json字符串
{
	$jsonstr=(array)(json_decode($_POST['json']));//将json字符串解析为php数组
	
	$con = mysql_connect("localhost", "root", "zh43907019");
	if (!$con)
	{
		die('Could not connect: ' . mysql_error());
	}
	mysql_select_db("behavior_analysis", $con);//连接数据库
	
	$sql="UPDATE admin SET password='".$jsonstr['chpassword']."' WHERE account='".$jsonstr['chaccount']."'";
	if(mysql_query($sql)) $status=0;
	else $status=1;
	$result = json_encode(array("status" => $status)); //将php数组封装为json字符串
	echo $result;//发送json字符串
	
	mysql_close($con);
}

?>