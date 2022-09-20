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
	
	$year=$jsonstr['year'];
	$start = mktime(0,0,0,1,1,$year);
	$year=$year+1;
	$end = mktime(0,0,0,1,1,$year);
	$sql="SELECT * FROM behavior WHERE UNIX_TIMESTAMP(start_time)>=$start and UNIX_TIMESTAMP(start_time)<$end ORDER BY start_time";
	$result = mysql_query($sql);

	while($row = mysql_fetch_array($result))
	{
		$behavior[]=$row;
	}
	
	$result = json_encode($behavior); //将php数组封装为json字符串
	echo $result;//发送json字符串
	mysql_close($con);
}
?>