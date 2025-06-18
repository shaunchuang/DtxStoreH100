/**
 * @author GaoYoubo
 * @modifier Jian-Hong
 * @since 2012-09-25
 * dialog元素的操作和处理
 */
if(typeof(mlog) === "undefined"){var mlog = function(){}};
mlog.dialog = {};
mlog.dialog.obj = null;
$.extend(mlog.dialog, {
	open: function(obj){
		if(!obj)	$('#'+dialogID).dialog("open");
		else		$(mlog.dialog.obj).dialog("open");
	},
	init: function(obj, openFunction, closeFunction){
		if($(obj).attr('width')) width = $(obj).attr('width');
		else					 width = "400";
		if($(obj).attr('height')) height = $(obj).attr('height');
		else					 height = "600";
		mlog.dialog.obj = $(obj).dialog({
			autoOpen: false,
			title: $(obj).attr('title'),
			hide: "explode",
			modal: true,
			width: width,
			height: height,
			close: closeFunction,
			open: openFunction
		});
	}
});