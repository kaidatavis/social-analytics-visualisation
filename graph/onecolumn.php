<!DOCTYPE HTML>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>News Map Visualize</title>
    <link rel="stylesheet" type="text/css" href="css/css.css"/>
    <script type="text/javascript" src="jquery-1.8.1.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDlmZn_jhR4UK9ydbiKf4Mtn9GDuZbLO5w&sensor=true"></script>
    <script type="text/javascript">
        $(document)
            .ready(function () {
            $('#user-tweets,#user-news-tweets,#news-tweets')
                .on('click', "a", function (event) {
                event.preventDefault();
                $('.frame')
                    .css("visibility", "visible")
                    .html($("<iframe src='" + this.href + "'></iframe>"));
                $('.arrow')
                    .css("visibility", "visible");
            });
            $('.arrow')
                .click(function () {
                $('.frame')
                    .find("iframe:last")
                    .remove();
                $('.frame,.arrow')
                    .css("visibility", "hidden");
            });
			$country = $("select[name='Country']");
            $channel = $("select[name='News Channel']");
            $country.change(function () {
                if ($(this).val() == "United Kingdom") {
                    $("select[name='News Channel'] option").remove();
                    $("<option>Sky news</option>").appendTo($channel);
                    $("<option>Guardian</option>").appendTo($channel);
                    $("<option>The Independent</option>").appendTo($channel);
                }
			     if ($(this).val() == "International news") {
                    $("select[name='News Channel'] option").remove();
                    $("<option>Euronews</option>").appendTo($channel);
                    $("<option>Reuters</option>").appendTo($channel);
                }
			     if ($(this).val() == "France") {
                    $("select[name='News Channel'] option").remove();
                    $("<option>France 24</option>").appendTo($channel);
                }

				
            });
        });			  function get(){
				   if ($country.val() == "Select country") {
					   alert("Select a country first");
					   return false;
				   }
				   if ($channel.val() == "News channels") {
					   alert("Select a channel");
					   
				   }else if ($channel.val()=="Reuters"){
					   var query="reuters";
					   var user_id="1652541";
					var count=$("#tweets-select option:selected").text();
					call_tweets(query,user_id,count);
					
				   }else if ($channel.val()=="Sky news"){
					   var query='skynews';
					   var user_id="7587032";
					var count=$("#tweets-select option:selected").text();
					   call_tweets(query,user_id,count);
					   					   
				   }else if ($channel.val()=="Euronews"){
					   var query='euronews';
					   var user_id="25067168";
					var count=$("#tweets-select option:selected").text();
					   call_tweets(query,user_id,count);
					   					   
				   }else if ($channel.val()=="Guardian"){
					   var query="guardian";
					   var user_id="25048816";
					var count=$("#tweets-select option:selected").text();
   					   call_tweets(query,user_id,count);
					   
				   }else if ($channel.val()=="The Independent"){
					   var query="Independent";
					   var user_id="16973333";
					var count=$("#tweets-select option:selected").text();
					   call_tweets(query,user_id,count);
				   }
			  }
        function news_tweets(query,user_id,count) {
	var news_array = [];
	var user_tweets = [];
	    return $.getJSON("https://api.twitter.com/1/statuses/user_timeline.json?callback=?", {
                    include_entities: "true",
                    include_rts: "false",
                    user_id: user_id,
                    count: count
                })
                    .then(function (data) {
                    var requests = $.map(data, function (item) {
                        news_array.push({
                            news_user: item.user
                                .name,
                            news_date: item.created_at,
                            news_profile_img: item.user
                                .profile_image_url,
                            news_text: item.text,
                            news_url: item.entities
                                .urls
                                .length ? item
                                .entities
                                .urls[0]
                                .url : ''
                        });
                        return $.getJSON("http://search.twitter.com/search.json?callback=?", {
                            q: item.text,
                            rpp: 2,
                            include_entities: "true",
                            result_type: "mixed"
                        })
                            .done(function (data) {
                            $.each(data.results, function (i, item) {
                                user_tweets.push({
                                    user: item.from_user,
                                    user_id: item.from_user_id,
                                    date: item.created_at,
                                    user_profile_img: item.profile_image_url,
                                    text: item.text,
                                    url: item.entities
                                        .urls
                                        .length ? item
                                        .entities
                                        .urls[0]
                                        .url : ''
                                });
                            });
                        });
                    });
                    //console.log("newsarray:", news_array);
                    return $.when
                        .apply(null, requests);
                })
                    .then(function () {
                    // this callback is executed [once] when all requests are done
                    // and the user_tweets array is filled
                    // arguments is an array of all search request results
                    // console.log("usertweets:", user_tweets);
                    // returns a combined array as the overall result
                    return [news_array, user_tweets,query];
                });
         }   
    
		function transfer_arrays(news_array, user_tweets,query){
            user_ids_2 = [];
            user_news_array = [];
            var ids_2;
            $("#user-tweets")
                .html("");
            $.getJSON("http://search.twitter.com/search.json?q=%40" + query + "&rpp=3&include_entities=true&result_type=mixed&callback=?",
            function (data) {
                $.each(data.results, function (i, item) {
                    var user = item.from_user;
                    var user_id = item.from_user_id;
                    var date = item.created_at;
                    var profile_img = item.profile_image_url;
                    var text = item.text;
                    var text = text.replace(/http:\/\/\S+/g, '<a href="$&" target="_blank">$&</a>');
                    var text = text.replace(/(@)(\w+)/g, ' $1<a href="http://twitter.com/$2" target="_blank">$2</a>');
                    var text = text.replace(/(#)(\w+)/g, ' $1<a href="http://search.twitter.com/search?q=%23$2" target="_blank">$2</a>');
                    create_news_array(user, user_id, date, profile_img, text);
                });

                function create_news_array(a, b, c, d, e, f) {
                    user_news_array.push({
                        user: a,
                        user_id: b,
                        date: c,
                        profile_img: d,
                        text: e
                    });
                }
                for (var i in user_news_array) {
                    user_ids_2.push(user_news_array[i].user_id);
                }
                ids_2 = user_ids_2.join();

                search_location(ids_2, user_news_array,user_tweets,news_array,query);
            });
            function search_location(ids_2, user_news_array,user_tweets,news_array,query) {
                $.getJSON("http://api.twitter.com/1/users/lookup.json?user_id=" + ids_2 + "&callback=?", function (data) {
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
                    geo(user_news_array,user_tweets,news_array,query);
                });
            }
            var mapOptions = {
                center: new google.maps.LatLng(48.69096, - 31.64063),
                zoom: 3,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            var bounds = new google.maps.LatLngBounds();
            var map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);


            function geo(user_news_array,user_tweets,news_array,query) {
                for (var i = 0; i < user_news_array.length; i++) {
                    var location = user_news_array[i].location;
                    var user = user_news_array[i].user;
                    var date = user_news_array[i].date;
                    var profile_img = user_news_array[i].profile_img;
                    var text = user_news_array[i].text;

                    geocode_user_tweets(user, date, profile_img, text, location,query);
                }
                for (var x = 0; x < news_array.length; x++) {
                    var news_date = news_array[x].news_date;
                    var news_profile_img = news_array[x].news_profile_img;
                    var news_text = news_array[x].news_text;
                    var news_url = news_array[x].news_url;
                    var news_user = news_array[x].news_user;
                   geocode_news_tweets(news_user, news_date, news_profile_img, news_text, news_url,user_tweets);
                }
			}
                function geocode_user_tweets(user, date, profile_img, text, location,query) {
                    var templates = [];
                    templates[0] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + text + '</div><p>Date Posted- ' + date + '</p></div>';
                    templates[1] = '<div class="tweets"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + text + '</div>';
                    templates[2] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + text + '</div><p>Date Posted- ' + date + '</p></div>';
                    templates[3] = '<div class="tweetss"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + text + '</div>';
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
                                content: templates[0].replace('user', user)
                                    .replace('text', text)
                                    .replace('date', date)
                            });
                            var $tweet = $(templates[1].replace('%user', user)
                                .replace(/%profile_img/g, profile_img)
                                .replace('%text', text));
                            $('#user-banner')
                                .css("visibility", "visible");
							$('#user-banner').text('Users mentioned @'+query+'');
                            $('#user-tweets')
                                .css({
                                'overflow-y': 'scroll',
                                'overflow-x': 'hidden'
                            })
                                .append($tweet);

                            function openInfoWindow() {
                                infowindow.open(map, marker);
                            }
                            google.maps.event.addListener(marker, 'click', openInfoWindow);
                            $tweet.find(".user")
                                .on('click', openInfoWindow);
                            bounds.extend(myLatLng);
                        } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                            setTimeout(function () {
                                geocode_user_tweets(user, date, profile_img, text, location);
                            }, 500);
                        } else {
                            $.getJSON("http://where.yahooapis.com/geocode?location=" + text + "&flags=J&appid=Y7pwNojV34HFg6fmPML_2_YDetrgip_ZFLNaq3cetV6waFtW3O1eF2wJOcU.FMkbHO5iU9R7DxFZ", function (data) {
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
                                    content: templates[2].replace('user', user)
                                        .replace('text', text)
                                        .replace('date', date)
                                });
                                var $tweet = $(templates[3].replace('%user', user)
                                    .replace(/%profile_img/g, profile_img)
                                    .replace('%text', text));
                                $('#user-banner')
                                    .css("visibility", "visible");
                                $('#news-banner')
                                    .css("visibility", "visible");
                                $('#user-tweets')
                                    .css({
                                    'overflow-y': 'scroll',
                                    'overflow-x': 'hidden'
                                })
                                    .append($tweet);

                                function openInfoWindow() {
                                    infowindow.open(map, marker);
                                }
                                google.maps.event.addListener(marker, 'click', openInfoWindow);
                                $tweet.find(".user")
                                    .on('click', openInfoWindow);

                                bounds.extend(myLatLng);
                            });
                        }
                    });
                }
				function geocode_news_tweets(news_user, news_date, news_profile_img, news_text, news_url,user_tweets){
				var text2=news_text.replace(/\r/g, "");
				var text2=text2.replace(/"/g,"");
				var text2=text2.replace(/\//g,"");
				 var text2=text2.replace(/:/g,""); 
				 var text2=text2.replace(/\./g,""); 
				 var text2=text2.replace(/`/g,""); 
				 var text2=text2.replace(/'/g,"");
				var news_title=news_text.replace(/http:\/\/\S+/g, '<a href="$&" target="_blank">$&</a>');
				var	news_templates=[];
                news_templates[0] = '<div><div></div><h2 class="firstHeading"><a href="' + news_profile_img + '" target="_blank">\
				<img src="' + news_profile_img + '"width="55" height="50"/></a>' + news_user + '</h2><div>' + news_text + '</div>\
				<p>Date Posted- ' + news_date + '</p></div>';
                news_templates[1] = '<div class="tweets"><div class="user">' + news_user + '</div><div class="date">' + news_date + '</div>\
			    <span class="clear"></span><div class="img"><a href="' + news_profile_img + '" target="_blank"><img src="' + news_profile_img + '"\
			    width="55" height="50"/>\
			    </a></div><div class="text">' + news_title + '<br /><div class="button">Show tweets</div></div>';
				$.getJSON("http://where.yahooapis.com/geocode?location=" + news_text + "&flags=J&appid=Y7pwNojV34HFg6fmPML_2_YDetrgip_ZFLNaq3cetV6waFtW3O1eF2wJOcU.FMkbHO5iU9R7DxFZ", function (data) {
                                console.log(data);
                                var latitude = data.ResultSet.Results[0].latitude;
                                var longitude = data.ResultSet.Results[0].longitude;
                                var myLatLng = new google.maps.LatLng(latitude, longitude);
                                var marker = new google.maps.Marker({
                                    icon: news_profile_img,
                                    title: news_user,
                                    map: map,
                                    position: myLatLng
                                });
                                var infowindow = new google.maps.InfoWindow({
                                    content: news_templates[0].replace('news_user', news_user)
                                        .replace('news_text', news_text)
                                        .replace('news_date', news_date)
                                });
                                var $tweet = $(news_templates[1].replace('%news_user', news_user)
                                    .replace(/%news_profile_img/g, news_profile_img)
                                    .replace('%news_text', news_text));
                                $('#user-banner')
                                    .css("visibility", "visible");
                                $('#news-banner')
                                    .css("visibility", "visible");
                                $('#news-tweets')
                                    .css({
                                    'overflow-y': 'scroll',
                                    'overflow-x': 'hidden'
                                })
                                    .append($tweet);

                                function openInfoWindow() {
                                    infowindow.open(map, marker);
                                }
								function open(){
									find('' + text2);
								}
                                google.maps.event.addListener(marker, 'click', openInfoWindow);
                                $tweet.find(".user")
                                    .on('click', openInfoWindow);
								$tweet.find('.button').on('click',open);

                                bounds.extend(myLatLng);
                            });

				}
		function find(text2){
			$('#user-news-tweets').html("");
			$('.frame')
				.find("iframe:last")
				.remove();
			$('.frame,.arrow')
				.css("visibility", "hidden");

			for (var x = 0; x < user_tweets.length; x++) {
				var user = user_tweets[x].user;
				var date = user_tweets[x].date;
				var profile_img = user_tweets[x].user_profile_img;
				var url = user_tweets[x].url;
				var location=user_tweets[x].location;
				var text = user_tweets[x].text;
				var text3=text.replace(/\r"\/:\.`'/g, "");
				var news_title=text.replace(/http:\/\/\S+/g, '<a href="$&" target="_blank">$&</a>');
				if (text3.indexOf(text2.substr(0, 10)) > -1) {
				geocode_user_news_tweets(user, date, profile_img, text, location);
				}
			}
			function geocode_user_news_tweets(user, date, profile_img, text,location){
			var templates = [];
                    templates[0] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + news_title + '</div><p>Date Posted- ' + date + '</p></div>';
                    templates[1] = '<div class="tweets"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + news_title + '</div>';
                    templates[2] = '<div><div></div><h2 class="firstHeading"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a>' + user + '</h2><div>' + news_title + '</div><p>Date Posted- ' + date + '</p></div>';
                    templates[3] = '<div class="tweetss"><div class="user">' + user + '</div><div class="date">' + date + '</div><span class="clear"></span><div class="img"><a href="' + profile_img + '" target="_blank"><img src="' + profile_img + '" width="55" height="50"/></a></div><div class="text">' + news_title + '</div>';
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
                                content: templates[0].replace('user', user)
                                    .replace('news_title', news_title)
                                    .replace('date', date)
                            });
                            var $tweet = $(templates[1].replace('%user', user)
                                .replace(/%profile_img/g, profile_img)
                                .replace('%news_title', news_title));
                            $('#user-banner')
                                .css("visibility", "visible");
							$('#user-banner').text('Users mentioned @'+query+'');
                            $('#user-news-tweets')
                                .css({
                                'overflow-y': 'scroll',
                                'overflow-x': 'hidden'
                            })
                                .append($tweet);

                            function openInfoWindow() {
                                infowindow.open(map, marker);
                            }
                            google.maps.event.addListener(marker, 'click', openInfoWindow);
                            $tweet.find(".user")
                                .on('click', openInfoWindow);
                            bounds.extend(myLatLng);
                        } else if (status === google.maps.GeocoderStatus.OVER_QUERY_LIMIT) {
                            setTimeout(function () {
                                geocode_user_news_tweets(user, date, profile_img, text, location);
                            }, 500);
                        } else {
                            $.getJSON("http://where.yahooapis.com/geocode?location=" + text + "&flags=J&appid=Y7pwNojV34HFg6fmPML_2_YDetrgip_ZFLNaq3cetV6waFtW3O1eF2wJOcU.FMkbHO5iU9R7DxFZ", function (data) {
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
                                    content: templates[2].replace('user', user)
                                        .replace('news_title', news_title)
                                        .replace('date', date)
                                });
                                var $tweet = $(templates[3].replace('%user', user)
                                    .replace(/%profile_img/g, profile_img)
                                    .replace('%news_title', news_title));
                                $('#user-banner')
                                    .css("visibility", "visible");
                                $('#news-banner')
                                    .css("visibility", "visible");
                                $('#user-news-tweets')
                                    .css({
                                    'overflow-y': 'scroll',
                                    'overflow-x': 'hidden'
                                })
                                    .append($tweet);

                                function openInfoWindow() {
                                    infowindow.open(map, marker);
                                }
                                google.maps.event.addListener(marker, 'click', openInfoWindow);
                                $tweet.find(".user")
                                    .on('click', openInfoWindow);

                                bounds.extend(myLatLng);
                            });
                        }
                    });

				}
			}
	
		}
            

					 function call_tweets(query,user_id, count) {

		  news_tweets(query, user_id, count).done(function callback(array) {
			  var news_array = array[0];
			  var user_tweets = array[1];
			  var query=array[2]; 
			  //console.log(query);
			      var ids=[];
				   var ids_3;
 
                for (var i in user_tweets) {
				   ids.push(user_tweets[i].user_id);
                }
                ids_3 = ids.join();
				search_location(ids_3);
            function search_location(ids_3) {
                $.getJSON("http://api.twitter.com/1/users/lookup.json?user_id=" + ids_3 + "&callback=?", function (data) {
                    $.each(data, function (i, item) {
                        var location = item.location;

                        user_tweets[i].location = location; //locations are added but some are empty

                    });
                    var userLocationMap = {};
                    for (var i = 0; i < user_tweets.length; i++) {
                        if (user_tweets[i].location) {
                            userLocationMap[user_tweets[i].user] = user_tweets[i].location;
                        }
                    }

                    for (var i = 0; i < user_tweets.length; i++) {
                        if (!user_tweets[i].location) {
                            user_tweets[i].location = userLocationMap[user_tweets[i].user];
                        }
                    }
                    trance(user_tweets);
                });
            }
			function trance(user_tweets){
			  transfer_arrays(news_array, user_tweets,query);
			}
		  })
			  .fail(console.log);
	  }
    </script>
</head>
<body>
<!--    <img class="international" src="img/euronews.png" width="48" height="48 "
    title="Euronews" onclick="call_tweets('euronews','25067168',3)" />
    <img class="international" src="img/reuters.png" width="48" height="48 "
    title="Reuters" onclick="call_tweets('reuters','1652541',10)" />
    <img class="england" src="img/skynews.jpg" width="48" height="48 " title="Skynews"
    onclick="call_tweets('skynews','7587032',3)" />
    <img class="england" src="img/guardian.jpg" width="48" height="48 " title="Guardian"
    onclick="news_tweets('guardian','87818409')" />
    <img class="france" src="img/france24.png" width="48" height="48 " title="France24"
    onclick="news_tweets('France24_fr','25048816')" />
    <img class="england" src="img/independent.jpg" width="48" height="48"
    title="The Independent" onclick="news_tweets('Independent','16973333')"
    />
-->  
<table width="200" border="0" align="center">
  <tr>
    <td><div class="styled-select">
<select name="Country">
<option>Select country</option>
  <option>France</option>
  <option>Greece</option>
  <option>Spain</option>
  <option >International news</option>
  <option>Italy</option>
  <option>Romania</option>
  <option>United Kingdom</option>
</select>
</div>
</td>
    <td><div class="styled-select">
<select name="News Channel">
  <option>News channels</option>
</select>
</div>
</td>
    <td><div class="styled-select">
<select id="tweets-select">
<option selected="true" value="5">5 tweets</option>
<option value="10">10 tweets</option>
<option value="20">20 Tweets</option>
<option value="50">50 Tweets</option>
</select> 
</div>
</td>
    <td><input type="submit" value="Submit" onClick="get();" />
</td>
  </tr>
</table>

<div style="clear:both;"></div>
    <div class="banner">
        <div id="user-banner"></div>
        <div id="news-banner">News Feeds</div></div>
<div style="clear:both;"></div>
    <div id="user-tweets" style="float:left; width:30%; border:solid #FCF 1px; height:400px;"></div>
    <div id="news-tweets" style="float:left; width:30%; margin-left:1%; border:solid #FCF 1px; height:400px;"></div>
    <div id="user-news-tweets" style="float:left; width:35%; margin-left:1%; border:solid #FCF 1px; height:400px;"></div>
    <div class="frame"></div>
<div class="arrow"></div><div style="clear:both;"></div>
    <div id="map_canvas" style="float:right; margin-top:20px; width:100%; border:solid #FCF 1px; height:400px;"></div>
</body>
</html>