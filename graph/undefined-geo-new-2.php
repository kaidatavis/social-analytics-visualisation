<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Highcharts Example</title>
    <style type="text/css">
	
	body{
    font-family::'Segoe UI',Arial,Helvetica,sans-serif;
    font-size: 14px;}
	.banner{
		width:1252px;
		height:auto;}
	#user-banner{
		font-size:18px;
		background-color:#733171;
		color:#FFFFFF;
		width:318px;
		visibility:hidden;
		float:left;		
	}
	#news-banner{
		font-size:18px;
		background-color:#733171;
		color:#FFFFFF;
		width:318px;
		visibility:hidden;		
		float:right;
	}
	iframe {
    width: 800px;
    height: 520px;
    border: none;
    position:absolute;
	top:100px;
	left:100px;
	z-index:1;
}


table.user-tweets td.user{
	font-weight:700;}
	table.news-tweets td.user{
	font-weight:700;}
	
.info{float:left; margin-left:10px;}
</style>
    <script type="text/javascript" src="jquery-1.7.2.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDlmZn_jhR4UK9ydbiKf4Mtn9GDuZbLO5w&sensor=true">        
    </script>
    <script type="text/javascript" src="JSPlacemaker.js"></script>
    <script type="text/javascript">
/*	$(document).ready(function(){
	$('#user-tweets').on('mouseenter', "a", function() {
    $(this).append($("<iframe src='"+this.href+"'></iframe>"));
}).on('mouseleave', 'a', function () {
    $(this).find("iframe:last").remove();
});
});
*/            function user_tweets(value) {
			user_ids_2 = [];
            array_2 = [];
            var ids_2;
				$("#user-tweets").html("");
                $.getJSON("http://search.twitter.com/search.json?q="+value+"&rpp=10&include_entities=true&result_type=mixed&callback=?",

                function (data) {
                    $.each(data.results, function (i, item) {
                        var user = item.from_user;
                        var user_id = item.from_user_id;
                        var date = item.created_at;
                        var profile_img = item.profile_image_url;
                        var text = item.text;
                        var url = (item.entities.urls.length > 0 ? item.entities.urls[0].url : '');
                        create_array(user, user_id, date, profile_img, text, url);
                    });

                    function create_array(a, b, c, d, e, f) {
                        array_2.push({
                            user: a,
                            user_id: b,
                            date: c,
                            profile_img: d,
                            text: e,
                            url: f
                        });
                    }
                    //console.log(array);

                    for (var i in array_2) {
                        user_ids_2.push(array_2[i].user_id);
                        // console.log(array[i].user_id);
                    }
                    ids_2 = user_ids_2.join();

                    //console.log(ids);
                    search_location(ids_2, array_2);
                });
            }

            function search_location(ids_2, array_2) {
                $.getJSON("http://api.twitter.com/1/users/lookup.json?user_id=" + ids_2 + "&callback=?", function (data) { //first attempt to find locations
                    $.each(data, function (i, item) {
                        var location = item.location;

                        array_2[i].location = location; //locations are added but some are empty

                    });
                    var userLocationMap = {};
                    for (var i = 0; i < array_2.length; i++) {
                        if (array_2[i].location) {
                            userLocationMap[array_2[i].user] = array_2[i].location;
                        }
                    }

                    for (var i = 0; i < array_2.length; i++) {
                        if (!array_2[i].location) {
                            array_2[i].location = userLocationMap[array_2[i].user];
                        }
                    }
                    geo(array_2);
                });
            }

            function geo(array_2) {
                loc = [];
                console.log(array_2);

                for (var i in array_2) {
                    var location = array_2[i].location;
                    var user = array_2[i].user;
                    var date = array_2[i].date;
                    var profile_img = array_2[i].profile_img;
                    var text = array_2[i].text;
                    var url = array_2[i].url;	
                    geocode(user, date, profile_img, text, url, location);
                }					
                var mapOptions = {
                    center: new google.maps.LatLng(35.74651, - 39.46289),
                    zoom: 2,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                // added this 
                var bounds = new google.maps.LatLngBounds();
                // create the map
                var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
                function geocode(user, date, profile_img, text, url, location) {
var templates = [];
templates[0] = '<div><div></div><h2 class="firstHeading"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a>'+user+'</h2><div>'+text+'</div><div><p><a href="' + url + '"target="_blank">'+url+'</a></p></div><p>Date Posted- '+date+'</p></div>';
templates[1] = '<table class="user-tweets" width="320" border="0"><tr><td  class="user"  colspan="2" rowspan="1">'+user+'</td></tr><tr><td width="59" rowspan="2" valign="top"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'<br /><a href="' + url + '"target="_blank">'+url+'</a></td></tr></table><hr>';
templates[2] = '<div><div></div><h2 class="firstHeading"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a>'+user+'</h2><div>'+text+'</div><div><p><a href="' + url + '"target="_blank">'+url+'</a></p></div><p>Date Posted- '+date+'</p></div>';
templates[3] = '<table class="user-tweets" width="320" border="0"><tr><td class="user"  colspan="2" rowspan="1">'+user+'</td></tr><tr><td width="59" rowspan="2" valign="top"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'<br /><a href="' + url + '"target="_blank">'+url+'</a></td></tr></table><hr>';
templates[4] = '<div><div></div><h2 class="firstHeading"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a>'+user+'</h2><div>'+text+'</div><div><p><a href="' + url + '"target="_blank">'+url+'</a></p></div><p>Date Posted- '+date+'</p></div>';
templates[5] = '<table class="user-tweets" width="320" border="0"><tr><td class="user"  colspan="2" rowspan="1">'+user+'</td></tr><tr><td width="59" rowspan="2" valign="top"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'<br /><a href="' + url + '"target="_blank">'+url+'</a></td></tr></table><hr>';
templates[6] = '<div><div></div><h2 class="firstHeading"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a>'+user+'</h2><div>'+text+'</div><div><p><a href="' + url + '"target="_blank">'+url+'</a></p></div><p>Date Posted- '+date+'</p></div>';
templates[7] = '<table class="user-tweets" width="320" border="0"><tr><td class="user"  colspan="2" rowspan="1">'+user+'</td></tr><tr><td width="59" rowspan="2" valign="top"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'<br /><a href="' + url + '"target="_blank">'+url+'</a></td></tr></table><hr>';

var geocoder = new google.maps.Geocoder();
                    geocoder.geocode({
                        address: location
                    }, function (response, status) {
                        if (status == google.maps.GeocoderStatus.OK) {
                            var x = response[0].geometry.location.lat(),
                                y = response[0].geometry.location.lng();
                            var myLatLng = new google.maps.LatLng(x, y);
                            var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });


						  var infowindow = new google.maps.InfoWindow({
							  content: templates[0].replace('user',user).replace('text',text).replace('url',url).replace('date',date)
						  });
						  var $tweet = $(templates[1].replace('%user',user).replace(/%profile_img/g,profile_img).replace('%text',text).replace('%url',url));
	  					  $('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
						  $('#user-tweets').css("overflow","scroll").append($tweet);
						  function openInfoWindow() {
							  infowindow.open(map, marker);
						  }
						  google.maps.event.addListener(marker, 'click', openInfoWindow);
						  $tweet.find(".user").on('click', openInfoWindow);
                            bounds.extend(myLatLng);
                        } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                            setTimeout(function () {
                                geocode(user,date,profile_img,text,url,location);
                            }, 500);
                        } else {
                            Placemaker.getPlaces(text, function (o) {
                                console.log(o);
                                if ($.isArray(o.match)) {
                                    if (o.match[0].place.name == "Europe" || o.match[0].place.name == "United States") {
                                        var latitude = o.match[1].place.centroid.latitude;
                                        var longitude = o.match[1].place.centroid.longitude;
                                        var myLatLng = new google.maps.LatLng(latitude, longitude);
                                        var marker = new google.maps.Marker({
                                            icon: profile_img,
                                            title: user,
                                            map: map,
                                            position: myLatLng
                                        });


						  var infowindow = new google.maps.InfoWindow({
							  content: templates[2].replace('user',user).replace('text',text).replace('url',url).replace('date',date)
						  });
						  var $tweet = $(templates[3].replace('%user',user).replace(/%profile_img/g,profile_img).replace('%text',text));
  	  					  $('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
						  $('#user-tweets').css("overflow","scroll").append($tweet);
						  function openInfoWindow() {
							  infowindow.open(map, marker);
						  }
						  google.maps.event.addListener(marker, 'click', openInfoWindow);
						  $tweet.find(".user").on('click', openInfoWindow);
                                        bounds.extend(myLatLng);
                                    }
                                    if ($.isArray(o.match)) {
                                        if (o.match[0].place.name !== "Europe") {
                                            var latitude = o.match[0].place.centroid.latitude;
                                            var longitude = o.match[0].place.centroid.longitude;
                                            var myLatLng = new google.maps.LatLng(latitude, longitude);
                                            var marker = new google.maps.Marker({
                                                icon: profile_img,
                                                title: user,
                                                map: map,
                                                position: myLatLng
                                            });


						  var infowindow = new google.maps.InfoWindow({
							  content: templates[4].replace('user',user).replace('text',text).replace('url',url).replace('date',date)
						  });
						  var $tweet = $(templates[5].replace('%user',user).replace(/%profile_img/g,profile_img).replace('%text',text));
  	  					  $('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
						  $('#user-tweets').css("overflow","scroll").append($tweet);
						  function openInfoWindow() {
							  infowindow.open(map, marker);
						  }
						  google.maps.event.addListener(marker, 'click', openInfoWindow);
						  $tweet.find(".user").on('click', openInfoWindow);

                                            bounds.extend(myLatLng);
                                        }
                                    }
                                }
                                if (!$.isArray(o.match) && o.error !== "no locations found") {
                                    var latitude = o.match.place.centroid.latitude;
                                    var longitude = o.match.place.centroid.longitude;
                                    var myLatLng = new google.maps.LatLng(latitude, longitude);
                                    var marker = new google.maps.Marker({
                                        icon: profile_img,
                                        title: user,
                                        map: map,
                                        position: myLatLng
                                    });


						  var infowindow = new google.maps.InfoWindow({
							  content: templates[6].replace('user',user).replace('text',text).replace('url',url).replace('date',date)
						  });
						  var $tweet = $(templates[7].replace('%user',user).replace(/%profile_img/g,profile_img).replace('%text',text));
  	  					  $('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
						  $('#user-tweets').css("overflow","scroll").append($tweet);
						  function openInfoWindow() {
							  infowindow.open(map, marker);
						  }
						  google.maps.event.addListener(marker, 'click', openInfoWindow);
						  $tweet.find(".user").on('click', openInfoWindow);
                                    
                                    bounds.extend(myLatLng);
                                }
                            });

                        }
                    });
                }
            }

            function news_tweets(value) {
			user_ids = [];
            array = [];
            var ids;
				$("#news-tweets").html("");
                $.getJSON("https://api.twitter.com/1/statuses/user_timeline.json?include_entities=true&include_rts=false&screen_name="+value+"&count=10&callback=?",

                function (data) { //console.log(data);
                    var len = data.length;
                    for (var i = 0; i < len; ++i) {
                        var user = data[i].user.name;
                        var date = data[i].created_at;
                        var profile_img = data[i].user.profile_image_url;
                        var text = data[i].text;
                        var url = (data[i].entities.urls.length > 0 ? data[i].entities.urls[0].url : '');
                        create_array(user, date, profile_img, text,url);
                    }
                    function create_array(a, b, c, d, e) {
                        array.push({
                            user: a,
                            date: b,
                            profile_img: c,
                            text: d,
							url:e
                        });
                    }
                    placemaker_news_tweets(array);
                });
            }

            function placemaker_news_tweets(array) {

                console.log(array);

                for (var i in array) {
                    var user = array[i].user;
                    var date = array[i].date;
                    var profile_img = array[i].profile_img;
                    var text = array[i].text;
					var url=array[i].url;
                    getLocation(user, date, profile_img, text,url);
                }
                var mapOptions = {
                    center: new google.maps.LatLng(35.74651, - 39.46289),
                    zoom: 2,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                // added this 
                var bounds = new google.maps.LatLngBounds();
                // create the map
                var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

                function getLocation(user, date, profile_img, text,url) {
				var templates = [];
templates[0] = '<div><div></div><h2 class="firstHeading"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a>'+user+'</h2><div>'+text+'</div><div><p><a href="' + url + '"target="_blank">'+url+'</a></p></div><p>Date Posted- '+date+'</p></div>';
templates[1] = '<table class="news-tweets" width="320" border="0"><tr><td class="user"  colspan="2" rowspan="1">'+user+'</td></tr><tr><td width="59" rowspan="2" valign="top"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'<br /><a href="' + url + '"target="_blank">'+url+'</a></td></tr></table><hr>';
templates[2] = '<div><div></div><h2 class="firstHeading"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a>'+user+'</h2><div>'+text+'</div><div><p><a href="' + url + '"target="_blank">'+url+'</a></p></div><p>Date Posted- '+date+'</p></div>';
templates[3] = '<table class="news-tweets" width="320" border="0"><tr><td class="user"  colspan="2" rowspan="1">'+user+'</td></tr><tr><td width="59" rowspan="2" valign="top"><a href="'+profile_img+'" target="_blank"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'<br /><a href="' + url + '"target="_blank">'+url+'</a></td></tr></table><hr>';
							var geocoder = new google.maps.Geocoder();
                    Placemaker.getPlaces(text, function (o) {
                        console.log(o);
                        if (!$.isArray(o.match)) {
                            var latitude = o.match.place.centroid.latitude;
                            var longitude = o.match.place.centroid.longitude;
                            var myLatLng = new google.maps.LatLng(latitude, longitude);
                            var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });

						  var infowindow = new google.maps.InfoWindow({
							  content: templates[0].replace('profile_img',profile_img).replace('user',user).replace('text',text).replace('url',url).replace('date',date)
						  });
						  var $tweet = $(templates[1].replace('%user',user).replace(/%profile_img/g,profile_img).replace('%text',text).replace('%url',url));
	  					  $('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
						  $('#news-tweets').css("overflow","scroll").append($tweet);
						  function openInfoWindow() {
							  infowindow.open(map, marker);
						  }
						  google.maps.event.addListener(marker, 'click', openInfoWindow);
						  $tweet.find(".user").on('click', openInfoWindow);
                            bounds.extend(myLatLng);
                        }
                        if ($.isArray(o.match)) {

                            var latitude = o.match[0].place.centroid.latitude;
                            var longitude = o.match[0].place.centroid.longitude;
                            var myLatLng = new google.maps.LatLng(latitude, longitude);
                            var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });

						  var infowindow = new google.maps.InfoWindow({
							  content: templates[2].replace('user',user).replace('text',text).replace('url',url).replace('date',date)
						  });
						  var $tweet = $(templates[3].replace('%user',user).replace(/%profile_img/g,profile_img).replace('%text',text).replace('%url',url));
	  					  $('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
						  $('#news-tweets').css("overflow","scroll").append($tweet);
						  function openInfoWindow() {
							  infowindow.open(map, marker);
						  }
						  google.maps.event.addListener(marker, 'click', openInfoWindow);
						  $tweet.find(".user").on('click', openInfoWindow);
                            bounds.extend(myLatLng);
                        } //end of second if statement



                    });
                }
            }
    </script>
</head>
<body><div class="news" style="width:80%; text-align:center;"><div class="info"><span style="font-style: italic; color:#F00;">"Choose one of the news logos to view<br />
news generated from users and agencies"</span></div>
<img src="euronews.png" width="48" height="48 " title="Euronews" onclick="news_tweets('euronews'); user_tweets('euronews')" />
<img src="reuters.png" width="48" height="48 " title="Reuters" onclick="news_tweets('reuters'); user_tweets('reuters')" />
<img src="skynews.jpg" width="48" height="48 " title="Skynews" onclick="news_tweets('skynews'); user_tweets('skynews')" />
<img src="guardian.jpg" width="48" height="48 " title="Guardian" onclick="news_tweets('guardian'); user_tweets('guardian')" />
<img src="independent.jpg" width="48" height="48" title="The Independent" onclick="news_tweets('Independent'); user-tweets('Independent')" />
</div>
<div style="clear:both;"></div>
<div class="banner"><div id="user-banner">User Tweets</div><div id="news-banner">News Tweets</div></div><div style="clear:both;"></div>
<div id="user-tweets" style="float:left; width:25%; border:solid #FCF 1px; height:400px;"></div>
<div id="map_canvas" style="float:left; margin-left:10px;width:47%; border:solid #FCF 1px; height:400px; " ></div>

<div id="news-tweets" style="float:left; width:25%; margin-left:10px; border:solid #FCF 1px; height:400px;"></div>

</body>

</html>