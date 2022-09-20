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
	if($jsonstr['month']==0)
	{
		for($i=1;$i<=12;$i++)
		{
			$total_time[$i]=0;
			$abnormal_time[$i]=0;
		}
		$year=$jsonstr['year'];
		$start = mktime(0,0,0,1,1,$year);
		$year=$year+1;
		$end = mktime(0,0,0,1,1,$year);
		$sql="SELECT * FROM behavior WHERE station_number='".$jsonstr['station_number']."' and UNIX_TIMESTAMP(start_time)>=$start and UNIX_TIMESTAMP(start_time)<$end ORDER BY start_time";
		
		$result = mysql_query($sql);
		$i=0;
		while($row = mysql_fetch_array($result))
		{
			$month=	idate("m",strtotime($row['start_time']));
			$total_time[$month]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
			if($row['feature']=='bad') $abnormal_time[$month]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
		}

	}
	else
	{
		for($i=1;$i<=31;$i++)
		{
			$total_time[$i]=0;
			$abnormal_time[$i]=0;
		}
		$year=$jsonstr['year'];
		$month=$jsonstr['month'];
		$start = mktime(0,0,0,$month,1,$year);
		$month=$month+1;
		$end = mktime(0,0,0,$month,1,$year);
		$sql="SELECT * FROM behavior WHERE station_number='".$jsonstr['station_number']."' and UNIX_TIMESTAMP(start_time)>=$start and UNIX_TIMESTAMP(start_time)<$end ORDER BY start_time";
		$result = mysql_query($sql);
		while($row = mysql_fetch_array($result))
		{
			$day=idate("d",strtotime($row['start_time']));
			$total_time[$day]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
			if($row['feature']=='bad') $abnormal_time[$day]+=strtotime($row['stop_time'])-strtotime($row['start_time']);
		}
	}
	
	$res=array();
	if($jsonstr['month']==0)
	{
		for($i=1;$i<=12;$i++)
	{
		$json_data = array ('abnormal_time'=>$abnormal_time[$i],'total_time'=> $total_time[$i]);
		array_push($res,$json_data);
	}
	}
	else
	{
		for($i=1;$i<=31;$i++)
	{
		$json_data = array ('abnormal_time'=>$abnormal_time[$i],'total_time'=> $total_time[$i]);
		array_push($res,$json_data);
	}
	}
	
	$result = json_encode($res); //将php数组封装为json字符串
	echo $result;//发送json字符串

	mysql_close($con);
}

?>