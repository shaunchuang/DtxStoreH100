$(document).ready(function() {
    $(".leftnav-title").click(function(e) {
        e.preventDefault();
        var $dropdown = $(this).next(".leftnav-dropdown");
        $dropdown.slideToggle("fast");

        // 切換圖示
        var $icon = $(this).find("i");
        if ($icon.hasClass("fa-chevron-down")) {
            $icon.removeClass("fa-chevron-down").addClass("fa-chevron-up");
        } else {
            $icon.removeClass("fa-chevron-up").addClass("fa-chevron-down");
        }
    });
});