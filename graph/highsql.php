<?php
include'connect.php';
/*$uk_array = array('Liverpool','London','London UK','UK','London',
'Dublin, Ireland','Manchester','Norwich','United Kingdom','Norwich','Duplin','England','ENGLAND',
'united kingdom');

$string = '';

foreach($uk_array as $term=>$i) {
    $string = $string."location LIKE '%".$i."%' OR ";
}
$string = substr($string, 0, -5);*/

$UK_30th="SELECT COUNT(location)as location FROM tweets WHERE location IN ('Duplin','United Kingdom','England','norwich',
'Manchester','Wandsworth','West Midlands','Redditch','Birmingham','dover','Chippenham, Wiltshire','Belfast, N.I',
'belfast','Inverness','Ipswich & Norwich','norwich','Ipswich','London Town!!','Belfast,Northern Ireland','Surrey England',
'Southampton') AND date(date)='2012-07-30'";
$uk_result_30= mysql_query($UK_30th) or die(mysql_error());


$UK_29th="SELECT COUNT(location)as location FROM tweets WHERE location IN ('Duplin','United Kingdom','England','norwich',
'Manchester','Wandsworth','West Midlands','Redditch','Birmingham','dover','Chippenham, Wiltshire','Belfast, N.I',
'belfast','Inverness','Ipswich & Norwich','norwich','Ipswich','London Town!!','Belfast,Northern Ireland','Surrey England',
'Southampton','Duplin, Ireland') AND date(date)='2012-07-29'";
$uk_result_29 = mysql_query($UK_29th) or die(mysql_error());


?>
<!DOCTYPE HTML>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Highcharts Example</title>
        <link href="css/bootstrap.css" rel="stylesheet">
        <style>
		.search{
			width:280px; margin-left:auto;margin-right:auto;margin-top:10px;
			}
		input[type="submit"]{
		  display: inline-block;
		  height: 28px;
		  padding: 4px;
		  margin-bottom: 9px;
		  font-size: 13px;
		  line-height: 18px;
		  color: #555555;
}
		</style>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
        <script src="Highcharts/js/highcharts.js"></script>
		<script src="Highcharts/js/modules/exporting.js"></script>
		<script type="text/javascript">
		 var chart;
	 function search(){
	  var value = $('#box').val();
	  if (value!==""){
			$.ajax({
			type:'post',
			url:'search-twitter.php',
			data: {vali:value}});}
				
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'line',
                marginRight: 130,
                marginBottom: 25,
				width: 700
            },
            title: {
                text: '3 Day tweets for keyword: "olympics"',
                x: -20 //center
            },
            subtitle: {
                text: 'Source: Twitter API',
                x: -20
            },
            xAxis: {
                categories: ['2012-07-30','2012-07-29','2012-07-27']
            },
            yAxis: {
                title: {
                    text: 'Tweets'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        this.x +' '+'('+ this.y +' Tweets)';
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 100,
                borderWidth: 0
            },
            series: [{
                name: 'England',
                data: [<?php while($row = mysql_fetch_array($uk_result_30)){
						echo  $row['location'];} ?>,<?php while($row = mysql_fetch_array($uk_result_29)){
						echo  $row['location'];} ?>, 9.5]
            }, {
                name: 'USA',
                data: [-0.2, 0.8, 5.7]
            }]
        });
		

	 }
		</script>
	</head>
	<body>
<div id="container" style="min-width:200px; height: 300px; margin: 0 auto"></div>
	<div class="search">
    
    <input type="text" class="input-medium search-query" id="box">
    <input class="btn" onClick="search()" id="get" type="submit" value="Submit">
   
    </div>
	</body>
</html>