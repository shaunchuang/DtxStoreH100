<#include "../../login/widget/dtx-login-header.ftl" />
    <main class="d-flex">
       <#include "../../login/widget/dtx-login-leftnav.ftl" />
        <div class="col-xxl-10 right-container">
            <div class="row mx-4">
            	<h2>建立 Scratch 教案</h2>
                	<hr>
                    <ul class="list-group list-group-flush mb-3">
                        <li class="list-group-item">1. 點擊「開始建置教案」</li>
                        <li class="list-group-item">2. 登入或註冊 Scratch 帳號</li>
                        <li class="list-group-item">3. 開始製作教案</li>
                        <li class="list-group-item">4. 完成後，複製專案連結並貼上輸入框送出</li>
                        <li class="list-group-item">5. 教案成功上架</li>
                    </ul>
            <a href="https://scratch.mit.edu/projects/1036569793/editor/" target="_blank" class="btn btn-primary">開始建置教案</a>
            <h2 class="mt-2">Scratch教案導入</h2>
            <div class="mt-2">
				<input type="text" class="form-control" id="scratchUrl" placeholder="請輸入Scratch專案網址"></input>
			</div>
			<div class="mt-2">
                <button type="button" class="btn btn-primary" onclick="getScratchUrl()">送出</button>
            </div>
            <div class="card-deck px-5">
            	<div class="row row-cols-3 card-deck-row">
								<!-- 教案卡片 -->
            	</div>
            </div>
            <div class="d-flex justify-content-center mt-4">
            	<nav aria-label="Page navigation example" class="pageNavigation">
  					<ul class="pagination" id="dynamicPagination">

  					</ul>
				</nav>
             </div>
        </div>
    </main>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/popper.js"></script>
    <script type="text/javascript" src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/leftnav.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/chart.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/login/dashboard.js"></script>
    <script>
    	let isLibrary = false;
		let isManage = false;
    
		let ageSlider;
		let priceSlider;
		let minAge = 3;
		let maxAge = 65;
		let minPrice = 0;
		let maxPrice = 3500;
		let currentPage = 1;
		let pageSize = 9;
		let totalPage = 0;
		let totalCount = 0;
		
		$(document).ready(function(){
			postFilterApi({"dataItem":JSON.stringify({"tagList": ["51"], "sliderValue": {"minPrice":"0","maxPrice":"3500","minAge":"3","maxAge":"65"}}), "pageData":JSON.stringify({"currentPage": currentPage, "pageSize": pageSize}),
			sort:JSON.stringify({"sortOneField":"","sortOneOrder":"","sortTwoField":"","sortTwoOrder":""}), "isLibrary": isLibrary},
			function(){setupPagination()
			});
		});
		
		function getScratchUrl(){
			Swal.fire({
	            title: '請稍候',
	            text: '正在導入教案...',
	            allowOutsideClick: false,
	            didOpen: () => {
	                Swal.showLoading();
	            }
	        });
			let scratchUrl = $('#scratchUrl').val();
			$('#scratchUrl').val('');
			    // 正規表達式提取 Scratch 專案的 ID
		    let scratchIdMatch = scratchUrl.match(/\/projects\/(\d+)/);
		    if (scratchIdMatch && scratchIdMatch[1]) {
		        let scratchId = scratchIdMatch[1];
				$.ajax({
			        url: '/api/lesson/scratch/create',
			        type: 'POST',
			        dataType: 'json',
			        data: {"scratchId": scratchId},
			        success: function(response){
			        	Swal.close();
			        	console.log(response);
			        	if(response.success){
			        		console.log('導入成功');
			        		Swal.fire({
			        		title:'Scratch教案導入成功',
			        		text:response.gameName + ' 教案導入成功',
			        		icon:'success'
				        	}).then((result) => {
				        		window.location.reload();
				        	});
			        	
			        	} else {
				        	swal.fire({
				        		title: "導入失敗",
				        		text: "教案導入失敗",
				        		icon: "error"
				        	});
			    		}
			        },
			        error: function(xhr, status, error) {
			        	Swal.close();
	                	console.error("Request failed: ", status, error);
	            	}
				});
			}
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
    
    /* 建立瀏覽卡片 */
	function createCard(dtoList) {
	    $('.card-deck-row').empty();
	
	    dtoList.forEach(function(dtoString) {
	        let dto = dtoString;
	        let isFavor = dto['isFavor'];
			
	        let keyno = formatKey(dto['evalFormUserId']);
	        
	        let ansList = dto['evalItemAnses'];
	        console.log('ansList', ansList['11']);
		    let cardHtml = `
		        <div class="col">
		            <div class="card">
		                <#if isManage??>
		                <a class="card-link" href="/web/lesson/manage/edit/` + keyno + `">
		                <#else>
		                <a class="card-link" href="/division/web/store/app/` + keyno + `">
		                </#if>
		                    <img src="` + ansList['4'] + `" class="card-img-top" alt="` + ansList['1'] + `">
		                    <div class="card-body">
		                        <h5 class="card-title">` + ansList['1'] + `</h5>
		                        <p class="card-text">` + ansList['2'] + `</p>
		                    </div>
		                </a>
		            </div>
		        </div>
		        `;
		            
		    $('.card-deck-row').append(cardHtml);
	    });
	
	    // 為所有收藏按鈕添加點擊事件監聽器
	    $('.favorite-btn').on('click', function(event) {
	        
	        var $favoriteBtn = $(this);
	        var $heartIcon = $favoriteBtn.find('i');
	        var isFavorite = $heartIcon.hasClass('fa-solid');
	        var lessonId = $favoriteBtn.data('lesson');
	        
	        var url = isFavorite ? '/api/RemoveFavorite' : '/api/AddFavorite';
        	$.ajax({
	            url: url,
	            type: 'POST',
	            data: {"lessonId": lessonId},
	            success: function(response) {
	                // 切換圖標樣式
	                $heartIcon.toggleClass('fa-regular fa-solid');
	                var message = isFavorite ? '已移除收藏' : '已加入收藏';
	                var $message = $favoriteBtn.next('.favorite-message');
	                $message.stop(true, true).text(message).fadeIn().delay(2000).fadeOut();
	                // 如果是收藏頁面，移除收藏時移除該卡片
	                if (isLibrary && isFavorite) {
	                    $favoriteBtn.closest('.col').fadeOut(function() {
	                        $(this).remove();
	                        getDataToPost();
	                    });
	                }
	            },
	            error: function(xhr, status, error) {
	                alert('操作失敗: ' + error);
	            }
	        });
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
	
	// Format key to 10 digits
    function formatKey(key) {
    	return key.toString().padStart(10, '0');
	}
	
	function bindPaginationEvent() {
	    $('#dynamicPagination .page-link').on('click', function(e) {
	        e.preventDefault();
	        console.log('click page');
	        let targetPage = $(this).data('page');
	        console.log('targetPage: ' + targetPage);
	        if (targetPage && targetPage > 0 && targetPage <= totalPage) {
	            currentPage = targetPage;
	            getDataToPost();
	        }
	    });
	}
	
	function getDataToPost(){
    	let sortData = getSortValue();
	    let tagList = getActiveTag() || [];
	    tagList.push('51');
	    let sliderValue = getSliderValue();
	    let searchQuery = $('#search-input').val();
        let combinedData = {"tagList": tagList, "sliderValue": sliderValue};
        let pageData = {"currentPage": currentPage, "pageSize": pageSize};
        postFilterApi({"dataItem":JSON.stringify(combinedData),"sort":JSON.stringify(sortData), "lessonName": searchQuery, "pageData": JSON.stringify(pageData), "isLibrary": isLibrary}, function() {
	        setupPagination();
	    });
    }
    
    function getSortValue() {
        let sortOne = $('#sortSelectOne').val();
        let sortTwo = $('#sortSelectTwo').val();
        
        let sortOneField = sortOne ? sortOne.replace(/(Asc|Desc)$/, '') : '';
        let sortOneOrder = sortOne ? (sortOne.endsWith('Asc') ? 'ASC' : 'DESC') : '';
        let sortTwoField = sortTwo ? sortTwo.replace(/(Asc|Desc)$/, '') : '';
        let sortTwoOrder = sortTwo ? (sortTwo.endsWith('Asc') ? 'ASC' : 'DESC') : '';
        
        return {
            sortOneField: sortOneField,
            sortOneOrder: sortOneOrder,
            sortTwoField: sortTwoField,
            sortTwoOrder: sortTwoOrder
        };
    }
    
    function getSliderValue(){
		    minAge = $('#ageSlider-wrap .rs-container .rs-pointer[data-dir="left"] .rs-tooltip').text();
    		maxAge = $('#ageSlider-wrap .rs-container .rs-pointer[data-dir="right"] .rs-tooltip').text();
    		
    		minPrice = $('#priceSlider-wrap .rs-container .rs-pointer[data-dir="left"] .rs-tooltip').text();
    		maxPrice = $('#priceSlider-wrap .rs-container .rs-pointer[data-dir="right"] .rs-tooltip').text();
    		
    		return {"minPrice": minPrice, "maxPrice": maxPrice, "minAge": minAge, "maxAge": maxAge};
	}
	
	function getActiveTag() {
	    let activeTag = [];
	    $('.tag-list ul li span').each(function() {
	        if ($(this).hasClass('active')) {
	            activeTag.push($(this).attr('data-tag'));
	        }
	    });
	    return activeTag;
	}
    </script>
    <style>
        .card-link {
            text-decoration: none;
            color: inherit;
        }
        .card-link:hover {
            text-decoration: none;
        }
    </style>
</body>
</html>
