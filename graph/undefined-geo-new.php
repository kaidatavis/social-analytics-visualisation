<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Highcharts Example</title>
    <style type="text/css">
</style>
    <script type="text/javascript" src="jquery-1.7.2.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDlmZn_jhR4UK9ydbiKf4Mtn9GDuZbLO5w&sensor=true">
    </script>
    <script src="Highcharts/js/highcharts.js"></script>
    <script src="Highcharts/js/modules/exporting.js"></script>
    <script type="text/javascript" src="JSPlacemaker.js"></script>
    <script type="text/javascript">
        function codeAddress() {
            user_ids = [];
            array = [];
            text_array = [];
            var ids;
			news_tweets();
            user_tweets();
			function news_tweets(){
				$.getJSON("https://api.twitter.com/1/statuses/user_timeline.json?include_entities=false&include_rts=false&screen_name=euronews&count=10&callback=?",
                function (data) {
                    $.each(data.results, function (i, item) {
                        var date = item.created_at;
                        var profile_img = item.profile_image_url;
                        var text = item.text;
                        var contentString = text;
                        var url = (item.entities.urls.length > 0 ? item.entities.urls[0].url : '');
                        create_array(date, profile_img, text, contentString, url);
                    });
                    function create_array(a, b, c, d, e, f, g) {
                        array.push({
                            date: c,
                            profile_img: d,
                            text: e,
                            contentString: f,
                            url: g
                        });
                    }
                placemaker_news_tweets(array); });
            }	
            	function placemaker_news_tweets(array){
					loc=[];
					console.log(array);
					
					for(var i in array){
					var location=array[i].location;
					var user = array[i].user;
                    var date = array[i].date;
                    var profile_img = array[i].profile_img;
                    var text = array[i].text;
                    var contentString = text;
					getLocation(user,date, profile_img, text, contentString,location);
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
				
				function getLocation(user,date, profile_img, text, contentString,location){
				var geocoder = new google.maps.Geocoder();
				Placemaker.getPlaces(text, function (o) {console.log(o);
                                if ($.isArray(o.match)) {
                                    if (o.match[0].place.name == "Europe"||o.match[0].place.name == "United States") {
										var latitude=o.match[1].place.centroid.latitude;
										var longitude=o.match[1].place.centroid.longitude;
										 var myLatLng = new google.maps.LatLng(latitude, longitude);
										var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });

                            var infowindow = new google.maps.InfoWindow({
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);
                                  }
                                    if ($.isArray(o.match)) {
                                        if (o.match[0].place.name !== "Europe") {
										var latitude=o.match[0].place.centroid.latitude;
										var longitude=o.match[0].place.centroid.longitude;
										 var myLatLng = new google.maps.LatLng(latitude, longitude);
										var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });

                            var infowindow = new google.maps.InfoWindow({
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);						
                                        }//end of second if statement
                                 }
						}
	
				});}}
            function user_tweets() {
                $.getJSON("http://search.twitter.com/search.json?q=euronews&rpp=10&include_entities=true&result_type=mixed&callback=?",
                function (data) {
                    $.each(data.results, function (i, item) {
                        var user = item.from_user;
                        var user_id = item.from_user_id;
                        var date = item.created_at;
                        var profile_img = item.profile_image_url;
                        var text = item.text;
                        var contentString = text;
                        var url = (item.entities.urls.length > 0 ? item.entities.urls[0].url : '');
                        create_array(user, user_id, date, profile_img, text, contentString, url);
                    });
                    function create_array(a, b, c, d, e, f, g) {
                        array.push({
                            user: a,
                            user_id: b,
                            date: c,
                            profile_img: d,
                            text: e,
                            contentString: f,
                            url: g
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
			
            	function geo(array){
					loc=[];
					console.log(array);
					
					for(var i in array){
					var location=array[i].location;
					var user = array[i].user;
                    var date = array[i].date;
                    var profile_img = array[i].profile_img;
                    var text = array[i].text;
                    var contentString = text;
					geocode(user,date, profile_img, text, contentString,location);
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
				
				function geocode(user,date, profile_img, text, contentString,location){
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
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);
                        } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                            setTimeout(function () {
                                geocode(user,date, profile_img, text, contentString,location);
                            }, 500);
                        } else {
                            Placemaker.getPlaces(text, function (o) {console.log(o);
                                if ($.isArray(o.match)) {
                                    if (o.match[0].place.name == "Europe"||o.match[0].place.name == "United States") {
										var latitude=o.match[1].place.centroid.latitude;
										var longitude=o.match[1].place.centroid.longitude;
										 var myLatLng = new google.maps.LatLng(latitude, longitude);
										var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });

                            var infowindow = new google.maps.InfoWindow({
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);
                                  }
                                    if ($.isArray(o.match)) {
                                        if (o.match[0].place.name !== "Europe") {
										var latitude=o.match[0].place.centroid.latitude;
										var longitude=o.match[0].place.centroid.longitude;
										 var myLatLng = new google.maps.LatLng(latitude, longitude);
										var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });

                            var infowindow = new google.maps.InfoWindow({
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);						
                                        }
                                    }
                                }
								if (!$.isArray(o.match) && o.error=="no locations found") {
									var latitude="";
										var longitude="";
										 var myLatLng = new google.maps.LatLng(latitude, longitude);
										var marker = new google.maps.Marker({
                                icon: profile_img,
                                title: user,
                                map: map,
                                position: myLatLng
                            });

                            var infowindow = new google.maps.InfoWindow({
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);
									 
                                }
								if (!$.isArray(o.match)&& o.error!=="no locations found") {    
									var latitude=o.match.place.centroid.latitude;
									var longitude=o.match.place.centroid.longitude;
									var myLatLng = new google.maps.LatLng(latitude, longitude);
									var marker = new google.maps.Marker({
									icon: profile_img,
									title: user,
									map: map,
									position: myLatLng
                            });

                            var infowindow = new google.maps.InfoWindow({
                                content: contentString
                            });
                            google.maps.event.addListener(marker, 'click', function () {
                                infowindow.open(map, marker);
                            });
                            bounds.extend(myLatLng);
								}
							});
					
                    }});
                }}
		}
    </script>
</head>
<body>
        <input type="button" value="Get" onclick=" codeAddress();">
   
    <div id="map_canvas" style="width:100%; border:solid #FCF 1px; height:500px"></div>
</body>

</html>