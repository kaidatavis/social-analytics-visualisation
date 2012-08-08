// JavaScript Document for gmap

function my_plot1(twitt_array)
{
	
	//var geocoder = new google.maps.Geocoder();
	
   var jsArray = twitt_array;
			
		var address=new Array();
		var latlng=new Array();
		address=jsArray;
		
		for(var k=0;k<jsArray.length;k++)
		{
			geocoder.geocode( { 'address': jsArray[k]}, function(results, status) {

          
 			if (status == google.maps.GeocoderStatus.OK) {
   			 latitude = results[0].geometry.location.lat();
    		longitude = results[0].geometry.location.lng();
   			 
			 setMarker(latitude,longitude);
			}
			});
			
		}
		
	
}
function geocodeAddress(addr,l,t_arr)
{
	 var geocoder = new google.maps.Geocoder();
	 geocoder.geocode( { 'address': addr}, function(results, status) {

              if (status == google.maps.GeocoderStatus.OK) {
   			 latitude = results[0].geometry.location.lat();
    		longitude = results[0].geometry.location.lng();
   			 //loc_arr[l].push(results[0].geometry.location);
			 if(longitude<setmap_pos[0][1]-90)
			 {
				 //removeByIndex(t_arr,l);
			 }
			 else if(longitude>setmap_pos[0][1]+90)
			 {
			 }
			 else
			 {
			 setMarker(latitude,longitude,t_arr,l);
			 }
			}
			else
			{
				//alert(status);
			}
            });
}
function my_plot(twitt_array)
{
	var jsArray=new Array();
	//setMap(setmap_pos[0][0],setmap_pos[0][1]);
	for(var k=0;k<twitt_array.length;k++)
	{
		jsArray[k]=twitt_array[k][0];
	}
	alert(jsArray.length);
  // twitt_array;
			
		var address=new Array();
		var latlng=new Array();
		address=jsArray;
		var total=address.length;
		//for (i=0;i<=addresses.length;i++) {}
$jq.each(address, function (i,value) {
			window.setTimeout(function(){
				geocodeAddress(value,i,twitt_array);
				if (i === (total - 1)) { 
				setmap_pos.splice(0,1);
				alert("Finished plotting successfully");/*finalize();*/ }
			}, i * 100);
		});
	

}
function foo(path) {
var pic = new Image();
pic.src = path;
}
//label in the marker
// Define the overlay, derived from google.maps.OverlayView
function Label(opt_options) {
     // Initialization
     this.setValues(opt_options);
 
     // Here go the label styles
     var span = this.span_ = document.createElement('span');
     span.style.cssText = 'position: relative; left: -50%; top: -40px; ' +
                          'white-space: nowrap;color:#00008B;' +
                          'padding: 2px;font-family: Arial; font-weight: bold;' +
                          'font-size: 12px;';
 
     var div = this.div_ = document.createElement('div');
     div.appendChild(span);
     div.style.cssText = 'position: absolute; display: none';
};
 
Label.prototype = new google.maps.OverlayView;
 
Label.prototype.onAdd = function() {
     var pane = this.getPanes().overlayImage;
     pane.appendChild(this.div_);
 
     // Ensures the label is redrawn if the text or position is changed.
     var me = this;
     this.listeners_ = [
          google.maps.event.addListener(this, 'position_changed',
               function() { me.draw(); }),
          google.maps.event.addListener(this, 'text_changed',
               function() { me.draw(); }),
          google.maps.event.addListener(this, 'zindex_changed',
               function() { me.draw(); })
     ];
};
 
// Implement onRemove
Label.prototype.onRemove = function() {
     this.div_.parentNode.removeChild(this.div_);
 
     // Label is removed from the map, stop updating its position/text.
     for (var i = 0, I = this.listeners_.length; i < I; ++i) {
          google.maps.event.removeListener(this.listeners_[i]);
     }
};
 
// Implement draw
Label.prototype.draw = function() {
     var projection = this.getProjection();
     var position = projection.fromLatLngToDivPixel(this.get('position'));
     var div = this.div_;
     div.style.left = position.x + 'px';
     div.style.top = position.y + 'px';
     div.style.display = 'block';
     div.style.zIndex = this.get('zIndex'); //ALLOW LABEL TO OVERLAY MARKER
     this.span_.innerHTML = this.get('text').toString();
};


//label in the marker
function initialize() {
	
    var latlng = new google.maps.LatLng(52.474, -1.868);
	
    var myOptions = {
        zoom: 1,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

  
  map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
   // var image = 'i/hotel-map-pin.png';
   //my_plot();
   
   
 
}
function setMap(lt,lg)
{
	  var latlng1 = new google.maps.LatLng(52.474, -1.868);
	
    var myOptions1 = {
        zoom: 5,
        center: latlng1,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

  
  map = new google.maps.Map(document.getElementById("map_canvas"), myOptions1);
}
function getLatLng()
{
	//$jq('#tweet_list').empty();
	tweet_arr.splice(0,tweet_arr.length);
	
	//alert(setmap_pos[0][0]);
	var place2plot=document.getElementById('loc_search').value;
	var keyword=document.getElementById('txt_search').value;
	 var geocoder1 = new google.maps.Geocoder();
	 geocoder1.geocode( { 'address': place2plot}, function(results, status) {

              if (status == google.maps.GeocoderStatus.OK) {
   			 var latitude1 = results[0].geometry.location.lat();
    		var longitude1 = results[0].geometry.location.lng();
   			 //loc_arr[l].push(results[0].geometry.location);
			 //setMarker(latitude,longitude);
			 setmap_pos.push([latitude1,longitude1]);
			
			 getLocation(latitude1,longitude1,keyword);
			}
			else
			{
				alert("Enter a valid place");
			}
            });
	
}
function setMarker(lat1,lng1,tw_arr,index)
		 {
			 var image = new google.maps.MarkerImage(tw_arr[index][2],
     new google.maps.Size(39, 39),
     new google.maps.Point(0,0),
     new google.maps.Point(39, 39));
    
     var shape = {
          coord: [1, 1, 1, 20, 18, 20, 18 , 1],
          type: 'poly'
     };  
		 var marker2 = new google.maps.Marker({
             position: new google.maps.LatLng(lat1,lng1),
             map: map,
			 
              //shape:shape, 
             icon: image,
             //title:image
			 title:tw_arr[index][1]
			 //title: document.getElementById('txt_search').value,
             //zIndex: hotels[i][3]
         });
		 /* var label = new Label({
               map: map
          });
          label.set('zIndex', 1234);
          label.bindTo('position', marker2, 'position');
          label.set('text', document.getElementById('txt_search').value);
		 */
		 
		 
		 }
google.maps.event.addDomListener(window, 'load', initialize);