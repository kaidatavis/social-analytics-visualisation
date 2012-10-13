$(document).ready(function () {
/*	$('#date').datepicker({ minDate: -7, maxDate: "0D",dateFormat:"yy-mm-dd" });
*/$country = $("select[name='Country']");
$channel = $("select[name='News Channel']");
$date=$('#date');
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
	 if ($(this).val() == "Greece") {
		$("select[name='News Channel'] option").remove();
		$("<option>Skai gr</option>").appendTo($channel);
		$("<option>Ert</option>").appendTo($channel);
	}	
	 if ($(this).val() == "Romania") {
		$("select[name='News Channel'] option").remove();
		$("<option>Realitatea TV</option>").appendTo($channel);
	}	
	 if ($(this).val() == "Italy") {
		$("select[name='News Channel'] option").remove();
		$("<option>RaiTv</option>").appendTo($channel);
	}
	 if ($(this).val() == "Spain") {
		$("select[name='News Channel'] option").remove();
		$("<option>Television Española</option>").appendTo($channel);
	}
		});
});		
function get(){
	 if ($country.val() == "Select country") {
		 alert("Select a country first");
		 return false;
	 }
	 if ($channel.val() == "News channels") {
		 alert("Select a channel");
		 
	 }else if ($channel.val()=="Reuters"){
		 var query="reuters";
		 var user_id="1652541";
	  var count=$("#tweets-select option:selected").val();
	  call_tweets(query,user_id,count);
	  
	 }else if ($channel.val()=="Sky news"){
		 var query='skyNews';
		 var user_id="7587032";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }else if ($channel.val()=="Euronews"){
		 var query='euronews';
		 var user_id="25067168";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }else if ($channel.val()=="Guardian"){
		 var query="guardian";
		 var user_id="87818409";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
		 
	 }else if ($channel.val()=="The Independent"){
		 var query="Independent";
		 var user_id="16973333";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
	 }
else if ($channel.val()=="France 24"){
		 var query='FRANCE24';
		 var user_id="1994321";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }
else if ($channel.val()=="Skai gr"){
		 var query='skaigr';
		 var user_id="17389650";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }
else if ($channel.val()=="Ert"){
		 var query='ErtSocial';
		 var user_id="297963914";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }
else if ($channel.val()=="Realitatea TV"){
		 var query='Realitatea TV';
		 var user_id="14237594";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }
else if ($channel.val()=="RaiTv"){
		 var query='RaiTv';
		 var user_id="44926477";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }
else if ($channel.val()=="Television Española"){
		 var query='TVE';
		 var user_id="487666345";
	  var count=$("#tweets-select option:selected").val();
		 call_tweets(query,user_id,count);
							 
	 }
}
