<?php

$con = mysql_connect("localhost", "root", "zh43907019");
	if (!$con)
	{
		die('Could not connect: ' . mysql_error());
	}
	mysql_select_db("behavior_analysis", $con);


	$sql="SELECT * FROM station";
	$result =mysql_query($sql);

while($rows = mysql_fetch_array($result))
{
$array[]= $rows;
}

/*添加行为信息数据
for($i=1;$i<=8;$i++)
{
	for($j=1;$j<=3;$j++)
	{
	if($i%2==0) $feature="good";
	else $feature="bad";
	$start0=strtotime("2017-02-21 16:00:00");
	$start=$start0+60*$i;
	$stop0=strtotime("2017-02-21 17:00:00");
	$stop=$stop0+180*$i;
	$start1= date('Y-m-d H:i:s', $start);
	$stop1= date('Y-m-d H:i:s', $stop);
  $sql="INSERT INTO behavior
(station_number, feature, start_time, stop_time) 
VALUES ('".$i."','".$feature."','".$start1."','".$stop1."') ";
mysql_query($sql);
	}
}*/


echo json_encode($array);

mysql_close($con);

?>