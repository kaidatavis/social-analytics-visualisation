$(document).ready(function () {
	$('#user-tweets,#user-news-tweets,#news-tweets')
		.on('click', "a", function (event) {
		event.preventDefault();
		$('.frame').css("visibility", "visible").html($("<iframe src='" + this.href + "'></iframe>"));
		$('.arrow').css("visibility", "visible");
	});
	$('.arrow').click(function () {
		$('.frame').find("iframe:last").remove();
		$('.frame,.arrow').css("visibility", "hidden");
	});
});