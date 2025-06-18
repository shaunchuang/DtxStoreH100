<#--
//********************************************************************
// @author: gaoyb(www.mspring.org)
// @Revision: 1.1 $, $Date: 2012/07/20 11:24:03 $
//******************************************************************** 
-->

<#--
/*
 *分頁條
 */
-->
<#macro pagingnavigator page="" form_id="" >
	<style type="text/css">
	/* 分頁欄樣式 */
	div.pagger {
		PADDING-RIGHT: 3px;
		PADDING-LEFT: 3px;
		PADDING-BOTTOM: 3px;
		MARGIN: 3px;
		PADDING-TOP: 3px;
		TEXT-ALIGN: center;
		FONT-SIZE: 12px;
		FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;
		PADDING-LEFT: 25px;
		float: right;
	}
	
	div.pagger A {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #036cb4;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid;
		TEXT-DECORATION: none
	}
	
	div.pagger A:hover {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger A:active {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger .current {
		BORDER-RIGHT: #036cb4 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #036cb4 1px solid;
		PADDING-LEFT: 5px;
		FONT-WEIGHT: bold;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #036cb4 1px solid;
		COLOR: #fff;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #036cb4 1px solid;
		BACKGROUND-COLOR: #036cb4
	}
	
	div.pagger .disabled {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #ddd;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid
	}
	</style>
	<div class="pagger">
		<#if ((page.getPageNo()) > 1)>
			<span><a href="javascript:changePage('${form_id}', 1);">首頁</a></span>
			<span><a href="javascript:changePage('${form_id}', ${page.getPageNo() - 1});">上一頁</a></span>
		<#else>
			<span class="disabled">首頁</span>
			<span class="disabled">上一頁</span>
		</#if>
		
		<#if (page.getPageNo() < page.getTotalPages())>
			<span><a href="javascript:changePage('${form_id}', ${page.getPageNo() + 1});">下一頁</a></span>
			<span><a href="javascript:changePage('${form_id}', ${page.getTotalPages()});">末頁</a></span>
		<#else>
			<span class="disabled">下一頁</span>
			<span class="disabled">末頁</span>
		</#if>
		<span>共${page.getTotalCount()}筆,第</span>
		<input type="text" class="textinput" style="width:3em;" name="goPageNumber" value="${page.getPageNo()}"/>
		<span>/${page.getTotalPages()}頁 <a href="javascript:changePageCustom('${form_id}');">GO</a></span>
	</div>
	<script type="text/javascript">
		function changePage(formId, pageNumber) {
			if (pageNumber < 1) {
		        pageNumber = 1;
			}
			var pageNoObj = document.getElementById("pageNo");
			if (pageNoObj) {
			    pageNoObj.value = pageNumber;
			}
			if(typeof pageIdentifier != 'undefined' && pageIdentifier === "ADMIN")
				document.getElementById(formId).submit();
			else
				wg.template.submitForm(formId);
		}
		function changePageCustom(formId) {
		    var pageNumbers = document.getElementsByName('goPageNumber')[0].value;	
		    if (!jQuery.isNumeric(pageNumbers)){
				custom_alert("換頁值必須輸入數字!!","換頁值不正確");
				return;
			} 
		    /*
		    var totalPages = document.getElementsByName('totalPages');
		    if (parseInt(totalPages)<parseInt(pageNumbers)){
				custom_alert("換頁值必須小於總頁數!!","換頁值不正確");
				return;
			} 
		    
		    if (!pageNumbers) {
		        changePage(formId, 1);
		    }
		    var pageNumber = 1;
		    for (var i = 0; i < pageNumbers.length; i++) {
		        if (pageNumber < pageNumbers[i].value) {
		            pageNumber = pageNumbers[i].value;
		        }
		    }
		    */
		    changePage(formId, pageNumbers);
		}
	</script>
</#macro>

<#macro subpagingnavigator page="" form_id="" callback="" >
	<style type="text/css">
	/* 分頁欄樣式 */
	div.pagger {
		PADDING-RIGHT: 3px;
		PADDING-LEFT: 3px;
		PADDING-BOTTOM: 3px;
		MARGIN: 3px;
		PADDING-TOP: 3px;
		TEXT-ALIGN: center;
		FONT-SIZE: 12px;
		FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;
		PADDING-LEFT: 25px;
		float: right;
	}
	
	div.pagger A {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #036cb4;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid;
		TEXT-DECORATION: none
	}
	
	div.pagger A:hover {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger A:active {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger .current {
		BORDER-RIGHT: #036cb4 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #036cb4 1px solid;
		PADDING-LEFT: 5px;
		FONT-WEIGHT: bold;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #036cb4 1px solid;
		COLOR: #fff;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #036cb4 1px solid;
		BACKGROUND-COLOR: #036cb4
	}
	
	div.pagger .disabled {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #ddd;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid
	}
	</style>
	<div class="pagger">
		<#if ((page.getPageNo()) > 1)>
			<#if callback?exists && (callback?length>0) >
				<span><a href="javascript:change_page('${form_id}', 1, ${callback});">首頁</a></span>
				<span><a href="javascript:change_page('${form_id}', ${page.getPageNo() - 1}, ${callback});">上一頁</a></span>
			<#else>
				<span><a href="javascript:change_page('${form_id}', 1);">首頁</a></span>
				<span><a href="javascript:change_page('${form_id}', ${page.getPageNo() - 1});">上一頁</a></span>
			</#if>
		<#else>
			<span class="disabled">首頁</span>
			<span class="disabled">上一頁</span>
		</#if>
		
		<#if (page.getPageNo() < page.getTotalPages())>
			<#if callback?exists && (callback?length>0) >
				<span><a href="javascript:change_page('${form_id}', ${page.getPageNo() + 1}, ${callback});">下一頁</a></span>
				<span><a href="javascript:change_page('${form_id}', ${page.getTotalPages()}, ${callback});">末頁</a></span>
			<#else>
				<span><a href="javascript:change_page('${form_id}', ${page.getPageNo() + 1});">下一頁</a></span>
				<span><a href="javascript:change_page('${form_id}', ${page.getTotalPages()});">末頁</a></span>
			</#if>
		<#else>
			<span class="disabled">下一頁</span>
			<span class="disabled">末頁</span>
		</#if>
		<span>共${page.getTotalCount()}筆,第</span>
		<input type="text" class="textinput" style="width:3em;" name="goPageNumber" value="${page.getPageNo()}"/>
		<#if callback?exists && (callback?length>0) >
			<span>/${page.getTotalPages()}頁 <a href="javascript:change_pageCustom('${form_id}', ${callback});">GO</a></span>
		<#else>
			<span>/${page.getTotalPages()}頁 <a href="javascript:change_pageCustom('${form_id}');">GO</a></span>
		</#if>
	</div>
	<script type="text/javascript">
		function custom_alert(msg){
			alert(msg);
		}
		
		function change_page(formId, pageNumber, callback) {
			if (pageNumber < 1) {
		        pageNumber = 1;
			}
			if(pageNumber){
				$("#pageNo").val(pageNumber);
				mlog.form.ajaxLoadElement($("#"+formId).attr('action'), formId, callback);
			}
		}
		function change_pageCustom(formId, callback) {
		    var pageNumbers = document.getElementsByName('goPageNumber')[0].value;	
		    if (!jQuery.isNumeric(pageNumbers)){
				custom_alert("換頁值必須輸入數字!!","換頁值不正確");
				return;
			} 
		    change_page(formId, pageNumbers, callback);
		}
	</script>
</#macro>

<#macro pagingnavigator2 page="" form_id="" >
	<style type="text/css">
	/* 分頁欄樣式 */
	div.pagger {
		PADDING-RIGHT: 3px;
		PADDING-LEFT: 3px;
		PADDING-BOTTOM: 3px;
		MARGIN: 3px;
		PADDING-TOP: 3px;
		TEXT-ALIGN: center;
		FONT-SIZE: 12px;
		FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;
		PADDING-LEFT: 25px;
		float: right;
	}
	
	div.pagger A {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #036cb4;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid;
		TEXT-DECORATION: none
	}
	
	div.pagger A:hover {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger A:active {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger .current {
		BORDER-RIGHT: #036cb4 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #036cb4 1px solid;
		PADDING-LEFT: 5px;
		FONT-WEIGHT: bold;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #036cb4 1px solid;
		COLOR: #fff;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #036cb4 1px solid;
		BACKGROUND-COLOR: #036cb4
	}
	
	div.pagger .disabled {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #ddd;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid
	}
	</style>
	<div class="page2">
		<span class="page2-info">共 ${page.getTotalCount()} 筆，第 ${page.getPageNo()} / ${page.getTotalPages()} 頁</span>
		<div class="page2-nav">
			<#if ((page.getPageNo()) > 1)>
				<#if callback?exists && (callback?length>0) >
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', 1, ${callback});">首頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() - 1}, ${callback});">上一頁</button>
				<#else>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', 1);">首頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() - 1});">上一頁</button>
				</#if>
			<#else>
				<button type="button" class="btn-style btn3 disabled">首頁</button>
				<button type="button" class="btn-style btn3 disabled">上一頁</button>
			</#if>
			<#if (page.getPageNo() < page.getTotalPages())>
				<#if callback?exists && (callback?length>0) >
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() + 1}, ${callback});">下一頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getTotalPages()}, ${callback});">末頁</button>
				<#else>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() + 1});">下一頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getTotalPages()});">末頁</button>
				</#if>
			<#else>
				<button type="button" class="btn-style btn3 disabled">下一頁</button>
				<button type="button" class="btn-style btn3 disabled">末頁</button>
			</#if>
		</div>
		<div class="page2-go">
			跳轉至第<input type="text" name="goPageNumber" value="${page.getPageNo()}" style="width:20px;"/>頁
			<#if callback?exists && (callback?length>0) >
				<input type="button" value="前往" class="btn-style btn1" onclick="change_pageCustom('${form_id}', ${callback});">
			<#else>
				<input type="button" value="前往" class="btn-style btn1" onclick="change_pageCustom('${form_id}');">
			</#if>
		</div>
	</div>
	<script type="text/javascript">
		function changePage(formId, pageNumber) {
			if (pageNumber < 1) {
		        pageNumber = 1;
			}
			var pageNoObj = document.getElementById("pageNo");
			if (pageNoObj) {
			    pageNoObj.value = pageNumber;
			}
			document.getElementById(formId).submit();
		}
		function changePageCustom(formId) {
		    var pageNumbers = document.getElementsByName('goPageNumber')[0].value;	
		    if (!jQuery.isNumeric(pageNumbers)){
				custom_alert("換頁值必須輸入數字!!","換頁值不正確");
				return;
			} 
		    /*
		    var totalPages = document.getElementsByName('totalPages');
		    if (parseInt(totalPages)<parseInt(pageNumbers)){
				custom_alert("換頁值必須小於總頁數!!","換頁值不正確");
				return;
			} 
		    
		    if (!pageNumbers) {
		        changePage(formId, 1);
		    }
		    var pageNumber = 1;
		    for (var i = 0; i < pageNumbers.length; i++) {
		        if (pageNumber < pageNumbers[i].value) {
		            pageNumber = pageNumbers[i].value;
		        }
		    }
		    */
		    changePage(formId, pageNumbers);
		}
	</script>
</#macro>

<#macro subpagingnavigator2 page="" form_id="" callback="" >
	<style type="text/css">
	/* 分頁欄樣式 */
	div.pagger {
		PADDING-RIGHT: 3px;
		PADDING-LEFT: 3px;
		PADDING-BOTTOM: 3px;
		MARGIN: 3px;
		PADDING-TOP: 3px;
		TEXT-ALIGN: center;
		FONT-SIZE: 12px;
		FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;
		PADDING-LEFT: 25px;
		float: right;
	}
	
	div.pagger A {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #036cb4;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid;
		TEXT-DECORATION: none
	}
	
	div.pagger A:hover {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger A:active {
		BORDER-RIGHT: #999 1px solid;
		BORDER-TOP: #999 1px solid;
		BORDER-LEFT: #999 1px solid;
		COLOR: #666;
		BORDER-BOTTOM: #999 1px solid
	}
	
	div.pagger .current {
		BORDER-RIGHT: #036cb4 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #036cb4 1px solid;
		PADDING-LEFT: 5px;
		FONT-WEIGHT: bold;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #036cb4 1px solid;
		COLOR: #fff;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #036cb4 1px solid;
		BACKGROUND-COLOR: #036cb4
	}
	
	div.pagger .disabled {
		BORDER-RIGHT: #eee 1px solid;
		PADDING-RIGHT: 5px;
		BORDER-TOP: #eee 1px solid;
		PADDING-LEFT: 5px;
		PADDING-BOTTOM: 2px;
		MARGIN: 2px;
		BORDER-LEFT: #eee 1px solid;
		COLOR: #ddd;
		PADDING-TOP: 2px;
		BORDER-BOTTOM: #eee 1px solid
	}
	</style>
	<div class="page2">
		<span class="page2-info">共 ${page.getTotalCount()} 筆，第 ${page.getPageNo()} / ${page.getTotalPages()} 頁</span>
		<div class="page2-nav">
			<#if ((page.getPageNo()) > 1)>
				<#if callback?exists && (callback?length>0) >
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', 1, ${callback});">首頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() - 1}, ${callback});">上一頁</button>
				<#else>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', 1);">首頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() - 1});">上一頁</button>
				</#if>
			<#else>
				<button type="button" class="btn-style btn3 disabled">首頁</button>
				<button type="button" class="btn-style btn3 disabled">上一頁</button>
			</#if>
			<#if (page.getPageNo() < page.getTotalPages())>
				<#if callback?exists && (callback?length>0) >
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() + 1}, ${callback});">下一頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getTotalPages()}, ${callback});">末頁</button>
				<#else>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getPageNo() + 1});">下一頁</button>
					<button type="button" class="btn-style btn3" onclick="change_page('${form_id}', ${page.getTotalPages()});">末頁</button>
				</#if>
			<#else>
				<button type="button" class="btn-style btn3 disabled">下一頁</button>
				<button type="button" class="btn-style btn3 disabled">末頁</button>
			</#if>
		</div>
		<div class="page2-go">
			跳轉至第<input type="text" name="goPageNumber" value="${page.getPageNo()}" style="width:20px;" onkeypress="return keypress_enter(event);"/>頁
			<#if callback?exists && (callback?length>0) >
				<input type="button" value="前往" class="btn-style btn1" onclick="change_pageCustom('${form_id}', ${callback});">
			<#else>
				<input type="button" value="前往" class="btn-style btn1" onclick="change_pageCustom('${form_id}');">
			</#if>
		</div>
	</div>
	<script type="text/javascript">
		function custom_alert(msg){
			alert(msg);
		}
		
		function keypress_enter(e) {
		    if (e.keyCode == 13) { 
		        return false;
		    }
		}
		
		function change_page(formId, pageNumber, callback) {
			if (pageNumber < 1) {
		        pageNumber = 1;
			}
			if(pageNumber){
				$("#pageNo").val(pageNumber);
				mlog.form.ajaxLoadElement($("#"+formId).attr('action'), formId, callback);
			}
		}
		function change_pageCustom(formId, callback) {
		    var pageNumbers = document.getElementsByName('goPageNumber')[0].value;	
		    if (!jQuery.isNumeric(pageNumbers)){
				custom_alert("換頁值必須輸入數字!!","換頁值不正確");
				return;
			} 
		    change_page(formId, pageNumbers, callback);
		}
	</script>
</#macro>

<#macro subpagingnavigator3 page="" form_id="" callback="" >
	<ul class="pagination pagination-sm no-margin pull-right">
		<#if (page.getPageNo() > 1)>
			<li><a href="javascript:void(0);" onclick="change_page('${form_id}', 1<#if callback?exists && (callback?length>0) >, ${callback}</#if>);" data-original-title="首頁" data-toggle="tooltip">&laquo;</a></li>
			<li><a href="javascript:void(0);" onclick="change_page('${form_id}', ${page.getPageNo() - 1}<#if callback?exists && (callback?length>0) >, ${callback}</#if>);" data-original-title="上一頁" data-toggle="tooltip">&lt;</a></li>
		</#if>
		<#assign offset=3 />
		<#if (page.getTotalPages()>0)>
			<#if (page.getPageNo()-offset<1)><#assign pagestart=1 /><#else><#assign pagestart=(page.getPageNo()-offset) /></#if>
			<#if (page.getPageNo()+offset>page.getTotalPages())><#assign pageend=page.getTotalPages() /><#else><#assign pageend=(page.getPageNo()+offset) /></#if>
			<#list pagestart..pageend as pageindex>
				<li <#if pageindex==page.getPageNo()>class="active"</#if>><a href="javascript:void(0);" onclick="change_page('${form_id}', ${pageindex}<#if callback?exists && (callback?length>0) >, ${callback}</#if>);">${pageindex}</a></li>
			</#list>
		</#if>
		<#if (page.getPageNo() < page.getTotalPages())>
			<li><a href="javascript:void(0);" onclick="change_page('${form_id}', ${page.getPageNo() + 1}<#if callback?exists && (callback?length>0) >, ${callback}</#if>);" data-original-title="下一頁" data-toggle="tooltip">&gt;</a></li>
			<li><a href="javascript:void(0);" onclick="change_page('${form_id}', ${page.getTotalPages()}<#if callback?exists && (callback?length>0) >, ${callback}</#if>);" data-original-title="末頁" data-toggle="tooltip">&raquo;</a></li>
		</#if>
	</ul>
	<script type="text/javascript">
		function custom_alert(msg){
			alert(msg);
		}
		
		function keypress_enter(e) {
		    if (e.keyCode == 13) { 
		        return false;
		    }
		}
		
		function change_page(formId, pageNumber, callback) {
			if (pageNumber < 1) {
		        pageNumber = 1;
			}
			if(pageNumber){
				$("#pageNo").val(pageNumber);
				mlog.form.ajaxLoadElement($("#"+formId).attr('action'), formId, callback);
			}
		}
		function change_pageCustom(formId, callback) {
		    var pageNumbers = document.getElementsByName('goPageNumber')[0].value;	
		    if (!jQuery.isNumeric(pageNumbers)){
				custom_alert("換頁值必須輸入數字!!","換頁值不正確");
				return;
			} 
		    change_page(formId, pageNumbers, callback);
		}
	</script>
</#macro>

<#--
列循環時獲取屬性值
-->
<#macro fieldValue value attribute="" >
	<#assign currentElement = value />
	<#list attribute?split(".") as x>
		<#if x?exists>
			<#if x_has_next>
				<#assign currentElement=value['${x}'] />
			<#else>
				<#assign currentElement=currentElement['${x}']!"" />
				<#if currentElement?is_boolean>
					${currentElement?string('是','否')}
				<#else>
					${currentElement}
				</#if>
			</#if>
		</#if>
	</#list>
</#macro>


<#macro show_errors>
	<#if (errors?exists && errors.errors?exists && errors.errors?size > 0)>
		<#assign msg = "" />
		<#list errors.errors as error>
			<#assign msg = msg +  error.message + "<br />" />
		</#list>
		<#if (msg?exists && msg?length > 0)>
			<script type="text/javascript">mlog.dialog.tip('${msg}');</script>
		</#if>
	</#if>
</#macro>

<#macro sub_string content from=0 to=0 suffix="">
	<#if (content!?length > 0 && from < content?length)>
		<#if (to > 0 && content?length > (to + 1) )>
			${content?substring(from, to)}${suffix}
		<#else>
			${content?substring(from)}
		</#if>
	</#if>
</#macro>