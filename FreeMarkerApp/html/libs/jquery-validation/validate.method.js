$(document).ready(function() {
	//數值驗證
	$.validator.addMethod("fixedDecimal", function (value, element, params) {
		var fixed = null;
		if(params && params.fixed){
			fixed = params.fixed;
		}else{
			fixed = 2; //預設2的小數位內
		}
		var regExpression = new RegExp("^\\d+\\.?\\d{0,"+params.fixed+"}$");
		return this.optional(element) || (regExpression.test(value));
	}, "請輸入合法的數值!");
	
	// 下拉框驗證
	$.validator.addMethod("hasItemSelected", function (value, element, params) {
	    var returnValue = false;
	    var selectedIndex = $(element).prop("selectedIndex");
	    if (selectedIndex != 0 || $(element).val().trim()!="") {
	        returnValue = true;
	    }
	    return returnValue;
	},"必須選擇一項");
	
	$.validator.addMethod("IDNumber",
			  function( value, element)
			  {
			  	if(value.length==0) return true;
			    if(value.length!=10) return false;
			    IDN = value;
			    IDTable = {A:10, B:11, C:12, D:13, E:14, F:15, G:16, H:17, I:34, J:18, K:19, M:21, N:22, O:35, P:23, Q:24, T:27, U:28, V:29, W:32, X:30, Z:33, L:20, R:25, S:26, Y:31};
			    LocalDigit = IDTable[IDN[0].toUpperCase()];
			    return /^[A-Za-z][1,2][\d]{8}/.test(value) && ((Math.floor(LocalDigit/10) + (LocalDigit%10)*9 + IDN[1]*8 + IDN[2]*7 + IDN[3]*6 + IDN[4]*5 + IDN[5]*4 + IDN[6]*3 + IDN[7]*2 + IDN[8]*1 + IDN[9]*1)%10==0);
			
			  }
			  , '請輸入有效的身分證字號'
			);
			
		$.validator.addMethod("mobile", function(value, element) { 
				var length = value.length; 
				var mobile = (/^09\d{8}$/);
				return this.optional(element) || (length == 10 && mobile.test(value)); 
				 }, "手機號碼格式錯誤");	

		$.validator.addMethod("chrnum", function(value, element) {
			var chrnum = /^([a-zA-Z0-9]+)$/;
			return this.optional(element) || (chrnum.test(value));
		}, "密碼只能輸入數字和字母(字符AZ, az, 0-9)");
		
		$.validator.addMethod("chinese", function(value, element) { 
				var chinese = /^[\u4e00-\u9fa5]+$/; 
					return this.optional(element) || (chinese.test(value)); 
				 }, "姓名只能輸入中文");  
				 
		$.validator.addMethod("byteRangeLength", function(value, element, param) { 
				var length=value.length; 
				for (var i=0; i < value.length ; i++) { 
					if (value.charCodeAt(i) > 127) { 
					length++; 
					}
				} 
				return this.optional(element) || (length >= param[0] && length <= param[1]); 
			}, $.validator.format("請確保輸入的值在{0}-{1}個字節之間(一個中文字算2個字節)"));
		
		

		   // 字符最小長度驗證（一個中文字符長度為2）
			jQuery.validator.addMethod("stringMinLength", function(value, element, param) {
			var length = value.length;
			for ( var i = 0; i < value.length; i++) {
			if (value.charCodeAt(i) > 127) {
			length++;
			}
			}
			return this.optional(element) || (length >= param);
			}, $.validator.format("長度不能小於{0}!"));
			
			// 字符最大長度驗證（一個中文字符長度為2）
			jQuery.validator.addMethod("stringMaxLength", function(value, element, param) {
			var length = value.length;
			for ( var i = 0; i < value.length; i++) {
			if (value.charCodeAt(i) > 127) {
			length++;
			}
			}
			return this.optional(element) || (length <= param);
			}, $.validator.format("長度不能大於{0}!"));
			
			// 字符驗證
			jQuery.validator.addMethod("stringCheck", function(value, element) {
			return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);
			}, "只能包括中文字、英文字母、數字和下劃線"); 
			
			//密碼驗證
			jQuery.validator.addMethod("pass", function (value, element) {
			    var password = /^((([a-zA-Z0-9])|([^\u4E00-\u9FA5\uf900-\ufa2d]))+)$/;
			    return this.optional(element) || (password.test(value));
			}, "只能輸入數字,字母,特殊字符,不能輸入中文");
			
			// 字符驗證
			jQuery.validator.addMethod("string", function(value, element) {
			return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);}, "不允許包含特殊符號!");
			
			// 必須以特定字符串開頭驗證
			jQuery.validator.addMethod("begin", function(value, element, param) {
			var begin = new RegExp("^" + param);
			return this.optional(element) || (begin.test(value));
			}, $.validator.format("必須以 {0} 開頭!"));
			
			// 驗證兩次輸入值是否不相同
			jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
			return value != $(param).val();
			}, $.validator.format("兩次輸入不能相同!"));
			
			// 驗證值不允許與特定值等於
			jQuery.validator.addMethod("notEqual", function(value, element, param) {
			return value != param;
			}, $.validator.format("輸入值不允許為{0}!"));
			
			// 驗證值必須大於特定值(不能等於)
			jQuery.validator.addMethod("gt", function(value, element, param) {
			return value > param;
			}, $.validator.format("輸入值必須大於{0}!"));
			
			// 驗證值小數位數不能超過兩位
			jQuery.validator.addMethod("decimal", function(value, element) {
			var decimal = /^-?\d+(\.\d{1,2})?$/;
			return this.optional(element) || (decimal.test(value));
			}, $.validator.format("小數位數不能超過兩位!"));
			
			// 電話號碼驗證
			jQuery.validator.addMethod("isTel", function(value, element) {
			var tel = /^\d{3,4}-?\d{7,9}$/; //電話號碼格式010-12345678
			return this.optional(element) || (tel.test(value));
			}, "請正確填寫您的電話號碼");
			
			// 聯繫電話(手機/電話皆可)驗證
			jQuery.validator.addMethod("isPhone", function(value,element) {
			var length = value.length;
			var mobile = /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/;
			var tel = /^\d{3,4}-?\d{7,9}$/;
			return this.optional(element) || (tel.test(value) || mobile.test(value));
			}, "請正確填寫您的聯繫電話");
			
			// 郵政編碼驗證
			jQuery.validator.addMethod("isZipCode", function(value, element) {
			var tel = /^[0-9]{6}$/;
			return this.optional(element) || (tel.test(value));
			}, "請正確填寫您的郵政編碼");
			
			// IP地址驗證
			jQuery.validator.addMethod("ip", function(value, element) {
			    var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
			    return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
			}, "Ip地址格式錯誤");		
	
	
	/**
	 * 验证文章标题是否存在
	 */
	/*
	 $.validator.addMethod("postTitleExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["title"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/postTitleExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	 * */

	/**
	 * 验证文章链接是否存在
	 */
	$.validator.addMethod("postUrlExists", function(value, element, params) {
		/*
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["url"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/postUrlExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
		*/
	});

	/**
	 * 验证文章链接的合法性
	 */
	$.validator.addMethod("postUrlIllegal", function(value, element, params) {
		var data = {};
		data["url"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/postUrlIllegal",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return true;
		} else {
			return false;
		}
	});

	/**
	 * 验证分类名称是否存在
	 */
	$.validator.addMethod("catalogNameExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/catalogNameExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});

	/**
	 * 验证博客地址的合法性
	 */
	$.validator.addMethod("blogurl", function(value, element, params) {
		if (!value.startWith("http://") && !value.startWith("https://")) {
			return false;
		}
		if (value.endWith('/') || value.endWith('\\')) {
			return false;
		}
		return true;
	});

	/**
	 * 检查角色名称是否存在
	 */
	$.validator.addMethod("roleNameExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/roleNameExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});

	////////////////////////////////用户验证 start
	/**
	 * 用户名是否存在
	 */
	$.validator.addMethod("userEmailExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/userEmailExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	
	$.validator.addMethod("userEmailExistsBackend", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/userEmailExistsBackend",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	
	/**
	 * 證件號是否存在
	 */
	$.validator.addMethod("userIdnoExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/userIdnoExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	
	$.validator.addMethod("userIdnoExistsBackend", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/userIdnoExistsBackend",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	}); 
	
	$.validator.addMethod("employeenoExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/employeenoExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	
	$.validator.addMethod("employeenoExistsBackend", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["name"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/employeenoExistsBackend",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	/**
	 * 用户Email是否存在
	 */
	$.validator.addMethod("userEmailExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["email"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/userEmailExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	
	/**
	 * 用户别名是否存在
	 */
	$.validator.addMethod("userAliasExists", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["alias"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/userAliasExists",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	});
	
	/**
	 * 用户病歷號是否存在
	 */
	$.validator.addMethod("userCharnoExistsBackend", function(value, element, params) {
		var data = {};
		if (params.id != undefined) {
			data["id"] = params.id;
		}
		data["charno"] = value;
		var result = $.ajax({
			url : mlog.variable.base + "/common/validate/userCharnoExistsBackend",
			async : false,
			data : data
		}).responseText;
		if (result == "true") {
			return false;
		} else {
			return true;
		}
	}); 
	////////////////////////////////用户验证 end
});
