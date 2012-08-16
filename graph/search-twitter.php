<?php
include 'connect.php';
if(isset($_POST['val']) && !empty($_POST['val'])){
$val=$_POST['val'];
function search_twitter($val){
$request = "http://search.twitter.com/search.json?q=".rawurlencode($val);
$response = file_get_contents($request); 
$jsonobj = json_decode($response);
 
if($jsonobj != null){
 
$con = mysql_connect('localhost', 'root', 'kolokou8kia');
 
if (!$con){
die('Could not connect: ' . mysql_error());
}
 
foreach($jsonobj->results as $item){
 
$id = $item->id;
$created_at = $item->created_at;
$created_at = strtotime($created_at);
$mysqldate = date('Y-m-d H:i:s',$created_at);
$from_user = mysql_real_escape_string($item->from_user);
$from_user_id = $item->from_user_id;
$text = mysql_real_escape_string($item->text);
$source = mysql_real_escape_string($item->source);
$profile_image_url = mysql_real_escape_string($item->profile_image_url);
$to_user_id = $item->to_user_id;
if($to_user_id==""){ $to_user_id = 0; }
 
mysql_select_db("twitter", $con);
$query = "INSERT into tweets VALUES ('',$id,'$mysqldate','$from_user',$from_user_id,'$text','$source','','$profile_image_url',$to_user_id,'$val')";
$result = mysql_query($query);
 
}}}

function select_ids(){
$ids=array(); 
$query2 = "SELECT from_user_id FROM tweets";
$result2 = mysql_query($query2);
while($row = mysql_fetch_array($result2))  
{
    $ids[]=$row["from_user_id"]; 
}

/*$separate=implode(",",$ids); echo($separate);
*/

foreach($ids as $id_number=>$id){
$request = "http://api.twitter.com/1/users/lookup.json?user_id=".$id."";
$response = file_get_contents($request); 
$ok = json_decode($response,true);
		foreach($ok as $p){
			$location=$p['location'];
			$query=mysql_query("UPDATE tweets SET location='$location' 
			WHERE from_user_id='$id'");
						}
								}		
}
search_twitter($val);
select_ids();
}


?>