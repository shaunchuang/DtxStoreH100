<#if currentUser??>
    <#include "../login/widget/dtx-login-header.ftl" />
<#else>
    <#include "../unlogin/dtx-unlogin-header.ftl"/>
</#if>
<main>
    <#if currentUser??>
        <div class="row">
            <#include "../login/widget/dtx-login-leftnav.ftl"/>
            <div class="col-xxl-10 right-container">
                <div class="row mt-4">
    <#else>
        
        <div class="main-container">
            <div class="banner row">
                <div class="col-md-6">
                    <h2 class="banner-title"><span>DTx數位治療教案</span>在宅訓練平台</h2>
                    <p class="banner-intro">收錄各界專家自主開發之數位教案，整合Steam平台成就/統計資訊，訓練成果明瞭。</p>
                </div>
                <div class="stats col-md-6">
                    <div class="row" style="flex-wrap: nowrap; display: flex;">
                        <div class="stat stat-border">
                            <span class="stat-number">3152</span>
                            <br>
                            <span class="stat-label">已參與人次</span>
                        </div>
                        <div class="stat stat-border">
                            <span class="stat-number">${indicationNum!""}</span>
                            <br>
                            <span class="stat-label">適用之適應症數量</span>
                        </div>
                        <div class="stat">
                            <span class="stat-number">${lessonNum!""}</span>
                            <br>
                            <span class="stat-label">收錄數位教案數量</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="content-container">
                <div class="row mt-4">
    </#if>
                    <div class="col-md-12">
                        <div class="search-group">
                            <div class="filter-button-block col-md-1 d-flex justify-content-center">
                                <button class="filter-button"><i class="fa-solid fa-arrow-down-short-wide"></i> <span> 篩選</span></button>
                            </div>
                            <div class="search-bar col-md-5-5">
                                <input type="text" class="form-control search-input" id="search-input"
                                    name="search-input" placeholder="請輸入教案名稱搜尋">
                                <button class="search-btn" id="search-btn"><i class="fas fa-search"></i></button>
                            </div>
                            <div class="sort-bar col-md-5-5 justify-content-end">
                                <div class="sort-text">排序</div>
                                <div class="sort-select-wrapper">
                                    <span class="sort-label">排序一</span>
                                    <select id="sortSelectOne" class="sort-select">                                        <option value="">不使用排序</option>
                                        <option value="usageDesc" disabled>使用人次最多</option>
                                        <option value="ratingDesc">評分最高</option>
                                        <option value="ratingAsc">評分最低</option>
                                        <option value="effectivenessDesc" disabled>使用成效最佳</option>
                                        <option value="timeDesc" disabled>使用時間最長</option>
                                        <option value="favoriteDesc">最多人收藏</option>
                                        <option value="releaseDesc">發行時間近⇢遠</option>
                                        <option value="releaseAsc">發行時間遠⇢近</option>
                                        <option value="ageDesc">適用年齡大⇢小</option>
                                        <option value="ageAsc">適用年齡小⇢大</option>
                                        <option value="priceAsc">價格小⇢大</option>
                                        <option value="priceDesc">價格大⇢小</option>
                                    </select>
                                </div>
                                <div class="sort-select-wrapper">
                                    <span class="sort-label">排序二</span>                                    <select id="sortSelectTwo" class="sort-select">
                                        <option value="" selected>不使用順位二排序</option>
                                        <!--<option value="usageDesc">使用人次最多</option>
                                        <option value="ratingDesc">評分最高</option>
                                        <option value="effectivenessDesc">使用成效最佳</option>
                                        <option value="timeDesc">使用時間最長</option>-->
                                        <option value="favoriteDesc">最多人收藏</option>
                                        <!--<option value="releaseDesc">發行時間近⇢遠</option>
                                        <option value="releaseAsc">發行時間遠⇢近</option>
                                        <option value="ageDesc">適用年齡大⇢小</option>
                                        <option value="ageAsc">適用年齡小⇢大</option>
                                        <option value="priceAsc">價格小⇢大</option>
                                        <option value="priceDesc">價格大⇢小</option>-->
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="row justify-content-center filter-table-area">
                            <div class="filter-table">
                                <table class="table table-bordered table-style">
                                    <tbody id="tagTableBody">
                                        <!-- 這裡由javascript 產生tableBody -->
                                        <tr class="goods-tr">
                                            <td class="title-td">適用年齡</td>
                                            <td>
                                                <div class="range_container">
                                                    <div class="sliders_control">
                                                        <input id="ageFromSlider" type="range" value="0" min="0" max="100" />
                                                        <input id="ageToSlider" type="range" value="100" min="0" max="100" />
                                                    </div>
                                                    <div class="form_control">
                                                        <div class="form_control_container">
                                                            <div class="form_control_container__time">最小年齡</div>
                                                            <input class="form_control_container__time__input" 
                                                                type="number" id="ageFromInput" value="0" min="0"
                                                                max="100" />
                                                        </div>
                                                        <div class="form_control_container">
                                                            <div class="form_control_container__time">最大年齡</div>
                                                            <input class="form_control_container__time__input"
                                                                type="number" id="ageToInput" value="100" min="0"
                                                                max="100" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="goods-tr">
                                            <td class="title-td">價格</td>
                                            <td>
                                                <div class="range_container">
                                                    <div class="sliders_control">
                                                        <input id="priceFromSlider" type="range" value="0" min="0" max="5000" />
                                                        <input id="priceToSlider" type="range" value="5000" min="0" max="5000" />
                                                    </div>
                                                    <div class="form_control">
                                                        <div class="form_control_container">
                                                            <div class="form_control_container__time">最低價格</div>
                                                            <input class="form_control_container__time__input"
                                                                type="number" id="priceFromInput" value="0" min="0"
                                                                max="5000" />
                                                        </div>
                                                        <div class="form_control_container">
                                                            <div class="form_control_container__time">最高價格</div>
                                                            <input class="form_control_container__time__input"
                                                                type="number" id="priceToInput" value="5000" min="0"
                                                                max="5000" />
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="goods-tr">
                                            <td colspan="3" class="reset-button-container">
                                                <button type="button" class="reset-btn-all" onclick="resetAllFilters()">重設所有篩選條件</button>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>    
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
                </div>
            </div>
        </div>
    </main>
    <link rel="stylesheet" href="/html/dtxstore/style/lessonQuery/dtx-query.css">
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/popper.js"></script>
    <script type="text/javascript" src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/rSlider.min.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/leftnav.js"></script>
<script>
let isLibrary = false;
let isManage = false;

<#if isManage??>
isManage = ${isManage};
</#if>

<#if isLibrary??>
isLibrary = ${isLibrary?c};
</#if>
let userId
<#if currentUser ??>
userId = ${ currentUser.id };
</#if>
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

const categoryName = {
    "1": "適應症",
    "2": "類型",
    "3": "語言",
    "4": "開發者",
    "5": "裝置",
    "6": "作業系統",
    "7": "時段",
    "8": "協作",
    "9": "其他"
};
	
$(document).ready(function(){
    // 檢查是否從教案詳情頁返回
    const fromDetailPage = sessionStorage.getItem('fromDetailPage');
    if (fromDetailPage === 'true') {
        sessionStorage.removeItem('fromDetailPage');
        // 重新加載列表以獲取最新的評論和評分數據
        getDataToPost();
    } else {
        postFilterApi({"isLibrary": isLibrary,"userId": userId}, function(){
            setupPagination();
        });
    }

    filterButton();
    createFilterTable();
    sortFunc();
    bindSearchInputEvent();
    // 綁定搜尋按鈕點擊事件
    $('#search-btn').on('click', function() {
        currentPage = 1; // 重置到第一頁
        getDataToPost(); // 調用 listLesson() 重新載入列表
    });
    // 當用戶點擊教案卡片，記錄狀態
    $(document).on('click', '.card-link', function() {
        sessionStorage.setItem('fromDetailPage', 'true');
    });

    // 檢查是否有點數更新
    const pointsUpdated = sessionStorage.getItem('pointsUpdated');
    if (pointsUpdated) {
        const newPoints = sessionStorage.getItem('currentPoints');
        updateUserPoints(newPoints);
        sessionStorage.removeItem('pointsUpdated');
        sessionStorage.removeItem('currentPoints');
    }
});
    
function setupPagination() {
    //console.log('totalCount', totalCount);
    if (totalCount == 0){
        
        $('.pageNavigation').hide();
    }
    
    let pagination = $('#dynamicPagination');
    pagination.empty();
    
    //console.log('totalPage in setupPage ' + totalPage);

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
	
function bindPaginationEvent() {
    $('#dynamicPagination .page-link').on('click', function(e) {
        e.preventDefault();
        let targetPage = $(this).data('page');
        console.log('targetPage: ' + targetPage);
        if (targetPage && targetPage > 0 && targetPage <= totalPage) {
            currentPage = targetPage;
            getDataToPost();
        }
    });
}
   
    
// 建立教案篩選表
function createFilterTable() {
    $.ajax({
        type: "GET", // 修改為 GET 方法
        url: `/DtxTag/api/list`, // 請求的 API URL
        dataType: "json", // 明確指定回應為 JSON 格式
        success: function (data) {
            createTagTableV2(data.tagList); // 直接傳遞回應的資料
        },
        error: function (xhr, status, error) {
            console.error("AJAX error: " + status + " - " + error);
            console.error("Response Text:", xhr.responseText);
        }
    });
}
    
	
function createTagTableV2(tags) {
	
    let tbody = $("#tagTableBody");
    tbody.empty(); // 清空現有內容

    // 將 tags 根據 tagType 分組
    let groupedTags = {};
    tags.forEach((tag) => {
        if (!groupedTags[tag.tagType]) {
            groupedTags[tag.tagType] = [];
        }
        groupedTags[tag.tagType].push(tag);
    });

    // 遍歷分組的標籤
    for (let tagType in groupedTags) {
        if (groupedTags.hasOwnProperty(tagType)) {
            let categoryTags = groupedTags[tagType];

            // 創建表格行
            let tr = $("<tr>").addClass("goods-tr");

            // 類別名稱單元格
            let tdTitle = $("<td>").addClass("title-td");
            let divTitle = $("<div>").text(categoryName[tagType]); // 假設 tagType 是類別名稱
            tdTitle.append(divTitle);
            tr.append(tdTitle);

            // 標籤列表單元格
            let tdTags = $("<td>").addClass("tag-list");
            let wrapDiv = $("<div>").addClass("wrap-div");
            let ul = $("<ul>");

            // 處理標籤資料
            categoryTags.forEach((tag, index) => {

                let li = $("<li>");
                if (tag.tagName) {
                    let span = $("<span>")
                        .attr("data-type", tag.tagType)
                        .attr("data-tag", tag.id)
                        .text(tag.tagName);
                    li.append(span);
                }

                // 超過 6 個標籤的項目隱藏
                if (index >= 6) {
                    li.hide();
                }
                ul.append(li);
            });

            wrapDiv.append(ul);
            tdTags.append(wrapDiv);

            // 添加「選更多」按鈕（如果標籤超過 6 個）
            if (categoryTags.length > 6) {
                let seeMoreBtnA = $("<div>").addClass("seeMore-btn-a");
                let seeMoreBtn = $("<button>")
                    .addClass("seeMore-btn")
                    .html("&nbsp;+ 選更多&nbsp;");

                seeMoreBtn.on("click", function () {
                    let isExpanded = $(this).data("expanded");
                    if (isExpanded) {
                        $(this)
                            .closest(".tag-list")
                            .find("ul li")
                            .each(function (index) {
                                if (index >= 6) {
                                    $(this).hide();
                                }
                            });
                        $(this).html("&nbsp;+ 選更多&nbsp;");
                    } else {
                        $(this).closest(".tag-list").find("ul li").show();
                        $(this).html("&nbsp;- 收回&nbsp;");
                    }
                    $(this).data("expanded", !isExpanded);
                });

                seeMoreBtn.data("expanded", false);
                seeMoreBtnA.append(seeMoreBtn);
                tdTags.append(seeMoreBtnA);
            }

            tr.append(tdTags);
            // **新增重設按鈕**
            let tdReset = $("<td>").addClass("reset-button-container");
            let resetBtn = $("<button>")
                .addClass("reset-btn")
                .text("重設")
                .attr("onclick", `resetTagFilter('` + tagType + `')`);
            let resetDiv = $("<div>");
            resetDiv.append(resetBtn);
            tdReset.append(resetDiv);
            tr.append(tdReset);
            tbody.append(tr);
        }
    }
    /*** 建立Age tr ***/
    let trAge = $('<tr>').addClass('goods-tr');
    let tdTitleAge = $('<td>').addClass('title-td');
    let divTitleAge = $('<div>').text("年齡");
    tdTitleAge.append(divTitleAge);
    
    let wrapDivAge = $('<div>').addClass('wrap-div').addClass('rangeSlider').attr('id','ageSlider-wrap');
    let tdTagsAge = $('<td>').addClass('tag-list').addClass('justify-content-center');
    let inputAge = $('<input>').attr('type','text').attr('id','ageSlider');
    wrapDivAge.append(inputAge);
    tdTagsAge.append(wrapDivAge);
    trAge.append(tdTitleAge);
    trAge.append(tdTagsAge);
    
    // **新增重設按鈕**
    let tdResetAge = $("<td>").addClass("reset-button-container");
    let resetAgeBtn = $("<button>")
        .addClass("reset-btn")
        .text("重設")
        .attr("onclick", "resetAgeFilter()");
    let resetAgeDiv = $("<div>");
    resetAgeDiv.append(resetAgeBtn);
    tdResetAge.append(resetAgeDiv);
    trAge.append(tdResetAge);
    
    tbody.append(trAge);
            
    /*** 建立Price tr ***/
    let trPrice = $('<tr>').addClass('goods-tr');
    let tdTitlePrice = $('<td>').addClass('title-td');
    let divTitlePrice = $('<div>').text("價格");
    tdTitlePrice.append(divTitlePrice);
    
    let wrapDivPrice = $('<div>').addClass('wrap-div').addClass('rangeSlider').attr('id','priceSlider-wrap');
    let tdTagsPrice = $('<td>').addClass('tag-list').addClass('justify-content-center');
    let inputPrice = $('<input>').attr('type','text').attr('id','priceSlider');
    wrapDivPrice.append(inputPrice);
    tdTagsPrice.append(wrapDivPrice);
    trPrice.append(tdTitlePrice);
    trPrice.append(tdTagsPrice);
    
    // **新增重設按鈕**
    let tdResetPrice = $("<td>").addClass("reset-button-container");
    let resetPriceBtn = $("<button>")
        .addClass("reset-btn")
        .text("重設")
        .attr("onclick", "resetPriceFilter()");
    let resetPriceDiv = $("<div>");
    resetPriceDiv.append(resetPriceBtn);
    tdResetPrice.append(resetPriceDiv);
    trPrice.append(tdResetPrice);
    tbody.append(trPrice);
    
    let resetTr = $("<tr>").addClass("reset-all-tr");
    let resetTd = $("<td>").attr("colspan", "3").addClass("reset-all-td");
    let resetAllBtn = $("<button>").addClass("reset-all-btn").text("全部重設").attr("onclick", "resetAllFilter()");
    resetTd.append(resetAllBtn);
    resetTr.append(resetTd);
    tbody.append(resetTr);
    
    filterItem();
}

	
// 建立篩選拉條
function createSlider() {
    //console.log('Create Slider minAge: ' + minAge + ' maxAge: ' + maxAge + ' minPrice: ' + minPrice + ' maxPrice: ' + maxPrice);
    // 建立年齡篩選
    ageSlider = new rSlider({
        target: '#ageSlider',
        values: [3, 6, 7, 10, 12, 14, 15, 16, 18, 19, 20, 65],
        range: true,
        tooltip: true,
        scale: false,
        labels: true,
        leftText: '3歲',
        rightText: '65歲以上',
        set: [parseInt(minAge), parseInt(maxAge)],
        step: 1,
        onChange: function (vals) {
            currentPage = 1;
            getDataToPost()
        }
    });
    // 建立價格篩選
    priceSlider = new rSlider({
        target: '#priceSlider',
        values: { min: 0, max: 3500 },
        range: true,
        tooltip: true,
        scale: false,
        labels: false,
        leftText: '免費',
        rightText: '更貴價格',
        set: [parseInt(minPrice), parseInt(maxPrice)],
        step: 100,
        onChange: function (vals) {
            currentPage = 1;
            getDataToPost()
        }
    });
}
	
// 篩選按鈕功能建立
function filterButton() {
    $('.filter-button').click(function () {
        $(this).toggleClass('active');
        if ($('.filter-table-area').css('display') === 'none') {
            $('.filter-table-area').css('display', 'flex');

            createSlider();
        } else {
            $('.filter-table-area').css('display', 'none');

            ageSlider.destroy();
            priceSlider.destroy();
            $('.slider-container').remove();
        }
    });
}

    

function createCardV2(data) {
    console.log('create Card V2 data: ', data);
    data.forEach((lessonAll) => {
        let isFavor = lessonAll.isFavor;
        let lessonItem = lessonAll.lesson;
        let tags = lessonAll.tags;

                // 預設標籤分類 (若無標籤則顯示 "無")
        let tagCategories = {
            "1": "無",  // 適應症
            "2": "無",  // 類型
            "3": "無",  // 語言
            "4": "無",  // 開發者
            "5": "無",  // 裝置
            "6": "無",  // 作業系統
            "7": "無",  // 時段
            "8": "無",  // 協作
            "9": "無"   // 其他
        };

        // 建立一個陣列來存放語言標籤 (tagType 為 3)
        let languageTags = [];
        // 根據 tagType 分類標籤 (語言標籤另行處理)
        tags.forEach(tag => {
            if (tag.tagType === 3) {
                languageTags.push(tag.tagName);
            } else {
                if (tagCategories[tag.tagType] && tagCategories[tag.tagType] === "無") {
                    tagCategories[tag.tagType] = tag.tagName;
                } else if (tagCategories[tag.tagType]) {
                    tagCategories[tag.tagType] += (', ' + tag.tagName);
                }
            }
        });

        // 處理語言標籤，優先顯示 '繁體中文(語音)'、'繁體中文'、'英文(語音)'、'英文'
        let prioritizedLanguages = ['繁體中文(語音)', '繁體中文', '英文(語音)', '英文'];
        let prioritizedLanguageTags = [];
        prioritizedLanguages.forEach(lang => {
            if (languageTags.includes(lang)) {
                prioritizedLanguageTags.push(lang);
            }
        });
        if (prioritizedLanguageTags.length > 0) {
            tagCategories["3"] = prioritizedLanguageTags.join(', ');
        } else {
            tagCategories["3"] = languageTags.length > 0 ? languageTags.join(', ') : "無";
        }

        // 處理年齡
        //console.log("lessonItem ", lessonItem);
        const csrrRating = lessonItem.csrrRate;
        const minAge = lessonItem.minAge;
        const maxAge = lessonItem.maxAge;
        let ageTag;
        // 如果有 csrrRating 優先顯示 csrrRating 對應的標籤
        if (csrrRating !== undefined && csrrRating !== "" && csrrRating !== null) {
            switch (csrrRating) {
                case "G":
                    ageTag = '普遍級';
                    break;
                case "P6":
                    ageTag = '保護級';
                    break;
                case "C12":
                    ageTag = '輔12級';
                    break;
                case "C15":
                    ageTag = '輔15級';
                    break;
                case "P18":
                    ageTag = '限制級';
                    break;
                default:
                    ageTag = '不限';
                    break;
            }
        } else {
            // 沒有 csrrRating 的情況下，依照 minAge 和 maxAge 處理
            if (minAge && !maxAge) {
                ageTag = minAge + "以上";
            } else if (!minAge && maxAge) {
                ageTag = maxAge + "以下";
            } else if (minAge && maxAge) {
                ageTag = minAge + "以上、" + maxAge + "以下";
            } else {
                ageTag = "不限";
            }
        }

        // 生成評分星星HTML
        const ratingHtml = generateRatingHtml(lessonAll.avgRating);
          // 格式化評論數量顯示
        const commentText = lessonAll.commentCount > 0 ? 
            (lessonAll.commentCount + ' 則評論') : '尚無評論';
            
        // 處理價格顯示
        const price = lessonItem.price;
        const priceText = price === 0 ? '免費' : 'NT$ ' + price.toLocaleString();
        const priceClass = price === 0 ? 'price-tag free' : 'price-tag paid';

        let tagHtml = `<div class="lesson-tag">
                            <div class="card-tag">適應症：<span>` + tagCategories["1"] + `</span></div>
                            <div class="card-tag">語言：<span>` + tagCategories["3"] + `</span></div>
                            <div class="card-tag">類型：<span>` + tagCategories["2"] + `</span></div>
                            <div class="card-tag">年齡限制：<span>` + ageTag + `</span></div>
                       </div>`;
        let cardHtml = `<div class="col">
                            <div class="card">
                                <div class="` + priceClass + `">` + priceText + `</div>
                                <a class="card-link" href="/ftl/dtxstore/application?app=` + lessonItem.id + `">
                                    <img src="/File/api/file/path` + lessonItem.headerImageUrl + `" class="card-img-top" alt="` + lessonItem.lessonName + `">
                                    <div class="card-body">
                                        <h5 class="card-title">` + lessonItem.lessonName + `</h5>
                                        ` + tagHtml + `
                                        <p class="card-text">` + lessonItem.lessonBrief + `</p>
                                    </div>
                                </a>
                                <#if currentUser??>
                                <button class="favorite-btn" data-lesson="` + lessonItem.id + `">
                                    <i class="fa-` + (isFavor ? 'solid' : 'regular') + ` fa-heart"></i>
                                </button>
                                <span class="favorite-message" style="display:none;"></span> <!-- 提示文字容器 -->
                                </#if>
                            <div class="card-footer bg-white p-2">
                                <div class="d-flex align-items-center">
                                    <div class="rating-display me-auto">
                                        <div class="stars-container">
                                            ` + ratingHtml + `
                                        </div>
                                        <span class="rating-value">
                                            ` + (lessonAll.avgRating > 0 ? lessonAll.avgRating.toFixed(1) : '尚無評分') + `
                                        </span>
                                    </div>
                                    <div class="review-count">
                                        <i class="fas fa-comment text-muted"></i>
                                        <span>` + commentText + `</span>
                                    </div>
                                </div>
                            </div>
                            </div>
                        </div>
                        `;

        $('.card-deck-row').append(cardHtml);
    });
    // 為所有收藏按鈕添加點擊事件監聽器
    $('.favorite-btn').on('click', function (event) {

        var $favoriteBtn = $(this);
        var $heartIcon = $favoriteBtn.find('i');
        var isFavorite = $heartIcon.hasClass('fa-solid');
        var lessonId = $favoriteBtn.data('lesson');

        var url = isFavorite ? '/UserFavoriteLesson/api/delete' : '/UserFavoriteLesson/api/create';
        $.ajax({
            url: url,
            type: 'POST',
            data: JSON.stringify({ "lessonId": lessonId, "userId": userId }),
            success: function (response) {
                // 切換圖標樣式
                $heartIcon.toggleClass('fa-regular fa-solid');
                var message = isFavorite ? '已移除收藏' : '已加入收藏';
                var $message = $favoriteBtn.next('.favorite-message');
                $message.stop(true, true).text(message).fadeIn().delay(2000).fadeOut();
                // 如果是收藏頁面，移除收藏時移除該卡片
                if (isLibrary && isFavorite) {
                    $favoriteBtn.closest('.col').fadeOut(function () {
                        $(this).remove();
                        getDataToPost();
                    });
                }
            },
            error: function (xhr, status, error) {
                alert('操作失敗: ' + error);
            }
        });
    });
}

// 生成星星HTML
function generateRatingHtml(rating) {
    const roundedRating = Math.round(rating * 2) / 2; // 四捨五入到0.5
    let starsHtml = '';
    
    for (let i = 1; i <= 5; i++) {
        let starClass = 'far fa-star'; // 空星
        
        if (i <= roundedRating) {
            starClass = 'fas fa-star'; // 實心星
        } else if (i - 0.5 === roundedRating) {
            starClass = 'fas fa-star-half-alt'; // 半星
        }
        
        starsHtml += `<i class="` + starClass + ` text-warning"></i>`;
    }
    
    return starsHtml;
}
    
/* 主篩選功能 */
function filterItem() {
    $('.tag-list ul li span').click(function () {
        $(this).toggleClass('active');
        currentPage = 1;
        getDataToPost();
    });
}

/* 取得已經點擊的標籤之data-tag數值 */
function getActiveTag() {
    let activeTag = [];
    $('.tag-list ul li span').each(function () {
        if ($(this).hasClass('active')) {
            activeTag.push($(this).attr('data-tag'));
        }
    });
    return activeTag;
}

function getSliderValue() {
    minAge = $('#ageSlider-wrap .rs-container .rs-pointer[data-dir="left"] .rs-tooltip').text() || '3';
    maxAge = $('#ageSlider-wrap .rs-container .rs-pointer[data-dir="right"] .rs-tooltip').text() || '65';

    minPrice = $('#priceSlider-wrap .rs-container .rs-pointer[data-dir="left"] .rs-tooltip').text() || '0';
    maxPrice = $('#priceSlider-wrap .rs-container .rs-pointer[data-dir="right"] .rs-tooltip').text() || '3500';

    return { "minPrice": minPrice, "maxPrice": maxPrice, "minAge": minAge, "maxAge": maxAge };
}

/*** 搜尋模組區塊***/
function bindSearchInputEvent() {
    $('#search-input').on('input', function () {
        currentPage = 1;
        getDataToPost();
    });
}
    
/*** 排序模組區塊 ***/
function sortFunc() {
    $('#sortSelectOne').change(function () {
        updateSortTwoOptions();
        currentPage = 1;
        getDataToPost()
    });

    $('#sortSelectTwo').change(function () {
        currentPage = 1;
        getDataToPost()
    });
}

/* 更新排序二 */
function updateSortTwoOptions() {
    let selectedSortOne = $('#sortSelectOne').val();
    let sortTwoSelect = $('#sortSelectTwo');

    // 先清空排序二的選項
    sortTwoSelect.empty();

    // 排序二的預設選項
    let defaultOption = $('<option value="" selected>不使用順位二排序</option>');
    sortTwoSelect.append(defaultOption);

    // 如果排序一選擇了不使用排序則直接返回
    if (!selectedSortOne) {
        return;
    }    // 所有排序選項
    let sortOptions = [
        //{ value: 'usageDesc', text: '使用人次最多' },
        { value: 'ratingDesc', text: '評分最高' },
        { value: 'ratingAsc', text: '評分最低' },
        //{ value: 'effectivenessDesc', text: '使用成效最佳' },
        //{ value: 'timeDesc', text: '使用時間最長' },
        { value: 'favoriteDesc', text: '最多人收藏' },
        { value: 'releaseDesc', text: '發行時間近⇢遠' },
        { value: 'releaseAsc', text: '發行時間遠⇢近' },
        { value: 'ageDesc', text: '適用年齡大⇢小' },
        { value: 'ageAsc', text: '適用年齡小⇢大' },
        { value: 'priceAsc', text: '價格小⇢大' },
        { value: 'priceDesc', text: '價格大⇢小' }
    ];

    // 根據排序一選項過濾排序二選項
    sortOptions.forEach(function (option) {
        if (!isRelatedToSortOne(option.value, selectedSortOne)) {
            let newOption = $('<option>').val(option.value).text(option.text);
            sortTwoSelect.append(newOption);
        }
    });
}

function isRelatedToSortOne(optionValue, selectedSortOne) {
    // 如果選擇了發行時間，過濾發行時間相關的選項
    if (selectedSortOne === 'releaseDesc' || selectedSortOne === 'releaseAsc') {
        return optionValue === 'releaseDesc' || optionValue === 'releaseAsc';
    }

    // 如果選擇了適用年齡，過濾適用年齡相關的選項
    if (selectedSortOne === 'ageDesc' || selectedSortOne === 'ageAsc') {
        return optionValue === 'ageDesc' || optionValue === 'ageAsc';
    }    if (selectedSortOne === 'priceAsc' || selectedSortOne === 'priceDesc') {
        return optionValue === 'priceAsc' || optionValue === 'priceDesc';
    }
    
    // 如果選擇了評分，過濾評分相關的選項
    if (selectedSortOne === 'ratingDesc' || selectedSortOne === 'ratingAsc') {
        return optionValue === 'ratingDesc' || optionValue === 'ratingAsc';
    }
    
    // 如果選擇了收藏數，過濾收藏數相關的選項
    if (selectedSortOne === 'favoriteDesc') {
        return optionValue === 'favoriteDesc';
    }

    // 其他情況不過濾
    return optionValue === selectedSortOne;
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


function postFilterApi(postData, callback) {
    //console.log("postData: ", postData);
    $.ajax({
        type: "POST",
        url: `/LessonMainInfo/api/filterlist`,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(postData),
        success: function (data) {
            //console.log("成功返回數據：", data);

            // 清空現有的卡片區域
            $('.card-deck-row').empty();

            if (data.lessons && data.lessons.length > 0) {
                createCardV2(data.lessons); // 使用返回數據生成卡片
                totalCount = data.totalCount || 0; // 總記錄數
                totalPage = Math.ceil(totalCount / pageSize); // 計算總頁數
                $('.pageNavigation').show(); // 顯示分頁導航
            } else {
                // 顯示 "無相關搜尋結果" 的區塊
                $('.card-deck-row').html(`
                    <div class="no-results">
                        <h5><img src="/html/dtxstore/images/dtxstore/no-results.png"> 無相關搜尋結果</h5>
                        <p>請嘗試更改篩選條件或關鍵字後再試一次。</p>
                    </div>
                `);
                $('.pageNavigation').hide(); // 隱藏分頁導航
            }

            // 調用回調函數（例如用於更新分頁導航）
            if (callback) {
                callback();
            }
        },
        error: function (xhr, status, error) {
            console.error("發生錯誤：", xhr.responseText);
        }
    });
}


function getDataToPost() {
    //console.log("開始收集篩選條件...");

    // 收集篩選條件
    let tagList = getActiveTag(); // 獲取已選中的標籤ID
    //console.log('tagList:', tagList);
    let sliderValue = getSliderValue(); // 獲取篩選範圍（年齡、價格）
    //console.log('sliderValue:', sliderValue);
    let sortData = getSortValue(); // 獲取排序條件
    //console.log('sortData:', sortData);
    let searchQuery = $('#search-input').val(); // 獲取搜尋關鍵字
    //console.log('searchQuery:', searchQuery);

    // 收集分頁條件
    let pageData = {
        currentPage: currentPage, // 當前頁數
        pageSize: pageSize // 每頁顯示的記錄數量
    };
    console.log('pageData:', pageData);

    // 構建POST數據
    let postData = {
        "tagList": tagList,
        "sliderValue": sliderValue,
        "sort": sortData,
        "lessonName": searchQuery, // 搜索條件
        "pageData": pageData,
        "userId": userId,
        "isLibrary": isLibrary
    };

    //console.log('postData:', postData);

    // 調用 postFilterApi 發送請求
    postFilterApi(postData, function () {
        setupPagination(); // 分頁設置
    });
}

function resetAgeFilter() {
    ageSlider.destroy();
    $('#ageSlider-wrap .slider-container').remove();
    minAge = '3';
    maxAge = '65'
    ageSlider = new rSlider({
        target: '#ageSlider',
        values: [3, 6, 7, 10, 12, 14, 15, 16, 18, 19, 20, 65],
        range: true,
        tooltip: true,
        scale: false,
        labels: true,
        leftText: '3歲',
        rightText: '65歲以上',
        set: [parseInt(minAge), parseInt(maxAge)],
        step: 1,
        onChange: function (vals) {
            currentPage = 1;
            getDataToPost()
        }
    });
}

function resetPriceFilter() {
    priceSlider.destroy();
    $('#priceSlider-wrap .slider-container').remove();
    minPrice = '0';
    maxPrice = '3500';
    // 建立價格篩選
    priceSlider = new rSlider({
        target: '#priceSlider',
        values: { min: 0, max: 3500 },
        range: true,
        tooltip: true,
        scale: false,
        labels: false,
        leftText: '免費',
        rightText: '更貴價格',
        set: [parseInt(minPrice), parseInt(maxPrice)],
        step: 100,
        onChange: function (vals) {
            currentPage = 1;
            getDataToPost()
        }
    });
}

// **新增重設標籤篩選功能**
function resetTagFilter(tagType = null) {
    console.log("tagTypeId:", tagType);
    if (tagType) {
        $('.tag-list ul li span[data-type="' + tagType + '"]').removeClass("active");
    } else {
        $('.tag-list ul li span').removeClass("active");
    }
    getDataToPost();
}

// **完整重設所有篩選條件**
function resetAllFilter() {
    resetAgeFilter();
    resetPriceFilter();
    resetTagFilter();
}

// 添加更新用戶點數的函數
function updateUserPoints(points) {
    if (points) {
        $('.current-points').text(points);
    } else {
        // 如果沒有提供點數參數，則從服務器獲取最新點數
        $.ajax({
            url: '/points/api/statistics',
            type: 'GET',
            dataType: 'json',
            success: function(response) {
                console.log('response: ', response);
                if (response && response.currentPoints !== undefined) {
                    $('.current-points').text(response.currentPoints);
                }
            },
            error: function(xhr, status, error) {
                console.error('獲取用戶點數失敗:', error);
            }
        });
    }
}
</script>
<style>
<#if currentUser??>
.card-deck .card {
    border: 1px solid #ddd;
    border-radius: 10px;
    transition: box-shadow 0.3s ease-in-out, transform 0.2s ease;
    margin-top: 2em;
    height: auto;
    width: 90%;
    position: relative;  /* 為絕對定位的價格標籤添加相對定位 */
}

.card-deck .card:hover {
    box-shadow: 0 5px 15px rgba(0,0,0,0.1);
    transform: translateY(-3px);
}

.search-group {
    margin-left: 4rem;
    margin-right: 6rem;
}
</#if>
.card-link {
    text-decoration: none;
    color: inherit;
    display: block;
}

.card-link:hover {
    text-decoration: none;
}

.card-text {
    color: #666;
    font-size: 14px;
    line-height: 1.5;
    max-height: 63px; /* 約3行文字高度 */
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
}

/* rSlider css 樣式*/
.rangeSlider {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 5rem;
}

.slider-container {
    width: 100%;
    margin-top: 2.25rem;
}

/* rSlider擴充css屬性修正 */
.slider-container {
    display: flex;
    align-items: center;
    justify-content: space-between;
    /* 确保左右文本在容器内两端对齐 */
    width: 100%;
}

.slider-left-text,
.slider-right-text {
    white-space: nowrap;
    margin: 0 25px;
}

.rs-container {
    flex-grow: 1;
    position: relative;
    display: flex;
    align-items: center;
}

.rs-scale {
    width: 100%;
}

.rs-container .rs-scale span {
    float: none;
    display: inline-block;
}

.rs-tooltip,
.rs-scale span ins {
    font-size: 16px !important;
}

.no-results {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    /* 讓區塊佔滿父元素高度 */
    text-align: center;
    /* 文字置中 */
    padding: 50px;
    /* 增加內邊距 */
    margin: 20px auto;
    /* 水平置中 */
    background-color: #f8f9fa;
    /* 增加背景顏色 */
    border: 1px solid #ddd;
    /* 添加邊框 */
    border-radius: 10px;
    /* 邊框圓角 */
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    /* 增加陰影 */
    width: 100%;
    /* 增加區塊寬度 */
    max-width: 800px;
    /* 限制最大寬度 */
}

.no-results h5 {
    font-size: 24px;
    /* 調整標題字體大小 */
    font-weight: bold;
    margin-bottom: 10px;
    color: #343a40;
    /* 調整文字顏色 */
}

.no-results p {
    font-size: 18px;
    /* 調整描述字體大小 */
    color: #6c757d;
    /* 調整文字顏色 */
    margin: 0;
}

.reset-button-container {
    vertical-align: middle;
    border-left: none;
}

.reset-button-container div {
    display: flex;
    justify-content: center;
    align-items: center;
    min-height: 100%;
    width: 100%;
}

.reset-btn {
    border: solid 1px #a5a5a5;
    border-radius: 0.3rem;
    background-color: white;
    padding: 0rem 0.5rem 0rem 0.5rem;
}

.tag-list {
    border-right: none;
    border-left: none;
}

.reset-all-td {
    vertical-align: middle;
    text-align: center;
}

.reset-all-btn {
    font-size: 1.5rem;
    border-radius: 0.3rem;
    padding-left: 0.5rem;
    padding-right: 0.5rem;
    background-color: white;
    border: solid 1px #a5a5a5;
}

.card-body {
    padding: 1.25rem;
}

.card-title {
    font-weight: 600;
    font-size: 18px;
    margin-bottom: 0.75rem;
    color: #333;
}

.card-tag {
    font-size: 14px;
	color: #666;
    white-space: nowrap;          /* 防止換行 */
    overflow: hidden;             /* 隱藏超出部分 */
    text-overflow: ellipsis;      /* 超出部分以省略號顯示 */
    margin-bottom: 4px;
}

.card-tag span {
    color: #444;
    font-weight: 500;
}

.card-footer {
    border-top: 1px solid rgba(0,0,0,0.05);
}

.rating-display {
    display: flex;
    align-items: center;
}

.stars-container {
    display: inline-flex;
    margin-right: 8px;
}

.stars-container i {
    font-size: 0.85rem;
    margin-right: 2px;
}

.rating-value {
    font-size: 0.85rem;
    font-weight: 500;
    color: #666;
}

.review-count {
    font-size: 0.85rem;
    color: #666;
}

.review-count i {
    margin-right: 4px;
    font-size: 0.8rem;
}

/* 價格標籤樣式 */
.price-tag {
    position: absolute;
    top: 10px;
    right: 10px;
    padding: 5px 12px;
    border-radius: 20px;
    font-weight: 600;
    font-size: 14px;
    color: white;
    z-index: 10;
    box-shadow: 0 2px 5px rgba(0,0,0,0.2);
}

.price-tag.free {
    background-color: #2ecc71;  /* 綠色 */
}

.price-tag.paid {
    background-color: #3498db;  /* 藍色 */
}

</style>
</body>
</html>