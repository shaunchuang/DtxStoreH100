<#include "../login/widget/dtx-login-header.ftl" />
    <main class="d-flex">
       <#include "../login/widget/dtx-login-leftnav.ftl" />
        <div class="col-xxl-10 right-container">
            <div class="row mx-4">
            <h2>請輸入Steam商店網址</h2>
				    <div>
				        <input type="text" class="form-control" placeholder="請輸入Steam商店網址"></input>
				    </div>
				    <div style="margin-top: 20px;">
                    	<button type="button" class="btn btn-primary" onclick="getSteamUrl()">送出</button>
                    </div>
					<div class="container mt-4">
						<div class="row card-deck-row">
						</div>
					</div>
            </div>
        </div>
    </main>
    <script src="/html/dtxstore/script/commonPage/popper.js"></script>
    <script src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script src="/html/dtxstore/script/commonPage/leftnav.js"></script>
    <script src="/html/dtxstore/script/commonPage/swal2.js"></script>
    <script>
    let isLibrary = false;
	let isManage = false;
    let currentPage = 1;
	let pageSize = 9;
	let totalPage = 0;
	let totalCount = 0;
    $(document).ready(function(){

    var dtoList = [];
    

//    postFilterApi({"isLibrary": isLibrary}, function(){
//		setupPagination();
//	});
	console.log(dtoList);
	createCard(dtoList);

	});
	
	function getSteamUrl(){
                        var button = $(".btn-primary");  // 取得按鈕
                        button.prop("disabled", true);   // 禁用按鈕

                        var url = $("input").val();
                        var jsonData = { "url": url };
                        console.log('jsonData: ', jsonData);
                        if(url){
                            Swal.fire({
                                title: '請稍候',
                                text: '正在導入教案...',
                                allowOutsideClick: false,
                                didOpen: () => {
                                    Swal.showLoading();
                                }
                            });

                            $.ajax({
                                url: '/steam/api/import',
                                type: 'POST',
                                contentType: "application/json",
                                dataType: 'json',
                                data: JSON.stringify(jsonData),
                                success: function(response, status, xhr) {
                                    Swal.close();
                                    console.log('response: ', response);
                                    if (response.success == true) {
                                        swal.fire({
                                            title: "Steam教案導入成功",
                                            text: response.lessonName + " 教案已成功導入",
                                            icon: "success"
                                        }).then(() => {
                                            window.location.reload();
                                        });
                                    } else {
                                        swal.fire({
                                            title: "未知錯誤",
                                            text: response.message || "請稍後再試。",
                                            icon: "error"
                                        });
                                    }
                                },
                                error: function(xhr, status, error) {
                                    Swal.close();
                                    let errorMessage = xhr.responseJSON?.message || "系統出現未知錯誤，請稍後再試。";
                                    swal.fire({
                                        title: "導入失敗",
                                        text: errorMessage,
                                        icon: "error"
                                    });
                                },
                                complete: function() {
                                    button.prop("disabled", false); // AJAX 完成後重新啟用按鈕
                                }
                            });
                        }
                    }


	function createCard(dtoList){
	    dtoList.forEach(function(dtoString){
	        let dto = JSON.parse(dtoString);
	        console.log(dto);
	        let anses = dto.evalItemAnses
	        console.log(anses);
	        
	            let keyno = formatKey(dto.evalFormUserId);
	            let cardHtml = `
	                <div class="col-md-4 mb-4 d-flex">
	                    <div class="card flex-fill">
	                        <img src="` + anses['4'] + `" class="card-img-top" alt="...">
	                        <div class="card-body d-flex flex-column">
	                            <h5 class="card-title">` + anses['1'] + `</h5>
	                            <p class="card-text flex-grow-1">` + anses['2'] + `</p>
	                            <button class="btn btn-danger delete-btn" data-keyno="` + keyno + `">刪除</button>
	                        </div>
	                    </div>
	                </div>
	            `;
	            $('.card-deck-row').append(cardHtml);            
	    });
	    // 綁定刪除按鈕點擊事件
	    $('.delete-btn').on('click', function() {
	        var keyno = $(this).data('keyno');
	        deleteLesson(keyno);
	    });
	}
    
    function formatKey(key) {
    	return key.toString().padStart(10, '0');
	}
	
	function deleteLesson(keyno){
		swal.fire({
			icon: 'question',
			text: '確認是否刪除教案',
			showCancelButton: true,
			confirmButtonText: '確定刪除',
			confirmButtonColor: '#dc3545'
		}).then((result) => {
			if (result.isConfirmed){
			
				Swal.fire({
		            title: '請稍候',
		            text: '正在刪除教案...',
		            allowOutsideClick: false,
		            didOpen: () => {
		                Swal.showLoading();
		            }
	        	});
				$.ajax({
			        url: '/api/lesson/delete',
			        type: 'POST',
			        dataType: 'json',
			        data: { "lessonId": keyno },
			        success: function(data) {
			        	Swal.close();
			            if (data.success) {
			                Swal.fire('刪除成功', '', 'success').then((result) => {
			        		window.location.reload();
			        	});
			            } else {
			                Swal.fire('刪除失敗', '', 'error');
			            }
			        },
			        error: function(error) {
			            console.error('錯誤:', error);
			            alert('刪除過程中出現錯誤');
			        }
			    });
			} 
		});

	}
	
	function getDataToPost(){
    	let sortData = getSortValue();
	    let tagList = getActiveTag();
	    let sliderValue = getSliderValue();
	    let searchQuery = $('#search-input').val();
        let combinedData = {"tagList": tagList, "sliderValue": sliderValue};
        let pageData = {"currentPage": currentPage, "pageSize": pageSize};
        postFilterApi({"dataItem":JSON.stringify(combinedData),"sort":JSON.stringify(sortData), "lessonName": searchQuery, "pageData": JSON.stringify(pageData), "isLibrary": isLibrary}, function() {
	        setupPagination();
	    });
    }
    
    // POST api 進行篩選。
	function postFilterApi(postData, callback){
        $.ajax({
            type: "POST",
            url: `/api/FilterSortLesson`,
            data: postData,
            success: function(data) {
                let result = JSON.parse(data); // 假設 data 已經是 JSON 解析過的對象

                if(result.success == false){
                	return;
                } else if (result.success == true){
                	
                	let lessonMainDTOs = JSON.parse(result.lessonMainDTOs);
                	createCard(lessonMainDTOs);
                	
                	totalCount = result.totalCount;
                	totalPage = result.totalPage;

                	console.log('total count: ' + result.totalCount);
                	console.log('total page: ' + result.totalPage);
                	
                	// 調用回調函數
                	if (callback) {
                    	callback();
                	}            	

                }
            },
            error: function(xhr, status, error) {
                console.error("AJAX error: " + status + " - " + error);
            }
        });
    }
    
    function setupPagination() {
    	console.log(totalCount);
    	if (totalCount == 0){
    		
    		$('.pageNavigation').hide();
    	}
    	
	    let pagination = $('#dynamicPagination');
	    pagination.empty();
	    
	    console.log('totalPage in setupPage ' + totalPage);
	
	    // 設置上一頁按鈕
	    let preLi = $('<li>').addClass('page-item');
	    if (currentPage === 1) {
	        preLi.addClass('disabled');
	    }
	    let preA = $('<a>').addClass('page-link').attr('href', '#').attr('aria-label', 'Previous').data('page', currentPage - 1);
	    let preSpan = $('<span>').attr('aria-hidden', 'true').text('«');
	    let preSpan2 = $('<span>').addClass('sr-only').text('Previous');
	    preA.append(preSpan).append(preSpan2);
	    preLi.append(preA);
	    pagination.append(preLi);
	
	    // 設置頁碼按鈕
	    let startPage = Math.max(currentPage - 1, 1);
	    let endPage = Math.min(currentPage + 1, totalPage);
	
	    if (startPage > 1) {
	        let firstLi = $('<li>').addClass('page-item');
	        let firstA = $('<a>').addClass('page-link').attr('href', '#').text('1').data('page', 1);
	        firstLi.append(firstA);
	        pagination.append(firstLi);
	        
	        if (startPage > 2) {
	            let dotsLi = $('<li>').addClass('page-item disabled');
	            let dotsA = $('<a>').addClass('page-link').attr('href', '#').text('...');
	            dotsLi.append(dotsA);
	            pagination.append(dotsLi);
	        }
	    }
	
	    for (let i = startPage; i <= endPage; i++) {
	        let li = $('<li>').addClass('page-item');
	        if (i === currentPage) {
	            li.addClass('active');
	        }
	        let a = $('<a>').addClass('page-link').attr('href', '#').text(i).data('page', i);
	        li.append(a);
	        pagination.append(li);
	    }
	
	    if (endPage < totalPage) {
	        if (endPage < totalPage - 1) {
	            let dotsLi = $('<li>').addClass('page-item disabled');
	            let dotsA = $('<a>').addClass('page-link').attr('href', '#').text('...');
	            dotsLi.append(dotsA);
	            pagination.append(dotsLi);
	        }
	        
	        let lastLi = $('<li>').addClass('page-item');
	        let lastA = $('<a>').addClass('page-link').attr('href', '#').text(totalPage).data('page', totalPage);
	        lastLi.append(lastA);
	        pagination.append(lastLi);
	    }
	
	    // 設置下一頁按鈕
	    let nextLi = $('<li>').addClass('page-item');
	    if (currentPage === totalPage) {
	        nextLi.addClass('disabled');
	    }
	    let nextA = $('<a>').addClass('page-link').attr('href', '#').attr('aria-label', 'Next').data('page', currentPage + 1);
	    let nextSpan = $('<span>').attr('aria-hidden', 'true').text('»');
	    let nextSpan2 = $('<span>').addClass('sr-only').text('Next');
	    nextA.append(nextSpan).append(nextSpan2);
	    nextLi.append(nextA);
	    pagination.append(nextLi);
	
	    bindPaginationEvent();
	}
	
    </script>
</body>
</html>
