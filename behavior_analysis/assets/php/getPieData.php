<?php

	$con = mysql_connect("localhost", "root", "zh43907019");
	if (!$con)
	{
		die('Could not connect: ' . mysql_error());
	}
	mysql_select_db("behavior_analysis", $con);//连接数据库
	
	$abnormal_time=0;  //异常工作时间(秒)
	$flag=0; //判断数据库中是否有数据
	
	$sql="SELECT * FROM behavior WHERE feature='bad'";
	$result = mysql_query($sql);
	while($row = mysql_fetch_array($result))
	{
		$abnormal_time+=strtotime($row['stop_time'])-strtotime($row['start_time']);
		$flag=1;
	}
	
	$total_time=$abnormal_time; //总工作时间(秒)
	
	$sql2="SELECT * FROM behavior WHERE feature='good'";
	$result2 = mysql_query($sql2);
	while($row2 = mysql_fetch_array($result2))
	{
		$total_time+=strtotime($row2['stop_time'])-strtotime($row2['start_time']);
		$flag=1;
	}
	if($flag==0) $ab_proportion=-1;  //如果数据库没有数据，$ab_proportion=-1
	else $ab_proportion=$abnormal_time/$total_time; //异常工作时间占总工作时间比例
	
	$result = json_encode(array("ab_proportion" => $ab_proportion)); //将php数组封装为json字符串
	echo $result;//发送json字符串
	
	mysql_close($con);
	
?>