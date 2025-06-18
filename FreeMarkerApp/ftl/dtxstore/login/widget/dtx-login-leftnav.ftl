<#assign isAdmin = false>
        <#list currentUser.roles as role>
            <#if role.id == 2>
                <#assign isAdmin = true>
            </#if>
        </#list>
        <#setting number_format="#,##0">
        <div class="col-xxl-2 sidebar">
            <div class="profile">
                <div class="row">
                    <div class="col-md-4 img-area"><img src="/html/dtxstore/images/dtxstore/avatar.png" alt="Profile Picture"
                            class="img-fluid rounded-circle">
                    </div>
                    <div class="col-md-8 profile-text">
                        <h3>${currentUser.username} 使用者</h3>
                        <p>${currentUser.email}</p>
                        <div class="points-display">
                            <i class="fas fa-coins text-warning me-2"></i>
                            <span class="current-points">${ (currentUser.currentPoints)!0?string["#,##0"] }</span> 點數
                        </div>
                    </div>
                </div>
            </div>
            <nav class="leftnav">
                <ul class="list-unstyled">
                    <li>
                        <a class="leftnav-title" href="#queryLesson">教案查詢 <i
                                class="fas fa-chevron-down"></i></a>
                        <ul id="queryLesson" class="list-unstyled leftnav-dropdown" style="display: none;">
                            <li><a class="leftnav-list-text" href="/ftl/dtxstore/query">教案篩選列表</a></li>
                            <li><a class="leftnav-list-text" href="/ftl/dtxstore/library">我的收藏</a></li>
                        </ul>
                    </li>
                    <hr>
                    <li>
                        <a class="leftnav-title" href="#manageLesson">教案管理 <i
                                class="fas fa-chevron-down"></i></a>
                        <ul id="manageLesson" class="list-unstyled leftnav-dropdown" style="display: none;">
                            <li><a class="leftnav-list-text" href="/ftl/dtxstore/manage/list">我的教案總表</a></li>
                            <li><a class="leftnav-list-text" href="/ftl/dtxstore/manage/upload">教案上架</a></li>
                            <li><a class="leftnav-list-text" href="#">教案下架</a></li>
                            <#if isAdmin>
                            <li><a class="leftnav-list-text" href="/ftl/dtxstore/manage/list">教案編輯</a></li>
                            <li><a class="leftnav-list-text" href="/ftl/dtxstore/manage/SteamAppImport">新增Steam教案</a></li>
                            </#if>
                        </ul>
                    </li>
                    <hr>
                    <li>
                        <a class="leftnav-title" href="#developLesson">教案開發 <i class="fas fa-chevron-down"></i></a>
                        <ul id="developLesson" class="list-unstyled leftnav-dropdown" style="display: none;">
                        	<li><a class="leftnav-list-text" href="/ftl/dtxstore/develop/scratch">新增Scratch教案</a></li>
                            <li><a class="leftnav-list-text" href="#">教案開發說明</a></li>
                            <li><a class="leftnav-list-text" href="#">設計教案</a></li>
                        </ul>
                    </li>
                    <hr>
                    <li>
                        <a class="leftnav-title" href="#myAccount">我的帳務 <i
                                class="fas fa-chevron-down"></i></a>
                        <ul id="myAccount" class="list-unstyled leftnav-dropdown dn" style="display: none;">
                            <li><a class="leftnav-list-text" href="/ftl/dtxstore/account/pointhistory">積分記錄</a></li>
                            <li><a class="leftnav-list-text" href="#">交易紀錄</a></li>
                            <li><a class="leftnav-list-text" href="#">交易設定</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>