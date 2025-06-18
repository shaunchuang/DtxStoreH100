<#include "../../login/widget/dtx-login-header.ftl" />
	<main class="d-flex">
	    <#include "../../login/widget/dtx-login-leftnav.ftl" />
	    <div class="col-xxl-10 right-container">
			<link href="/html/dtxstore/style/datatables.min.css" rel="stylesheet">
	        <div class="list-container">
	            <div class="list-header mb-3">
	                <h5 class="lesson-table-title">我的教案總表</h5>
	            </div>
	            <div class="card">
	                <div class="card-body shadow">
						<div class="input-group mb-3">
						    <label class="input-group-text bg-light text-dark border-end-0" for="filter-level">進階搜尋</label>
						    <select class="form-select w-25" id="filter-level">
						        <option selected>全部</option>
						        <option value="1">審核中</option>
						        <option value="2">已審核</option>
						    </select>
						    <div class="input-keyword pe-3">
						    	<input type="text" class="form-control" id="search-keyword" placeholder="請輸入關鍵字">
						    </div>
						    <button class="btn" id="search-btn"><i class="fas fa-search"></i> 搜尋</button>
						</div>
	                    <table class="table table-bordered table-hover" id="lesson-table">
	                        <thead class="table-light">
	                            <tr>
	                                <th scope="col">教案ID</th>
	                                <th scope="col">教案名稱</th>
	                                <th scope="col">適應症</th>
	                                <th scope="col">成就數量</th>
	                                <th scope="col">統計項次</th>
	                                <th scope="col">配發次數</th>
	                                <th scope="col">評論等級</th>
	                                <th scope="col">審核狀態</th>
	                                <th scope="col">功能項</th>
	                            </tr>
	                        </thead>
	                        <tbody>
								<!--
	                            <tr>
	                                <td><div class="td-center">01</div></td>
	                                <td>2024/04/06</td>
	                                <td>注意力不足過動症(ADHD)</td>
	                                <td>104</td>
	                                <td>0</td>
	                                <td>4</td>
	                                <td>4.5</td>
	                                <td>
	                                	<div class="td-center">
	                                		<span class="badge bg-warning text-dark">審核中</span>
	                                	</div>
	                                </td>
	                                <td>
	                                	<div class="func-button-group"> 
		                                    <button class="btn btn-outline-primary btn-sm" data-tooltip="編輯"><i class="fas fa-edit"></i></button>
		                                    <button class="btn btn-outline-danger btn-sm" data-tooltip="刪除"><i class="fas fa-trash-alt"></i></button>
	                                    </div>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td><div class="td-center">02</div></td>
	                                <td>2024/04/19</td>
	                                <td>注意力不足過動症(ADHD)</td>
	                                <td>104</td>
	                                <td>0</td>
	                                <td>4</td>
	                                <td>4.5</td>
	                                <td>
	                                	<div class="td-center">
	                                		<span class="badge bg-success">已審核</span>
	                                	</div>
	                                </td>
	                                <td class="func-button-group">
	                                    <button class="btn btn-outline-primary btn-sm" data-tooltip="編輯"><i class="fas fa-edit"></i></button>
	                                    <button class="btn btn-outline-danger btn-sm" data-tooltip="刪除"><i class="fas fa-trash-alt"></i></button>
	                                </td>
	                            </tr>
								-->
	                        </tbody>
	                    </table>
	                </div>
	            </div>
	        </div>
	    </div>
	</main>
	<script src="/html/dtxstore/script/commonPage/jQuery.js"></script>
	<script src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
	<script src="/html/dtxstore/script/commonPage/leftnav.js"></script>
	<script src="/html/dtxstore/script/datatables.min.js"></script>
	<script src="/html/dtxstore/script/commonPage/swal2.js"></script>
	<script>

	let userId;
	<#if currentUser??>
	userId = ${currentUser.id};
	console.log('userId', userId);
	</#if>

	$(document).ready(function () {
		// 初始化 DataTables
		initDataTable();

	});

	function initDataTable() {
		$('#lesson-table').DataTable({
			// 設定資料取得方式
			ajax: {
				url: '/LessonMainInfo/api/manageList',
				type: 'POST',
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				data: function () {
					return JSON.stringify({ "userId": userId });
				},

				error: function (xhr, error, thrown) {
					console.error("狀態碼:", xhr.status);
					console.error("錯誤訊息:", error);
					console.error("詳細資訊:", thrown);
				}
			},
			columns: [
				{ data: 'id' },
				{ data: 'lessonName' },
				{ data: 'disease' },
				{ data: 'achievementCount' },
				{ data: 'statCount' },
				{ data: 'distributionCount' },
				{ 
					data: 'commentLevel',
					render: function(data, type, row) {
						// 檢查 data 是否為有效數字
						if (data === null || data === undefined || isNaN(data) || data == 0) {
							return '尚無評分';
						}
						return parseFloat(data).toFixed(1); // 將數字四捨五入到一位小數
					}
				},
				{
					data: 'status',
					// 如果要顯示不同標籤可在這自訂格式
					render: function (data, type, row) {
						
						if (data === 'PUBLISHED') {
							return '<div class="td-center"><span class="badge bg-success">已審核</span></div>';
						} else {
							return '<div class="td-center"><span class="badge bg-warning text-dark">未審核</span></div>';
						}
					}
				},
				{
					data: null,
					render: function (data, type, row) {
						// data 即為整個該列的物件，可從 row 拿到想要的欄位
						return `
						<div class="func-button-group">
							<button class="btn btn-outline-primary btn-sm" data-tooltip="編輯" onclick="editLesson(`+ row.id + `)">
								<i class="fas fa-edit"></i>
							</button>
							<button class="btn btn-outline-danger btn-sm" data-tooltip="刪除" onclick="deleteLesson(`+ row.id +`, '`+ row.lessonName +`')">
								<i class="fas fa-trash-alt"></i>
							</button>
						</div>
						`;
					}
				}
			],
			language: {
                    url: "/html/dtxstore/script/zh-HANT.json"
                }
			// 可自行設定 DataTables 的其他選項，例如：
			// paging: true,
			// searching: true,
			// ordering: true,
		});
	}

	// 編輯、刪除按鈕的事件 (示範)
	function editLesson(lessonId) {
		window.location.href = "/ftl/dtxstore/manage/edit?lessonId="+lessonId;
	}

	function deleteLesson(lessonId, lessonName) {
		Swal.fire({
			title: "請輸入教案名稱以確認刪除",
			text: "請輸入：" + lessonName,
			input: "text",
			inputPlaceholder: "請輸入教案名稱",
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#d33",
			cancelButtonColor: "#3085d6",
			confirmButtonText: "確定刪除",
			cancelButtonText: "取消",
			inputValidator: (value) => {
				if (!value) {
					return "請輸入教案名稱！";
				} else if (value !== lessonName) {
					return "輸入的名稱與教案名稱不符！";
				}
			}
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					url: "/LessonMainInfo/api/delete",
					type: "POST",
					contentType: "application/json",
					data: JSON.stringify({ lessonId: lessonId }),
					success: function (response) {
						if (response.success) {
							Swal.fire("刪除成功！", "教案已成功刪除。", "success").then(() => {
								$('#lesson-table').DataTable().ajax.reload(); // 重新載入 DataTable
							});
						} else {
							Swal.fire("刪除失敗", response.message || "請稍後再試！", "error");
						}
					},
					error: function (xhr, status, error) {
						Swal.fire("刪除失敗", "發生錯誤，請稍後再試！", "error");
						console.error("刪除失敗:", xhr.responseText);
					}
				});
			}
		});
	}
	</script>
	<style>
	    .list-container {
	        width: 95%;
	        margin: 0 auto; /* 讓 container 水平置中 */
	    }

		#lesson-table td, #lesson-table th {
			vertical-align: middle; /* 垂直置中 */
		}

		#lesson-table th {
			text-align: left; /* 水平置中 */
		}

	    .lesson-table-title {
			font-size: 1.5rem;
	        font-weight: bold;
	        color: #00CB96;
	    }
	    .badge {
	        font-size: 0.9rem;
	    }
	    
	    .func-button-group {
	    	text-align: center;
	    }
	    
	    .func-button-group button {
	        border-color: black;
	        color: black;
	        position: relative; /* 設置相對定位 */
	    }
	    
	    .func-button-group button::after {
		    content: attr(data-tooltip); /* 使用 data-tooltip 的值作為內容 */
		    position: absolute; /* 設置絕對定位 */
		    bottom: 110%; /* 提升到按鈕上方 */
		    left: 50%; /* 水平置中 */
		    transform: translateX(-50%); /* 修正偏移 */
		    background-color: black; /* 設置背景顏色 */
		    color: white; /* 設置文字顏色 */
		    padding: 3px 8px; /* 增加內邊距 */
		    border-radius: 4px; /* 圓角效果 */
		    font-size: 0.8rem; /* 字體大小 */
		    white-space: nowrap; /* 防止文字換行 */
		    display: none; /* 預設不顯示 */
		    z-index: 10; /* 讓提示浮於最上層 */
		}
		
		.func-button-group button:hover::after {
		    display: block; /* 滑鼠懸停時顯示提示 */
		}
	    
	    .td-center {
	        display: flex;
	        justify-content: center; /* 水平置中 */
	        align-items: center; /* 垂直置中 */
	        height: 100%;
	    }
	    
	    .input-group-text {
	        background-color: #f8f9fa; /* 調整背景色 */
	        font-weight: bold;
	    }
	    
	    table th {
			text-align: center;
	    }
	    
		#filter-level {
		    max-width: 150px; /* 限制下拉選單的最大寬度 */
		    width: auto; /* 使其適應內容 */
		}
	    
	    #search-keyword {
	    	border-radius: 0 8px 8px 0;
	    }
	    
	    .input-keyword {
	    	width: 50%
	    }
	    
	    #search-btn {
	    	color: black;
	    	border: solid 1px grey;
	    	border-radius: 8px;
	    	background-color: white;
	    }
	    
		#search-btn:hover {
	    	color: white;
	    	border: solid 1px grey;
	    	border-radius: 8px;
	    	background-color: #5c5656;
	    }

	</style>

</body>
</html>