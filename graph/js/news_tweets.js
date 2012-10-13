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
                            rpp: count,
                            include_entities: "true",
                            result_type: "recent"
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
                    console.log("newsarray:", news_array);
                    return $.when
                        .apply(null, requests);
                })
                    .then(function () {
                    return [news_array, user_tweets,query,count];
                });
         }   
