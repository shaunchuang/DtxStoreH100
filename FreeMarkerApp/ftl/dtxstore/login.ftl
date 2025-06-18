<!DOCTYPE html>
<html lang="zh-TW">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DTx 教案市集上架平台 - 登入</title>
    <link rel="icon" href="/html/dtxstore/images/dtxstore/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/html/dtxstore/style/bootstrap-5.3.3-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="/html/dtxstore/style/fontawesome-6/css/all.min.css">
    <link rel="stylesheet" href="/html/dtxstore/style/unlogin/dtx-unlogin-header.css">
<style>
    /* 全局設置 */
    body {
        min-height: 100vh;
        display: flex;
        flex-direction: column;
        background: linear-gradient(to bottom right, #50a3a2 0%, #53e3a6 100%);
        margin: 0;
    }

    /* 登入容器 */
    .login-container {
        max-width: 100%;
        width: 90%; /* 適應小螢幕 */
        max-width: 400px;
        margin: 0 auto;
        padding: 20px; /* 減小固定 padding 以適應小螢幕 */
        background: white;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .login-container h1 {
        font-size: 1.5rem; /* 調整標題字體大小 */
        font-weight: 600;
        margin-bottom: 15px;
        text-align: center;
        color: #333;
    }

    /* 表單控制 */
    .form-control {
        border-radius: 5px;
        padding: 10px; /* 更加適應小螢幕 */
    }

    .btn-login {
        background-color: #50a3a2;
        border: none;
        color: white;
        font-weight: 600;
    }

    .btn-login:hover {
        background-color: #43d3a5;
    }

    /* 背景泡泡動畫 */
    .bg-bubbles {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: -1;
        overflow: hidden;
    }

    .bg-bubbles li {
        position: absolute;
        list-style: none;
        display: block;
        width: 80px; /* 調整大小以適應小螢幕 */
        height: 80px;
        background-size: cover;
        bottom: -120px;
        border-radius: 50%;
        animation: move 25s infinite;
        opacity: 0.5;
    }
    
    .bg-bubbles li:nth-child(1) {
	        left: 10%;
	        animation-delay: 0s;
	        background-image: url('/html/dtxstore/images/dtxstore/lesson/lesson1.jpg');
	    }

	    .bg-bubbles li:nth-child(2) {
	        left: 20%;
	        animation-delay: 2s;
	        animation-duration: 12s;
	        background-image: url('/html/dtxstore/images/dtxstore/lesson/lesson2.jpg');
	    }

	    .bg-bubbles li:nth-child(3) {
	        left: 25%;
	        animation-delay: 4s;
	        background-image: url('/html/dtxstore/images/dtxstore/lesson/lesson3.jpg');
	    }

	    .bg-bubbles li:nth-child(4) {
	        left: 40%;
	        animation-delay: 0s;
	        animation-duration: 18s;
	        background-image: url('/html/dtxstore/images/dtxstore/lesson/lesson4.jpg');
	    }

	    .bg-bubbles li:nth-child(5) {
	        left: 70%;
	        animation-delay: 1s;
	        background-image: url('/html/dtxstore/images/dtxstore/lesson/lesson5.jpg');
	    }

	    .bg-bubbles li:nth-child(6) {
	        left: 80%;
	        animation-delay: 3s;
	        animation-duration: 17s;
	        background-image: url('/html/dtxstore/images/dtxstore/lesson/lesson6.jpg');
	    }

	    .bg-bubbles li:nth-child(7) {
	        left: 90%;
	        animation-delay: 2s;
	        background-image: url('/html/dtxstore/images/dtxstore/lesson/lesson7.jpg');
	    }

    @keyframes move {
        0% {
            transform: translateY(0) scale(1);
        }
        100% {
            transform: translateY(-800px) scale(1.2);
        }
    }

    /* Footer 設計 */
    footer {
        z-index: 10;
        position: relative;
        background: #f8f9fa;
        padding: 15px 0;
    }

    footer .container {
        text-align: center;
    }

    footer a {
        text-decoration: none;
        color: #6c757d;
    }

    footer a:hover {
        text-decoration: underline;
    }

    /* 調整字體大小以適應小螢幕 */
    @media (max-width: 576px) {
        .login-container {
            padding: 15px;
        }

        .login-container h1 {
            font-size: 1.2rem;
        }

        .form-control {
            font-size: 14px;
        }

        .btn-login {
            font-size: 14px;
            padding: 10px;
        }

        .bg-bubbles li {
            width: 60px; /* 更小的泡泡 */
            height: 60px;
        }

        footer {
            padding: 10px 0;
        }

        footer a {
            font-size: 14px;
        }
    }
</style>

</head>
<body>
    <!-- 頂部 Header -->
    <header>
        <div class="container">
            <div class="logo">
                <a href="/ftl/dtxstore/query"><img src="/html/dtxstore/images/dtxstore/itri_EL_A.jpg" alt="ITRI Logo"></a>
            </div>
            <div class="header-nav-bar">
                <nav>
                    <ul>
                        <li><a href="#"></a></li>
                    </ul>
                </nav>
            </div>
            <div class="auth-buttons">
                <!--<a href="/ftl/dtxstore/login"><button class="login">登入</button></a>-->
                <a href="/ftl/dtxstore/register"><button class="register">註冊</button></a>
            </div>
        </div>
    </header>

    <!-- 背景泡泡 -->
    <ul class="bg-bubbles">
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
        <li></li>
    </ul>

    <div class="d-flex justify-content-center align-items-center flex-grow-1">
        <div class="login-container">
            <h1>DTx 教案市集上架平台</h1>
            
            <!-- 錯誤訊息區域 -->
    		<div id="error-message" class="alert alert-danger d-none" role="alert"></div>
            
            <form id="login-form">
                <div class="mb-3">
                    <label for="account" class="form-label">帳號</label>
                    <input type="text" id="account" name="account" class="form-control" placeholder="請輸入帳號" required>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">密碼</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="請輸入密碼" required>
                </div>
                <div class="mb-3">
                    <label for="captcha" class="form-label">驗證碼</label>
                    <div class="d-flex align-items-center">
                        <img id="validateCodeImg" src="/security/api/captcha" alt="captcha" class="me-2" style="cursor: pointer;" onclick="reloadCaptcha()">
                        <i class="fa-solid fa-arrows-rotate text-primary" style="cursor: pointer;" onclick="reloadCaptcha()"></i>
                        <input type="text" id="captcha" name="captcha" class="form-control ms-2" placeholder="請輸入驗證碼" required>
                    </div>
                </div>
                <button type="button" id="login-btn" class="btn btn-login w-100">登入</button>
            </form>
            <p class="text-center mt-3">
                還沒有帳號？<a href="/ftl/dtxstore/register" class="text-primary">立即註冊</a>
            </p>
        </div>
    </div>

    <!-- Footer -->
    <footer class="bg-light text-center text-lg-start mt-auto">
        <div class="container">
            <p class="mb-0">
                <b>版本 2.0</b>
            </p>
            <p class="mb-0">
                <a href="https://www.itri.org.tw/" target="_blank" class="text-muted">ITRI</a>. 版權所有 © 2024-2027
            </p>
        </div>
    </footer>
    <script type="text/javascript" src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
    <script src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/swal2.js"></script>
    <script>
    function reloadCaptcha() {
        document.getElementById("validateCodeImg").src = "/security/api/captcha?" + new Date().getTime();
    }

    $("#login-btn").on("click", function() {
        $.ajax({
            url: "/security/api/login",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ 
                account: $("#account").val().trim(), 
                password: $("#password").val().trim(), 
                captcha: $("#captcha").val().trim() 
            }),
            dataType: "json",
            success: function(data) {

                if (data.success) {
                    // 檢查是否獲得了每日登入積分獎勵
                    if (data.dailyLoginPoints && data.dailyLoginPoints > 0) {
                        // 顯示獲得積分的提醒
                        let pointsMsg = data.dailyLoginMessage ? data.dailyLoginMessage : '恭喜獲得每日登入獎勵！';
                        let currentPointsVal = data.currentPoints ? data.currentPoints : 0;
                        
                        Swal.fire({
                            title: '登入成功',
                            html: 
                                '<div class="mt-3 alert alert-success">' +
                                    '<i class="fa-solid fa-gift me-2"></i>' +
                                    pointsMsg + '<br>' +
                                    '<small>當前總積分: ' + currentPointsVal + '</small>' +
                                '</div>',
                            icon: 'success',
                            timer: 2000,
                            timerProgressBar: true,
                            showConfirmButton: false
                        }).then(() => {
                            window.location.href = "/ftl/dtxstore/dashboard";
                        });
                    } else {
                        // 普通登入成功的提示
                        Swal.fire({
                            title: data.message,
                            text: '1秒後自動跳轉...',
                            icon: 'success',
                            timer: 1000,
                            showConfirmButton: false
                        }).then(() => {
                            window.location.href = "/ftl/dtxstore/dashboard";
                        });
                    }
                } else {
                    Swal.fire({
                        title: "登入失敗",
                        text: data.message,
                        icon: 'error',
                   }).then(() => {
                        $("#account").val('');
                        $("#password").val('');
                        $("#captcha").val('');
                        reloadCaptcha();
                    });
                }
            },
            error: function(xhr, status, error) {
                    console.log("xhr", xhr);
                    console.log("status", status);
                    console.log("error", error);
                   Swal.fire({
                        title: "登入失敗",
                        icon: 'error',
                   });
            }
        });
    });

    // 監聽輸入框的鍵盤事件
    $("#login-form input").on("keydown", function(event) {
        if (event.key === "Enter") {
            event.preventDefault(); // 防止表單的默認提交行為
            $("#login-btn").click(); // 觸發登入按鈕的點擊事件
        }
    });
    </script>

	<style>
	.alert {
	    margin-top: 10px;
	}

	</style>

</body>
</html>
