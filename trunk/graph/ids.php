<?php
$request = "http://search.twitter.com/search.json?q=%23euronews&rpp=100&include_entities=true&result_type=mixed";
$response = file_get_contents($request);
$ids="";
$ok = json_decode($response,true);
$id=array();
		foreach($ok['results'] as $p){
			
			$p['from_user_id']=$id;
		}print_r($id);
$ids=join(" ",$id);
echo $ids;
?>