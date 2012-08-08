// JavaScript Document for processing twitter api

function getLocation(lat,lon,key1)
{
	$jq.ajax({
			 //url:"https://bensharon4u:1godisgood2@stream.twitter.com/1/statuses/filter.json?track=cricket",
			 url: 'http://search.twitter.com/search.json?q='+key1+'&rpp=100&geocode='+lat+','+lon+',100mi',
			 dataType:'jsonp',
			 success:function(_json){
			// $jq('#tweet_list').append('<ul></ul>');
			 //var $listItems=$jq('#tweet_list').find('ul');
			 $jq.each(_json.results,function(key){
							var loc=_json.results[key].location;
							var txt=_json.results[key].text;
							var prof_pic=_json.results[key].profile_image_url;
							var created_time=_json.results[key].created_at;
							tweet_arr.push([loc,txt,prof_pic,created_time]);				 	
								 
											 });
			 }
			 });	
}

