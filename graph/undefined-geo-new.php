<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Highcharts Example</title>
    <style type="text/css">
	body{
    font-family: Georgia;
    font-size: 12px;}
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
		background-color:#F66200;
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
    -moz-transform: scale(0.5);
    -moz-transform-origin: 0 0;
    -o-transform: scale(0.5);
    -o-transform-origin: 0 0;
    -webkit-transform: scale(0.5);
    -webkit-transform-origin: 0 0;
}
</style>
    <script type="text/javascript" src="jquery-1.7.2.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDlmZn_jhR4UK9ydbiKf4Mtn9GDuZbLO5w&sensor=true">        
    </script>
    <script src="Highcharts/js/highcharts.js"></script>
    <script src="Highcharts/js/modules/exporting.js"></script>
    <script type="text/javascript" src="JSPlacemaker.js"></script>
    <script type="text/javascript">
$(document).ready(function(){
	$('#user-tweets').on('mouseenter', "a", function() {
    $(this).append($("<iframe src='"+this.href+"'></iframe>"));
}).on('mouseleave', 'a', function () {
    $(this).find("iframe:last").remove();
});
});
        function showTweets(value) {
            user_ids = [];
            array = [];
            text_array = [];
            var ids;
			$("#user-tweets").html("");
            news_tweets(value);
            user_tweets(value);

            function news_tweets(value) {
                $.getJSON("https://api.twitter.com/1/statuses/user_timeline.json?include_entities=false&include_rts=false&screen_name="+value+"&count=10&callback=?",

                function (data) { //console.log(data);
                    var len = data.length;
                    for (var i = 0; i < len; ++i) {
                        var user = data[i].user.name;
                        var date = data[i].created_at;
                        var profile_img = data[i].user.profile_image_url;
                        var text = data[i].text;
                        create_array(user, date, profile_img, text);
                    }
                    function create_array(a, b, c, d, e) {
                        array.push({
                            user: a,
                            date: b,
                            profile_img: c,
                            text: d
                        });
                    }
                    placemaker_news_tweets(array);
                console.log(array);});
            }

            function placemaker_news_tweets(array) {

                //console.log(array);

                for (var i in array) {
                    var user = array[i].user;
                    var date = array[i].date;
                    var profile_img = array[i].profile_img;
                    var text = array[i].text;
                    getLocation(user, date, profile_img, text);
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

                function getLocation(user, date, profile_img, text) {
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
                                content: text
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
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
                                content: text
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);
                        } //end of second if statement



                    });
                }
            }

            function user_tweets(value) {
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
                        array.push({
                            user: a,
                            user_id: b,
                            date: c,
                            profile_img: d,
                            text: e,
                            url: f
                        });
                    }
                    //console.log(array);

                    for (var i in array) {
                        user_ids.push(array[i].user_id);
                        // console.log(array[i].user_id);
                    }
                    ids = user_ids.join();

                    //console.log(ids);
                    search_location(ids, array);
                });
            }

            function search_location(ids, array) {
                $.getJSON("http://api.twitter.com/1/users/lookup.json?user_id=" + ids + "&callback=?", function (data) { //first attempt to find locations
                    $.each(data, function (i, item) {
                        var location = item.location;

                        array[i].location = location; //locations are added but some are empty

                    });
                    var userLocationMap = {};
                    for (var i = 0; i < array.length; i++) {
                        if (array[i].location) {
                            userLocationMap[array[i].user] = array[i].location;
                        }
                    }

                    for (var i = 0; i < array.length; i++) {
                        if (!array[i].location) {
                            array[i].location = userLocationMap[array[i].user];
                        }
                    }
                    geo(array);
                });
            }

            function geo(array) {
                loc = [];			
            
                for (var i in array) {
                    var location = array[i].location;
                    var user = array[i].user;
                    var date = array[i].date;
                    var profile_img = array[i].profile_img;
                    var text = array[i].text;
                    var url = array[i].url;	
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
                            var contentString = '<div id="content"><div id="siteNotice"></div><h2 id="firstHeading" class="firstHeading">' + user + '</h2><div id="bodyContent">' + text + '</div><p><a href="' + url + '"target="_blank">'+url+'</a></p><p>Date Posted- ' + date + '</p></div>';


                            var infowindow = new google.maps.InfoWindow({
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
					$('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
					$('#user-tweets').css("overflow","scroll");
					$('#user-tweets').append('<table class="tweets" width="320" border="0"><tr><td rowspan="1">'+user+'</td><td rowspan="1">'+date+'</td></tr><tr><td width="45"><a href="'+profile_img+'"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'<p><a href="' + url + '"target="_blank">'+url+'</a></p></td></tr></table><hr>');

                            bounds.extend(myLatLng);
                        } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                            setTimeout(function () {
                                geocode(user, date, profile_img, text, location);
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
                            var contentString = '<div id="content"><div id="siteNotice"></div><h2 id="firstHeading" class="firstHeading">' + user + '</h2><div id="bodyContent">' + text + '</div><p><a href="' + url + '"target="_blank">'+url+'</a></p><p>Date Posted- ' + date + '</p></div>';
                                        var infowindow = new google.maps.InfoWindow({
                                            content: contentString
                                        });
                                        google.maps.event.addListener(marker, 'click', function () {
                                            infowindow.open(map, marker);
                                        });
					$('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
					$('#user-tweets').css("overflow","scroll");
					$('#user-tweets').append('<table width="320" border="0"><tr><td colspan="2" rowspan="1">'+user+'</td><td rowspan="1">'+date+'</td></tr><tr><td width="45"><a href="'+profile_img+'"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'</td></tr></table><hr>');

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
                       var contentString = '<div id="content"><div id="siteNotice"></div><h2 id="firstHeading" class="firstHeading">' + user + '</h2><div id="bodyContent">' + text + '</div><p><a href="' + url + '"target="_blank">'+url+'</a></p><p>Date Posted- ' + date + '</p></div>';
                                            var infowindow = new google.maps.InfoWindow({
                                                content: contentString
                                            });
                                            google.maps.event.addListener(marker, 'click', function () {
                                                infowindow.open(map, marker);
                                          });
					$('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
							$('#user-tweets').css("overflow","scroll");
					$('#user-tweets').append('<table width="320" border="0"><tr><td colspan="2" rowspan="1">'+user+'</td><td rowspan="1">'+date+'</td></tr><tr><td width="45"><a href="'+profile_img+'"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'</td></tr></table><hr>');

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
                            var contentString = '<div id="content"><div id="siteNotice"></div><h2 id="firstHeading" class="firstHeading">' + user + '</h2><div id="bodyContent">' + text + '</div><p><a href="' + url + '"target="_blank">'+url+'</a></p><p>Date Posted- ' + date + '</p></div>';
                                    var infowindow = new google.maps.InfoWindow({
                                        content: contentString
                                    });
                                    google.maps.event.addListener(marker, 'click', function () {
                                        infowindow.open(map, marker);
					$('#user-banner').css("visibility","visible");$('#news-banner').css("visibility","visible");
							$('#user-tweets').css("overflow","scroll");
					$('#user-tweets').append('<table width="320" border="0"><tr><td colspan="2" rowspan="1">'+user+'</td><td rowspan="1">'+date+'</td></tr><tr><td width="45"><a href="'+profile_img+'"><img src="'+profile_img+'" width="55" height="50"/></a></td><td width="186">'+text+'</td></tr></table><hr>');
                                    });
                                    bounds.extend(myLatLng);
                                }
                            });

                        }
                    });
                }
            }
        }
    </script>
</head>
<body><div class="news" style="float:left; width:100%; text-align:center;">
<img src="img/euronews.png" width="48" height="48 " title="Euronews" onclick=showTweets("euronews"); />
<img src="img/reuters.png" width="48" height="48 " title="Reuters" onclick=showTweets("reuters"); />
<img src="img/guardian.jpg" width="48" height="48 " title="Guardian" onclick=showTweets("guardian"); />
<img src="img/times.png" width="48" height="48 " title="TheTimes" onclick=showTweets("thetimes"); />
<img src="img/independent.jpg" width="48" height="48" title="The Independent" onclick=showTweets("thetimes");/></div>
<div style="clear:both;"></div>
<div class="banner"><div id="user-banner">User Tweets</div><div id="news-banner">News Tweets</div></div><div style="clear:both;"></div>
<div id="user-tweets" style="float:left; width:25%; border:solid #FCF 1px; height:400px;"></div>
<div id="map_canvas" style="float:left; margin-left:10px;width:47%; border:solid #FCF 1px; height:400px;"></div>

<div id="news-tweets" style="float:left; width:25%; margin-left:10px; border:solid #FCF 1px; height:400px;"></div>

</body>

</html>