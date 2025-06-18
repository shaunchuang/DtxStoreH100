/**
 * @author Jian-Hong
 * @since 2016/10/31
 */
if(typeof(mlog) === "undefined"){var mlog = function(){}};
mlog.datatable = {};
$.extend(mlog.datatable, {
	createInstance: function(){
		//定義物件
		var dtObj = {
			dbFunc: null,
			dbTable: null,
			init: function(funcObj, tableObj, resultData){
				this.dbFunc = $(funcObj);
				//初始化dbTable
				if(!this.dbTable){
					this.dbTable = $(tableObj).dataTable({
				    	"oLanguage": {
						  	"sProcessing":   "處理中...",
						    "sLengthMenu":   "顯示_MENU_ 記錄/頁",
						    "sZeroRecords":  "沒有符合的記錄",
						    "sInfo":         "顯示第 _START_ 至 _END_ 項記錄，共 _TOTAL_ 項",
						    "sInfoEmpty":    "顯示第 0 至 0 項記錄，共 0 項",
						    "sInfoFiltered": "(由 _MAX_ 項記錄搜尋)",
						    "sInfoPostFix":  "",
						    "sSearch":       "搜尋:",
						    "oAria": {
						            "sSortAscending": ":  從小到大 ..",
						            "sSortDescending": ": 從大到小 .."
						        },
						    "sEmptyTable":   "",
						    "sUrl":          "",
						    "oPaginate": {
						        "sFirst":    "首頁 ",
						        "sPrevious": "上頁 ",
						        "sNext":     " 下頁",
						        "sLast":     " 末頁"
						    }
						},
				        bJQueryUI: true,
				        "bProcessing": true,
				        "bDestroy": true,
				        "aaData": [],
				        "order": [[1, ""]], 
				        "aoColumnDefs":  this.DTColumnDefs()
				    });
				}
				this.dbTable.fnClearTable();
				if(resultData && resultData.length>0){
					this.dbTable.fnAddData(resultData);
				}
			},
			serialize: function () {
			    var anTrs = this.dbTable.$('tr');
			    var selectedItems = "";
			    //取得Datatable的欄位
			    var tmpColumns = this.dbTable.fnSettings().aoColumns;
			    for (var i = 0; i < anTrs.length; i++) {
			        var aPos = this.dbTable.fnGetPosition(anTrs[i]);
			        var aData = this.dbTable.fnGetData(aPos);
			        for(var j=1; j<tmpColumns.length; j++){
			            selectedItems += aData[tmpColumns[j].mData];
			            if(j<tmpColumns.length-1){
			            	selectedItems += "%2C";
			            }
			        }
			        //selectedItems += aData.attrId + "%2C" + aData.attrKey + "%2C" + aData.attrValue;
			        if (i < anTrs.length - 1) {
			            selectedItems += "%0D%0A";
			        }
			    }
				return selectedItems;
			},
			deleteData: function(){
				var tmpObj = this;
				$(this.dbTable).find(".idSelectCheckbox").each(function (i) {
		            if ($(this).prop('checked')) {
		                var nTr = $(this).parents('tr');
		                tmpObj.dbTable.fnDeleteRow(nTr);
		            }
		        });
			},
			selectAllChk: function(obj){
				if ($(obj).prop('checked')) {
					$(this.dbTable).find(".idSelectCheckbox").each(function () {
		                $(this).prop('checked', true);
		                $(this).trigger("change");
		            });
		        } else {
		        	$(this.dbTable).find(".idSelectCheckbox").each(function () {
		                $(this).prop('checked', false);
		                $(this).trigger("change");
		            });
		        }
			},
			addData: function(){
				//檢查是否合法地新增
		        var tmpTrs = this.dbTable.$('tr'); 
			    for (var i = 0; i < tmpTrs.length; i++) {
			        var tmpPos = this.dbTable.fnGetPosition(tmpTrs[i]);
			        var tmpData = this.dbTable.fnGetData(tmpPos);
		            if(!this.legallyAddDT(tmpData, tmpPos, null)){
		              return;
		            }
			    } 
				
				if(!this.legallyAddDT()) return;
				
				//新增一筆紀錄到datatable
			    var aData = [];
			    aData.push(this.DTGetData(null));
			    this.dbTable.fnAddData(aData);
		        
		        //清除資料
		        this.DTClearInput();
			},
			editData: function(){
				//只能選擇一筆資料做修改
		        if($(this.dbTable).find(".idSelectCheckbox:checked").length!=1){
		              alert('請選擇其中一筆資料!');
		              return;
		         }
		    
		        //設定選擇值
		        var nTr = $(this.dbTable).find(".idSelectCheckbox:checked").parents('tr');
		        var aPos = this.dbTable.fnGetPosition(nTr[0]);
		        var aData = this.dbTable.fnGetData(aPos);
		        this.DTSetInput(aData);
		    
		        if(this.dbFunc){
		        	$(this.dbFunc).find('.dt_menu').hide();
			        $(this.dbFunc).find('.dt_edit').show();
		        }
			},
			updateData: function(){
				//取得選取資料
			    var aTr = $(this.dbTable).find(".idSelectCheckbox:checked").parents('tr');
			    var aPos = this.dbTable.fnGetPosition(aTr[0]);
			    var aData = this.dbTable.fnGetData(aPos);
			    
			    //檢查是否合法地修改
			    var tmpTrs = this.dbTable.$('tr'); 
				for (var i = 0; i < tmpTrs.length; i++) {
				    var tmpPos = this.dbTable.fnGetPosition(tmpTrs[i]);
				    var tmpData = this.dbTable.fnGetData(tmpPos);
			        if(!this.legallyAddDT(tmpData, tmpPos, aPos)){
			        	return;
			        }
				} 
			    
			    //清除選取
				$(this.dbTable).find(".idSelectCheckbox:checked").prop( "checked", false );
			    
			    //取得欄位名稱在Datatable的Index
			    var tmpColumns = this.dbTable.fnSettings().aoColumns;
			    var tmpColumnIndex = {};
			    for(var i=0; i<tmpColumns.length; i++){
			        tmpColumnIndex[tmpColumns[i].mData] = i;
			    }
			    
			    //更新欄位值
			    var tmpData = this.DTGetData(aData);
			    for (var tmpProperty in tmpData) {
			        if (tmpData.hasOwnProperty(tmpProperty)) {
			            this.dbTable.fnUpdate(tmpData[tmpProperty], aPos, tmpColumnIndex[tmpProperty], false, false);
			        }
			    }
			      
			    //清除設定值
			    this.DTClearInput();
			    if(this.dbFunc){
			    	$(this.dbFunc).find('.dt_menu').show();
				    $(this.dbFunc).find('.dt_edit').hide();
			    }
			},
			cancelEdit: function(){
				this.DTClearInput();
				if(this.dbFunc){
					$(this.dbFunc).find('.dt_menu').show();
					$(this.dbFunc).find('.dt_edit').hide();
				}
			    //清除選取
			    $(this.dbTable).find(".idSelectCheckbox:checked").prop( "checked", false );
			},
			//=======以下為使用者需要定義的函數=======
			DTColumnDefs: function(){},
			legallyAddDT: function(tmpData, tmpPos, aPos){},
			DTGetData: function(){},
			DTSetInput: function(){},
			DTClearInput: function(){}
		};
		return dtObj;
	}
});