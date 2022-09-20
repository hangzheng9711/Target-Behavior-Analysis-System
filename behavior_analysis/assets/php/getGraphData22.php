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
	
	$qid = mysql_query("select count(DISTINCT station_number) as total from station ");
	$res = mysql_fetch_array($qid);
	$station_count = $res['total'];  //工位号个数
	for($i=1;$i<=$station_count;$i++)
	{
		$total_time[$i]=0;  //每个工位在指定时间内的总工作时间
		$abnormal_time[$i]=0;  //每个工位在指定时间内的异常工作时间
	}
	if($jsonstr['month']==0)
	{
		$year=$jsonstr['year'];
		$start = mktime(0,0,0,1,1,$year);
		$year=$year+1;
		$end = mktime(0,0,0,1,1,$year);
		$sql="SELECT * FROM behavior WHERE UNIX_TIMESTAMP(start_time)>=$start and UNIX_TIMESTAMP(start_time)<$end ORDER BY start_time";
		$result = mysql_query($sql);
		while($row = mysql_fetch_array($result))
		{
			$total_time[$row['station_number']]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
			if($row['feature']=='bad') $abnormal_time[$row['station_number']]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
		}

	}
	else
	{
		$year=$jsonstr['year'];
		$month=$jsonstr['month'];
		$start = mktime(0,0,0,$month,1,$year); //转成时间戳
		$month=$month+1;
		$end = mktime(0,0,0,$month,1,$year);
		$sql="SELECT * FROM behavior WHERE UNIX_TIMESTAMP(start_time)>=$start and UNIX_TIMESTAMP(start_time)<$end ORDER BY start_time";
		$result = mysql_query($sql);
		while($row = mysql_fetch_array($result))
		{
			$total_time[$row['station_number']]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
			if($row['feature']=='bad') $abnormal_time[$row['station_number']]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
		}
	}
	
	$res=array();
	for($i=1;$i<=$station_count;$i++)
	{
		$json_data = array ('station_count'=>$i,'abnormal_time'=> $abnormal_time[$i],'total_time'=> $total_time[$i]);
		array_push($res,$json_data);
	}
	
	$result = json_encode($res); //将php数组封装为json字符串
	echo $result;//发送json字符串

	mysql_close($con);
}

?>