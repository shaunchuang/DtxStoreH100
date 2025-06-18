/**
 * @author GaoYoubo
 * @since 2012-11-16
 * mlog.stat mlog统计函数
 */
if ( typeof (mlog) === "undefined") {
	var mlog = function() {
	}
};

mlog.stat = {};

$.extend(mlog.stat, {
	postClick : function(post_id) {
		if (!post_id) {
			return;
		}
		$.get(mlog.variable.base + "/ctrl/stat?cmd=post_click&post_id=" + post_id);
	},
	adClick : function(ad_id) {
		if (!ad_id) {
			return;
		}
		$.get(mlog.variable.base + "/ctrl/stat?cmd=ad_click&ad_id=" + ad_id);
	},
	blogClick : function() {
		$.get(mlog.variable.base + "/ctrl/stat?cmd=blog_click");
	}
});
