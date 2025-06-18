<#include "../../login/widget/dtx-login-header.ftl" />
<main class="d-flex">
    <#include "../../login/widget/dtx-login-leftnav.ftl" />
    <div class="col-xxl-10 right-container">
        <div class="row mx-4">
	        <div class="point-header">
	            <h5 class="point-title">我的積分紀錄</h5>
	        </div>
            
            <!-- 積分摘要卡片 -->
            <div class="row mt-3">
                <div class="col-md-3">
                    <div class="card points-stat-card shadow-sm">
                        <div class="card-body text-center d-flex flex-column justify-content-center">
                            <i class="fa-solid fa-coins text-warning fa-2x mb-2"></i>
                            <h5 class="card-title">當前積分</h5>
                            <p class="card-text points-value display-6 fw-bold text-warning" id="current-points">${(currentUser.currentPoints)!0}</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card points-stat-card shadow-sm">
                        <div class="card-body text-center d-flex flex-column justify-content-center">
                            <i class="fa-solid fa-plus text-success fa-2x mb-2"></i>
                            <h5 class="card-title">累計獲得</h5>
                            <p class="card-text points-value display-6 fw-bold text-success" id="total-earned">--</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card points-stat-card shadow-sm">
                        <div class="card-body text-center d-flex flex-column justify-content-center">
                            <i class="fa-solid fa-minus text-danger fa-2x mb-2"></i>
                            <h5 class="card-title">累計使用</h5>
                            <p class="card-text points-value display-6 fw-bold text-danger" id="total-used">--</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card points-stat-card shadow-sm">
                        <div class="card-body text-center d-flex flex-column justify-content-center">
                            <i class="fa-solid fa-calendar-day text-primary fa-2x mb-2"></i>
                            <h5 class="card-title">連續登入</h5>
                            <p class="card-text points-value display-6 fw-bold text-primary" id="login-streak">--</p>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- 積分獲取和使用記錄 -->
            <div class="dashboard-block mt-4">
                <div class="dashboard-title m-1">積分記錄</div>
                
                <!-- Tab 切換按鈕 -->
                <ul class="nav nav-tabs" id="pointsTab" role="tablist">
                    <li class="nav-item" role="presentation">
                        <button class="nav-link active" id="earnings-tab" data-bs-toggle="tab" data-bs-target="#earnings-pane" type="button" role="tab">獲得記錄</button>
                    </li>
                    <li class="nav-item" role="presentation">
                        <button class="nav-link" id="usages-tab" data-bs-toggle="tab" data-bs-target="#usages-pane" type="button" role="tab">使用記錄</button>
                    </li>
                </ul>
                
                <!-- Tab 內容 -->
                <div class="tab-content" id="pointsTabContent">
                    <!-- 獲得積分記錄 -->
                    <div class="tab-pane fade show active p-3" id="earnings-pane" role="tabpanel" tabindex="0">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>日期時間</th>
                                        <th>描述</th>
                                        <th>獲得積分</th>
                                        <th>剩餘積分</th>
                                        <th>過期日期</th>
                                    </tr>
                                </thead>
                                <tbody id="earnings-table-body">
                                    <tr>
                                        <td colspan="6" class="text-center">載入中...</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <nav aria-label="積分分頁" class="mt-3">
                            <ul class="pagination justify-content-center" id="earnings-pagination">
                            </ul>
                        </nav>
                    </div>
                    
                    <!-- 使用積分記錄 -->
                    <div class="tab-pane fade p-3" id="usages-pane" role="tabpanel" tabindex="0">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>日期時間</th>
                                        <th>用途</th>
                                        <th>描述</th>
                                        <th>使用積分</th>
                                    </tr>
                                </thead>
                                <tbody id="usages-table-body">
                                    <tr>
                                        <td colspan="4" class="text-center">載入中...</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <nav aria-label="積分使用分頁" class="mt-3">
                            <ul class="pagination justify-content-center" id="usages-pagination">
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
            
            <!-- 積分獲取方式說明 -->
            <div class="dashboard-block mt-4">
                <div class="dashboard-title m-1">獲取積分方式</div>
                <div class="p-3">
                    <div class="row">
                        <div class="col-md-4 mb-3">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title"><i class="fa-solid fa-calendar-check text-primary me-2"></i>每日登入</h5>
                                    <p class="card-text">每天首次登入系統可獲得 10 點積分</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title"><i class="fa-solid fa-upload text-success me-2"></i>上傳教案</h5>
                                    <p class="card-text">成功上傳教案後可獲得 50 點積分</p>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4 mb-3">
                            <div class="card">
                                <div class="card-body">
                                    <h5 class="card-title"><i class="fa-solid fa-star text-warning me-2"></i>教案評分</h5>
                                    <p class="card-text">為教案提供評分和評論可獲得 5 點積分</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<script src="/html/dtxstore/script/commonPage/popper.js"></script>
<script src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
<script src="/html/dtxstore/script/commonPage/jQuery.js"></script>
<script src="/html/dtxstore/script/commonPage/leftnav.js"></script>
<!-- 引入積分記錄相關腳本 -->
<script src="/html/dtxstore/script/points/pointsHistory.js"></script>

<!-- 在頁面底部添加內嵌樣式，確保我們的樣式在所有CSS載入後應用 -->
<style>
.point-title {
    font-size: 1.5rem;
    font-weight: bold;
    color:#00CB96
}

.points-stat-card {
    max-height: 15rem; /* 減少最大高度 */
}
.points-stat-card .card-body {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 1.2rem 1.5rem 0.8rem; /* 減少底部內邊距 */
}

.points-stat-card .points-value {
    font-size: 2.2rem; /* 調整字體大小 */
    font-weight: 700;
    line-height: 1;
    margin: 0 0 0.2rem 0; /* 減少底部間距 */
    padding: 0;
}

.points-stat-card .card-title {
    font-size: 1.1rem;
    margin-bottom: 0.3rem; /* 減少底部間距 */
}

.points-stat-card .fa-2x {
    margin-bottom: 0.5rem; /* 減少底部間距 */
}

/* 調整display-6類的大小，以減少其在card中的間距 */
.points-stat-card .display-6 {
    font-size: 1.8rem;
    line-height: 1;
}
</style>

</body>
</html>
