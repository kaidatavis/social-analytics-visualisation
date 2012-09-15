<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>News Map Visualize</title>
    <link rel="stylesheet" type="text/css" href="css/css.css"
    />
    <script type="text/javascript" src="jquery-1.7.2.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDlmZn_jhR4UK9ydbiKf4Mtn9GDuZbLO5w&sensor=true"></script>    
    <script type="text/javascript" src="JSPlacemaker.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#user-tweets').on('click', "a", function (event) {
                event.preventDefault();
                $('.frame').css("visibility", "visible").html($("<iframe src='" + this.href + "'></iframe>"));
                $('.arrow').css("visibility", "visible");
            });
            $('.arrow').click(function () {
                $('.frame').find("iframe:last").remove();
                $('.frame,.arrow').css("visibility", "hidden");
            });
        });
        function news_tweets(query, user_id) {
            news_array = [];
            user_ids_2 = [];
            user_news_array = [];
			full_array=[];
            var ids_2;
            $("#news-tweets").html("");
            $.getJSON("https://api.twitter.com/1/statuses/user_timeline.json?include_entities=true&include_rts=false&user_id=" + user_id + "&count=10&callback=?",

            function (data) { //console.log(data);
                var len = data.length;
                for (var i = 0; i < len; ++i) {
                    var user = data[i].user.name;
                    var date = data[i].created_at;
                    var profile_img = data[i].user.profile_image_url;
                    var text = data[i].text;
                    var url = (data[i].entities.urls.length > 0 ? data[i].entities.urls[0].url : '');
                    create_news_array(user, date, profile_img, text, url);
                }

                function create_news_array(a, b, c, d, e) {
                    news_array.push({
                        user: a,
                        date: b,
                        profile_img: c,
                        text: d,
                        url: e
                    });
                }
                deploy_user_tweets(news_array, query);
            });

            function deploy_user_tweets(news_array, query) {

                $("#user-tweets").html("");
                $.getJSON("http://search.twitter.com/search.json?q=%40" + query + "&rpp=10&include_entities=true&result_type=mixed&callback=?",

                function (data) {
                    $.each(data.results, function (i, item) {
                        var user = item.from_user;
                        var user_id = item.from_user_id;
                        var date = item.created_at;
                        var profile_img = item.profile_image_url;
                        var text = item.text;
                        var url = (item.entities.urls.length > 0 ? item.entities.urls[0].url : '');
                        create_news_array(user, user_id, date, profile_img, text, url);
                    });

                    function create_news_array(a, b, c, d, e, f) {
                        user_news_array.push({
                            user: a,
                            user_id: b,
                            date: c,
                            profile_img: d,
                            text: e,
                            url: f
                        });
                    }


                    for (var i in user_news_array) {
                        user_ids_2.push(user_news_array[i].user_id);
                        //console.log(news_array[i].user_id);
                    }
                    ids_2 = user_ids_2.join();

                    //console.log(ids_2);
                    search_location(ids_2, user_news_array, news_array);
                });
            }

            function search_location(ids_2, user_news_array, news_array) {
                $.getJSON("http://api.twitter.com/1/users/lookup.json?user_id=" + ids_2 + "&callback=?", function (data) { //first attempt to find locations
                    $.each(data, function (i, item) {
                        var location = item.location;

                        user_news_array[i].location = location; //locations are added but some are empty

                    });
                    var userLocationMap = {};
                    for (var i = 0; i < user_news_array.length; i++) {
                        if (user_news_array[i].location) {
                            userLocationMap[user_news_array[i].user] = user_news_array[i].location;
                        }
                    }

                    for (var i = 0; i < user_news_array.length; i++) {
                        if (!user_news_array[i].location) {
                            user_news_array[i].location = userLocationMap[user_news_array[i].user];
                        }
                    }
                    combine_arrays(user_news_array, news_array);
                });
            }

            function combine_arrays(user_news_array, news_array) {
                full_array = user_news_array.concat(news_array);
                console.log(full_array);
                geo(full_array);
            }
                var mapOptions = {
                    center: new google.maps.LatLng(48.69096,-31.64063),
                    zoom: 3,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                // added this 
                var bounds = new google.maps.LatLngBounds();
                // create the map
                var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);


            function geo(full_array) {
                //console.log(user_news_array);
                for (var i = 0; i < full_array.length; i++) {
                    var location = full_array[i].location;
                    var user = full_array[i].user;
                    var date = full_array[i].date;
                    var profile_img = full_array[i].profile_img;
                    var text = full_array[i].text;
                    var url = full_array[i].url;

                    if (user == "Reuters Top News" || user == "Sky News" || user == "The Guardian") {
                        geocode_news_tweets(user, date, profile_img, text, url);
                    }

                    else  {
                        geocode_user_tweets(user, date, profile_img, text, url, location);
                    }

                }
                function geocode_user_tweets(user, date, profile_img, text, url, location) {
                    var templates = [];
                    templates[0] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + text + '</div><div><p><a href="' + url + '"target="_blank">' + url + '</a></p></div><p>Date Posted- ' + date + '</p></div>';
                    templates[1] = '<div class="tweets"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + text + '<br /><a href="' + url + '"target="_blank">' + url + '</a></div>';
                    templates[2] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + text + '</div><div><p><a href="' + url + '"target="_blank">' + url + '</a></p></div><p>Date Posted- ' + date + '</p></div>';
                    templates[3] = '<div class="tweetss"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + text + '<br /><a href="' + url + '"target="_blank">' + url + '</a></div>';
                    templates[4] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + text + '</div><div><p><a href="' + url + '"target="_blank">' + url + '</a></p></div><p>Date Posted- ' + date + '</p></div>';
                    templates[5] = '<div class="tweetss"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + text + '<br /><a href="' + url + '"target="_blank">' + url + '</a></div>';
                    templates[6] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + text + '</div><div><p><a href="' + url + '"target="_blank">' + url + '</a></p></div><p>Date Posted- ' + date + '</p></div>';
                    templates[7] = '<div class="tweetss"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + text + '<br /><a href="' + url + '"target="_blank">' + url + '</a></div>';

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
                                content: templates[0].replace('user', user).replace('text', text).replace('url', url).replace('date', date)
                            });
                            var $tweet = $(templates[1].replace('%user', user).replace(/%profile_img/g, profile_img).replace('%text', text).replace('%url', url));
                            $('#user-banner').css("visibility", "visible");
                            $('#user-tweets').css({'overflow-y':'scroll','overflow-x':'hidden'}).append($tweet);

                            function openInfoWindow() {
                                infowindow.open(map, marker);
                            }
                            google.maps.event.addListener(marker, 'click', openInfoWindow);
                            $tweet.find(".user").on('click', openInfoWindow);
                            bounds.extend(myLatLng);
                        } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                            setTimeout(function () {
                                geocode_user_tweets(user, date, profile_img, text, url, location);
                            }, 500);
                        } else {
/*                            var str = text;
                            var text2 = str.replace("#", "");
							
*/                            

							  $.getJSON("http://where.yahooapis.com/geocode?location="+text+"&flags=J&appid=Y7pwNojV34HFg6fmPML_2_YDetrgip_ZFLNaq3cetV6waFtW3O1eF2wJOcU.FMkbHO5iU9R7DxFZ", function (data) { 
							  console.log(data);
                                    var latitude = data.ResultSet.Results[0].latitude;
                                    var longitude = data.ResultSet.Results[0].longitude;
                                    var myLatLng = new google.maps.LatLng(latitude, longitude);
                                    var marker = new google.maps.Marker({
                                        icon: profile_img,
                                        title: user,
                                        map: map,
                                        position: myLatLng
                                    });


                                    var infowindow = new google.maps.InfoWindow({
                                        content: templates[2].replace('user', user).replace('text', text).replace('url', url).replace('date', date)
                                    });
                                    var $tweet = $(templates[3].replace('%user', user).replace(/%profile_img/g, profile_img).replace('%text', text));
                                    $('#user-banner').css("visibility", "visible");
                                    $('#news-banner').css("visibility", "visible");
                            $('#user-tweets').css({'overflow-y':'scroll','overflow-x':'hidden'}).append($tweet);

                                    function openInfoWindow() {
                                        infowindow.open(map, marker);
                                    }
                                    google.maps.event.addListener(marker, 'click', openInfoWindow);
                                    $tweet.find(".user").on('click', openInfoWindow);

                                    bounds.extend(myLatLng);
                                
/*                                if (!$.isArray(o.match) && o.error == "no locations found") {
								geocode_user_tweets(user, date, profile_img, text, url, location);
								}
*/
					});
/*					});
*/                        }
                    });
                    /*setInterval( function() {
				 move(user_news_array,value); }, 60000 );
				 
				 function move(){
					$('#user-tweets')
				 }
           function update_tweets(user_news_array,value){
			   var update_news_array=[];
		$.getJSON("http://search.twitter.com/search.json?q="+value+"&rpp=1&include_entities=true&result_type=mixed&callback=?",
                function (data) {
                    $.each(data.results, function (i, item) {
                        var user = item.from_user;
                        var user_id = item.from_user_id;
                        var date = item.created_at;
                        var profile_img = item.profile_image_url;
                        var text = item.text;
                        var url = (item.entities.urls.length > 0 ? item.entities.urls[0].url : '');
                        create_news_array(user, user_id, date, profile_img, text, url);
                    });

                    function create_news_array(a, b, c, d, e, f) {
                        update_news_array.push({
                            user: a,
                            user_id: b,
                            date: c,
                            profile_img: d,
                            text: e,
                            url: f
                        });
                    }

			} //update_tweets(function)
*/
                }

                function geocode_news_tweets(user, date, profile_img, text, url) {

							  $.getJSON("http://where.yahooapis.com/geocode?location="+text+"&flags=J&appid=Y7pwNojV34HFg6fmPML_2_YDetrgip_ZFLNaq3cetV6waFtW3O1eF2wJOcU.FMkbHO5iU9R7DxFZ", function (data) { 
							  console.log(data,user,text);
                                    var latitude = data.ResultSet.Results[0].latitude;
                                    var longitude = data.ResultSet.Results[0].longitude;
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
                        
                    });

                }
            }
        } //user_tweets
    </script>
</head>
<body>
    <div class="news" style="width:80%; text-align:center;><div class=" info
    "><span style="font-style: italic; color:#F00; ">"Pick one of the news logos to view<br
    />news generated from users and agencies"</span>
    </div>
    <img class="international" src="img/euronews.png" width="48" height="48 "
    title="Euronews" onclick="news_tweets('euronews','25067168')" />
    <img class="international" src="img/reuters.png" width="48" height="48 "
    title="Reuters" onclick="news_tweets('reuters','1652541')" />
    <img class="england" src="img/skynews.jpg" width="48" height="48 " title="Skynews"
    onclick="news_tweets('skynews','7587032')" />
    <img class="england" src="img/guardian.jpg" width="48" height="48 " title="Guardian"
    onclick="news_tweets('guardian','87818409')" />
    <img class="france" src="img/france24.png" width="48" height="48 " title="France24"
    onclick="news_tweets('France24_fr','25048816')" />
    <img class="england" src="img/independent.jpg" width="48" height="48"
    title="The Independent" onclick="news_tweets('Independent','16973333')"
    />
 <div style="clear:both;"></div>
    <div class="banner">
        <div id="user-banner">User Tweets</div>
        <!--<div id="news-banner">News Tweets</div>--></div>
    <div style="clear:both;"></div>
    <div id="user-tweets" style="float:left; width:42%; border:solid #FCF 1px; height:400px;"></div>
    <div class="frame" style="width:57%; float:right; height:400px; border:solid #FCF 1px; border-radius:5px; visibility:hidden;"></div>
    <div style="clear:both;"></div>
       <div id="map_canvas" style="float:right; margin-top:20px; width:100%; border:solid #FCF 1px; height:400px;"></div>
 <div class="arrow"></div>
        <!--<div id="news-tweets" style="float:left; width:25%; margin-left:10px;
        border:solid #FCF 1px; height:400px;"></div>
-->
</body>

</html>