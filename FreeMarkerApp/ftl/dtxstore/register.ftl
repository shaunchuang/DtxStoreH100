<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DTx 教案市集上架平台 - 註冊</title>
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
    .register-container {
        width: 90%; /* 適應小螢幕 */
        max-width: 70%;
        margin: 0 auto;
        padding: 20px; /* 減小固定 padding 以適應小螢幕 */
        background: white;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .register-container h1 {
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

    .btn-register {
        background-color: #50a3a2;
        border: none;
        color: white;
        font-weight: 600;
    }

    .btn-bl {
        display:flex;
        justify-content: center;
    }

    .btn-register:hover {
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
                <a href="/ftl/dtxstore/login"><button class="login">登入</button></a>
                <!--<a href="/register"><button class="register">註冊</button></a>-->
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

<!-- 註冊表單 -->
    <div class="d-flex justify-content-center align-items-center flex-grow-1">
        <div class="register-container">
            <h1>DTx 教案市集上架平台 - 註冊</h1>
            <form id="register-form">
                <div class="row">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="username" class="form-label">姓名</label>
                            <input type="text" id="username" name="username" class="form-control" placeholder="請輸入姓名" required>
                        </div>
                        <div class="mb-3">
                            <label for="account" class="form-label">帳號名稱</label>
                            <input type="text" id="account" name="account" class="form-control" placeholder="請輸入帳號名稱" required>
                        </div>
                        <div class="mb-3">
                            <label for="email" class="form-label">電子郵件</label>
                            <input type="email" id="email" name="email" class="form-control" placeholder="請輸入電子郵件" required>
                        </div>
                        <div class="mb-3">
                            <label for="telCell" class="form-label">手機號碼</label>
                            <input type="tel" id="telCell" name="telCell" class="form-control" placeholder="請輸入10位手機號碼" pattern="[0-9]{10}" required>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="password" class="form-label">密碼</label>
                                <input type="password" id="password" name="password" class="form-control" placeholder="請輸入密碼" required>
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">確認密碼</label>
                            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" placeholder="請再次輸入密碼" required>
                        </div>
                        <div class="mb-3">
                            <label for="captcha" class="form-label">驗證碼</label>
                            <div class="d-flex align-items-center">
                                <img id="validateCodeImg" src="/security/api/captcha" alt="captcha" class="me-2" style="cursor: pointer;" onclick="reloadCaptcha()">
                                <i class="fa-solid fa-arrows-rotate text-primary" style="cursor: pointer;" onclick="reloadCaptcha()"></i>
                                <input type="text" id="captcha" name="captcha" class="form-control ms-2" placeholder="請輸入驗證碼">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btn-bl">
                    <button type="button" id="register-btn" class="btn btn-register w-25">註冊</button>
                </div>
            </form>
            <p class="text-center mt-3">
                已有帳號？<a href="/ftl/dtxstore/login" class="text-primary">立即登入</a>
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
    <script src="/html/dtxstore/script/commonPage/jQuery.js"></script>
    <script type="text/javascript" src="/html/dtxstore/script/commonPage/swal2.js"></script>
    <script>
    function reloadCaptcha() {
        document.getElementById("validateCodeImg").src = "/security/api/captcha?" + new Date().getTime();
    }

    $("#register-btn").on("click", function() {
        const username = $("#username").val().trim();
        const account = $("#account").val().trim();
        const email = $("#email").val().trim();
        const telCell = $("#telCell").val().trim();
        const password = $("#password").val().trim();
        const confirmPassword = $("#confirmPassword").val().trim();
        const captcha = $("#captcha").val().trim();

        console.log("收到資訊");

        // **檢查是否有未填寫的欄位**
        if (!username) {
            Swal.fire({ title: "姓名未填寫", text: "請輸入您的姓名", icon: "warning" });
            return;
        }
        if (!account) {
            Swal.fire({ title: "帳號未填寫", text: "請輸入帳號名稱", icon: "warning" });
            return;
        }
        if (!email) {
            Swal.fire({ title: "電子郵件未填寫", text: "請輸入電子郵件", icon: "warning" });
            return;
        }
        if (!telCell) {
            Swal.fire({ title: "手機號碼未填寫", text: "請輸入您的手機號碼", icon: "warning" });
            return;
        }
        if (!password) {
            Swal.fire({ title: "密碼未填寫", text: "請輸入密碼", icon: "warning" });
            return;
        }
        if (!confirmPassword) {
            Swal.fire({ title: "確認密碼未填寫", text: "請再次輸入密碼", icon: "warning" });
            return;
        }

        // **檢查密碼是否一致**
        if (password !== confirmPassword) {
            Swal.fire({ title: "密碼不一致", text: "兩次輸入的密碼不一致！", icon: "error" });
            return;
        }

        // **發送 AJAX 請求**
        $.ajax({
            url: "/security/api/register",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ username, account, email, telCell, password, captcha }),
            dataType: "json",
            success: function(data) {
                if (data.success) {
                    Swal.fire({
                        title: "註冊成功",
                        text: "正在跳轉至登入頁面...",
                        icon: "success",
                        timer: 2000,  // **自動關閉 SweetAlert**
                        showConfirmButton: false
                    }).then(() => {
                        window.location.href = "/ftl/dtxstore/login"; // 跳轉至登入頁面
                    });
                } else {
                    Swal.fire({ title: "註冊失敗", text: data.message, icon: "error" }).then(() => {
                        $("#captcha").val('');
                        reloadCaptcha();
                    });;
                }
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
                Swal.fire({ title: "註冊失敗", text: "請檢查輸入資訊或稍後再試。", icon: "error" });
            }
        });
    });


    </script>

    <script type="text/javascript" src="/html/dtxstore/style/bootstrap-5.3.3-dist/js/bootstrap.min.js"></script>
</body>
</html>
