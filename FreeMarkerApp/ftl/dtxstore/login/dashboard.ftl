<#include "../login/widget/dtx-login-header.ftl" />
	<main class="d-flex">
       <#include "../login/widget/dtx-login-leftnav.ftl" />
        <div class="col-xxl-10 right-container">
            <div class="row mx-4">
                <div class="notify-block">
                    <i class="fa-solid fa-circle-exclamation"></i>
                    <span class="notify-text">教案 1 Minecraft 已審核完畢，成功上架!</span>
                </div>
            </div>
            <div class="dashboard-block">
                <div class="dashboard-title m-1">教案管理</div>
                <div class="row" style="flex-wrap: nowrap;">
                    <div class="col-md-6 manage-table-area m-2">
                        <table class="table table-bordered manage-table">
                            <thead>
                                <tr>
                                    <th class="id-td">ID</th>
                                    <th class="lesson-td">教案名稱</th>
                                    <th class="status-td">狀態</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="id-td">1</td>
                                    <td class="lesson-td">Minecraft</td>
                                    <td class="status-td"><span class="badge badge-success">已上架</span></td>
                                </tr>
                                <tr>
                                    <td class="id-td">2</td>
                                    <td class="lesson-td">Portal 2</td>
                                    <td class="status-td"><span class="badge badge-warning">審核中</span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <div class="d-flex">
                            <div class="col-md-4 dash-square mx-3 px-2">
                                <i class="fa-solid fa-envelope-open-text icon-color m-2 fa-xl"></i>
                                <div class="stat-num m-2">1</div>
                                <div class="stat-text m-2">待審核教案</div>
                            </div>
                            <div class="col-md-4 dash-square mx-3 px-2">
                                <i class="fa-regular fa-pen-to-square icon-color m-2 fa-xl"></i>
                                <div class="stat-num m-2">1</div>
                                <div class="stat-text m-2">待修改教案</div>
                            </div>
                            <div class="col-md-4 dash-square mx-3 px-2">
                                <i class="fa-solid fa-cloud-arrow-up icon-color m-2 fa-xl"></i>
                                <div class="stat-num m-2">1</div>
                                <div class="stat-text m-2">已上架教案</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dashboard-block">
                <div class="dashboard-title m-1">數據分析</div>
                <div class="row justify-content-around">
                    <div class="col-md-6">
                        <canvas id="stat-chart" class="stat-chart p-2 m-2"></canvas>
                    </div>
                    <div class="col-md-6">

                        <div class="row">
                            <div class="dash-square mx-4 mb-4">
                                <i class="fa-solid fa-book icon-color m-2 fa-xl"></i>
                                <div class="stat-num m-2">32</div>
                                <div class="stat-text m-2">教案配發次數</div>
                            </div>
                            <div class="dash-square mx-4 mb-4">
                                <i class="fa-regular fa-circle-down icon-color m-2 fa-xl"></i>
                                <div class="stat-num m-2">39</div>
                                <div class="stat-text m-2">教案被下載次數</div>
                            </div>
                            <div class="row">
                                <div class="dash-square m-4">
                                    <i class="fa-solid fa-warehouse icon-color m-2 fa-xl"></i>
                                    <div class="stat-num m-2">28</div>
                                    <div class="stat-text m-2">教案被收藏數</div>
                                </div>
                                <div class="dash-square m-4">
                                    <i class="fa-regular fa-star icon-color m-2 fa-xl"></i>
                                    <div class="stat-num m-2">4.6</div>
                                    <div class="stat-text m-2">教案平均評分</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="lb-tab-area">
                    <div class="lb-tab active lb-border">使用次數最多教案</div>
                    <div class="lb-tab lb-border">評論最佳教案</div>
                    <div class="lb-tab lb-border">成效最佳教案</div>
                    <div class="lb-tab">最多收藏教案</div>
                </div>
                <div class="lb-deck px-5 pb-3">
                    <div class="card mx-3">
                        <img src="https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/992960/header.jpg?t=1552021871" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title">A Lullaby of Colors VR</h5>
                            <div class="rating">
                                <span class="rating-number">4.2</span>
                                <div class="stars">
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9734;</span>
                                </div>
                                <span class="rating-count">(28)</span>
                            </div>
                            <p class="card-text">Blissful psychedelic relaxation experience for VR. Fly through breathtakingly beautiful worlds of endless sweet-tasting colors and gentle sounds. Great for relaxing, open-eyed meditation, or jaw-dropping drug-free tripping.</p>
                        </div>
                    </div>
                    <div class="card mx-3">
                        <img src="https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1672970/header.jpg?t=1717003107" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title">Minecraft</h5>
                            <div class="rating">
                                <span class="rating-number">4.2</span>
                                <div class="stars">
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9734;</span>
                                </div>
                                <span class="rating-count">(15)</span>
                            </div>
                            <p class="card-text">在全新的動作冒險遊戲中全力奮戰，遊戲靈感來自經典的「深入地牢」，也是 Minecraft 宇宙中的新成員！</p>
                        </div>
                    </div>
                    <div class="card mx-3">
                        <img src="https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/1245620/header.jpg?t=1721682743" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title">WiseMind</h5>
                            <div class="rating">
                                <span class="rating-number">4.3</span>
                                <div class="stars">
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9734;</span>
                                </div>
                                <span class="rating-count">(21)</span>
                            </div>
                            <p class="card-text">Relax your mind and body with WiseMind, a series of science and evidence based trainings built in virtual reality by scientists and therapists. Develop a healthier self, starting today!</p>
                        </div>
                    </div>
                    <div class="card mx-3">
                        <img src="https://shared.akamai.steamstatic.com/store_item_assets/steam/apps/2217730/header.jpg?t=1672270776" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title">theBlu</h5>
                            <div class="rating">
                                <span class="rating-number">4.5</span>
                                <div class="stars">
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9734;</span>
                                </div>
                                <span class="rating-count">(53)</span>
                            </div>
                            <p class="card-text">Experience the wonder and majesty of the ocean through a series of habitats and come face to face with some of the most awe inspiring species on the planet.</p>
                        </div>
                    </div>
                    <div class="card mx-3">
                        <img src="/html/dtxstore/images/dtxstore/lesson-title.jpg" class="card-img-top" alt="...">
                        <div class="card-body">
                            <h5 class="card-title">Portal 2</h5>
                            <div class="rating">
                                <span class="rating-number">4.7</span>
                                <div class="stars">
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9733;</span>
                                    <span class="star">&#9734;</span>
                                </div>
                                <span class="rating-count">(35)</span>
                            </div>
                            <p class="card-text">「永久測試行動計畫」已經擴大，您現在可以設計合作遊戲謎題給您和您的好友！</p>
                        </div>
                    </div>
                </div>
            </div>
    </main>
    <script src="/html/dtxstore/script/commonPage/popper.js"></script>
    <script src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script src="/html/dtxstore/script/commonPage/leftnav.js"></script>
    <script src="/html/dtxstore/script/commonPage/chart.js"></script>
    <script src="/html/dtxstore/script/login/dashboard.js"></script>
</body>
</html>
<script>
</script>