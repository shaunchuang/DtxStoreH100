<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${blogname}<#if subname??>｜${subname}</#if></title>
	<link rel="icon" href="/html/dtxstore/images/dtxstore/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/html/dtxstore/style/lessonQuery/rSlider.min.css">
	<link rel="stylesheet" href="/html/dtxstore/style/fontawesome-6/css/all.min.css">
    <link rel="stylesheet" href="/html/dtxstore/style/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/html/dtxstore/style/login/dtx-login-leftnav.css">
    <link rel="stylesheet" href="/html/dtxstore/style/login/dtx-login-dashboard.css">
    <link rel="stylesheet" href="/html/dtxstore/style/login/dtx-login-header.css">
    <link rel="stylesheet" href="/html/dtxstore/style/lessonQuery/dtx-app.css">
    <script>
    function toggleDropdown() {
        document.getElementById("dropdownMenu").classList.toggle("show");
    }
    
	/*function logout() {
	    Swal.fire({
	        title: '確認是否登出?',
	        text: "確定要登出嗎？",
	        icon: 'warning',
	        showCancelButton: true,
	        confirmButtonColor: '#3085d6',
	        cancelButtonColor: '#d33',
	        confirmButtonText: '確認',
	        cancelButtonText: '取消'
	    }).then((result) => {
	        if (result.isConfirmed) {
	            // 如果按下「確認」，跳轉到登出路徑
	            window.location.href = "/logout";
	        }
	    });
	}*/
	window.onclick = function(event) {
            if (!event.target.matches('.fa-circle-user')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");
                for (var i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }
	</script>
	<style>
		.dropdown-content {
            display: none;
            position: absolute;
            background-color: #f9f9f9;
            min-width: 150px;
            box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            z-index: 1;
            left: -5rem; /* 向左移動 20px */
        }

        .dropdown-content a {
            color: black;
            padding: 12px 16px;
            text-decoration: none;
            display: block;
        }

        .dropdown-content a:hover {
            background-color: #f1f1f1;
        }

        .show {
            display: block;
        }
	</style>
</head>

<body>
    <header>
        <div class="logo">
            <a href="/ftl/dtxstore/dashboard"><img src="/html/dtxstore/images/dtxstore/itri_EL_A.jpg" onclick=homePage() alt="ITRI Logo"></a>
        </div>
        <!--
        <nav class="top-nav">
            <ul>
                <li class="active"><a class="top-nav-title" href="#">首頁</a></li>
                <li>
                    <a class="top-nav-title" href="#">排行榜</a>
                    <ul class="dropdown">
                        <li><a class="top-nav-title" href="#">使用次數最多教案</a></li>
                        <li><a class="top-nav-title" href="#">評論最佳教案</a></li>
                        <li><a class="top-nav-title" href="#">成效最佳教案</a></li>
                        <li><a class="top-nav-title" href="#">最多收藏教案</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        -->
        <div class="search">
            <div class="row">
                <div class="col-xxl-3"></div>
                <div class="col-xxl-9 search-input-container">
                    <!--<i class="fa fa-search search-icon"></i>
                    <input type="text" placeholder="請輸入適應症、教案名稱">-->
                </div>
            </div>
        </div>
        <div class="icons me-5">
            <div class="icon-bell">
                <i class="fa-solid fa-bell fa-2xl"></i>
                <span class="notification-badge"></span>
            </div>
            <div class="icon-user"><i class="fa-solid fa-circle-user fa-2xl" onclick="toggleDropdown()"></i>
            	<div id="dropdownMenu" class="dropdown-content">
            		<a href="/ftl/dtxstore/logout">登出</a>
            	</div>
            </div>
        </div>
        <!--</div>-->
    </header>
