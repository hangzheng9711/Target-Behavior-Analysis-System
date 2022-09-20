<?php
if($_POST['json'])//接受字符串
{
 $jsonstr=(array)(json_decode($_POST['json']));//
 $con = mysql_connect("localhost", "root", "zh43907019");
	if (!$con)
	{
		die('Could not connect: ' . mysql_error());
	}
	mysql_select_db("behavior_analysis", $con);//连接数据库
  //将每个字段提取出来
  
    $station_number=$jsonstr['station_number'];
    $video_number=$jsonstr['video_number'];
    $abscissa=$jsonstr['abscissa'];
    $ordinate=$jsonstr['ordinate'];
    $length=$jsonstr['length'];
    $width=$jsonstr['width'];

  $sql="INSERT INTO station
(station_number, video_number, abscissa, ordinate, length,width) 
VALUES ('".$station_number."','".$video_number."','".$abscissa."','".$ordinate."','".$length."','".$width."') ";

if(mysql_query($sql)) $status=0;//存储成功
	else $status=1;//存储失败
	$result = json_encode(array("status" => $status)); //将php数组封装为json字符串
	echo $result;//发送json字符串
	mysql_close($con);
}

?>