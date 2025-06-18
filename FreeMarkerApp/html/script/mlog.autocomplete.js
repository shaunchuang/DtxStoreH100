/**
 * @author Jian-Hong
 * chart 2015-06-01
 * Calendar元素的操作和处理
 */
if(typeof(mlog) === "undefined"){var mlog = function(){}};
mlog.autocomplete = {};
$.extend(mlog.autocomplete, {
	isSelectTrigger: false,
	init: function(obj, url, minLength, select, isEditable, postData){
		$(obj).autocomplete({
			source: function(request, response) {
				if (typeof postData == 'undefined') postData = {};
				postData["term"] = request.term;
				$.ajax({
					type: "POST",
					data: postData,
					url: url,
					dataType: "json",
					success: function(data) {
						var array = $.map(data, function(m) {
	                		return {
		                    	label: m.label,
	                    		value: m.value
	                		};
	            		});
	            		response(array.slice(0, 15)); //只顯示其中15筆資料
					}
				});
			},
		    focus: function(event,ui) {
		    	//$(obj).val(ui.item.label);
		    	return false;
		    },
		    change: function(event,ui){
		    	if(!isEditable){
		    		if (ui.item==null){
			    		$(obj).val('');
			    		$(obj).focus();
		            }
		    	}
	        },
			minLength: minLength,
			select: select,
			close: function() {
				if(mlog.autocomplete.isSelectTrigger){
					$(obj).autocomplete("search");
					//$(".ui-autocomplete").show();
					mlog.autocomplete.isSelectTrigger = false;
				}
		    }
		});
		$(obj).click(function(){
			//if(!$(this).val()){
				$(this).autocomplete("search");
			//}
		});
	},
	initMultipleSelect: function(obj, url, minLength, select, isEditable, postData){
		$(obj).autocomplete({
			source: function(request, response) {
				if (typeof postData == 'undefined') postData = {};
				//postData["term"] = request.term;
				$.ajax({
					type: "POST",
					data: postData,
					url: url,
					dataType: "json",
					success: function(data) {
						var array = $.map(data, function(m) {
	                		return {
		                    	label: m.label,
	                    		value: m.value
	                		};
	            		});
	            		response(array.slice(0, 15)); //只顯示其中15筆資料
					}
				});
			},
		    focus: function(event,ui) {
		    	//$(obj).val(ui.item.label);
		    	return false;
		    },
		    change: function(event,ui){
		    	if(!isEditable){
		    		if (ui.item==null){
			    		$(obj).val('');
			    		$(obj).focus();
		            }
		    	}
	        },
			minLength: minLength,
			select: select,
			close: function() {
				if(mlog.autocomplete.isSelectTrigger){
					$(obj).autocomplete("search");
					//$(".ui-autocomplete").show();
					mlog.autocomplete.isSelectTrigger = false;
				}
		    }
		});
		$(obj).click(function(){
			//if(!$(this).val()){
				$(this).autocomplete("search");
			//}
		});
	}
});