<#include "../../login/widget/dtx-login-header.ftl" />
    <main class="d-flex">
       <#include "../../login/widget/dtx-login-leftnav.ftl" />
	   	<!-- Quill CSS -->
		<link href="/html/dtxstore/style/quill-2.0.3.css" rel="stylesheet">
		<link href="/html/dtxstore/style/datatables.min.css" rel="stylesheet">

        <div class="col-xxl-10 right-container">
        	<div class="upload-container">
	        	<div class="upload-header">
	        		<span class="lesson-table-title"><a href="/ftl/dtxstore/manage/list">我的教案總表</a> / </span><span class="lesson-title">新增教案</span>
	        	</div>
		        <div class="lesson-edit-form">
		            <ul class="tab-menu">
		                <li class="active" data-tab="lesson-info">教案資訊</li>
						<li data-tab="lesson-tag">教案標籤</li>
						<li data-tab="lesson-equipment">教案設備</li>
		                <li data-tab="achievement-info">成就資訊</li>
		                <li data-tab="statistics-info">統計資訊</li>
						<li data-tab="news-info">更新資訊</li>
						<li data-tab="research-info">教案研究</li>
		                <!--<li data-tab="distribution-record">配發紀錄</li>-->
		            </ul>
		            <!-- 教案資訊 -->
		            <div class="tab-content px-4 active" id="lesson-info">
		                <form class="lesson-form">
							<table class="lesson-table">
							    <tr>
							        <td class="lesson-label">教案名稱</td>
							        <td>
								        <div class="lesson-short-text-input">
								        	<input type="text" id="lesson-name" name="lesson-name" placeholder="請輸入教案名稱" class="lesson-input">
								        </div>
							        </td>
							    </tr>
							    <tr>
							        <td class="lesson-label">教案介紹</td>
							        <td>
							        	<div class="lesson-intro-input">
							        		<textarea id="lesson-description" name="lesson-description" rows="3" placeholder="請輸入教案介紹" class="lesson-textarea"></textarea>
							        	</div>
							        </td>
							    </tr>
								<tr class="lowCode-tr">
									<td class="lesson-label">建立Low Code教案</td>
									<td>
										<div class="lesson-intro-input">
											<button onclick="window.open('http://127.0.0.1:8601/editor.html', '_blank')" class="btn btn-primary">
												開啟 Low Code 編輯器
											</button>
											<button onclick="window.open('/File/api/file/path/20250506_135909_Project.html', '_blank')" class="btn btn-secondary">
												測試教案
											</button>
										</div>
									</td>
								</tr>
								<!-- 教案封面圖片 -->
								<tr>
								    <td class="lesson-label">教案封面圖片</td>
								    <td>
								        <div class="image-area">
								            <div class="image-container">
								                <!-- 封面圖片上傳區 -->
								                <div class="upload-area" id="header-upload-area">
								                    <input type="file" id="lesson-header-image" name="lesson-header-image" accept="image/*" class="file-input">
								                    <label for="lesson-header-image">
								                        <div class="upload-icon"><i class="fas fa-upload"></i></div>
								                        <p>拖放圖片到此處或點擊選擇檔案</p>
								                    </label>
								                </div>
								                <!-- 封面圖片預覽 -->
								                <div class="image-preview-container" style="position: relative; display: none;" id="header-preview-container">
								                    <img id="header-image-preview" src="" alt="預覽圖片" class="image-preview">
								                    <button type="button" class="btn btn-danger btn-sm remove-image" id="remove-header-image" style="position: absolute; top: 5px; right: 5px;">
								                        <i class="fas fa-times"></i>
								                    </button>
								                </div>
								            </div>
								        </div>
								    </td>
								</tr>

							    <tr>
							    	<td class="lesson-label">教案適應症</td>
							    	<td>
							    		<div class="lesson-checkbox-group" id="lesson-indication-checkboxes">
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="ADHD">
								                <span class="custom-checkbox"></span>
								                注意力不足過動症(ADHD)
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="PTSD">
								                <span class="custom-checkbox"></span>
								                創傷後壓力症候群(PTSD)
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="anxiety">
								                <span class="custom-checkbox"></span>
								                焦慮症
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="depression">
								                <span class="custom-checkbox"></span>
								                憂鬱症(DD)
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="developmental-delay">
								                <span class="custom-checkbox"></span>
								                發展性語言障礙
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="expressive-language-disorder">
								                <span class="custom-checkbox"></span>
								                表達性語言障礙
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="social-language-disorder">
								                <span class="custom-checkbox"></span>
								                社交性語言障礙
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="neurological-disorder">
								                <span class="custom-checkbox"></span>
								                神經性語言障礙
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="degenerative-disease">
								                <span class="custom-checkbox"></span>
								                退化性關節炎
								            </label>
								            <label class="checkbox-label">
								                <input type="checkbox" name="lesson-indication" value="muscle-dystrophy">
								                <span class="custom-checkbox"></span>
								                肌肉萎縮症
								            </label>
								        </div>
							    	</td>
							    </tr>
							    <tr>
							    	<td class="lesson-label">教案目的</td>
							    	<td>
							    		<div class="lesson-purpose-input">
							    			<input type="text" id="lesson-purpose" name="lesson-purpose" placeholder="請輸入教案目的" class="lesson-input">
							    		</div>
							    	</td>
							    </tr>
							    <tr>
							    	<td class="lesson-label">注意事項</td>
							    	<td>
							    		<div class="lesson-precaution-input">
							    			<input type="text" id="lesson-precaution" name="lesson-precaution" placeholder="請輸入注意事項" class="lesson-input">
							    		</div>
							    	</td>
							    </tr>
							    <tr>
							    	<td class="lesson-label">教案售價 (NT$)</td>
							    	<td>
							    		<div class="lesson-short-text-input">
							    			<input type="text" id="lesson-price" name="lesson-price" placeholder="請輸入售價" class="lesson-input">
							    		</div>
							    	</td>
							    </tr>
								<!-- 教案影片 -->
								<tr>
								    <td class="lesson-label">教案影片</td>
								    <td>
								        <div class="video-area">
								            <div class="video-container">
								                <!-- 教案影片上傳區 -->
								                <div class="upload-area" id="video-upload-area">
								                    <input type="file" id="lesson-video" name="lesson-video" accept="video/*" class="file-input">
								                    <label for="lesson-video">
								                        <div class="upload-icon"><i class="fas fa-video"></i></div>
								                        <p>拖放影片到此處或點擊選擇檔案</p>
								                    </label>
								                </div>
								                <!-- 教案影片預覽 -->
								                <div class="video-preview-container" style="position: relative; display: none;" id="video-preview-container">
								                    <video id="video-preview" controls class="video-preview" style="width: 100%; max-width: 400px; height: auto;">
								                        您的瀏覽器不支援影片播放。
								                    </video>
								                    <button type="button" class="btn btn-danger btn-sm remove-video" id="remove-video" style="position: absolute; top: 5px; right: 5px;">
								                        <i class="fas fa-times"></i>
								                    </button>
								                </div>
								            </div>
								        </div>
								    </td>
								</tr>
								<!-- 教案圖片 -->
								<tr>
								    <td class="lesson-label">教案圖片</td>
								    <td>
								        <div class="image-area">
								            <!-- 已上傳的圖片網格 -->
								            
								            <div class="image-upload-section">
								                <h6>新增教案圖片</h6>
								                <div class="image-container">
								                    <!-- 教案圖片上傳區 -->
								                    <div class="upload-area" id="body-upload-area">
								                        <input type="file" id="lesson-body-image" name="lesson-image" accept="image/*" class="file-input">
								                        <label for="lesson-body-image">
								                            <div class="upload-icon"><i class="fas fa-upload"></i></div>
								                            <p>拖放圖片到此處或點擊選擇檔案</p>
								                        </label>
								                    </div>
								                    <!-- 教案圖片預覽 -->
								                    <div class="image-preview-container" style="position: relative; display: none;" id="body-preview-container">
								                        <img id="body-image-preview" src="" alt="預覽圖片" class="image-preview">
								                        <button type="button" class="btn btn-danger btn-sm remove-image" id="remove-body-image" style="position: absolute; top: 5px; right: 5px;">
								                            <i class="fas fa-times"></i>
								                        </button>
								                    </div>
								                </div>
								            </div>
								            <div class="image-scroll-wrapper">
                                    <div class="scroll-indicator scroll-left" id="scroll-left">
                                        <i class="fas fa-chevron-left"></i>
                                    </div>
                                    <div class="image-grid-container" id="lesson-images-grid">
                                        <!-- 圖片會被動態加入到這裡 -->
                                    </div>
                                    <div class="scroll-indicator scroll-right" id="scroll-right">
                                        <i class="fas fa-chevron-right"></i>
                                    </div>
                                </div>
								        </div>
								    </td>
								</tr>

							    <tr class="upload-exe">
							    	<td class="lesson-label">安裝檔案上傳</td>
							    	<td>
							    	<div class="lesson-short-text-input">
							    		<input type="file" id="lesson-install" name="lesson-install" accept=".zip,.rar,.7z" class="lesson-input"></td>
							    	</div>
							    </tr>

								<tr>
									<td class="lesson-label">刪除教案</td>
									<td>
										<div class="lesson-delete-blk">
											<button class="btn-lesson-delete">刪除教案</button>
										</div>
									</td>
								</tr>
							</table>
							<div class="button-container">
			                    <div class="form-buttons">
			                        <button class="btn-save">儲存</button>
			                        <button class="btn-cancel">回總表</button>
			                    </div>
		                    </div>
		                </form>
		            </div>
					<!-- 教案標籤 -->
		            <div class="tab-content px-4" id="lesson-tag">
						<form class="lesson-tag-form">
							<table class="lesson-table">
								<tr>
									<td class="lesson-label">教案類型</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-type-checkboxes">
										</div>
									</td>
								</tr>
								<tr>
									<td class="lesson-label">教案語言</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-language-checkboxes">
										</div>
									</td>
								</tr>
								<tr>
									<td class="lesson-label">教案開發者</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-developer-checkboxes">

										</div>
									</td>
								</tr>
								<tr>
									<td class="lesson-label">教案裝置</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-device-checkboxes">

										</div>
									</td>
								</tr>
								<tr>
									<td class="lesson-label">教案平台</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-platform-checkboxes">
										</div>
									</td>
								</tr>
								<tr>
									<td class="lesson-label">使用時段</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-time-checkboxes">
										</div>
									</td>
								</tr>
								<tr>
									<td class="lesson-label">使用者協作</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-coop-checkboxes">
										</div>
									</td>
								</tr>
								<tr>
									<td class="lesson-label">其他</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-other-checkboxes">
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
					<!-- 教案設備資訊 -->
					<div class="tab-content px-4" id="lesson-equipment">
						<form class="lesson-equipment-form">
							<table class="lesson-equipment-table">
								<tr>
									<td class="lesson-label">教案設備</td>
									<td>
										<div class="lesson-checkbox-group" id="lesson-equipment-checkboxes">
											<label class="checkbox-label">
												<input type="checkbox" name="lesson-equipment" value="VR">
												<span class="custom-checkbox"></span>
												Virtual Reality (VR)
											</label>
											<label class="checkbox-label">
												<input type="checkbox" name="lesson-equipment" value="AR">
												<span class="custom-checkbox"></span>
												Augmented Reality (AR)
											</label>
											<label class="checkbox-label">
												<input type="checkbox" name="lesson-equipment" value="PC">
												<span class="custom-checkbox"></span>
												個人電腦
											</label>
											<label class="checkbox-label">
												<input type="checkbox" name="lesson-equipment" value="Mobile">
												<span class="custom-checkbox"></span>
												行動裝置
											</label>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
		            <!-- 成就資訊 -->
		            <div class="tab-content px-4" id="achievement-info">
		                <table class="table" id="achievementTable">
		                    <thead>
		                        <tr>
		                            <th>ID</th>
		                            <th>API名稱</th>
		                            <th>顯示名稱</th>
		                            <th>成就描述</th>
		                            <th>成就解鎖圖片</th>
		                            <th>成就未解鎖圖片</th>
		                            <th>操作</th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                        <tr>
		                            <td>1</td>
		                            <td>REVIVE_20</td>
		                            <td>Life of the party</td>
		                            <td>Revive a download friend 20 times.</td>
		                            <td><img src="" alt="解鎖圖片" class="thumbnail"></td>
		                            <td><img src="" alt="未解鎖圖片" class="thumbnail"></td>
		                            <td>
		                            	<div class="func-button-group">
			                                <button class="btn-ach-edit"><i class="fa-solid fa-pen-to-square"></i></button>
			                                <button class="btn-ach-delete"><i class="fa-regular fa-trash-can"></i></button>
		                            	</div>
		                            </td>
		                        </tr>
		                        <tr>
		                            <td>2</td>
		                            <td>REVIVE_20</td>
		                            <td>Life of the party</td>
		                            <td>Revive a download friend 20 times.</td>
		                            <td><img src="" alt="解鎖圖片" class="thumbnail"></td>
		                            <td><img src="" alt="未解鎖圖片" class="thumbnail"></td>
		                            <td>
		                            	<div class="func-button-group">
			                                <button class="btn-ach-edit"><i class="fa-solid fa-pen-to-square"></i></button>
			                                <button class="btn-ach-delete"><i class="fa-regular fa-trash-can"></i></button>
		                            	</div>
		                            </td>
		                        </tr>
		                    </tbody>
		                </table>
					    <div class="d-flex justify-content-end mb-3">
					        <button class="btn btn-primary add-item" id="add-statistics-item">
					            <i class="fa-solid fa-circle-plus"></i> 新增項目
					        </button>
					    </div>
		                <div class="button-container">
			                <div class="form-buttons">
			                    <button class="btn-save">儲存</button>
			                    <button class="btn-cancel">回總表</button>
			                </div>
		                </div>
		            </div>
		
		            <!-- 統計資訊 -->
		            <div class="tab-content px-4" id="statistics-info">
		                <table class="table">
		                    <thead>
		                        <tr>
		                            <th>ID</th>
		                            <th>類型</th>
		                            <th>API名稱</th>
		                            <th>顯示名稱</th>
		                            <th>最大變更</th>
		                            <th>最小值</th>
		                            <th>最大值</th>
		                            <th>預設值</th>
		                            <th>僅限增長</th>
		                            <th>合計</th>
		                            <th>區間(分鐘)</th>
		                            <th>操作</th>
		                        </tr>
		                    </thead>
		                    <tbody>
		                        <tr>
		                            <td>1</td>
		                            <td>INT</td>
		                            <td>SP.progress</td>
		                            <td>進行時間</td>
		                            <td>5.0</td>
		                            <td>5.0</td>
		                            <td>5.0</td>
		                            <td>5.0</td>
		                            <td><input type="checkbox" checked></td>
		                            <td><input type="checkbox"></td>
		                            <td><input type="text" placeholder="區間"></td>
		                            <td>
		                            	<div class="func-button-group">
			                                <button class="btn-stat-edit"><i class="fa-solid fa-pen-to-square"></i></button>
			                                <button class="btn-stat-delete"><i class="fa-regular fa-trash-can"></i></button>
		                            	</div>
		                            </td>
		                        </tr>
		                    </tbody>
		                </table>
		                <div class="d-flex justify-content-end mb-3">
					        <button class="btn btn-primary add-item" id="add-statistics-item">
					            <i class="fa-solid fa-circle-plus"></i> 新增項目
					        </button>
					    </div>
		                <div class="button-container">
			                <div class="form-buttons">
			                    <button class="btn-save">儲存</button>
			                    <button class="btn-cancel">回總表</button>
			                </div>
		                </div>
		            </div>
					<!-- 教案新聞 -->
		            <div class="tab-content px-4" id="news-info">
		                <form class="lesson-form">
							<table class="lesson-table news-table">
							    <tr>
							        <td class="lesson-label">新聞標題</td>
							        <td>
								        <div class="lesson-long-text-input">
								        	<input type="text" id="news-title" name="news-title" placeholder="請輸入教案新聞標題" class="lesson-input">
								        </div>
							        </td>
							    </tr>
								<tr>
									<td class="lesson-label">新聞內容</td>
									<td>
										<div class="lesson-news-editor">
										<!-- Quill 編輯器容器 -->
										<div id="news-editor" style="height: 200px;"></div>
										<input type="hidden" name="news-content" id="news-content">
										</div>
									</td>
								</tr>
							</table>
							<div class="button-container">
								<div class="form-buttons">
									<button class="btn-save">儲存</button>
									<button class="btn-cancel">回總表</button>
								</div>
		                	</div>
						</form>
		            </div>
					<!-- 教案研究 -->
		            <div class="tab-content px-4" id="research-info">
		                <form class="lesson-form">
							<table class="lesson-table research-table">
								<tr>
									<td class="lesson-label">選擇研究類型</td>
									<td class="research-type">
										<div class="lesson-short-text-input">
											<select name="research-type-select" id="research-type-select" class="lesson-input">
												<option>論文期刊</option>
												<option>Clinical-Trial</option>
											</select>
										</div>
									</td>
								</tr>
							    <tr>
							        <td class="lesson-label">論文期刊引用資訊</td>
							        <td>
								        <div class="lesson-long-text-input">
								        	<input type="text" id="apa-text" name="apa-text" placeholder="請輸入論文期刊引用資訊" class="lesson-input">
								        </div>
							        </td>
							    </tr>
								<tr>
							        <td class="lesson-label">論文期刊連結</td>
							        <td>
								        <div class="lesson-long-text-input">
								        	<input type="text" id="research-link" name="research-link" placeholder="請輸入論文期刊連結" class="lesson-input">
								        </div>
							        </td>
							    </tr>
							</table>
							<div class="button-container">
								<div class="form-buttons">
									<button class="btn-save">儲存</button>
									<button class="btn-cancel">回總表</button>
								</div>
		                	</div>
						</form>
		            </div>
		    	</div>
	    	</div>
        </div>
    </main>
	<!-- 解鎖圖片上傳 Modal -->
	<div class="modal fade" id="unlockedImageModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">上傳解鎖圖片</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3">
						<div class="upload-area achievement-upload-area" id="unlocked-upload-area">
							<input type="file" id="unlocked-image" accept="image/*" class="file-input">
							<label for="unlocked-image">
								<div class="upload-icon"><i class="fas fa-upload"></i></div>
								<p>拖放圖片到此處或點擊選擇檔案</p>
							</label>
						</div>
						<img id="unlocked-preview" src="" class="img-preview mt-2" style="display: none;">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="save-unlocked-image">儲存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 未解鎖圖片上傳 Modal -->
	<div class="modal fade" id="lockedImageModal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">上傳未解鎖圖片</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>
				<div class="modal-body">
					<div class="mb-3">
						<div class="upload-area achievement-upload-area" id="locked-upload-area">
							<input type="file" id="locked-image" accept="image/*" class="file-input">
							<label for="locked-image">
								<div class="upload-icon"><i class="fas fa-upload"></i></div>
								<p>拖放圖片到此處或點擊選擇檔案</p>
							</label>
						</div>
						<img id="locked-preview" src="" class="img-preview mt-2" style="display: none;">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="save-locked-image">儲存</button>
				</div>
			</div>
		</div>
	</div>
    <script src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script src="/html/dtxstore/script/commonPage/leftnav.js"></script>
	<script src="/html/dtxstore/script/commonPage/swal2.js"></script>
	<script src="/html/dtxstore/script/quill-2.0.3.js"></script>
	<script src="/html/dtxstore/script/datatables.min.js"></script>

	<script>
	let userId;
	<#if currentUser??>
	userId = ${currentUser.id};
	</#if>
	let lessonId;
	<#if lessonId??>
	lessonId = ${lessonId};
	</#if>
	let lessonMainInfo;
	<#if lessonMainInfo??>
	lessonMainInfo = ${lessonMainInfo};
		if(lessonMainInfo.executionId.includes('steam')){
			$('.lowCode-tr').hide();
			$('.upload-exe').hide();
		}else{
			$('.lowCode-tr').show();
			$('.upload-exe').show();
		}
	</#if>

	let newsEditor;
	$(document).ready(function () {

		switchTab();
		cancelBtn();
		saveBtn();
		if(lessonMainInfo){
			// 顯示教案資訊
			console.log('lessonMainInfo: ', lessonMainInfo);
			showLessonInfo();
		}
		showAchievement();
		newsEditor = initQuillEditor("#news-editor", "#news-content");
		showStatistics();
		setupLessonImageUpload();
		lessonTag();
	});

	// 初始化圖片網格的事件監聽器
	function initImageGridEvents() {
		// 點擊圖片打開模態預覽
		$('.image-grid-item img').on('click', function() {
			const imgSrc = $(this).attr('src');
			$('#modalImage').attr('src', imgSrc);
			$('#imageModal').css('display', 'block');
		});
		
		// 點擊刪除按鈕
		$('.remove-grid-image').on('click', function(e) {
			e.stopPropagation(); // 阻止冒泡，避免觸發圖片點擊事件
			
			const gridItem = $(this).closest('.image-grid-item');
			const imageId = gridItem.data('id');
			const imageIndex = gridItem.data('index');
			
			// 確認刪除
			Swal.fire({
				title: "確定要刪除這張圖片嗎？",
				text: "刪除後將無法恢復！",
				icon: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d33",
				cancelButtonColor: "#3085d6",
				confirmButtonText: "是的，刪除",
				cancelButtonText: "取消"
			}).then((result) => {
				if (result.isConfirmed) {
					// 根據 ID 從後端刪除圖片
					$.ajax({
						url: "/LessonImage/api/delete", // 假設有這個 API
						type: "POST",
						contentType: "application/json",
						data: JSON.stringify({ "imageId": imageId }),
						success: function(response) {
							// 從 DOM 中移除圖片
							gridItem.remove();
							
							// 從全局變數中移除該資源
							lessonImageResources = lessonImageResources.filter((item, idx) => idx !== imageIndex);
							
							// 重新渲染圖片網格以更新索引
							renderImageGrid(lessonImageResources);
							
							Swal.fire("刪除成功！", "該圖片已被刪除。", "success");
						},
						error: function(xhr, status, error) {
							console.error("刪除圖片失敗:", error);
							Swal.fire("刪除失敗", "請稍後再試。", "error");
						}
					});
				}
			});
		});
	}
	
	// 初始化水平滾動指示器功能
	function initScrollIndicators() {
		const container = $('#lesson-images-grid');
		const leftIndicator = $('#scroll-left');
		const rightIndicator = $('#scroll-right');
		const scrollAmount = 300; // 增加每次滾動的像素數量
		
		// 初始檢查滾動狀態
		updateScrollIndicatorsState();
		
		// 左邊滾動按鈕點擊事件
		leftIndicator.off('click').on('click', function() {
			if (!$(this).hasClass('disabled')) {
				container.animate({
					scrollLeft: container.scrollLeft() - scrollAmount
				}, 300);
			}
		});
		
		// 右邊滾動按鈕點擊事件
		rightIndicator.off('click').on('click', function() {
			if (!$(this).hasClass('disabled')) {
				container.animate({
					scrollLeft: container.scrollLeft() + scrollAmount
				}, 300);
			}
		});
		
		// 為容器添加滑鼠滾輪事件
		container.off('wheel').on('wheel', function(e) {
		e.preventDefault();
		// 直接改 scrollLeft，沒有動畫延遲
		container[0].scrollLeft += e.originalEvent.deltaY > 0 ? 400 : -400;
		updateScrollIndicatorsState();
		});
		
		// 監聽滾動事件，更新指示器狀態
		container.off('scroll').on('scroll', function() {
			updateScrollIndicatorsState();
		});
		
		// 更新滾動指示器狀態
		function updateScrollIndicatorsState() {
			const scrollLeft = container.scrollLeft();
			const maxScrollLeft = container[0].scrollWidth - container[0].clientWidth;
			
			// 清除所有禁用狀態並重新評估
			leftIndicator.removeClass('disabled');
			rightIndicator.removeClass('disabled');
			
			// 檢查是否有內容需要滾動
			const hasOverflow = container[0].scrollWidth > container[0].clientWidth;
			
			if (!hasOverflow) {
				// 如果內容不需要滾動，禁用兩側按鈕
				leftIndicator.addClass('disabled');
				rightIndicator.addClass('disabled');
				console.log('沒有內容需要滾動，禁用兩側按鈕');
				return;
			}
			
			// 當滾動到最左側時，禁用左側按鈕
			if (scrollLeft <= 5) {
				leftIndicator.addClass('disabled');
			}
			
			// 當滾動到最右側時，禁用右側按鈕
			if (scrollLeft >= maxScrollLeft - 5) {
				rightIndicator.addClass('disabled');
			}
		}
		
		// 窗口大小改變時重新檢查滾動狀態
		$(window).resize(function() {
			updateScrollIndicatorsState();
		});
	}
	
	// 模態預覽圖片的事件處理
	$(document).ready(function() {
		// 點擊關閉按鈕關閉模態對話框
		$('.image-modal-close').on('click', function() {
			$('#imageModal').css('display', 'none');
		});
		
		// 點擊模態對話框背景時也關閉
		$('#imageModal').on('click', function(event) {
			if (event.target === this) {
				$(this).css('display', 'none');
			}
		});
		
		// 確保頁面加載完後初始化水平滾動功能
		setTimeout(function() {
			if ($('#lesson-images-grid').children().length > 0) {
				initScrollIndicators();
			}
		}, 500);
	});

	// 設置教案圖片上傳功能
	function setupLessonImageUpload() {
		// 檢查元素是否存在，避免錯誤
		if ($('#lesson-header-image').length > 0) {
			// 封面圖片上傳預覽
			$('#lesson-header-image').on('change', function(e) {
				const file = e.target.files[0];
				if (file) {
					const reader = new FileReader();
					reader.onload = function(e) {
						$('#header-image-preview').attr('src', e.target.result);
						$('#header-preview-container').show();
						//$('#header-upload-area').hide();
					};
					reader.readAsDataURL(file);
				}
			});
		}

		// 檢查移除封面圖片按鈕是否存在
		if ($('#remove-header-image').length > 0) {
			// 移除封面圖片
			$('#remove-header-image').on('click', function() {
				$('#header-image-preview').attr('src', '');
				$('#header-preview-container').hide();
				$('#header-upload-area').show();
				$('#lesson-header-image').val('');
			});
		}

		// 檢查教案圖片元素是否存在
		if ($('#lesson-body-image').length > 0) {
			// 教案圖片上傳預覽
			$('#lesson-body-image').on('change', function(e) {
				const file = e.target.files[0];
				if (file) {
					const reader = new FileReader();
					reader.onload = function(e) {
						$('#body-image-preview').attr('src', e.target.result);
						$('#body-preview-container').show();
						$('#body-upload-area').hide();
					};
					reader.readAsDataURL(file);
				}
			});
		}

		// 檢查移除教案圖片按鈕是否存在
		if ($('#remove-body-image').length > 0) {
			// 移除教案圖片
			$('#remove-body-image').on('click', function() {
				$('#body-image-preview').attr('src', '');
				$('#body-preview-container').hide();
				$('#body-upload-area').show();
				$('#lesson-body-image').val('');
			});
		}

		// 檢查教案影片元素是否存在
		if ($('#lesson-video').length > 0) {
			// 教案影片上傳預覽
			$('#lesson-video').on('change', function(e) {
				const file = e.target.files[0];
				if (file) {
					const url = URL.createObjectURL(file);
					$('#video-preview').attr('src', url);
					$('#video-preview-container').show();
					// $('#video-upload-area').hide(); // <-- 移除這行，讓上傳區一直顯示
				}
			});
		}

		// 檢查移除教案影片按鈕是否存在
		if ($('#remove-video').length > 0) {
			// 移除教案影片
			$('#remove-video').on('click', function() {
				const videoElement = $('#video-preview')[0];
				if (videoElement && videoElement.src) {
					URL.revokeObjectURL(videoElement.src);
				}
				$('#video-preview').attr('src', '');
				$('#video-preview-container').hide();
				$('#video-upload-area').show();
				$('#lesson-video').val('');
			});
		}
	}

	// Tab 切換功能
	function switchTab() {
		document.querySelectorAll(".tab-menu li").forEach(tab => {
			tab.addEventListener("click", () => {
				const activeTab = document.querySelector(".tab-menu .active");
				const activeContent = document.querySelector(".tab-content.active");
				
				activeTab.classList.remove("active");
				activeContent.classList.remove("active");
				
				tab.classList.add("active");
				const targetContent = document.getElementById(tab.getAttribute("data-tab"));
				if (targetContent) {
					targetContent.classList.add("active");
				}
			});
		});
	}


    // 取消按鈕
	function cancelBtn(){
		$(".btn-cancel").on("click", function (event) {
			event.preventDefault();
			Swal.fire({
				title: "確定要離開嗎？",
				text: "資料尚未儲存，離開後將遺失變更",
				icon: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d33",
				cancelButtonColor: "#3085d6",
				confirmButtonText: "是的，離開",
				cancelButtonText: "取消"
			}).then((result) => {
				if (result.isConfirmed) {
					window.location.href = "/ftl/dtxstore/manage/list"; // 返回總表
				}
			});
		});
	}

	// 儲存按鈕
	function saveBtn(){
		$(".btn-save").on("click", function (event) {
			event.preventDefault();

			// 收集表單數據
			let formData = new FormData();
			formData.append("lesson-name", $("#lesson-name").val());
			formData.append("lesson-description", $("#lesson-description").val());
			formData.append("lesson-purpose", $("#lesson-purpose").val());
			formData.append("lesson-precaution", $("#lesson-precaution").val());
			formData.append("lesson-price", $("#lesson-price").val());
			
			// 檢查並添加封面圖片文件（如果有選擇新文件）
			const headerImageFile = $("#lesson-header-image")[0].files[0];
			if (headerImageFile) {
				formData.append("lesson-header-image", headerImageFile);
			} else if ($("#header-image-preview").attr("src") && $("#header-preview-container").is(":visible")) {
				// 如果沒有選新文件，但有預覽圖，表示使用現有圖片
				formData.append("lesson-header-image-url", $("#header-image-preview").attr("src"));
			}
			
			// 處理現有的教案圖片資源
			if (lessonImageResources && lessonImageResources.length > 0) {
				// 將所有現有圖片的 ID 傳遞給後端
				const existingImageIds = lessonImageResources.map(img => img.id);
				formData.append("existing-image-ids", JSON.stringify(existingImageIds));
			}
			
			// 檢查並添加新上傳的教案圖片文件
			const bodyImageFile = $("#lesson-body-image")[0].files[0];
			if (bodyImageFile) {
				formData.append("lesson-body-image", bodyImageFile);
				
				// 如果用戶既上傳了新圖片，又預覽了這張圖片
				if ($("#body-preview-container").is(":visible")) {
					formData.append("has-new-lesson-image", "true");
				}
			}
			
			// 檢查並添加教案影片文件（如果有選擇新文件）
			const videoFile = $("#lesson-video")[0].files[0];
			if (videoFile) {
				formData.append("lesson-video", videoFile);
			} else if ($("#video-preview").attr("src") && $("#video-preview-container").is(":visible")) {
				// 如果沒有選新文件，但有預覽影片，表示使用現有影片
				formData.append("lesson-video-url", $("#video-preview").attr("src"));
			}
			
			formData.append("news-content", newsEditor.root.innerHTML);
			
			// 如果是編輯已存在的教案，添加教案ID
			if (lessonId) {
				formData.append("lessonId", lessonId);
			}

			// 取得所有選中的適應症
			let indications = [];
			$("input[name='lesson-indication']:checked").each(function () {
				indications.push($(this).val());
			});
			formData.append("lesson-indications", indications.join(",")); 
			$.ajax({
				url: "/LessonMainInfo/api/saveLesson",
				type: "POST",
				data: formData,
				processData: false,
				contentType: false,
				success: function (response) {
					Swal.fire({
						title: "儲存成功",
						text: "您的教案已成功儲存！",
						icon: "success",
						confirmButtonText: "確定"
					}).then(() => {
						window.location.href = "/ftl/dtxstore/manage/list"; // 成功後返回列表
					});
				},
				error: function (xhr, status, error) {
					Swal.fire({
						title: "儲存失敗",
						text: "請檢查輸入內容，或稍後再試。",
						icon: "error",
						confirmButtonText: "確定"
					});
				}
			});
		});
	}

	function initQuillEditor(selector, hiddenInputSelector) {
		let quill = new Quill(selector, {
			theme: 'snow',  // 可選 'bubble'
			placeholder: '請輸入新聞內容...',
			modules: {
				toolbar: [
					[{ header: [1, 2, false] }],
					['bold', 'italic', 'underline'],
					['blockquote', 'code-block'],
					[{ list: 'ordered' }, { list: 'bullet' }],
					[{ script: 'sub' }, { script: 'super' }],
					[{ indent: '-1' }, { indent: '+1' }],
					[{ direction: 'rtl' }],
					[{ size: ['small', false, 'large', 'huge'] }],
					[{ color: [] }, { background: [] }],
					[{ font: [] }],
					[{ align: [] }],
					['clean']
				]
			}
		});

		// 當表單提交時，把 Quill 內容存入隱藏欄位
		$(".lesson-form").on("submit", function () {
			$(hiddenInputSelector).val(quill.root.innerHTML);
		});

		return quill; // 回傳 Quill 物件，以便後續操作
	}

	/* 取得教案資訊 */
	function showLessonInfo() {
		$('.lesson-title').text(lessonMainInfo.lessonName);
		$('#lesson-name').val(lessonMainInfo.lessonName);
		$('#lesson-description').val(lessonMainInfo.lessonBrief);
		$('#lesson-purpose').val(lessonMainInfo.lessonPurpose);
		$('#lesson-precaution').val(lessonMainInfo.usageRestriction);
		$('#lesson-price').val(lessonMainInfo.price);
		
		// 顯示封面圖片預覽
		if (lessonMainInfo.headerImageUrl) {
			const headerImageUrl = 'http://127.0.0.1:7000/File/api/file/path' + lessonMainInfo.headerImageUrl;
			$('#header-image-preview').attr('src', headerImageUrl);
			$('#header-preview-container').show();
			//$('#header-upload-area').hide();
		}
		
		// 如果有教案ID則從API獲取該教案的所有圖片和影片資源
		if (lessonId) {
			fetchLessonMedias(lessonId);
		}
	}
	
	// 全局變數用於追踪已加載的教案圖片資源
	let lessonImageResources = [];
	
	// 從API獲取教案的圖片和影片資源
	function fetchLessonMedias(lessonId) {
		$.ajax({
			url: "/LessonImage/api/lesson",
			type: "POST",
			contentType: "application/json",
			data: JSON.stringify({ "lessonId": lessonId }),
			success: function(response) {
				console.log("教案媒體資源: ", response);
				
				// 處理回傳的媒體資源
				if (Array.isArray(response) && response.length > 0) {
					// 分類處理媒體資源
					const videos = response.filter(item => item.type === "VIDEO");
					const images = response.filter(item => item.type === "IMAGE");
					
					// 儲存圖片資源到全局變數中
					lessonImageResources = images;
					
					// 顯示影片(如果有)
					if (videos.length > 0) {
						// 取第一個影片顯示，如果有多個的話
						const video = videos[0];
						let videoUrl = video.filePath;
						
						// 檢查是否為絕對URL，若不是則加上前綴
						if (!videoUrl.startsWith('http')) {
							videoUrl = 'http://127.0.0.1:7000/File/api/file/path' + videoUrl;
						}
						
						// 設定影片源並顯示
						$('#video-preview').attr('src', videoUrl);
						$('#video-preview-container').show();
						//$('#video-upload-area').hide();
					}
					
					// 顯示所有教案圖片
					if (images.length > 0) {
						renderImageGrid(images);
					}
				}
			},
			error: function(xhr, status, error) {
				console.error("獲取教案媒體資源失敗:", error);
			}
		});
	}
	
	// 渲染教案圖片網格
	function renderImageGrid(images) {
		// 清空圖片網格容器
		const gridContainer = $('#lesson-images-grid');
		gridContainer.empty();
		
		// 遍歷所有圖片並創建網格項
		images.forEach((image, index) => {
			let imageUrl = image.filePath;
			
			// 檢查是否為絕對URL，若不是則加上前綴
			if (!imageUrl.startsWith('http')) {
				imageUrl = 'http://127.0.0.1:7000/File/api/file/path' + imageUrl;
			}
			
			// 創建網格項元素
			const gridItem = $(`
				<div class="image-grid-item" data-id="` + image.id + `" data-index="` + index + `">
					<img src="` + imageUrl + `" alt="教案圖片`+ (index + 1) +`">
					<button type="button" class="btn btn-danger btn-sm remove-grid-image">
						<i class="fas fa-times"></i>
					</button>
				</div>
			`);
			
			// 將網格項加入到容器中
			gridContainer.append(gridItem);
		});
		
		// 初始化圖片網格的事件監聽器
		initImageGridEvents();
		
		// 初始化滾動指示器
		initScrollIndicators();
	}

	function showAchievement() {
		let table;
		$('#achievementTable tbody').empty(); // 銷毀舊的 DataTable 實例
		if(lessonId == null){
			return;
		}
		$.ajax({
			url: "/LessonAchievement/api/lesson",
			type: "POST",
			data: JSON.stringify({"lessonId": lessonId}),
			success: function (response) {
				console.log(response);
				// 確保 response 是有效陣列
				if (!Array.isArray(response) || response.length === 0) {
					console.warn("無成就資料或 response 格式錯誤");
					return;
				}

				// 轉換 response 為 DataTables 可用的格式
				let tableData = response.map((item, index) => {
					return {
						dbId: item.id,  // 紀錄資料庫 ID
						id: item.id,  // 自動生成 ID
						apiName: item.apiName,
						displayName: item.displayName,
						description: item.description,
						unlockedIcon: `<img src="/File/api/file/path` + item.unlockedIconUrl + `" width="50" height="50" class="thumbnail achievement-image" data-type="unlocked">`,
						lockedIcon: `<img src="/File/api/file/path` + item.lockedIconUrl + `" width="50" height="50" class="thumbnail achievement-image" data-type="locked">`
					};
				});

				console.log(tableData);

				// 初始化 DataTables
				table = $('#achievementTable').DataTable({
					destroy: true,   // 避免重複初始化
					data: tableData, // 設定資料
					rowId: 'dbId', // 這行讓 DataTables 內部綁定資料庫 ID
					columns: [
						{ data: "id", title: "ID" },
						{ data: "apiName", title: "API 名稱" },
						{ data: "displayName", title: "顯示名稱" },
						{ data: "description", title: "成就描述" },
						{ data: "unlockedIcon", title: "解鎖圖片", orderable: false },
						{ data: "lockedIcon", title: "未解鎖圖片", orderable: false },
						{ data: null, title: "操作", defaultContent: `<div class="func-button-group">
																		<button class="btn-ach-edit"><i class="fa-solid fa-pen-to-square"></i></button>
																		<button class="btn-ach-delete"><i class="fa-regular fa-trash-can"></i></button>
																	</div>` }
					],
					language: {
						url: "/html/dtxstore/script/zh-HANT.json"
					}
				});
			},
			error: function (xhr, status, error) {
				Swal.fire({
					title: "載入成就失敗",
					text: "請通知管理員，或稍後再試。",
					icon: "error",
					confirmButtonText: "確定"
				});
			}
		});

		// 點擊編輯按鈕
		$('#achievementTable tbody').on('click', '.btn-ach-edit', function () {
			let $row = $(this).closest('tr');
			let rowData = table.row($row).data();
			let dbId = rowData.dbId;
			
			// 標記該行為編輯模式
			$row.addClass('editing');
			
			// 儲存原始資料
			$row.data('originalData', table.row($row).data());
			
			// 將可編輯欄位轉為 input
			$row.find('td').each(function (index) {
				if (index === 1 || index === 2 || index === 3) {
					let cellText = $(this).text().trim();
					$(this).html('<input type="text" class="form-control" value="'+cellText+'">');
				} else if (index === 4 || index === 5) {
					// 為圖片加上可點擊的視覺提示
					let $img = $(this).find('img');
					$img.addClass('clickable');
					// 重新綁定點擊事件
					$img.off('click').on('click', function() {
						if ($row.hasClass('editing')) {
							let imageType = $(this).data('type');
							let modalId = imageType === 'unlocked' ? 'unlockedImageModal' : 'lockedImageModal';
							let modal = new bootstrap.Modal(document.getElementById(modalId));
							
							// 儲存當前行資料到 Modal
							$('#'+ modalId).data('rowData', {
								row: $row,
								data: rowData,
								imageType: imageType
							});
							
							modal.show();
						}
					});
				}
			});
			
			// 變更按鈕
			$(this).removeClass('btn-ach-edit').addClass('btn-ach-save')
				.html('<i class="fa-solid fa-check"></i>');
			$row.find('.btn-ach-delete').removeClass('btn-ach-delete').addClass('btn-ach-cancel')
				.html('<i class="fa-solid fa-times"></i>');
		});

		// 修改儲存按鈕的處理
		$('#achievementTable tbody').on('click', '.btn-ach-save', function () {
			let $row = $(this).closest('tr');
			let rowData = table.row($row).data();
			
			// 讀取 input 值
			let newApiName = $row.find('td:eq(1) input').val();
			let newDisplayName = $row.find('td:eq(2) input').val();
			let newDescription = $row.find('td:eq(3) input').val();

			// 建立 FormData
			let formData = new FormData();
			formData.append('id', rowData.dbId);
			formData.append('lessonId', lessonId);
			formData.append('apiName', newApiName);
			formData.append('displayName', newDisplayName);
			formData.append('description', newDescription);
			formData.append('unlockValue', rowData.unlockValue || 0.0);
			formData.append('hidden', rowData.hidden || false);
			formData.append('creator', userId);

			// 檢查是否有暫存的圖片檔案
			let unlockedFile = $row.data('unlockedFile');
			let lockedFile = $row.data('lockedFile');
			if (unlockedFile) {
				formData.append('unlockedImage', unlockedFile);
			}
			if (lockedFile) {
				formData.append('lockedImage', lockedFile);
			}

			    // 正確顯示 FormData 內容
			console.log('FormData contents:');
			for (let pair of formData.entries()) {
				console.log(pair[0] + ': ' + (pair[1] instanceof File ? pair[1].name : pair[1]));
			}
			// 發送 AJAX 請求
			$.ajax({
				url: '/LessonAchievement/api/save',
				type: 'POST',
				data: formData,
				processData: false,
				contentType: false,
				success: function(response) {
					console.log(response);
					if(response.success) {
						console.log('response: ', response);
						// 更新表格資料
						rowData.apiName = newApiName;
						rowData.displayName = newDisplayName;
						rowData.description = newDescription;
						if (response.unlockedIconUrl) rowData.unlockedIcon = rowData.unlockedIcon = `<img src="` + response.unlockedIconUrl + `" width="50" height="50" class="thumbnail achievement-image" data-type="unlocked">`;
						if (response.lockedIconUrl) rowData.lockedIcon = rowData.lockedIcon = `<img src="` + response.lockedIconUrl + `" width="50" height="50" class="thumbnail achievement-image" data-type="locked">`;
						
						// 移除編輯模式和暫存資料
						$row.removeClass('editing');
						$row.removeData('unlockedFile lockedFile unlockedPreview lockedPreview');
						
						// 重繪該行
						table.row($row).data(rowData).draw(false);
						
						Swal.fire({
							title: '儲存成功',
							text: '成就已成功更新！',
							icon: 'success',
							confirmButtonText: '確定'
						});
					} else {
						Swal.fire({
							title: '儲存失敗',
							text: response.message || '發生未知錯誤',
							icon: 'error',
							confirmButtonText: '確定'
						});
					}
				},
				error: function() {
					Swal.fire({
						title: '儲存失敗',
						text: '請稍後再試或聯繫管理員',
						icon: 'error',
						confirmButtonText: '確定'
					});
				}
			});
		});
		// 點擊儲存按鈕，完成儲存動作
		/*$('#achievementTable tbody').on('click', '.btn-ach-save', function () {
			let $row = $(this).closest('tr');
			let rowData = table.row($row).data();
			console.log("rowData", rowData);
			// 讀取 input 值並更新資料（這邊依照欄位順序更新，可依實際情況調整）
			let newApiName = $row.find('td:eq(1) input').val();
			let newDisplayName = $row.find('td:eq(2) input').val();
			let newDescription = $row.find('td:eq(3) input').val();

            // 使用正則表達式從 HTML 字串中萃取 URL
            let unlockedIconUrl = rowData.unlockedIcon.match(/src="([^"]+)"/)?.[1] || "";
            let lockedIconUrl = rowData.lockedIcon.match(/src="([^"]+)"/)?.[1] || "";
			// 更新資料物件
			let updatedData = {
				id: rowData.dbId,   // 資料庫 ID
				lessonId: lessonId,            // 關聯的 lessonId
				apiName: newApiName,
				displayName: newDisplayName,
				description: newDescription,
				unlockValue: rowData.unlockValue || 0.0,
				hidden: rowData.hidden || false,
				unlockedIconUrl: unlockedIconUrl || "",
				lockedIconUrl: lockedIconUrl || "",
				creator: userId
			};

			console.log('uploadData: ', updatedData);
			// 發送 AJAX POST 請求，將 updatedData 送到後端
			$.ajax({
				url: "/LessonAchievement/api/save",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(updatedData),
				success: function(response) {
					console.log(response);
					if(response.success) {
						// 更新表格資料
						rowData.apiName = newApiName;
						rowData.displayName = newDisplayName;
						rowData.description = newDescription;
						
						// 更新圖片 HTML
						if (response.unlockedIconUrl) {
							rowData.unlockedIcon = `<img src="/File/api/file/path` + response.unlockedIconUrl+`" width="50" height="50" class="thumbnail achievement-image" data-type="unlocked">`;
						}
						if (response.lockedIconUrl) {
							rowData.lockedIcon = `<img src="/File/api/file/path` + response.lockedIconUrl+`" width="50" height="50" class="thumbnail achievement-image" data-type="locked">`;
						}

						// 移除編輯模式和暫存資料
						$row.removeClass('editing');
						$row.removeData('unlockedFile lockedFile unlockedPreview lockedPreview');
						
						// 重繪該行
						table.row($row).data(rowData).draw(false);
						
						Swal.fire({
							title: '儲存成功',
							text: '成就已成功更新！',
							icon: 'success',
							confirmButtonText: '確定'
						});
					} else {
						Swal.fire({
							title: '儲存失敗',
							text: response.message || '發生未知錯誤',
							icon: 'error',
							confirmButtonText: '確定'
						});
					}
				},
				error: function () {
					Swal.fire({
						title: "儲存失敗",
						text: "請稍後再試或聯繫管理員",
						icon: "error",
						confirmButtonText: "確定"
					});
				}
			});
		});*/


	// 修改取消按鈕的處理，確保清除暫存資料
	$('#achievementTable tbody').on('click', '.btn-ach-cancel', function () {
		let $row = $(this).closest('tr');
		let originalData = $row.data('originalData');
		
		// 移除編輯模式標記和暫存資料
		$row.removeClass('editing');
		$row.removeData('unlockedFile lockedFile unlockedPreview lockedPreview');
		
		// 移除圖片的可點擊視覺提示
		$row.find('.achievement-image').removeClass('clickable');
		
		// 使用原始資料重繪該行
		table.row($row).data(originalData).draw(false);

		// 還原按鈕
		$row.find('.btn-ach-save').removeClass('btn-ach-save').addClass('btn-ach-edit')
			.html('<i class="fa-solid fa-pen-to-square"></i>');
		$(this).removeClass('btn-ach-cancel').addClass('btn-ach-delete')
			.html('<i class="fa-regular fa-trash-can"></i>');

		// 移除 Modal 相關元素
		$('.modal-backdrop').remove();
		$('body').removeClass('modal-open').css('overflow', '');
	});

		// 點擊刪除按鈕，刪除該成就資料
		$('#achievementTable tbody').on('click', '.btn-ach-delete', function () {
			let $row = $(this).closest('tr');
			let rowData = table.row($row).data();
			let dbId = rowData.dbId; // 取得資料庫 ID

			Swal.fire({
				title: "確定要刪除嗎？",
				text: "刪除後將無法恢復！",
				icon: "warning",
				showCancelButton: true,
				confirmButtonColor: "#d33",
				cancelButtonColor: "#3085d6",
				confirmButtonText: "是的，刪除",
				cancelButtonText: "取消"
			}).then((result) => {
				if (result.isConfirmed) {
					$.ajax({
						url: "/LessonAchievement/api/delete",
						type: "POST",
						contentType: "application/json",
						data: JSON.stringify({ achievementId: dbId }),  // 透過 dbId 刪除資料庫內對應的資料
						success: function (response) {
							// 從 DataTable 移除該行
							if(response.success){
								table.row($row).remove().draw();
								Swal.fire("刪除成功！", "該筆資料已被刪除。", "success");
							} else {
								Swal.fire("刪除失敗!", response.message, "error");
							}
						},
						error: function (xhr, status, error) {
							Swal.fire("刪除失敗！", "請稍後再試。", "error");
						}
					});
				}
			});
		});

		// 點擊圖片開啟對應的 Modal
		$('#achievementTable').on('click', '.achievement-image', function() {
			// 檢查是否在編輯模式
			let $row = $(this).closest('tr');
			if (!$row.hasClass('editing')) {
				return; // 如果不是編輯模式，直接返回
			}

			let rowData = table.row($row).data();
			let imageType = $(this).data('type');
			
			// 根據圖片類型選擇要開啟的 Modal
			let modalId = imageType === 'unlocked' ? 'unlockedImageModal' : 'lockedImageModal';
			let modal = new bootstrap.Modal(document.getElementById(modalId));
			
			// 儲存當前行資料到 Modal
			$('#'+modalId).data('rowData', {
				row: $row,
				data: rowData,
				imageType: imageType
			});
			
			modal.show();
		});

		setupAchievementImageUpload();
	}

	function setupAchievementImageUpload() {
		// 設置兩個上傳區域
		setupImageUpload('#unlocked-upload-area', '#unlocked-image', '#unlocked-preview');
		setupImageUpload('#locked-upload-area', '#locked-image', '#locked-preview');

		// 解鎖圖片儲存
		$('#save-unlocked-image').click(function() {
			saveAchievementImage('unlockedImageModal', 'unlocked');
		});

		// 未解鎖圖片儲存
		$('#save-locked-image').click(function() {
			saveAchievementImage('lockedImageModal', 'locked');
		});
	}

	function previewImage(file, previewElement) {
		// 檢查檔案大小
		if (file.size > 1024 * 1024) { // 1MB
			Swal.fire({
				title: '圖片太大',
				text: '請上傳小於 1MB 的圖片',
				icon: 'error'
			});
			return;
		}

		// 檢查圖片尺寸
		const img = new Image();
		img.src = URL.createObjectURL(file);
		
		img.onload = function() {
			URL.revokeObjectURL(this.src); // 釋放記憶體
			
			if (this.width !== 64 || this.height !== 64) {
				Swal.fire({
					title: '圖片尺寸不符',
					text: '請上傳 64x64 像素的圖片',
					icon: 'error'
				});
				return;
			}

			// 通過檢查後，顯示預覽
			const reader = new FileReader();
			reader.onload = (e) => {
				previewElement.src = e.target.result;
				previewElement.style.display = 'block';
			};
			reader.readAsDataURL(file);
		};
	}

	function saveAchievementImage(modalId, imageType) {
		let modalData = $('#'+modalId).data('rowData');
		let $row = modalData.row;
		let fileInput = imageType === 'unlocked' ? '#unlocked-image' : '#locked-image';
		let file = $(fileInput)[0].files[0];

		// 檢查檔案是否存在和符合要求
		if (!file) {
			Swal.fire({
				title: '請選擇圖片',
				text: '尚未選擇任何圖片',
				icon: 'warning'
			});
			return;
		}

		// 檢查檔案大小
		if (file.size > 1024 * 1024) {
			Swal.fire({
				title: '圖片太大',
				text: '請上傳小於 1MB 的圖片',
				icon: 'error'
			});
			return;
		}

		// 檢查圖片尺寸
		const img = new Image();
		img.src = URL.createObjectURL(file);
		
		img.onload = function() {
			URL.revokeObjectURL(this.src);
			
			if (this.width !== 64 || this.height !== 64) {
				Swal.fire({
					title: '圖片尺寸不符',
					text: '請上傳 64x64 像素的圖片',
					icon: 'error'
				});
				return;
			}

			// 讀取檔案並暫存
			const reader = new FileReader();
			reader.onload = function(e) {
				// 暫存圖片檔案和預覽URL
				$row.data(imageType + 'File', file);
				$row.data(imageType + 'Preview', e.target.result);
				
				// 更新表格中的圖片預覽
				let $img = $row.find(`img[data-type="` + imageType + `"]`);
				$img.attr('src', e.target.result);
				$img.addClass('clickable');
				
				// 關閉 Modal
				bootstrap.Modal.getInstance(document.getElementById(modalId)).hide();
				
				// 清除 Modal 中的預覽圖和input
				$(`#` + imageType + `-preview`).attr('src', '').hide();
				$(fileInput).val('');
			};
			reader.readAsDataURL(file);
		};
	}

	// 設置圖片上傳區域
	function setupImageUpload(areaSelector, inputSelector, previewSelector) {
		const uploadArea = document.querySelector(areaSelector);
		const fileInput = document.querySelector(inputSelector);
		const preview = document.querySelector(previewSelector);

		// DragOver
		uploadArea.addEventListener('dragover', (e) => {
			e.preventDefault();
			uploadArea.style.backgroundColor = '#E8F8F3';
		});

		// DragLeave
		uploadArea.addEventListener('dragleave', (e) => {
			e.preventDefault();
			uploadArea.style.backgroundColor = '#F8F8F8';
		});

		// Drop
		uploadArea.addEventListener('drop', (e) => {
			e.preventDefault();
			uploadArea.style.backgroundColor = '#F8F8F8';
			
			const files = e.dataTransfer.files;
			if (files.length > 0) {
				fileInput.files = files;
				previewImage(files[0], preview);
			}
		});

		// 檔案選擇變更
		fileInput.addEventListener('change', (e) => {
			const file = e.target.files[0];
			if (file) {
				previewImage(file, preview);
			}
		});
	}

	// 預覽圖片
	function previewImage(file, previewElement) {
		const reader = new FileReader();
		reader.onload = (e) => {
			previewElement.src = e.target.result;
			previewElement.style.display = 'block';
		};
		reader.readAsDataURL(file);
	}

	// 預覽影片函數
	function previewVideo(file, previewElement) {
		// 釋放之前的物件URL（如果有的話）
		if (previewElement.src && previewElement.src.startsWith('blob:')) {
			URL.revokeObjectURL(previewElement.src);
		}
		// 創建新的物件URL並設為影片源
		const url = URL.createObjectURL(file);
		previewElement.src = url;
		previewElement.style.display = 'block';
	}

    // === 1. 封面圖片事件 ===
    const headerUploadArea  = document.getElementById("header-upload-area");
    const headerFileInput   = document.getElementById("lesson-header-image");
    const headerPreview     = document.getElementById("header-image-preview");

    // DragOver
    headerUploadArea.addEventListener("dragover", (event) => {
        event.preventDefault();
        headerUploadArea.style.backgroundColor = "#E8F8F3";
    });

    // DragLeave
    headerUploadArea.addEventListener("dragleave", (event) => {
        event.preventDefault();
        headerUploadArea.style.backgroundColor = "#F8F8F8";
    });

    // Drop
    headerUploadArea.addEventListener("drop", (event) => {
        event.preventDefault();
        headerUploadArea.style.backgroundColor = "#F8F8F8";
        
        const files = event.dataTransfer.files;
        if (files.length > 0) {
            headerFileInput.files = files;  // 將檔案放到 input
            previewHeaderImage(files[0]);   // 預覽
        }
    });

    // 選檔案變動
    headerFileInput.addEventListener("change", (event) => {
        const file = event.target.files[0];
        if (file) {
            previewHeaderImage(file);
        }
    });
    
    // === 3. 教案影片事件 ===
    const videoUploadArea = document.getElementById("video-upload-area");
    const videoFileInput = document.getElementById("lesson-video");
    const videoPreview = document.getElementById("video-preview");
    
    if (videoUploadArea) {
        // DragOver
        videoUploadArea.addEventListener("dragover", (event) => {
            event.preventDefault();
            videoUploadArea.style.backgroundColor = "#E8F8F3";
        });
        // DragLeave
        videoUploadArea.addEventListener("dragleave", (event) => {
            event.preventDefault();
            videoUploadArea.style.backgroundColor = "#F8F8F8";
        });
        // Drop
        videoUploadArea.addEventListener("drop", (event) => {
            event.preventDefault();
            videoUploadArea.style.backgroundColor = "#F8F8F8";
            const files = event.dataTransfer.files;
            if (files.length > 0) {
                const file = files[0];
                // 檢查是否為影片文件
                if (file.type.startsWith('video/')) {
                    videoFileInput.files = files;  // 將檔案放到 input
                    previewVideo(file, videoPreview);
                    $('#video-preview-container').show();
                    // $('#video-upload-area').hide(); // <-- 移除這行，讓上傳區一直顯示
                } else {
                    Swal.fire({
                        title: '格式錯誤',
                        text: '請上傳影片檔案',
                        icon: 'error'
                    });
                }
            }
        });
        // 選檔案變動
        videoFileInput.addEventListener("change", (event) => {
            const file = event.target.files[0];
            if (file) {
                previewVideo(file, videoPreview);
                $('#video-preview-container').show();
                // $('#video-upload-area').hide(); // <-- 移除這行，讓上傳區一直顯示
            }
        });
    }

    // 預覽函式 (封面)
    function previewHeaderImage(file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            headerPreview.src = e.target.result;
            headerPreview.style.display = "block";
        };
        reader.readAsDataURL(file);
    }

	// 在 Modal 中加入提示文字
	$('#unlockedImageModal .modal-body, #lockedImageModal .modal-body').prepend(`
		<div class="alert alert-info" role="alert">
			<i class="fas fa-info-circle"></i> 請上傳 64x64 像素的圖片，檔案大小不超過 1MB
		</div>
	`);

	// 在 Modal 關閉時清理
	$('#unlockedImageModal, #lockedImageModal').on('hidden.bs.modal', function () {
		// 清除預覽圖和input
		let modalId = $(this).attr('id');
		let imageType = modalId === 'unlockedImageModal' ? 'unlocked' : 'locked';
		$('#'+imageType+'-preview').attr('src', '').hide();
		$('#'+imageType+'-image').val('');
		
		// 確保移除 backdrop
		$('.modal-backdrop').remove();
		$('body').removeClass('modal-open').css('overflow', '');
	});

	// 刪除教案
	$(".btn-lesson-delete").on("click", function (event) {
		event.preventDefault();

		// 取得教案名稱
		let lessonTitle = $(".lesson-title").text().trim();

		Swal.fire({
			title: "確定要刪除這個教案嗎？",
			html: `請輸入教案名稱 "<strong>` + lessonTitle + `</strong>" 來確認刪除：<br><br>
				<input type="text" id="confirmLessonName" class="swal2-input" placeholder="輸入教案名稱">`,
			icon: "warning",
			showCancelButton: true,
			confirmButtonColor: "#d33",
			cancelButtonColor: "#3085d6",
			confirmButtonText: "是的，刪除",
			cancelButtonText: "取消",
			preConfirm: () => {
				// 取得使用者輸入的名稱
				const userInput = document.getElementById("confirmLessonName").value.trim();

				// 驗證輸入是否正確
				if (userInput !== lessonTitle) {
					Swal.showValidationMessage(`輸入的教案名稱不正確，請再次確認！`);
				}
				return userInput;
			}
		}).then((result) => {
			// 確認輸入無誤後進行刪除
			if (result.isConfirmed) {
				$.ajax({
					url: "/LessonMainInfo/api/delete",
					type: "POST",
					contentType: "application/json",
					data: JSON.stringify({ lessonId: lessonId }),
					success: function (response) {
						if (response.success) {
							Swal.fire("刪除成功！", "教案已被刪除。", "success").then(() => {
								window.location.href = "/ftl/dtxstore/manage/list"; // 返回教案總表
							});
						} else {
							Swal.fire("刪除失敗", response.message || "請稍後再試。", "error");
						}
					},
					error: function () {
						Swal.fire("刪除失敗", "請稍後再試或聯繫管理員", "error");
					}
				});
			}
		});
	});




    // === 2. 教案圖片事件 ===
    const bodyUploadArea  = document.getElementById("body-upload-area");
    const bodyFileInput   = document.getElementById("lesson-body-image");
    const bodyPreview     = document.getElementById("body-image-preview");

    // DragOver
    bodyUploadArea.addEventListener("dragover", (event) => {
        event.preventDefault();
        bodyUploadArea.style.backgroundColor = "#E8F8F3";
    });

    // DragLeave
    bodyUploadArea.addEventListener("dragleave", (event) => {
        event.preventDefault();
        bodyUploadArea.style.backgroundColor = "#F8F8F8";
    });

    // Drop
    bodyUploadArea.addEventListener("drop", (event) => {
        event.preventDefault();
        bodyUploadArea.style.backgroundColor = "#F8F8F8";

        const files = event.dataTransfer.files;
        if (files.length > 0) {
            bodyFileInput.files = files;
            previewBodyImage(files[0]);
        }
    });

    // 選檔案變動
    bodyFileInput.addEventListener("change", (event) => {
        const file = event.target.files[0];
        if (file) {
            previewBodyImage(file);
        }
    });

    // 預覽函式 (教案)
    function previewBodyImage(file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            bodyPreview.src = e.target.result;
            bodyPreview.style.display = "block";
        };
        reader.readAsDataURL(file);
    }

	function showStatistics(){
		$('#statistics-info tbody').empty();
	}

	// 添加鍵盤導航支援
	$(document).on('keydown', function(e) {
		// 確保焦點在圖片區域或附近
		if ($('.image-area:hover, .image-grid-item:hover, .scroll-indicator:hover').length) {
			const container = $('#lesson-images-grid');
			if (e.keyCode === 37) { // 左箭頭
				container.animate({ scrollLeft: container.scrollLeft() - 200 }, 300);
				e.preventDefault();
			} else if (e.keyCode === 39) { // 右箭頭
				container.animate({ scrollLeft: container.scrollLeft() + 200 }, 300);
				e.preventDefault();
			}
		}
	});

// 1. 先取得已選標籤（通常在頁面初始化時 AJAX 取得）
let selectedTagIds = []; // 這裡存放已選 tag id
function lessonTag() {
$.ajax({
    url: '/LessonTag/api/lesson', // 你的 API
    type: 'post',
    data: JSON.stringify({ "lessonId": lessonId }), // 傳入教案ID
    dataType: 'json',
    success: function(response) {
        // 假設 response 是 [{id: 33, ...}, ...]
        selectedTagIds = response.map(tag => tag.id);
        // 標籤載入後再載入全部標籤
        listDTxTag();
    }
});
}
// 2. 載入全部標籤時，根據 selectedTagIds 判斷是否勾選
function listDTxTag(){
    $.ajax({
        url: '/DtxTag/api/list',
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response.success) {
                let tagList = response.tagList;
                // 清空所有 checkbox 區域
                $('#lesson-indication-checkboxes').empty();
                $('#lesson-type-checkboxes').empty();
                $('#lesson-language-checkboxes').empty();
                $('#lesson-developer-checkboxes').empty();
                $('#lesson-device-checkboxes').empty();
                $('#lesson-platform-checkboxes').empty();
                $('#lesson-time-checkboxes').empty();
                $('#lesson-coop-checkboxes').empty();
                $('#lesson-other-checkboxes').empty();

                tagList.forEach(function(tag) {
                    // 判斷是否已選
                    let checked = selectedTagIds.includes(tag.id) ? 'checked' : '';
                    let checkboxHtml = `
                        <label class="checkbox-label">
                            <input type="checkbox" name="lesson-`+getTagTypeName(tag.tagType)+`" value="`+tag.id+`" ` + checked + `>
                            <span class="custom-checkbox"></span>
                            `+tag.tagName+`
                        </label>
                    `;
                    // 根據 tagType 放到對應區塊
                    if(tag.tagType == 1){
                        $('#lesson-indication-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 2){
                        $('#lesson-type-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 3){
                        $('#lesson-language-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 4){
                        $('#lesson-developer-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 5){
                        $('#lesson-device-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 6){
                        $('#lesson-platform-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 7){
                        $('#lesson-time-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 8){
                        $('#lesson-coop-checkboxes').append(checkboxHtml);
                    } else if(tag.tagType == 9){
                        $('#lesson-other-checkboxes').append(checkboxHtml);
                    }
                });
            } else {
                console.error('無法載入標籤列表:', response.message);
            }
        },
        error: function(xhr, status, error) {
            console.error('載入標籤列表失敗:', error);
        }
    });
}

// 輔助函式：根據 tagType 取得對應名稱
function getTagTypeName(tagType) {
    switch(tagType) {
        case 1: return 'indication';
        case 2: return 'type';
        case 3: return 'language';
        case 4: return 'developer';
        case 5: return 'device';
        case 6: return 'platform';
        case 7: return 'time';
        case 8: return 'coop';
        case 9: return 'other';
        default: return '';
    }
}
	</script>

    <style>
    
    .lesson-title {
        color: #00CB96;
    }

    .upload-container {
        width: 95%;
        margin: 0 auto; /* 讓 container 水平置中 */
        display: flex;
        flex-direction: column; /* 讓內部元素從上到下排列 */
    }

    .tab-content {
        display: none;
    }

    .tab-content.active {
        display: block;
    }

    .upload-header {
        font-size: 1.5rem;
        font-weight: 900;
    }

	.upload-header a {
		text-decoration: none;
		color: inherit;
	}

    .lesson-edit-form {
        border: solid 1px #D6D9DF;
        border-radius: 8px; 
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1); /* 加入陰影效果 */
        max-width: 100%;
        overflow-x: hidden;
    }

    .tab-menu {
        display: flex;
        list-style: none;
        padding: 0;
        margin: 0 0 20px 0;
        border-bottom: 1px solid #ddd;
        background-color: #F8F8F8;
        border-radius: 8px 8px 0 0;
    }

    .tab-menu li {
        padding: 10px 20px;
        cursor: pointer;
        color: #555;
    }

    .tab-menu .active {
        border: 1px solid #ddd;
        border-bottom: 0; /* 移除 active 標籤的底部邊框 */
        color: black;
        font-weight: 900;
        background-color: white;
        border-radius: 8px 8px 0 0;
        position: relative;
        z-index: 4; /* 提高 z-index 讓它覆蓋其他元素 */
        margin-bottom: -1px; /* 向下延伸蓋住 tab-menu 的 border-bottom */
        margin-left: -1px;
    }

    .lesson-form {
        margin-top: 20px;
		max-width: 100%
    }

    .lesson-table {
        width: 100%;
		max-width: 100%;
		table-layout: fixed;
        border-collapse: collapse;
    }

    .lesson-table tr {
        border-bottom: 1px solid #ddd;
    }

    .lesson-label {
        width: 10%;
        font-weight: bold;
        color: black;
        padding: 10px 20px;
        text-align: left;
        white-space: nowrap; /* 避免文字換行 */
    }

	.lesson-short-text-input
    {
        padding: 1rem;
    }

	.lesson-long-text-input
	{
		padding: 1rem;
	}

    
    .lesson-purpose-input,
    .lesson-precaution-input {
    	padding: 1rem;
    }

    .lesson-intro-input {
        padding: 1rem;
    }

    .lesson-input,
    .lesson-textarea {
        width: 100%;
        padding: 10px;
        font-size: 1rem;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-shadow: inset 0px 1px 3px rgba(0, 0, 0, 0.1); /* 內部陰影 */
        transition: border-color 0.3s ease, box-shadow 0.3s ease; /* 增加互動效果 */
        box-sizing: border-box;
        max-width: 100%;
    }

    .lesson-input:focus,
    .lesson-textarea:focus {
        border-color: #009688; /* 將焦點時的邊框顏色改為主題色 */
        box-shadow: 0px 0px 6px rgba(0, 150, 136, 0.3); /* 增加外部發光效果 */
        outline: none; /* 移除預設的 outline */
    }

    .lesson-textarea {
        resize: none; /* 禁止調整大小 */
    }

    .checkbox-group label {
        display: inline-block;
        margin-right: 10px;
    }

    .checkbox-group input {
        margin-right: 5px;
    }
    
    .image-area {
    	padding: 2rem 1rem;
    	width: 100%; /* 確保容器佔滿整個寬度 */
    }
    
    .image-container {
    	background-color: #F8F8F8;
    	display: flex;
		height: 10rem;
		max-width: 100%;
		width: 100%;
		overflow-x: auto;
		box-sizing: border-box;
    }
    
    /* 教案影片區域樣式 - 與圖片樣式保持一致 */
    .video-area {
    	padding: 2rem 1rem;
    }
    
    .video-container {
    	background-color: #F8F8F8;
    	display: flex;
		height: 10rem;
    }
    
    .upload-area {
	    position: relative; /* 確保內部的子元素以這個區域為參考 */
	    border: 2px dashed #00CB96;
	    border-radius: 8px;
	    padding: 20px;
	    text-align: center;
	    background-color: #F8F8F8;
	    cursor: pointer;
	    transition: background-color 0.3s ease;
	    overflow: hidden; /* 防止子元素溢出 */
	    width: 10rem;
	}
	
	.upload-area:hover {
	    background-color: #E8F8F3;
	}
	
	.upload-area input[type="file"] {
	    position: absolute;
	    top: 0;
	    left: 0;
	    width: 100%; /* 完全覆蓋父容器 */
	    height: 100%; /* 完全覆蓋父容器 */
	    opacity: 0; /* 隱藏原始 input，但仍然可以點擊 */
	    cursor: pointer;
	    z-index: 2; /* 確保它位於最上層 */
	}
	
	.upload-icon {
	    font-size: 2rem; /* 調整圖標大小 */
	    color: #00CB96; /* 圖標顏色 */
	    z-index: 1; /* 保證圖標在背景區域之下 */
	    position: relative;
	    margin-bottom: 10px; /* 與文字間距 */
	}
	
	.upload-area p {
	    margin: 10px 0 0;
	    color: #555;
	    font-size: 0.9rem;
	    z-index: 1; /* 同樣保證文字在背景區域之下 */
	    position: relative;
	}
	
	.image-preview {
	    max-height: 100%; /* 限制圖片高度 */
	    border-radius: 8px; /* 圓角 */
	    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 陰影效果 */
	    object-fit: contain; /* 保持圖片比例 */
		margin-left: 10px;
	}
	
	/* 教案影片預覽樣式 - 與圖片預覽樣式保持一致 */
	.video-preview {
	    max-height: 100%; /* 限制影片高度 */
	    border-radius: 8px; /* 圓角 */
	    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* 陰影效果 */
	    object-fit: contain; /* 保持影片比例 */
		margin-left: 10px;
	}
	
	/* 影片預覽容器樣式 */
	.video-preview-container {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    height: 100%;
	    position: relative;
	}
	
	/* 圖片預覽容器樣式 - 與影片預覽容器保持一致 */
	.image-preview-container {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	    height: 100%;
	    position: relative;
	}
	
	/* 圖片網格容器 */
	.image-grid-container {
		display: flex;
		flex-wrap: nowrap;
		overflow-x: auto;
		gap: 15px;
		margin-bottom: 20px;
		padding: 10px 40px; /* 增加左右padding，為滾動按鈕留出空間 */
		scrollbar-width: thin; /* Firefox */
		-ms-overflow-style: none; /* IE and Edge */
		position: relative;
		scroll-behavior: smooth; /* 平滑滾動效果 */
		-webkit-overflow-scrolling: touch; /* 在iOS上支持慣性滾动 */
		max-width: 100%;
		width: 100%;
		box-sizing: border-box;
	}
	
	/* 適用於 Webkit 瀏覽器的滾動條樣式 */
	.image-grid-container::-webkit-scrollbar {
		height: 8px;
	}
	
	.image-grid-container::-webkit-scrollbar-thumb {
		background-color: #d1d1d1;
		border-radius: 4px;
	}
	
	.image-grid-container::-webkit-scrollbar-track {
		background: #f1f1f1;
		border-radius: 4px;
	}
	
	/* 圖片滾動包裝器 */
	.image-scroll-wrapper {
		position: relative;
		width: 100%;
		margin-bottom: 15px;
	}
	
	/* 滾動指示器 */
	.scroll-indicator {
		position: absolute;
		top: 50%;
		transform: translateY(-50%);
		width: 36px;
		height: 36px;
		background-color: rgba(255, 255, 255, 0.9);
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
		cursor: pointer;
		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.3);
		z-index: 100;
		transition: all 0.2s ease;
		opacity: 0.95;
		pointer-events: auto;  /* 確保滾動指示器可點擊 */
	}
	
	.scroll-indicator:hover {
		background-color: #ffffff;
		opacity: 1;
	}
	
	.scroll-left {
		left: 5px;
	}
	
	.scroll-right {
		right: 5px;
	}
	
	.scroll-indicator.disabled {
		opacity: 0.3;
		cursor: not-allowed;
		pointer-events: none;  /* 禁用點擊，但仍然保持可見 */
	}
	
	/* 圖片網格中的單個圖片項 */
	.image-grid-item {
		position: relative;
		border-radius: 8px;
		overflow: hidden;
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
		height: 150px;
		min-width: 180px; /* 設定最小寬度，確保在水平捲動時維持合適的大小 */
		max-width: 220px; /* 設定最大寬度 */
		flex: 0 0 auto; /* 防止容器縮放 */
		cursor: pointer;
		transition: transform 0.2s ease;
	}
	
	.image-grid-item img {
		width: 100%;
		height: 100%;
		object-fit: cover;
		transition: transform 0.3s ease;
	}
	
	.image-grid-item:hover img {
		transform: scale(1.05);
	}
	
	/* 圖片網格中的刪除按鈕 */
	.image-grid-item .remove-grid-image {
		position: absolute;
		top: 5px;
		right: 5px;
		padding: 0.25rem 0.5rem;
		z-index: 10;
		opacity: 0.7;
	}
	
	.image-grid-item:hover .remove-grid-image {
		opacity: 1;
	}
	
	/* 圖片上傳區域標題 */
	.image-upload-section h6 {
		margin-bottom: 10px;
		color: #444;
		font-weight: bold;
	}
	
	/* 圖片預覽模態對話框 */
	.image-modal {
		display: none;
		position: fixed;
		z-index: 9999;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		overflow: auto;
		background-color: rgba(0,0,0,0.9);
	}
	
	.image-modal-content {
		display: block;
		margin: 0 auto;
		max-width: 80%;
		max-height: 80%;
		padding-top: 60px;
	}
	
	.image-modal-close {
		position: absolute;
		top: 15px;
		right: 35px;
		font-size: 40px;
		font-weight: bold;
		color: #f1f1f1;
		cursor: pointer;
	}

    .button-container {
        display: flex;
        justify-content: center;
    }

    .form-buttons {
        display: flex;
        justify-content: space-between;
        width: 25%;
    }

    .form-buttons button {
        margin: 1rem;
        font-weight: bold;
        border: solid 2px #007AFF;
        box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
    }

    .form-buttons .btn-cancel {
        background-color: white;
        color: #007AFF;
    }

    .btn-save {
        background-color: #007AFF;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .btn-save:hover {
        background-color: #0066CC;
    }

    .btn-cancel {
        background-color: #f44336;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .btn-cancel:hover {
        background-color: #007AFF;
        color: white;
    }

    .lesson-checkbox-group {
        display: flex;
        flex-wrap: wrap;
        gap: 10px;
        padding: 1rem;
    }
    
    .checkbox-label {
		position: relative;
		display: inline-flex;
		align-items: center;
		cursor: pointer;
	}

	.checkbox-label input[type="checkbox"] {
		display: none;
	}

	.custom-checkbox {
		width: 20px;
		height: 20px;
		border: 2px solid #009688;
		border-radius: 4px;
		background: #fff;
		margin-right: 10px;
		display: inline-block;
		position: relative;
	}

	.checkbox-label input[type="checkbox"]:checked + .custom-checkbox {
		background: #009688;
		border-color: #00796b;
	}

	.checkbox-label input[type="checkbox"]:checked + .custom-checkbox::after {
		color: #fff;
		position: absolute;
		left: 4px;
		top: 0px;
		font-size: 14px;
	}
	
	.add-item {
		background-color: #00CB96;
		border-color: #00CB96;
	}
	
	.add-item:hover {
		background-color: #009688;
		border-color: #009688;
	}
	
	.func-button-group button{
		background-color: white;
		height: 2rem;
		width: 2rem;
		border-radius: 8px;
	}
	
	.func-button-group button:hover{
		background-color: grey;
		color: white;
		height: 2rem;
		width: 2rem;
		border-radius: 8px;
	}
	
	button.btn-ach-edit {
	    margin-right: 0.5rem;
	}

	button.btn-ach-save {
	    margin-right: 0.5rem;
	}

	.lesson-news-editor {
		padding: 1rem;
	}

	.achievement-upload-area {
		width: 100%;
		height: 120px;
		margin-bottom: 10px;
	}

	.img-preview {
		max-width: 100%;
		max-height: 200px;
		object-fit: contain;
	}

	/* 編輯模式下的圖片樣式 */
	.achievement-image {
		cursor: default;
		width: 50px;
		height: 50px;
	}

	.achievement-image.clickable {
		cursor: pointer;
		transition: transform 0.2s;
	}

	.achievement-image.clickable:hover {
		transform: scale(1.1);
		box-shadow: 0 0 10px rgba(0,0,0,0.2);
	}

	.editing .achievement-image.clickable {
		border: 2px solid #007bff;
		border-radius: 4px;
		padding: 2px;
	}


	/* 編輯模式下的提示 */
	.editing .achievement-image.clickable::after {
		content: '點擊更換';
		position: absolute;
		bottom: -20px;
		left: 50%;
		transform: translateX(-50%);
		font-size: 12px;
		color: #007bff;
		white-space: nowrap;
	}

	/* 確保圖片容器有相對定位 */
	#achievementTable td {
		position: relative;
	}
    
	.lesson-delete-blk {
		padding: 1rem;
	}

	.btn-lesson-delete {
		color: #d33;
		background-color: white;
		border-radius: 8px;
		border: 2px solid #d33;
		padding: 0.5rem 1rem 0.5rem 1rem;
		font-weight: 700;
	}

	.btn-lesson-delete:hover {
		color: white;
		background-color: #d33;
	}

    </style>
    
    <!-- 圖片預覽模態對話框 -->
    <div id="imageModal" class="image-modal">
        <span class="image-modal-close">&times;</span>
        <img class="image-modal-content" id="modalImage">
    </div>
</body>
</html>