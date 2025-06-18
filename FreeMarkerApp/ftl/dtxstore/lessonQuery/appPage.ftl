<#if currentUser??>
    <#include "../login/widget/dtx-login-header.ftl"/>
<#else>
    <#include "../unlogin/dtx-unlogin-header.ftl"/>
</#if>

<#if currentUser??>
    <main>
        <div class="row">
            <#include "../login/widget/dtx-login-leftnav.ftl"/>
            <div class="col-xxl-10 d-flex justify-content-center right-container">
<#else>
    <main>
</#if>
                <div class="app-main-container p-4">
                    <div class="row">
                        <div class="col-md-6">
                            <div id="carouselIndicators" class="carousel slide" data-bs-ride="carousel">
                                <div class="carousel-indicators">
                                    <button type="button" data-bs-target="#carouselIndicators" data-bs-slide-to="0"
                                        class="active" aria-current="true" aria-label="Slide 1"></button>
                                    <button type="button" data-bs-target="#carouselIndicators" data-bs-slide-to="1"
                                        aria-label="Slide 2"></button>
                                    <button type="button" data-bs-target="#carouselIndicators" data-bs-slide-to="2"
                                        aria-label="Slide 3"></button>
                                </div>
                                <div class="carousel-inner">
                                    <div class="carousel-item active">
                                        <img src="" class="d-block w-100" alt="...">
                                    </div>
                                    <div class="carousel-item">
                                        <img src="" class="d-block w-100" alt="...">
                                    </div>
                                    <div class="carousel-item">
                                        <img src="" class="d-block w-100" alt="...">
                                    </div>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#carouselIndicators"
                                    data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Previous</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carouselIndicators"
                                    data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Next</span>
                                </button>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="right-intro ms-2">
                                <h3 class="app-title m-2">Minecraft</h3>
                                <div class="intro-rating m-2">
                                    <span class="star empty-star"><i class="fas fa-star"></i></span>
                                    <span class="star empty-star"><i class="fas fa-star"></i></span>
                                    <span class="star empty-star"><i class="fas fa-star"></i></span>
                                    <span class="star empty-star"><i class="fas fa-star"></i></span>
                                    <span class="star empty-star"><i class="fas fa-star"></i></span>
                                    <!--<span class="star"><i class="fas fa-star"></i></span>-->
                                    <!--<span class="star empty-star"><i class="fas fa-star"></i></span>-->
                                    <!--<span class="rating-value">4.9（32則評論）</span>-->
                                    <span class="rating-value">目前無評論</span>
                                </div>
                                <table class="table right-intro-table">
                                    <tr>
                                        <td>適應症：</td>
                                        <td class="intro-indication">此教案無適應症</td>
                                    </tr>
                                    <tr>
                                        <td>主要目的：</td>
                                        <td class="main-purpose-info">此教案無主要目的</td>
                                    </tr>
                                    <tr>
                                        <td>類型：</td>
                                        <td class="intro-type"></td>
                                    </tr>
                                    <tr>
                                        <td>教案語言：</td>
                                        <td>
                                            <span class="intro-language"></span>
                                            <span class="show-more-btn">顯示更多</span>
                                            <div class="show-more-content"></div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>適用年齡：</td>
                                        <td class="intro-age">輔12</td>
                                    </tr>
                                </table>
                                <div class="lesson-function d-flex m-3">
                                    <div class="lesson-price p-2 px-3 mx-2">售價 NT$120</div>
                                    <#if currentUser??>
                                    <div>
                                        <a class="lesson-try-link"><div class="lesson-try p-2 px-3 mx-2">教案試用</div></a>
                                        <button class="add-favorites-link"><div class="add-favorites p-2 px-3 mx-2"><span><i class="fa-regular fa-heart"></i><span class="favor-text"> 加入收藏</span></span></div></button>
                                    </div>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--<div class="row">
                        <div class="full-introduction col-xxl-12">
                            <div class="sub-title mb-2">教案介紹</div>
                            <div class="lesson-intro-text">
                            </div>
                        </div>
                    </div>-->
                    <div class="row mt-3">
                        <div class="col-md-6">
                            <div class="sub-title my-2">
                                注意事項
                            </div>
                            <table class="table caution-table">
                                <tr>
                                    <td>使用限制：</td>
                                    <td class="caution-info">此教案無使用限制</td>
                                </tr>
                                <tr>
                                    <td>建議使用時段：</td>
                                    <td class="timeSuggestion-info">此教案無建議使用時段</td>
                                </tr>
                            </table>
                            <div class="sub-title my-2">
                                教案資訊
                            </div>
                            <table class="table lesson-intro-table">
                                <tr>
                                    <td>簡介：</td>
                                    <td><span class="brief-intro">此教案無簡介</span><span><button class="show-intro-btn">顯示更多</button></span></td>
                                </tr>
                                <tr>
                                    <td>開發者：</td>
                                    <td class="developer">此教案無開發者資訊</td>
                                </tr>
                                <tr>
                                    <td>發行日期：</td>
                                    <td class="release-date">此教案無發行日期</td>
                                </tr>
                                <tr>
                                    <td>使用者協作：</td>
                                    <td class="co-op">此教案無使用者協作</td>
                                </tr>
                                <tr>
                                    <td>支援裝置：</td>
                                    <td class="device">此教案無特殊裝置支援</td>
                                </tr>
                                <tr>
                                    <td>其他功能：</td>
                                    <td class="other-func">此教案無其他功能</td>
                                </tr>
                            </table>
                            <!--
                            <div class="lesson-info">
                                <div class="left-intro py-1">
                                    <span class="left-intro-title">簡介：</span><span class="brief-intro">無簡介</span>
                                    <span><button class="show-intro-btn">顯示更多</button></span>
                                </div>
                                <div class="left-intro py-1">
                                    <span class="left-intro-title">開發者：</span><span class="developer"></span>
                                </div>
                                <div class="left-intro py-1">
                                    <span class="left-intro-title">發行日期：</span><span class="release-date">無發行日期</span>
                                </div>
                                <div class="left-intro py-1">
                                    <span class="left-intro-title">使用者協作：</span><span class="co-op"></span>
                                </div>
                                <div class="left-intro py-1">
                                    <span class="left-intro-title">其他功能：</span><span class="other-func"></span>
                                </div>-->
                                <!--<div class="left-intro my-1">
                                    <span class="left-intro-title">主要目的：</span><span>預防</span>、<span>維持</span>
                                </div>
                                <div class="left-intro my-1">
                                    <span class="left-intro-title">注意事項：</span><span>可能出現3D暈眩的狀況，建議使用20~30分鐘休息一次</span>
                                </div>
                                <div class="left-intro my-1">
                                    <span class="left-intro-title">使用限制：</span><span>不適用於75歲以上患者</span>
                                </div>
                                <div class="caution-tab my-1">
                                    <span class="left-intro-title">建議使用時段：</span><span>睡前</span>、<span>晚餐飯後</span>
                                </div>-->
                            <!--</div>-->
                        </div>
                        <div class="col-md-6">
                            <div class="sub-title my-2">
                                教案設備需求
                            </div>
                            <ul class="nav nav-tabs os-nav" id="osTabs" role="tablist">
                                <!-- 動態生成的 OS Tabs 會放在這裡 -->
                            </ul>
                            <div class="os-tab-content" id="osTabContent">
                                此教案無作業系統需求
                                <!-- 動態生成的 OS Tab Content 會放在這裡 -->
                            </div>
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col-xxl-12">
                            <div class="row review-header-row align-items-center mb-3">
                                <div class="col-6">
                                    <div class="review-header">
                                        <div class="reivew-title sub-title">評分與評論</div>
                                    </div>
                                </div>
                                
                                <!-- 撰寫評論按鈕 - 僅登入用戶可見，靠右對齊 -->
                                <#if currentUser??>
                                <div class="col-6 text-end">
                                    <div class="comment-button-container">
                                        <button type="button" class="btn btn-primary" id="openCommentModal">
                                            <i class="fas fa-comment"></i> 撰寫評論
                                        </button>
                                    </div>
                                </div>
                                </#if>
                            </div>
                            
                            <!-- 評論列表區塊 -->
                            <div id="comments-container">
                                <div class="no-review">此教案目前無評論</div>
                            </div>
                            
                        </div>
                    </div>
                    <div class="row mt-4">
                        <div class="col-xxl-12">
                            <div class="app-nav">
                                <div class="app-nav-tab p-1 px-3 tab-right active" data-tab="tab1">
                                    <div class="app-nav-title">教案使用分析</div>
                                </div>
                                <div class="app-nav-tab p-1 px-3 tab-right" data-tab="tab2">
                                    <div class="app-nav-title">相關研究資料</div>
                                </div>
                                <div class="app-nav-tab p-1 px-3" data-tab="tab3">
                                    <div class="app-nav-title">教案成就/統計資料</div>
                                </div>
                            </div>
                            <div id="tab1" class="tab-content">
                                <div class="row lesson-analytic">
                                    <div class="col-md-4">
                                        <img class="analytic-img" src="" alt="Header-img">
                                    </div>
                                    <div class="col-md-8 stats-area">
                                        <div class="stats-analytic">
                                            <div class="stat-analytic">
                                                <div class="stat-analytic-num">0</div>
                                                <div class="stat-analytic-text">使用者平均年齡</div>
                                            </div>
                                            <div class="stat-analytic">
                                                <div class="stat-analytic-num">0</div>
                                                <div class="stat-analytic-text">用戶使用過該教案</div>
                                            </div>
                                            <div class="stat-analytic">
                                                <div class="stat-analytic-num">0</div>
                                                <div class="stat-analytic-text">用戶持續使用該教案超過一週</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="analytic-buttons">
                                        <button class="m-2 p-2 px-3 active">使用成效</button>
                                        <button class="m-2 p-2 px-3">使用次數統計</button>
                                        <button class="m-2 p-2 px-3">使用年齡統計</button>
                                        <button class="m-2 p-2 px-3">教案評論等級</button>
                                        <button class="m-2 p-2 px-3">收藏數量</button>
                                    </div> <!-- analytic-buttons -->
                                </div> <!-- lesson-analytic -->
                            </div>
                            <div id="tab2" class="tab-content hidden">
                                <div>
                                    <table class="table">
                                        <thead>
                                            <tr class="table-bg-color">
                                                <th class="index-column">#</th>
                                                <th style="text-align: center;">Clinical trial</th>
                                            </tr>
                                        </thead>
                                        <tbody id="clinical-trial-body">
                                            <tr>
                                                <td colspan="2" class="no-research">此教案無臨床研究</td>
                                            </tr>
                                        </tbody>
                                        <thead>
                                            <tr class="table-bg-color">
                                                <th class="index-column">#</th>
                                                <th style="text-align: center;">Research Paper</th>
                                            </tr>
                                        </thead>
                                        <tbody id="research-paper-body">
                                            <tr>
                                                <td colspan="2" class="no-research">此教案無文獻研究</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div> <!-- tab2 -->
                            <div id="tab3" class="tab-content hidden">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h3>成就列表</h3>
                                        <div class="achievements">
                                            <table class="table achTable">
                                                <thead>
                                                    <tr>
                                                        <th>成就圖示</th>
                                                        <th>成就名稱</th>
                                                        <th>成就描述</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <h3>統計資料</h3>
                                        <div class="stats">
                                            <table class="table statsTable">
                                                <thead>
                                                    <tr>
                                                        <th>統計名稱</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div> <!-- row -->
                            </div> <!-- tab3 -->
                        </div>
<#if currentUser??>
                    </div>
                </div>
</#if>
                </div>
            </div>
        </div>
    </main>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script type="text/javascript" src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/appPage.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/leftnav.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/swal2.js"></script>
<#if currentUser??>
<style>
    .app-main-container {
    	width: 77%;
    }
    	
</style>
</#if>

</body>
<!-- 評論模態視窗 -->
<div class="modal fade" id="commentModal" tabindex="-1" aria-labelledby="commentModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="commentModalLabel">撰寫評論</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="commentForm">
                    <div class="star-rating mb-3">
                        <span>評分：</span>
                        <div class="rating-stars d-inline-block">
                            <i class="fas fa-star rating-input" data-rating="1"></i>
                            <i class="fas fa-star rating-input" data-rating="2"></i>
                            <i class="fas fa-star rating-input" data-rating="3"></i>
                            <i class="fas fa-star rating-input" data-rating="4"></i>
                            <i class="fas fa-star rating-input" data-rating="5"></i>
                        </div>
                        <span id="selected-rating" class="ms-2">0</span><span>/5</span>
                    </div>
                    <div class="mb-3">
                        <textarea class="form-control" id="commentText" rows="4" placeholder="分享您對此教案的看法..."></textarea>
                    </div>
                </form>
                <!--<div class="text-success mt-2" id="comment-success-message" style="display:none;">
                    <i class="fas fa-check-circle"></i> 感謝您的評論！您已獲得 5 點積分獎勵。
                </div>-->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="submitComment">提交評論</button>
            </div>
        </div>
    </div>
</div>
<div class="modal" id="show-more-intro" tabindex="-1" role="dialog" aria-labelledby="loadingModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <div class="intro-content"></div>
            </div>
        </div>
    </div>
</div>
<div id="previewModal" class="modal fade" tabindex="-1">
  <div class="modal-dialog modal-dialog-centered modal-xl">
    <div class="modal-content bg-transparent border-0 position-relative">
      <!-- 1. 挪到最前面；2. 加上 btn-close-white；3. aria-label -->
      <button type="button"
              class="btn-close btn-close-white position-absolute top-0 end-0 m-3"
              data-bs-dismiss="modal"
              aria-label="Close"></button>

      <div class="modal-body p-0">
        <div id="modalCarousel" class="carousel slide" data-bs-interval="false">
          <div class="carousel-inner"></div>
          <button class="carousel-control-prev" type="button" data-bs-target="#modalCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon"></span>
          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#modalCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon"></span>
          </button>
        </div>
      </div>
    </div>
  </div>
</div>

<script>
const main = ${ lessonMain };
console.log('main', main);
<#if currentUser??>
    let cUserId = ${ currentUser.id!"" }
<#else>
    let cUserId = null;
</#if>
const lessonId = main.id;
let isFavor;
let lessonImagesGlobal = [];

$('.app-title').text(main.lessonName);
$('.brief-intro').text(main.lessonBrief);
$('.intro-content').html(main.lessonDescription);
$('.release-date').text(formatDate(main.releaseDate));
if (main.price == 0) {
    $('.lesson-price').text('免費');
    $('.lesson-price').addClass('free-price');
} else {
    $('.lesson-price').text('售價 NT$ ' + main.price);
}

$('.analytic-img').attr('src', '/File/api/file/path' + main.headerImageUrl);

// 主要目的和使用限制
if (main.lessonPurpose) {
    $('.main-purpose-info').text(main.lessonPurpose);
}

if (main.usageRestriction) {
    $('caution-info').text(main.usageRestriction);
}


$(document).ready(function () {
    introAge();
    showFavor();
    requestLessonApi();
    addFavorite();
    showMoreIntro();
    lessonTryLink();
    initComments(); // 初始化評論區
    setupCommentForm(); // 設置評論表單
});

function showFavor() {
    if (cUserId !== null) {
        $.ajax({
            url: "/UserFavoriteLesson/api/lesson",
            type: 'POST',
            data: JSON.stringify({ "lessonId": lessonId, "userId": cUserId }),
            success: function (response) {
                console.log("favorite: ", response);
                if (response.isFavor) {
                    isFavor = true;
                }
            },
            error: function (xhr, status, error) {
                console.log('操作失敗: ', xhr, status, error);
            }
        });
    } else {
        return;
    }
}

function lessonTryLink() {
    if (typeof main.executionId !== 'undefined') {
        const eId = main.executionId;
        if (eId.includes('scratch/')) {
            // 提取 scratch ID
            const scratchId = ansList['11'].split('/')[1];
            $('.lesson-try-link').attr('href', 'https://scratch.mit.edu/projects/' + scratchId + '/fullscreen').attr('target', '_blank');

        } else if (eId.includes('customScratch/')) {
            const scratchId = ansList['11'].split('/')[1];
            $('.lesson-try-link').attr('href', 'https://icare.itri.org.tw/' + scratchId).attr('target', '_blank');

        } else if (eId.includes('mentalwe')) {
            $('.lesson-try-link').attr('href', ansList['11']).attr('target', '_blank');

        } else {
            // 預設情況，設定為 steam://run/
            $('.lesson-try-link').attr('href', 'steam://run/' + eId);
        }
    } else {
        console.log('main.executionId is undefined.');
        $('.lesson-try-link').hide();
    }
}

function showMoreIntro() {
    $('.show-intro-btn').click(function () {
        $('#show-more-intro').modal('show');
    });
}

function introAge() {
    const age = main.minAge;
    const csrrRating = main.csrrRate;


    if (csrrRating != undefined & csrrRating != "" & csrrRating != null) {
        switch (csrrRating) {
            case "G":
                $('.intro-age').text('普遍級   ');
                $('.intro-age').append('<img src="/html/dtxstore/images/rating/普遍級.png">');
                break;
            case "P6":
                $('.intro-age').text('保護級   ');
                $('.intro-age').append('<img src="/html/dtxstore/images/rating/保護級.png">');
                break;
            case "C12":
                $('.intro-age').text('輔12級   ');
                $('.intro-age').append('<img src="/html/dtxstore/images/rating/輔12級.png">');
                break;
            case "C15":
                $('.intro-age').text('輔15級   ');
                $('.intro-age').append('<img src="/html/dtxstore/images/rating/輔15級.png">');
                break;
            case "P18":
                $('.intro-age').text('限制級   ');
                $('.intro-age').append('<img src="/html/dtxstore/images/rating/限制級.png">');
                break;
            default:
                $('.intro-age').text('不限');
                break;
        }

    } else if (age != 0) {
        $('.intro-age').text(age);
    } else if (age == 0) {
        $('.intro-age').text('不限');
    } else {
        $('.intro-age').text('不限');
    }
}


// 抓取教案資料
function requestLessonApi() {
    const apiUrls = [
        `/LessonAchievement/api/lesson`,
        `/LessonStatistics/api/lesson`,
        `/LessonImage/api/lesson`,
        `/LessonSystemRequirement/api/lesson`,
        `/LessonComment/api/lesson`,
        `/LessonNews/api/lesson`,
        `/LessonResearch/api/lesson`,
        `/LessonTag/api/lesson`
    ];
    // 創建一個包含所有 fetch POST 請求的 Promise 陣列
    const requests = apiUrls.map(url =>
        $.ajax({
            url: url,
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json', // 確保後端能正確解析 JSON
            data: JSON.stringify({ "lessonId": lessonId })
        }).done(response => {
            //console.log("Response from "+url+" : ", response);
        }).fail((xhr, status, error) => {
            console.error("Error from " + url + " : ", error, xhr.responseText);
        })
    );

    // 使用 Promise.all 並行處理所有請求
    Promise.all(requests)
        .then(responses => {
            //console.log("All API Responses:", responses); // 確認所有回應
            const [lessonAchievement, lessonStatistic, lessonImage,
                lessonRequirement, lessonComment, lessonNews, lessonResearch, lessonTag] = responses;

            //console.log('Lesson Achievement:', lessonAchievement);
            //console.log('Lesson Statistic:', lessonStatistic);
            //console.log('Lesson Image:', lessonImage);
            console.log('Lesson Requirement:', lessonRequirement);
            //console.log('Lesson Comment:', lessonComment);
            //console.log('Lesson Research:', lessonResearch);
            //console.log('Lesson Tag:', lessonTag);


            updateCarousel(lessonImage);
            addAchAndStats(lessonAchievement, lessonStatistic);
            updateLessonRequirement(lessonRequirement);
            updateLessonTag(lessonTag);
            //updateLessonResearch(lessonResearch);

        })
        .catch(error => {
            console.error('Error fetching data:', error);
        });
}

function formatKey(key) {
    return key.toString().padStart(10, '0');
}

// 建立 carousel：動態渲染圖片或影片，並註冊點擊預覽事件
function updateCarousel(lessonImage) {
  const indicators = document.querySelector('.carousel-indicators');
  const inner      = document.querySelector('#carouselIndicators .carousel-inner');  // 對 lessonImage 進行排序，使影片優先於圖片顯示
  lessonImage.sort((a, b) => {
    // 若 a 是影片但 b 不是影片，a 排前面
    if (a.type === 'VIDEO' && b.type !== 'VIDEO') return -1;
    // 若 a 不是影片但 b 是影片，b 排前面
    if (a.type !== 'VIDEO' && b.type === 'VIDEO') return 1;
    // 若兩者類型相同，保持原始順序
    return 0;
  });

  // 儲存給 Modal 用
  lessonImagesGlobal = lessonImage;

  // 清空原本內容
  indicators.innerHTML = '';
  inner.innerHTML      = '';

  if (!Array.isArray(lessonImage) || lessonImage.length === 0) {
    console.error('Error: lessonImage 不是陣列或為空');
    return;
  }

  lessonImage.forEach((data, idx) => {
    const isVideo = data.type === 'VIDEO';

    // 1. 建立指示器按鈕
    const btn = document.createElement('button');
    btn.type = 'button';
    btn.setAttribute('data-bs-target', '#carouselIndicators');
    btn.setAttribute('data-bs-slide-to', idx);
    btn.setAttribute('aria-label', `Slide ` + (idx+1));
    if (idx === 0) {
      btn.classList.add('active');
      btn.setAttribute('aria-current', 'true');
    }
    indicators.appendChild(btn);

    // 2. 建立輪播項目
    const item = document.createElement('div');
    item.classList.add('carousel-item');
    if (idx === 0) item.classList.add('active');

    if (isVideo) {
      // --- 影片處理 ---
      const vid = document.createElement('video');
      vid.classList.add('d-block','w-100');
      vid.src         = data.filePath.startsWith('http')
                        ? data.filePath
                        : '/File/api/file/path' + data.filePath;      vid.autoplay    = true;
      vid.muted       = true;
      vid.loop        = false; // 設為 false，使影片不會循環播放
      vid.playsInline = true;

      // 播放結束自動跳下一張
      vid.addEventListener('ended', () => {
        const carousel = bootstrap.Carousel.getInstance(
          document.getElementById('carouselIndicators')
        );
        if (carousel) carousel.next();
      });

      // 綁定點擊打開 Modal carousel
      vid.dataset.index = idx;
      vid.addEventListener('click', openPreview);

      item.appendChild(vid);
    } else {
      // --- 圖片處理 ---
      const img = document.createElement('img');
      img.src   = '/File/api/file/path' + data.filePath;
      img.alt   = `Slide ` + (idx+1);
      img.classList.add('d-block','w-100');

      // 綁定點擊打開 Modal carousel
      img.dataset.index = idx;
      img.addEventListener('click', openPreview);

      item.appendChild(img);
    }

    inner.appendChild(item);
  });

  // 初始化 Modal 裡的 carousel（一次渲染所有 slides）
  initModalCarousel(lessonImagesGlobal);
}
// 點擊 carousel 項目時，於 Modal 中放大預覽
function openPreview(e) {
  const idx     = parseInt(e.currentTarget.dataset.index, 10);
  const modalEl = document.getElementById('previewModal');
  const modal   = new bootstrap.Modal(modalEl);
  modal.show();

  modalEl.addEventListener('shown.bs.modal', () => {
    const carousel = bootstrap.Carousel.getOrCreateInstance(
      document.getElementById('modalCarousel')
    );
    carousel.to(idx);

    const activeSlide = document.querySelector('#modalCarousel .carousel-item.active');
    const activeVideo = activeSlide?.querySelector('video');
    if (activeVideo) activeVideo.play();
  }, { once: true });

  modalEl.addEventListener('hidden.bs.modal', () => {
    modalEl.querySelectorAll('video').forEach(v => {
      v.pause();
      v.currentTime = 0;
    });
    const carousel = bootstrap.Carousel.getInstance(
      document.getElementById('modalCarousel')
    );
    if (carousel) carousel.to(0);
  });
}

// 建立 成就、統計 資料表
function addAchAndStats(achMesg, statMesg) {

    // 處理成就資料
    if (Array.isArray(achMesg) && achMesg.length > 0) {
        $.each(achMesg, function (index, achData) {
            const achIcon = '/File/api/file/path' + achData.unlockedIconUrl;
            const achName = achData.displayName;
            const achDesc = achData.description;

            const tr = $('<tr></tr>');
            const td1 = $('<td class="img-col"></td>');
            const td2 = $('<td></td>');
            const td3 = $('<td></td>');

            const img = $('<img>').attr({
                src: achIcon,
                width: 50,
                height: 50
            });

            td1.append(img);
            td2.text(achName);
            td3.text(achDesc);

            tr.append(td1, td2, td3);

            $('.achTable tbody').append(tr);
        });
    } else {
        const tr = $('<tr></tr>');
        const td = $('<td></td>').attr('colspan', 3).text('此教案無成就資料');
        tr.append(td);
        $('.achTable tbody').append(tr);
    }

    // 處理統計資料
    if (Array.isArray(statMesg) && statMesg.length > 0) {
        $.each(statMesg, function (index, statData) {
            const statName = statData.apiName;

            const tr = $('<tr></tr>');
            const td = $('<td></td>').text(statName);

            tr.append(td);

            $('.statsTable tbody').append(tr);
        });
    } else {
        const tr = $('<tr></tr>');
        const td = $('<td></td>').attr('colspan', 1).text('此教案無統計資料');
        tr.append(td);
        $('.statsTable tbody').append(tr);
    }
}

function initModalCarousel(images) {
  const inner = document.querySelector('#modalCarousel .carousel-inner');
  inner.innerHTML = '';  // 清空
    // 使用與主輪播相同的排序邏輯，確保排序一致
    const sortedImages = [...images].sort((a, b) => {
    if (a.type === 'VIDEO' && b.type !== 'VIDEO') return -1;
    if (a.type !== 'VIDEO' && b.type === 'VIDEO') return 1;
    return 0;
  });

  sortedImages.forEach((data, idx) => {
    const item = document.createElement('div');
    item.classList.add('carousel-item');
    if (idx === 0) item.classList.add('active');

    if (data.type === 'VIDEO') {
      const vid = document.createElement('video');
      vid.classList.add('d-block', 'w-100');
      vid.src           = data.filePath.startsWith('http')
                          ? data.filePath
                          : '/File/api/file/path' + data.filePath;      vid.controls      = true;
      vid.autoplay      = true;   
      vid.muted         = true;
      vid.loop          = false; // 設為 false，使影片不會循環播放
      vid.playsInline   = true;
      
      // 播放結束自動跳下一張
      vid.addEventListener('ended', () => {
        const carousel = bootstrap.Carousel.getInstance(
          document.getElementById('modalCarousel')
        );
        if (carousel) carousel.next();
      });
      
      item.appendChild(vid);
    } else {
      const img = document.createElement('img');
      img.classList.add('d-block', 'w-100');
      img.src = '/File/api/file/path' + data.filePath;
      item.appendChild(img);
    }

    inner.appendChild(item);
  });
  const modalCarouselEl = document.getElementById('modalCarousel');
  const modalCarousel = bootstrap.Carousel.getOrCreateInstance(modalCarouselEl);

  modalCarouselEl.addEventListener('slide.bs.carousel', e => {
    modalCarouselEl.querySelectorAll('video').forEach(v => {
      v.pause();
      v.currentTime = 0;
    });
    // 下一个要显示的 slide
    const nextVid = e.relatedTarget.querySelector('video');
    if (nextVid) nextVid.play();
  });
}

// 建立教案需求	
function updateLessonRequirement(sysReq) {
    let requirements = sysReq;
    //console.log('sysReq', sysReq);

    if (!Array.isArray(requirements) || requirements.length === 0) {
        //console.log('沒有任何需求資料');
        return;
    }

    // 先清空現有的內容
    $('.os-nav').empty();
    $('#osTabContent').empty();

    // 使用 set 儲存所有出現過的平台
    const platforms = new Set();
    // 我們只處理「minimum」「recommended」兩種需求類型
    const reqTypes = ['MINIMUM', 'RECOMMENDED'];

    // contentMap 會以平台 (platform) 作為第一層，再以需求類型 (minimum / recommended) 作為第二層存放資訊
    const contentMap = {};

    requirements.forEach(item => {
        //console.log('處理 item:', item); // 確保每筆資料都有進入迴圈
        const { platform, requirementType, operatingSystem, processor, memory, graphicsCard, directxVersion, soundCard, storageSpace, notes } = item;

        // 若此筆資料沒有 operatingSystem 或 processor，就直接略過不顯示
        if (!operatingSystem || !processor) {
            console.log('缺少 OS 或 CPU，跳過:', item);
            return;
        }

        // 將平台加入集合
        platforms.add(platform);

        // 若 contentMap 裡尚未有此平台，先初始化
        if (!contentMap[platform]) {
            contentMap[platform] = {};
        }

        // 將對應需求類型的內容存入 contentMap
        contentMap[platform][requirementType] = {
            os: operatingSystem,
            cpu: processor,
            ram: memory,
            graphic: graphicsCard,
            dx: directxVersion,
            audio: soundCard,
            storage: storageSpace,
            note: notes
        };

        //console.log('contentMap:', contentMap);
    });

    // 開始動態生成 Tabs (os-nav) 以及對應的 Tab Content (osTabContent)
    // 需求類型要依照 '最低配備'(minimum) 與 '建議配備'(recommended) 的順序顯示
    platforms.forEach(platform => {
        //console.log('正在處理平台:', platform);
        reqTypes.forEach(type => {
            // 若該平台在 contentMap 中確實有資料才顯示
            if (contentMap[platform] && contentMap[platform][type]) {
                //console.log('正在處理需求類型:', type);
                // 先決定要顯示的名稱 (type)
                const reqTypeName = (type === 'MINIMUM') ? '最低配備' : '建議配備';

                // 建立對應的 tab id、tab content id
                const tabId = platform + '-' + type + '-tab';
                const containerId = platform + '-' + type + '-info';

                // 建立平台 + 需求類型的 tab 按鈕
                const osTabHtml = `
                    <li class="nav-item" role="presentation">
                        <button class="nav-link"
                                id="` + tabId + `"
                                data-bs-toggle="tab"
                                data-bs-target="#` + containerId + `"
                                type="button"
                                role="tab"
                                aria-controls="` + containerId + `"
                                aria-selected="false">
                            ` + platform + ` - ` + reqTypeName + `
                        </button>
                    </li>
                `;
                $('.os-nav').append(osTabHtml);

                // 生成表格內容
                const { os, cpu, ram, graphic, dx, audio, storage, note } = contentMap[platform][type];

                let osTabContentHtml = `
                    <div class="os-tab-pane fade mt-2" id="` + containerId + `" role="tabpanel" aria-labelledby="` + tabId + `" style="display: none;">
                        <table class="table requireTable">
                            <tbody>
                `;
                // 作業系統
                if (os) {
                    osTabContentHtml += `<tr><td>作業系統：</td><td>` + os + `</td></tr>`;
                }
                // 處理器
                if (cpu) {
                    osTabContentHtml += `<tr><td>處理器：</td><td>` + cpu + `</td></tr>`;
                }
                // 記憶體
                if (ram) {
                    osTabContentHtml += `<tr><td>記憶體：</td><td>` + ram + `</td></tr>`;
                }
                // 顯示卡
                if (graphic) {
                    osTabContentHtml += `<tr><td>顯示卡：</td><td>` + graphic + `</td></tr>`;
                }
                // DirectX
                if (dx) {
                    osTabContentHtml += `<tr><td>DirectX：</td><td>` + dx + `</td></tr>`;
                }
                // 音效卡
                if (audio) {
                    osTabContentHtml += `<tr><td>音效卡：</td><td>` + audio + `</td></tr>`;
                }
                // 儲存空間
                if (storage) {
                    osTabContentHtml += `<tr><td>儲存空間：</td><td>` + storage + `</td></tr>`;
                }
                // 備註
                if (note) {
                    osTabContentHtml += `<tr><td>備註：</td><td>` + note + `</td></tr>`;
                }

                osTabContentHtml += `
                            </tbody>
                        </table>
                    </div>
                `;
                $('#osTabContent').append(osTabContentHtml);
            }
        });
    });

    // 若有產生 tab，預設選中第一個
    const $firstTabButton = $('.os-nav button:first');
    if ($firstTabButton.length > 0) {
        $firstTabButton.addClass('active');
        $('#osTabContent .os-tab-pane:first').addClass('show active').css('display', 'block');
    }

    // 處理點擊 tab 時，切換對應的內容
    $('.os-nav button').click(function () {
        $('.os-tab-pane').removeClass('show active').css('display', 'none');
        const targetId = $(this).data('bs-target');
        $(targetId).addClass('show active').css('display', 'block');
        $('.os-nav button').removeClass('active');
        $(this).addClass('active');
    });
}


function updateLessonTag(tagData) {
    // 檢查是否已收藏
    if (isFavor === true) {
        var $icon = $('.add-favorites-link').find('i');
        $icon.toggleClass('fa-regular fa-solid');
        $icon.parent().contents().last().replaceWith(' 已收藏');
    }



    // 根據 tagType 將標籤分組
    const groupedTags = tagData.reduce((acc, tag) => {
        const { tagType } = tag;
        if (!acc[tagType]) {
            acc[tagType] = []; // 如果該類型不存在，初始化為空陣列
        }
        acc[tagType].push(tag);
        return acc;
    }, {});

    // 處理適應症
    let indicationContent = '';
    const tagType1Tags = groupedTags[1] || [];
    $.each(tagType1Tags, function (index, tag) {
        if (index > 0) {
            indicationContent += '、';
        }
        indicationContent += tag.tagName;
    });
    if (indicationContent !== '') {
        $('.intro-indication').text(indicationContent);
    }

    // 處理類型
    let typeContent = '';
    const tagType2Tags = groupedTags[2] || [];
    $.each(tagType2Tags, function (index, tag) {
        if (index > 0) {
            typeContent += '、';
        }
        typeContent += tag.tagName;
    });
    if (typeContent !== '') {
        $('.intro-type').text(typeContent);
    }

    // 處理開發者
    let developerContent = '';
    const tagType4Tags = groupedTags[4] || [];
    $.each(tagType4Tags, function (index, tag) {
        if (index > 0) {
            developerContent += '、';
        }
        developerContent += tag.tagName;
    });
    if (developerContent !== '') {
        $('.developer').text(developerContent);
    }

    // 使用者協作
    let userContent = '';
    const tagType8Tags = groupedTags[8] || [];
    $.each(tagType8Tags, function (index, tag) {
        if (index > 0) {
            userContent += '、';
        }
        userContent += tag.tagName;
    });
    if (userContent !== '') {
        $('.co-op').text(userContent);
    }

    // 處理裝置
    let deviceContent = '';
    const tagType5Tags = groupedTags[5] || [];
    $.each(tagType5Tags, function (index, tag) {
        if (index > 0) {
            deviceContent += '、';
        }
        deviceContent += tag.tagName;
    });
    if (deviceContent !== '') {
        $('.device').text(deviceContent);
    }

    // 處理其他
    let otherContent = '';
    const tagType9Tags = groupedTags[9] || [];
    $.each(tagType9Tags, function (index, tag) {
        if (index > 0) {
            otherContent += '、';
        }
        otherContent += tag.tagName;
    });
    if (otherContent !== '') {
        $('.other-func').text(otherContent);
    }

    // 處理語言
    let languageContent = '';
    const tagType3Tags = groupedTags[3] || [];
    let preferredTags = ["繁體中文", "繁體中文(語音)", "英文", "英文(語音)"];

    // 按照偏好排序的標籤
    const sortedTags = tagType3Tags
        .filter(tag => preferredTags.includes(tag.tagName)) // 只保留偏好標籤
        .sort((a, b) => preferredTags.indexOf(a.tagName) - preferredTags.indexOf(b.tagName)); // 按偏好排序

    // 其餘非偏好的標籤
    const otherTags = tagType3Tags.filter(tag => !preferredTags.includes(tag.tagName));

    // 合併排序後的偏好標籤與其他標籤
    const finalTags = [...sortedTags, ...otherTags];


    languageContent = finalTags.map(tag => tag.tagName).join('、');
    if (languageContent !== '') {
        $('.intro-language').text(languageContent);
        $('.show-more-btn').hide();
    }

    // 檢查內容是否超過一行
    let $introLanguage = $('.intro-language');
    if ($introLanguage[0].scrollWidth > $introLanguage[0].clientWidth) {
        $('.show-more-btn').show();
    } else {
        $('.show-more-btn').hide();
    }

    // 顯示更多功能
    $('.show-more-btn').off('click').on('click', function () {
        var $content = $(this).siblings('.show-more-content');
        var fullText = $('.intro-language').text();

        $content.text(fullText);
        $content.toggle(); // 顯示或隱藏內容

        // 切換按鈕文字
        if ($content.is(':visible')) {
            $(this).text('隱藏');
        } else {
            $(this).text('顯示更多');
        }
    });
}


function updateLessonComment(comment) {

}

function updateLessonResearch(research) {
    var researchDTO = [];
    try {
        researchDTO = JSON.parse(research.researchDTO);
        console.log(researchDTO);
    } catch (error) {
        console.error('Error parsing researchDTO:', error);
        return;
    }
    researchDTO.forEach(function (researchData, index) {
        var researchAnses = researchData['evalItemAnses'];
        const researchType = researchAnses["303"];
        const researchTitle = researchAnses["301"];
        const researchLink = researchAnses["302"];

        var rowHtml = '<tr><td class="index-column">' + (index + 1) + '</td><td><a href="' + researchLink + '">' + researchTitle + '</a></td></tr>';


        if (researchType == '1') {
            $('#clinical-trial-body .no-research').remove();
            $('#clinical-trial-body').append(rowHtml);
        } else if (researchType == '2') {
            $('#research-paper-body .no-research').remove();
            $('#research-paper-body').append(rowHtml);
        }
    });
}

// 首字母大寫
function capitalizeFirstLetter(string) {
    if (!string) return string; // 如果字串為空，直接返回
    return string.charAt(0).toUpperCase() + string.slice(1);
}

// 加入收藏教案
function addFavorite() {
    $('.add-favorites-link').on('click', function () {
        var $icon = $(this).find('i');
        var isFavorite = $icon.hasClass('fa-solid');

        var url = isFavorite ? '/UserFavoriteLesson/api/delete' : '/UserFavoriteLesson/api/create';
        $.ajax({
            url: url,
            type: 'POST',
            data: JSON.stringify({ "lessonId": lessonId, "userId": cUserId }),
            success: function (response) {
                console.log('favorite response: ' + response);
                // 切換圖標樣式
                $icon.toggleClass('fa-regular fa-solid');
                var text = isFavorite ? '加入收藏' : '已收藏';
                $icon.parent().contents().last().replaceWith(' ' + text);
            },
            error: function (xhr, status, error) {
                alert('操作失敗: ' + error);
            }
        });
    });
}

function formatDate(timeStamp) {
    const date = new Date(timeStamp);
    return date.getFullYear() + ' 年 ' + (date.getMonth() + 1).toString().padStart(2, '0') + ' 月 ' + date.getDate().toString().padStart(2, '0') + ' 日';
}

// 初始化評論區域，加載現有評論
function initComments() {
    $.ajax({
        url: "/LessonComment/api/lesson",
        type: 'POST',
        data: JSON.stringify({ "lessonId": lessonId }),
        contentType: 'application/json',
        success: function (comments) {
            displayComments(comments);
            updateAvgRating(comments);
        },
        error: function (xhr, status, error) {
            console.error('Error fetching comments:', error);
        }
    });
}

// 顯示評論列表
function displayComments(comments) {
    if (!comments || comments.length === 0) {
        return; // 如果沒有評論，保留 "無評論" 提示
    }
    
    // 清除 "無評論" 提示
    $('#comments-container').empty();
    
    // 顯示平均評分
    let avgRating = calculateAvgRating(comments);
    const ratingHtml = `
    <div class="review-rating mb-1">
        ` + generateStarRating(avgRating) + `
        <span class="rating-title-value"><span class="review-ave">` + avgRating.toFixed(1) + `</span>（<span class="total-reivew-count">` + comments.length + `</span>則評論）</span>
    </div>`;
    $('#comments-container').append(ratingHtml);
    
    // 顯示評論列表
    const $commentsRow = $('<div class="row row-cols-1 row-cols-md-2"></div>');
    
    comments.sort((a, b) => new Date(b.createTime) - new Date(a.createTime)).forEach(comment => {
        // 使用後端提供的userName，如果沒有則使用creator
        const isCurrentUser = comment.creator === cUserId;
        
        const $commentCol = $('<div class="col mb-3"></div>');
        const $commentContainer = $(`
            <div class="review-container p-3 border rounded">
                <div class="review-header d-flex justify-content-between mb-2">
                    <div>
                        <h5>` + comment.displayName +(isCurrentUser ? ` (我自己)` : ``) + `</h5>
                    </div>
                    <span>` + formatCommentDate(comment.createTime) + `</span>
                </div>
                <div class="review-rating mb-2">
                    ` + generateStarRating(comment.rating) + `
                    <span class="rating-value ms-2">` + comment.rating + `.0</span>
                </div>
                <div class="review-body">
                    ` + comment.commentText + `
                </div>
            </div>
        `);
        
        $commentCol.append($commentContainer);
        $commentsRow.append($commentCol);
    });
    
    $('#comments-container').append($commentsRow);
}

// 更新頁面頂部的整體評分
function updateAvgRating(comments) {
    if (!comments || comments.length === 0) return;
    
    const avgRating = calculateAvgRating(comments);
    
    // 更新頁面頂部的星級
    const $introRating = $('.intro-rating');
    $introRating.empty();
    
    // 使用相同的星星生成函數，確保一致性
    $introRating.append(generateStarRating(avgRating));
    
    // 添加評分值和評論數
    $introRating.append(`<span class="rating-value">` + avgRating.toFixed(1) + `（` + comments.length + `則評論）</span>`);
}

// 計算平均評分
function calculateAvgRating(comments) {
    if (!comments || comments.length === 0) return 0;
    
    const totalRating = comments.reduce((sum, comment) => sum + comment.rating, 0);
    return totalRating / comments.length;
}

// 修改產生星級評分HTML的函數
function generateStarRating(rating) {
    let html = '';
    const roundedRating = Math.round(rating * 2) / 2; // 四捨五入到最近的0.5
    
    for (let i = 1; i <= 5; i++) {
        if (i <= Math.floor(roundedRating)) {
            // 整顆星
            html += `<span class="star"><i class="fas fa-star"></i></span>`;
        } else if (i - 0.5 === roundedRating) {
            // 半星
            html += `<span class="star"><i class="fas fa-star-half-alt"></i></span>`;
        } else {
            // 空星
            html += `<span class="empty-star"><i class="far fa-star"></i></span>`;
        }
    }
    return html;
}

// 格式化評論日期
function formatCommentDate(timestamp) {
    const date = new Date(timestamp);
    return date.getFullYear() + `/` + (date.getMonth() + 1).toString().padStart(2, '0') + `/` + date.getDate().toString().padStart(2, '0');
}

// 設置評論表單
function setupCommentForm() {
    // 打開評論模態窗口
    $('#openCommentModal').click(function() {
        // 首先檢查用戶是否已經評論過此教案
        if (cUserId !== null) {
            $.ajax({
                url: "/LessonComment/api/lesson",
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({ "lessonId": lessonId }),
                success: function(comments) {
                    // 檢查當前用戶是否已經評論過
                    const hasCommented = comments.some(comment => comment.creator === cUserId);
                    
                    if (hasCommented) {
                        Swal.fire({
                            title: '已評論',
                            text: '您已經評論過此教案，不能重複評論！',
                            icon: 'info',
                            confirmButtonColor: '#00CB96'
                        });
                    } else {
                        $('#commentModal').modal('show');
                        resetCommentForm();
                    }
                },
                error: function(xhr, status, error) {
                    console.error('檢查評論狀態失敗:', error);
                    $('#commentModal').modal('show');
                    resetCommentForm();
                }
            });
        } else {
            // 用戶未登入的情況下
            $('#commentModal').modal('show');
            resetCommentForm();
        }
    });
    
    // 評分星星點擊事件
    let currentRating = 0;
    
    $('.rating-input').hover(
        function() {
            const rating = $(this).data('rating');
            updateStars(rating, true);
        },
        function() {
            updateStars(currentRating, false);
        }
    );
    
    $('.rating-input').click(function() {
        currentRating = $(this).data('rating');
        updateStars(currentRating, false);
        $('#selected-rating').text(currentRating);
    });
    
    // 更新星星顯示
    function updateStars(rating, hover) {
        $('.rating-input').each(function() {
            const starRating = $(this).data('rating');
            if (starRating <= rating) {
                $(this).addClass('selected');
            } else {
                $(this).removeClass('selected');
            }
        });
    }
    
    // 重置評論表單
    function resetCommentForm() {
        $('#commentText').val('');
        currentRating = 0;
        updateStars(0, false);
        $('#selected-rating').text('0');
        //$('#comment-success-message').hide();
    }
    
    // 提交評論
    $('#submitComment').click(function() {
        const commentText = $('#commentText').val().trim();
        
        // 驗證評論
        if (currentRating === 0) {
            Swal.fire({
                title: '請先評分',
                text: '請先評分！至少選擇一顆星星',
                icon: 'warning',
                confirmButtonColor: '#00CB96'
            });
            return;
        }
        
        if (commentText === '') {
            Swal.fire({
                title: '請輸入評論',
                text: '請輸入評論內容！',
                icon: 'warning',
                confirmButtonColor: '#00CB96'
            });
            return;
        }
        
        // 準備評論數據
        const commentData = {
            lessonId: lessonId,
            commentText: commentText,
            rating: currentRating,
            creator: cUserId
        };
        
        // 發送評論前再次檢查是否已經評論過
        $.ajax({
            url: "/LessonComment/api/lesson",
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ "lessonId": lessonId }),
            success: function(comments) {
                const hasCommented = comments.some(comment => comment.creator === cUserId);
                
                if (hasCommented) {
                    Swal.fire({
                        title: '已評論',
                        text: '您已經評論過此教案，不能重複評論！',
                        icon: 'info',
                        confirmButtonColor: '#00CB96'
                    });
                    $('#commentModal').modal('hide');
                } else {
                    // 發送評論
                    submitComment(commentData);
                }
            },
            error: function(xhr, status, error) {
                console.error('檢查評論狀態失敗:', error);
                // 如果檢查失敗，仍然允許提交評論
                submitComment(commentData);
            }
        });
    });

    // 實際提交評論的函數
    function submitComment(commentData) {
        $.ajax({
            url: "/LessonComment/api/create",
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(commentData),
            success: function(response) {
                // 假設回傳資料中有新的點數資訊
                if (response && response.currentPoints) {
                    sessionStorage.setItem('pointsUpdated', 'true');
                    sessionStorage.setItem('currentPoints', response.currentPoints);
                } else {
                    // 如果回傳資料中沒有點數資訊，則設置一個標記，讓列表頁主動去獲取
                    sessionStorage.setItem('pointsUpdated', 'true');
                }

                // 顯示成功訊息
                //$('#comment-success-message').show();
                
                // 延遲關閉模態窗口並重新加載評論
                //setTimeout(function() {
                    $('#commentModal').modal('hide');
                    initComments();
                    

                    // 顯示 SweetAlert2 成功提示（比照登入成功方式）
                    let pointsMsg = "感謝您的評論！您已獲得 5 點積分獎勵。";
                    let currentPointsVal = response.currentPoints ? response.currentPoints : 0;

                    Swal.fire({
                        title: '評論成功',
                        html: 
                            '<div class="mt-3 alert alert-success">' +
                                '<i class="fa-solid fa-gift me-2"></i>' + pointsMsg +
                                '<br>' +
                                '<small>當前總積分: ' + currentPointsVal + '</small>' +
                            '</div>',
                        icon: 'success',
                        timer: 2000,
                        timerProgressBar: true,
                        showConfirmButton: false
                    });

                if (response && response.currentPoints) {
                    console.log('currentPoints', response.currentPoints);
                    $('.current-points').text(response.currentPoints);
                }
            },
            error: function(xhr, status, error) {
                // 檢查是否為重複評論錯誤
                if (xhr.responseText && xhr.responseText.includes("duplicate")) {
                    Swal.fire({
                        title: '已評論',
                        text: '您已經評論過此教案，不能重複評論！',
                        icon: 'info',
                        confirmButtonColor: '#00CB96'
                    });
                } else {
                    Swal.fire({
                        title: '評論失敗',
                        text: '評論發送失敗，請稍後再試！',
                        icon: 'error',
                        confirmButtonColor: '#00CB96'
                    });
                }
                console.error('Error posting comment:', error);
            }
        });
    }
}

</script>
<style>

/* 樣式化 loader */
.loader {
    border: 16px solid #f3f3f3;
    /* Light grey */
    border-top: 16px solid #3498db;
    /* Blue */
    border-radius: 50%;
    width: 120px;
    height: 120px;
    animation: spin 2s linear infinite;
    position: fixed;
    /* 固定位置 */
    top: 50%;
    /* 居中 */
    left: 50%;
    transform: translate(-50%, -50%);
    z-index: 1000;
    /* 確保在最前 */
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}



//移除教案試用超連結底線
.lesson-try-link {
    text-decoration: none;
}

.lesson-try:hover {
    background-color: #00CB96;
    color: white;

}

.add-favorites-link:hover .fa-heart {
    color: red;
    font-weight: 900;
    content: "\f004";
}

.add-favorites:hover {
    background-color: #00CB96;
    color: white;
}

.add-favorites-link {
    border: none;
    background-color: white;
}

.lesson-try {
    color: black;
}

.hidden {
    display: none;
}

.index-column {
    width: 5%;
    text-align: center;
    border-right: 1px solid #ddd;
}

.table-bg-color th {
    background-color: rgba(0, 195, 160, 0.2);
}

.achTable th {
    white-space: nowrap;
}

.img-col {
    text-align: center;
}

.intro-age img {
    width: 1.5rem;
    height: 1.5rem;
}

.intro-language {
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    display: inline-block;
    max-width: 20rem;
    /* 根據需要調整最大寬度 */
    vertical-align: middle;
}

.show-more-btn {
    display: inline-block;
    color: blue;
    cursor: pointer;
    margin-left: 10px;
}

.show-more-btn:hover {
    font-weight: bold;
}

.show-more-content {
    display: none;
    position: absolute;
    background: white;
    border: 1px solid #ccc;
    padding: 10px;
    z-index: 1000;
}

.show-intro-btn {
    cursor: pointer;
    border: none;
    background-color: white;
    color: blue;
}

.show-intro-btn:hover {
    font-weight: bold;
}

.left-intro-title {
    font-weight: bold;
}

/* modal */
.modal-content {
    width: auto;
}

.modal {
    --bs-modal-width: 50%
}

.requireTable td:first-child {
    white-space: nowrap;
    font-weight: bold;
}

.right-intro-table td {
    border: none;
}

.right-intro-table td:first-child {
    white-space: nowrap;
    font-weight: bold;
    width: 20%;
}

.caution-table td:first-child {
    white-space: nowrap;
    font-weight: bold;
    width: 20%;
}

.caution-table td {
    border: none;
}

.lesson-intro-table td:first-child {
    white-space: nowrap;
    font-weight: bold;
    width: 20%;
}

.lesson-intro-table td {
    border: none;
}

.no-research {
    text-align: center;
    border: 1px solid #ddd;
}

.no-review {
    background-color: #ccc;
    height: 10rem;
    width: 100%;
    font-weight: bold;
    display: flex;
    justify-content: center;
    /* 水平置中 */
    align-items: center;
    /* 垂直置中 */

}

/* 評論相關樣式 */
/* 評論區塊固定高度與滾動效果 - 精簡版 */
#comments-container {
    max-height: 500px;
    overflow-y: auto;
    border: 1px solid #eee;
    border-radius: 8px;
    padding: 15px;
    padding-top: 0;
    background-color: #fafafa;
}

.comment-form-container {
    background-color: #f8f9fa;
}

.rating-input {
    color: #ccc;
    cursor: pointer;
    font-size: 1.5rem;
    margin-right: 3px;
}

.rating-input.selected {
    color: #ffc107;
}

.rating-input:hover {
    color: #ffc107;
}

.star {
    color: #ffc107;
    margin-right: 3px;
}

.review-rating .empty-star {
    color: #ccc;
    margin-right: 3px;
    font-size: 18px;
}

.fa-star-half-alt {
    color: #ffc107; /* 確保半星也是金色 */
}

.review-container {
    background-color: #fff;
    height: 100%;
}

.review-body {
    font-size: 0.95rem;
}

.review-rating .rating-value {
    font-weight: bold;
}

/* 成功訊息樣式 */
#comment-success-message {
    font-size: 0.9rem;
}

#comment-success-message i {
    color: #28a745;
}

/* 評論模態窗樣式 */
#commentModal .rating-stars {
    font-size: 1.8rem;
}

/* 評論模態窗樣式 - 調整居中和大小 */
#commentModal .modal-dialog {
    max-width: 600px;
    margin: 1.75rem auto; /* 確保水平居中 */
}

#commentModal .modal-content {
    width: 100%; /* 確保內容寬度填滿 dialog */
    margin: 0 auto;
}

.review-header-row {
    margin-bottom: 1rem;
    border-bottom: 1px solid #ddd
}

.review-header .reivew-title {
    margin-bottom: 0; /* 移除可能存在的下邊距 */
    font-weight: bold;
    border-bottom: none;
}

#openCommentModal {
    background-color: #00CB96;
    border-color: #00CB96;
    padding: 4px 8px;
    margin-bottom: 2px;
}

#openCommentModal:hover {
    background-color: #00b085;
    border-color: #00b085;
}

.free-price {
    background-color: #00CB96;
    border: solid #00CB96 2px;
    color: white;
    /*border: 1px solid #ffa9a9;*/
}

.carousel-item video {
    display: block;
    width: 100%;
    height: auto;
    object-fit: cover;
}
/* 讓 Modal 背景半透明黑 */
#previewModal .modal-content {
  background-color: rgba(0,0,0,0.8);
}
#previewModal .modal-body {
  max-height: 90vh;
  overflow: auto;
}
</style>

</html>
