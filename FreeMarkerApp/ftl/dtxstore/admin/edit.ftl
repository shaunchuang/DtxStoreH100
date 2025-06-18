<#include "../login/widget/dtx-login-header.ftl" />
    <main class="d-flex">
       <#include "../login/widget/dtx-login-leftnav.ftl" />
        <div class="col-xxl-10 right-container">
        	<div class="row m-3" id="mainInfo">
        	</div>
            <div class="row mx-4">
            	<h3>教案評論</h3>
                <div class="col-md-6">
                	<div class="p-2">
	                	<label>評論等級</label>
	                    <div id="ratingLevel" class="commentRating">
	                        <span data-rating="1"></span>
	                        <span data-rating="2"></span>
	                        <span data-rating="3"></span>
	                        <span data-rating="4"></span>
	                        <span data-rating="5"></span>
	                    </div>
                    </div>
                    <div class="p-2">
	                    <label>適應症</label>
	                    <select class="form-select" id="commentIndication">
	                    </select>
                    </div>
                </div>
                <div class="col-md-6">
					<div class="p-2">
	                    <label>評論內容</label>
	                    <textarea class="form-control" id="commentContent" rows="3"></textarea>
                    </div>
                </div>
                <div class="col-md-6">
                	<button class="btn btn-primary mt-3" onclick="saveComment()">送出</button>
                </div>
            </div>
            <hr>
            <div class="row m-4">
            	<h3>教案文獻</h3>
            	<div class="col-md-12">
            		<div id="researchList">
            			<table class="table">
            				<thead>
            			    <tr>
            			    	<th scope="col">#</th>
            					<th scope="col">文獻類型</th>
            					<th scope="col">文獻名稱</th>
            					<th scope="col">文獻網址</th>
            					<th scope="col">操作</th>
            				</tr>
            				</thead>
            				<tbody>
            				</tbody>
            			</table>
            		</div>
            	    <div class="p-2">
            			<label>文獻類型</label>
            			<select class="form-select" id="researchType">
            				<option value="">請選擇</option>
            				<option value="1">paper</option>
            				<option value="2">clinical trial</option>
            			</select>
            		</div>
            		<div class="p-2">
            			<label>文獻名稱</label>
            			<input type="text" id="researchName" class="form-control">
            		</div>
            		<div class="p-2">
            			<label>文獻網址</label>
            			<input type="text" id="researchUrl" class="form-control">
            		</div>
            		<div>
            			<button class="btn btn-primary mt-3" id="addResearch" onclick="addResearch()">新增</button>
            		</div>
            	</div>
            </div>
            <hr>
            <div class="row m-4">
            </div>
        </div>
    </main>
    <script type="text/javascript" src="/html/dtxstore/script/dtxstore/popper.js"></script>
    <script type="text/javascript" src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/dtxstore/jQuery.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/dtxstore/leftnav.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/dtxstore/chart.js"></script>
    <script>
		let main = ${mainDTO};
		console.log(main);
    	
    	let lessonId = ${lessonId};
    	console.log(lessonId);
    	
    	
        $(document).ready(function(){
            createMain();
            createCommentOption();
            setRating();
            createResearchList();
        });
        
        function createMain(){
        	let ansList = main['evalItemAnses'];
        	let html = "";
        	html += "<div class='col-md-6'>";
        	html += "<div class='p-2 d-flex'>";

        	html += "<h2>" + ansList['1'] + "</h2>";
        	html += "</div>";
        	html += "</div>";
        	html += "<div class='col-md-6'>";
        	html += "<div class='p-2'>";
        	
        	html += "<img src='" + ansList['4'] + "' class='img-fluid' id='appPic'>";
        	html += "</div>";
        	html += "</div>";
        	$("#mainInfo").append(html);
        }
        
        
		function createCommentOption(){
		    $.ajax({
		        url: "${base}/${__lang}/division/api/Indication/GetIndication",
		        type: "POST",
		        success: function(response){
		            try {
		            	console.log(JSON.parse(response));
		                let data = JSON.parse(response);
		                console.log('Parsed tagList:', data.tagList);
		                let tagList = data.tagList;
		
		                let html = "";
		                
		                html+= "<option value=''>請選擇</option>";
		                
		                for (let i = 0; i < tagList.length; i++) {
		                    html += "<option value='" + tagList[i].id + "'>" + tagList[i].tagNameZh + " - " + tagList[i].tagNameEn + "</option>";
		                }
		                $("#commentIndication").html(html);

		            } catch (e) {
		                console.error('Error parsing response or tagList:', e);
		            }
		        },
		        error: function(xhr, status, error) {
		            console.error('AJAX Error: ' + status + error);
		        }
		    });
		}
		
		function createRating(){
			$("#ratingLevel").rateYo({
		        rating: 0, // 初始評分值
		        fullStar: true, // 是否只允許整星評分
		        onSet: function(rating, rateYoInstance) {
		            // 當用戶設置評分時觸發
		            console.log("The rating is set to " + rating);
		            // 您可以在這裡將評分值保存到一個隱藏的輸入框或變量中
		        }
		    });
		}
		
		function saveComment() {
	        let rating = $("#ratingLevel").data("rating");
	        let commentContent = $("#commentContent").val();
	        let commentIndication = $("#commentIndication").val();
			
			$.ajax({
                url: "${base}/${__lang}/division/api/Lesson/AddLessonComment",
                type: "POST",
                data: {
                    lessonId: formatKey(lessonId),
                    data: JSON.stringify({
                        rating: rating,
                        commentContent: commentContent,
                        commentIndication: commentIndication
                    })
                },
                success: function(response){
                	console.log('Rating:', rating);
	        		console.log('Content:', lessonContent);
	        		console.log('Indication:', commentIndication);
                    console.log(response);
                    alert("評論成功");
                },
                error: function(xhr, status, error){
                    console.error('AJAX Error: ' + status + error);
                }
            });
	        // 在這裡添加保存評論的邏輯

	    }
	    
	    function setRating(){
	        // 評分系統
	        $("#ratingLevel span").on("click", function() {
	            var ratingValue = $(this).data("rating");
	            $("#ratingLevel span").removeClass("selected");
	            $(this).addClass("selected");
	            $(this).prevAll().addClass("selected");
	
	            // 存儲評分值
	            $("#ratingLevel").data("rating", ratingValue);
	            console.log("The rating is set to " + ratingValue);
	        });
	
	        $("#ratingLevel span").hover(
	            function() {
	                $(this).addClass("hover");
	                $(this).prevAll().addClass("hover");
	            }, function() {
	                $(this).removeClass("hover");
	                $(this).prevAll().removeClass("hover");
	            }
	        );
	    }


        /* 建立研究列表*/
		function createResearchList(){
			$.ajax({
                url: "${base}/${__lang}/division/api/Lesson/GetLessonResearch",
                type: "POST",
                data: {
                    lessonId: formatKey(lessonId)
                },
                success: function(response){
                    	
                        console.log(JSON.parse(response));
                        let data = JSON.parse(response);
                        let researchDTO = JSON.parse(data.researchDTO);
                        console.log('Parsed researchList:', researchDTO);
                        
                        $("#researchList tbody").empty();

                        let html = "";
                        for (let i = 0; i < researchDTO.length; i++) {
                            html += "<tr>";
                            html += "<td scope='row'>" + (i + 1) + "</td>";
                            if(researchDTO[i].evalItemAnses['303'] == 1){
                                html += "<td>paper</td>";
                            }else if(researchDTO[i].evalItemAnses['303'] == 2){
                                html += "<td>clinical trial</td>";
                            }
                            html += "<td>" + (researchDTO[i].evalItemAnses['301']) + "</td>";
                            html += "<td>" + (researchDTO[i].evalItemAnses['302']) + "</td>";
                            html += "<td><button class='btn btn-danger' onclick='deleteResearch(" + (researchDTO[i].evalFormUserId) + ")'>刪除</button></td>";
                            html += "</tr>";
                        }
                        $("#researchList tbody").html(html);
                        
                },
                error: function(xhr, status, error) {
                    console.error('AJAX Error: ' + status + error);
                }
            });
		}
		
		/* 刪除教案研究 */
		function deleteResearch(researchId){
			$.ajax({
                url: "${base}/${__lang}/division/api/Lesson/DeleteLessonResearch",
                type: "POST",
                data: {
                    researchId: researchId
                },
                success: function(response){
                    console.log(response);
                    createResearchList();
                },
                error: function(xhr, status, error){
                    console.error('AJAX Error: ' + status + error);
                }
            });
		}
		
		/* 新增教案研究 */
		function addResearch(){
		    let researchType = $("#researchType").val();
		    let researchName = $("#researchName").val();
		    let researchUrl = $("#researchUrl").val();
		    $.ajax({
                url: "${base}/${__lang}/division/api/Lesson/AddLessonResearch",
                type: "POST",
                data: {
                    lessonId: formatKey(lessonId),
                    data: JSON.stringify({
                        researchType: researchType,
                        researchName: researchName,
                        researchUrl: researchUrl
                    })
                },
                success: function(response){
                    console.log(response);
                    createResearchList();
                    $('#researchUrl').val('');
                    $('#researchType').val('');
                    $('#researchName').val('');
                    alert("新增成功");
                },
                error: function(xhr, status, error){
                    console.error('AJAX Error: ' + status + error);
                }
            });
        }
		
        
        function postApi(data, url){
        	ajax({
        		url: url,
        		type: "POST",
        		data: data,
        		success: function(response){
        			return response;
        		},
        		error: function(xhr, status, error){
        			console.error('AJAX Error: ' + status + error);
        		}
        	});
        }
        
        function formatKey(key) {
    		return key.toString().padStart(10, '0');
		}
        
    </script>
    <style>
		.commentRating {
		    unicode-bidi: bidi-override;
		    display: inline-flex;
		    direction: ltr;  // 確保從左到右顯示
		}
		
		.commentRating span {
		    font-size: 2rem;
		    cursor: pointer;
		    color: #ccc;  // 空心星星顏色
		}
		
		.commentRating span:hover,
		.commentRating span.hover,
		.commentRating span.selected {
		    color: gold;  // 實心星星顏色
		}
		
		.commentRating span:before {
		    content: '\2606';  // 空心星星
		}
		
		.commentRating span.hover:before,
		.commentRating span.selected:before {
		    content: '\2605';  // 實心星星
		}
		
		#appPic {
            max-width: 100%;
            max-height: 100%;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        }

    </style>
</body>
</html>
