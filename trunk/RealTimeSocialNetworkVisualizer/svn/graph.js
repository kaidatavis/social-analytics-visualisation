// JavaScript Document for graph processing

//function to compute the difference between dates
function daysBetween(date1, date2) 
{
    //Get 1 second in milliseconds
    var one_msec = 1000;

    var date1_ms = date1.getTime();
    var date2_ms = date2.getTime();

    // Calculate the difference in milliseconds
    var difference_ms = date2_ms - date1_ms;

    // Convert back to seconds
    return Math.round(difference_ms / one_msec);
}

//For removing duplication in the array
function remDup(arrayName)
{
	var newArray=new Array();
	label:for(var i=0; i<arrayName.length;i++ )
	{  
		for(var j=0; j<newArray.length;j++ )
		{
			if(newArray[j][1]>setmap_pos[0][1]+20)
			continue label;
  			
			else if(newArray[j][1]<setmap_pos[0][1]-20)
  			continue label;
  			
			else if(newArray[j][0]==arrayName[i][0]) 
  			continue label;
  
		}
  		newArray[newArray.length] = arrayName[i];
  	}
 return newArray;
}

function view_data()
{
	
	var mul=tweet_arr;
	
	for(var y=0;y<mul.length;y++)
	{
		temp_arr[y]=mul[y][3];
	}
	temp_tweet=tweet_categorize();
	
	//alert("Temp tweet array length:"+temp_tweet.length);
	//cjson();
	//mul = jQuery.grep(mul, function(n){ return (n); });
	
	
	var res=remDup(mul);
	
	alert(res.length);
  
	my_plot(res);
	
}


//calculating the time periods
function tweet_categorize()
{
var mul_arr=temp_arr;
//var res=mul_arr;
//alert(mul_arr[mul_arr.length-1]);

var first=parseInt(daysBetween(param(mul_arr[mul_arr.length-1]),param(mul_arr[0])));

	//var dec=(parseInt(first/4)).toString();
	
	
	res.push([(parseInt(first/4)).toString(),0]);
	res.push([(parseInt(first/2)).toString(),0]);
	res.push([(parseInt(0.75*first)).toString(),0]);
	res.push(["Others",0]);
	
	if(mul_arr.length>0)
	{
	res[0][1]++;
	}
	var elt;
	for(var l=1;l<mul_arr.length;l++)
	{
		elt=parseInt(daysBetween(param(mul_arr[mul_arr.length-1]),param(mul_arr[l])));
		cal(first,elt);
		elt=null;
	}
	
	return res;
}
function param(f)
{
	var tmp=new moment(f);
	var tmp1=tmp.utc();
	var z=new Date(tmp1);
	return z;
}
function cal(f1,el)
{
    //var str = "Wed, 1 Aug 2012 17:11:11 +0000";
//var dt=Date.parse(str);

if(el<=parseInt(f1/4))
{
	res[0][1]++;
}
else if(el<=parseInt(f1/2))
{
	res[1][1]++;
}
else if(el<=parseInt(f1*0.75))
{
	res[2][1]++;
}
else if(el<=parseInt(f1))
{
	res[3][1]++;
}
else
{
	res[4][1]++;
}


}
//Code for updating graph
function cjson()
{
	//alert(temp_tweet);
	var tw=temp_tweet;
	//tw=t;	
	var edited="{'label':[";
	for(var j=0;j<tw.length;j++)
	{
		if(j==tw.length-1)
		{
			edited +="'"+tw[j][0]+"'";
		}
		else
		{
			edited +="'"+tw[j][0]+"',";
		}
	}
	edited += "],'values':[";
	for(var i=0;i<tw.length;i++) 
	{
    	if(i==tw.length-1)
		{
			edited += "{'label':'"+tw[i][0]+"','values':["+tw[i][1]+"]}";
		}
		else
		{
			edited += "{'label':'"+tw[i][0]+"','values':["+tw[i][1]+"]},";
		}
	}
	edited = edited.substring(0, edited.length) + "]}";

	var obj=eval( '(' + edited + ')'); 
	var b=1;
	//alert(obj.values[0]);
	init(b,obj);
	
}