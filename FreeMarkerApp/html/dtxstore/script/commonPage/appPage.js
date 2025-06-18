$(document).ready(function () {
	
    // 處理輪播
	var myCarousel = document.querySelector('#carouselIndicators')
	var carousel = new bootstrap.Carousel(myCarousel, {
		interval: 20000,
		wrap: true
	})

	// 處理頁籤點擊事件
	$('.app-nav-tab').click(function () {
	    $('.app-nav-tab').removeClass('active');
	    $(this).addClass('active');
	
	    var tabId = $(this).data('tab');
	
	    // 只隱藏非 os-tab-pane 的 tab-content
	    $('.tab-content').addClass('hidden');
	    $('#' + tabId).removeClass('hidden');
	});

	$('.analytic-buttons button').click(function () {
		$('.analytic-buttons button').removeClass('active');
		$(this).addClass('active');
	});

});