/**
 * @author GaoYoubo
 * @since 2012-09-25
 * form元素的操作和處理
 */
if(typeof(mlog) === "undefined"){var mlog = function(){}};
mlog.form = {};
$.extend(mlog.form, {
	
   /**
	 * 選擇選擇所有checkbox
	 */
	checkAll : function(obj, cbName) {
		var array = $(obj).closest("table").find('[name="'+cbName+'"]');
		for ( var i = 0; i < array.length; i++) {
			array[i].checked = obj.checked;
		}
	},

	/**
	 * 當只選擇當前checkbox時， 返回當前checkbox的值
	 */
	checkThis : function(_this, cbName) {
		var array = document.getElementsByName(cbName);
		var count = 0;
		for ( var i = 0; i < array.length; i++) {
			if (array[i].checked) {
				count++;
			}
			if (count >= 2) {
				return;
			}
		}
		return _this.value;
	}, 
	
	/**
	 * 提交form
	 * @param {} formId
	 * @param {} action
	 */
	submitForm : function(formId, action){
		var form = document.getElementById(formId);
		if (action) {
			form.action = action;
		}
		form.submit();
	},
	
	confirmSubmit : function(formId, action, msg) {
		var default_msg = '確認刪除選擇項嗎？';
		if (msg) {
			default_msg = msg;
		}
		if (confirm(default_msg)) {
			mlog.form.submitForm(formId, action);
		}
	},
	
	/**
	 * form表單驗證
	 * @param {} conf.selector form選擇器
	 * @param {} conf.showErrors showErrors方法
	 * @param {} conf.success 驗證成功後的回調函數
	 * @param {} conf.onfocusout 失去焦點時是否驗證 默認：false
	 * @param {} conf.onkeyup 輸入時是否驗證 默認：false
	 * @param {} conf.onclick 點擊時是否驗證 默認：false
	 * @param {} conf.errorLabelContainer
	 * @param {} conf.wrapper
	 */
	validate : function(conf){
		if(conf === undefined || conf.selector === undefined) return;
		
		if(conf.onfocusout === undefined){conf.onfocusout = false}
		if(conf.onkeyup === undefined){conf.onkeyup = false}
		if(conf.onclick === undefined){conf.onclick = false}
		
		$.metadata.setType("attr", "validate");
		$(conf.selector).validate({
			success : conf.success,
			showErrors: conf.showErrors,
			errorLabelContainer : $(conf.errorLabelContainer),
			wrapper: conf.wrapper,
			onfocusout : conf.onfocusout,
			onkeyup : conf.onkeyup,
			onclick : conf.onclick,
			showErrors : conf.showErrors,
			errorPlacement: conf.errorPlacement,
			errorClass: conf.errorClass,
			highlight: conf.highlight
		});
	},
	
	ajaxLoadElement: function(url, elementId, callback){
		var element = $('#'+elementId);
		mlog.form.maskElement(element);
		$.ajax({
			type: "POST",
			url:  url,
			data: element.serialize(),
			success: function(data){
				element.replaceWith(data);
				if(callback && typeof(callback) == "function"){
					callback();
				}
				mlog.form.unmaskElement(element);
			}
		});
	},
	
	maskElement: function(obj){
		/*
		if(typeof(mask)!="function" || typeof(unmask)!="function"){
			//動態加入javascript file
			$.ajax({
				url: "${base}/script/jquery-loadmask/loadmask.js",
				dataType: "script",
				async: false
			});
			//動態加入css file
			$('<link/>', {
					rel: 'stylesheet',
					type: 'text/css',
					href: '${base}/script/jquery-loadmask/loadmask.css'
			}).appendTo('head');
		}
		obj.mask("請稍候...");
		*/
	},
	
	unmaskElement: function(obj){
		/*
		obj.ummask();
		*/
	}
});