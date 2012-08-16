<!DOCTYPE HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Highcharts Example</title>
 <style type="text/css">
</style>
<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDlmZn_jhR4UK9ydbiKf4Mtn9GDuZbLO5w&sensor=true">
</script>
<script src="Highcharts/js/highcharts.js"></script>
<script src="Highcharts/js/modules/exporting.js"></script>
<script type="text/javascript" src="JSPlacemaker.js"></script>
<script type="text/javascript">
function all() {
var array=[];
var dateArray = [];
var user_ids=[];
var dateString;
$.getJSON("http://search.twitter.com/search.json?callback=?&q=olympics&rpp=5",
  function(data){

$.each(data.results, function(i, item){
  var user=item.from_user;
  var user_id=item.from_user_id;
  var date=item.created_at;
  var created_at=new Date(item.created_at);
  var month = created_at.getMonth();
  var day = created_at.getDate();
  var year = created_at.getFullYear();
  var created= day +'/'+ month+ '/'+ year;
create_array(date,user,user_id);
 });
				  
function create_array(x,y,z){
	 array.push({date:x,username:y,user_id:z});
	
				}

//	console.log(array);


for (var i in array) {
	user_ids.push(array[i].user_id); 
		}
var ids=user_ids.join();
//		console.log (ids);

lookup_locations(user_ids);
for (var i in array) {
	dateArray.push(array[i].date);
		}  
highchart(dateArray);
});
function lookup_locations(user_ids){
	$.getJSON("http://api.twitter.com/1/users/lookup.json?user_id="+user_ids+"&callback=?",
  function(data){

$.each(data, function(i, item){
   var location=item.location; //console.log(location);
  array[i].location = location;
		});
  
//console.log(array);
});//get.json
	
}//lookup function

/*	var result = array.filter(//////////////count values
function (value) {
return value === 'london';
}).length;*/

  }//function all
  
function highchart(dateString){
var chart;
chart = new Highcharts.Chart({
chart: {
	renderTo: 'container',
	type: 'line',
	marginRight: 130,
	marginBottom: 25,
	width:600
},
title: {
	text: 'Monthly Average Temperature',
	x: -20 //center
},
subtitle: {
	text: 'Source: WorldClimate.com',
	x: -20
},
xAxis: {
	categories: dateString
},
yAxis: {
	title: {
		text: 'Temperature (°C)'
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
			this.x +': '+ this.y +'°C';
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
	name: 'Tokyo',
	data: [7.0, 6.9, 9.5, 14.5, 18.2]
}, {
	name: 'New York',
	data: [-0.2, 0.8, 5.7, 11.3, 17.0]
}, {
	name: 'Berlin',
	data: [-0.9, 0.6, 3.5, 8.4, 13.5]
}, {
	name: 'London',
	data: [3.9, 4.2, 5.7, 8.5, 11.9]
}]
});
  }

function codeAddress(){
var user_ids=[];
var array=[];
var ids;
$.getJSON("http://search.twitter.com/search.json?q=%23euronews&rpp=10&include_entities=true&result_type=mixed&callback=?",
function(data){
$.each(data.results, function(i, item){
var user=item.from_user;
var user_id=item.from_user_id;
var date=item.created_at;
var profile_img=item.profile_image_url;
var text=item.text;
var url=(item.entities.urls.length > 0 ? item.entities.urls[0].url : '');
	if ((url).length==''){
		
}
create_array(user,user_id,date,profile_img,url);
 });
function create_array(a,b,c,d,e){
	array.push({user:a,user_id:b,date:c,profile_img:d,url:e});
				}//console.log(array);

for (var i in array) {
    user_ids.push(array[i].user_id); 
  // console.log(array[i].user_id);
				}
		ids=user_ids.join();	  


//console.log(ids);	
var geocoder = new google.maps.Geocoder();;
var mapOptions = {
      center: new google.maps.LatLng(35.74651,-39.46289), 
      zoom: 2,
       mapTypeId: google.maps.MapTypeId.ROADMAP
};
// added this 
var bounds = new google.maps.LatLngBounds();
// create the map
var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

$.getJSON("http://api.twitter.com/1/users/lookup.json?user_id="+ids+"&callback=?", function (data) {
  $.each(data, function (i, item) {
    var screen_name = item.screen_name;
    var img = item.profile_image_url;
    var location = item.location;
	var contentString = screen_name;
    geocoder.geocode({
      address: location
    }, function (response, status) {
      if (status == google.maps.GeocoderStatus.OK) {
        var x = response[0].geometry.location.lat(),
          y = response[0].geometry.location.lng(); 
        var myLatLng = new google.maps.LatLng(x, y);
        var marker = new google.maps.Marker({
          icon: img,
          title: screen_name,
          map: map,
          position: myLatLng
        });
		
		var infowindow = new google.maps.InfoWindow({
    	content: contentString
			});
		google.maps.event.addListener(marker, 'click', function() {
  		infowindow.open(map,marker);
			});
        bounds.extend(myLatLng);
      } /*else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {    
            setTimeout(function() {
                geocoder.geocode({address:location});
            }, 200);}*/else {
        console.log("Geocode was not successful for the following reason: " + status);
      }
    });
  });
 // map.fitBounds(bounds);
});
});//get.JSON+query=euronews
}
</script>
</head>
<body>
<div id="container" style="width: 50%; height: 500px; float:left; border:solid #FCF 1px;"></div>
<div id="map_canvas" style="width:40%; float:left; margin-left:10px; border:solid #FCF 1px; height:500px"></div>
<div style="clear:both"><input type="button" value="Get" onclick="all(); codeAddress();"></div>

</body>
</html>