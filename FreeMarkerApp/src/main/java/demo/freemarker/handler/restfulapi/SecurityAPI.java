/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;

import demo.freemarker.api.UserAPI;
import demo.freemarker.api.UserRoleAPI;
import demo.freemarker.core.PointUtil;
import demo.freemarker.core.SecurityUtils;
import demo.freemarker.model.PointAllocation;
import demo.freemarker.model.User;
import itri.sstc.framework.core.api.RESTfulAPI;
import itri.sstc.framework.core.httpd.session.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class SecurityAPI extends RESTfulAPI {

    public SecurityAPI() {
    }

    @Override
    public String getContextPath() {
        return "/security/api/";
    }

    @RESTfulAPIDefine(url = "login", methods = "post", description = "登入")
    private String doLogin(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);

        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject(requestBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return null;
        }

        if (!jsonRequest.has("account") || jsonRequest.getString("account").equals("") ||
                !jsonRequest.has("password") || jsonRequest.getString("password").equals("") ||
                !jsonRequest.has("captcha") || jsonRequest.getString("captcha").equals("")) {
            responseJson.put("message", "請輸入帳號、密碼和驗證碼");
            responseJson.put("success", Boolean.FALSE);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseJson.toString();
        }

        String account = jsonRequest.getString("account").trim().toLowerCase();
        String password = jsonRequest.getString("password").trim();
        String captchaInput = jsonRequest.getString("captcha").trim();

        HttpSession session = getSession(exchange);
        String storedCaptcha = (String) session.getAttribute("captcha");

        if (storedCaptcha == null || !storedCaptcha.equals(captchaInput)) {
            responseJson.put("message", "驗證碼錯誤");
            responseJson.put("success", Boolean.FALSE);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseJson.toString();
        }

        User user = UserAPI.getInstance().getUserByAccount(account);
        if (user == null) {
            responseJson.put("message", "用戶不存在");
            responseJson.put("success", Boolean.FALSE);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseJson.toString();
        }
        Boolean isValid = SecurityUtils.verifyPassword(password, user.getPassword());

        if (!isValid) {
            System.out.println("密碼錯誤: " + account);
            responseJson.put("message", "密碼錯誤");
            responseJson.put("success", Boolean.FALSE);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseJson.toString();
        }

        // 檢查今天是否是第一次登入
        boolean isFirstLoginToday = UserAPI.getInstance().isFirstLoginOfDay(user.getId());
        
        // 設置獎勵積分標誌
        Long awardedPoints = 0L;
        
        // 如果是今天第一次登入，發放獎勵積分
        if (isFirstLoginToday) {
            System.out.println("今天第一次登入，發放獎勵積分");
            PointAllocation allocation = PointUtil.getInstance().awardDailyLoginPoints(user.getId());
            if (allocation != null) {
                awardedPoints = allocation.getPoints();
                responseJson.put("dailyLoginPoints", awardedPoints);
                responseJson.put("dailyLoginMessage", "恭喜獲得" + awardedPoints + "點每日登入獎勵！");
            } else {
                responseJson.put("dailyLoginPoints", 0);
            }
        } else {
            // 今天已經登入過，不再獎勵積分
            responseJson.put("dailyLoginPoints", 0);
        }

        // 更新最後登入日期
        UserAPI.getInstance().updateLastLoginDate(user.getId());
        User updatedUser = UserAPI.getInstance().getUser(user.getId());
        // 設置當前用戶並清除驗證碼
        SecurityUtils.setCurrentUser(session, "DTXSTORE", user);
        session.setAttribute("captcha", null);
        System.out.println("currentPoints：" + updatedUser.getCurrentPoints());
        responseJson.put("message", "登入成功");
        responseJson.put("success", Boolean.TRUE);
        responseJson.put("currentPoints", updatedUser.getCurrentPoints());
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseJson.toString();
    }

    @RESTfulAPIDefine(url = "register", methods = "post", description = "註冊")
    private String doRegister(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);

        // 解析 JSON
        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject(requestBody.toString());
            User user = new User();
            String account = jsonRequest.getString("account").trim().toLowerCase();
            String username = jsonRequest.getString("username").trim();
            String password = jsonRequest.getString("password").trim();
            String email = jsonRequest.getString("email").trim();
            String telCell = jsonRequest.getString("telCell").trim();
            user.setUsername(username);
            String hashedPass = SecurityUtils.hashPassword(password);
            user.setPassword(hashedPass);
            user.setAccount(account);
            user.setTelCell(telCell);
            user.setEmail(email);
            user.setCurrentPoints(0L);
            user.setCreatedTime(new Date());
            user.setStatus(User.UserStatus.APPROVED);
            UserAPI.getInstance().createUser(user);

            System.out.println("UserId" + user.getId());
            UserRoleAPI.getInstance().createUserRole(user.getId(), 1L);

        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            responseJson.put("message", "註冊失敗");
            responseJson.put("success", Boolean.FALSE);
            return null;
        }
        // 登入成功回應
        responseJson.put("message", "註冊成功");
        responseJson.put("success", Boolean.TRUE);

        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseJson.toString();
    }

    @RESTfulAPIDefine(url = "captcha", methods = "get", description = "取得圖片驗證碼")
    private String generateCaptcha(HttpExchange exchange) throws IOException {
        HttpSession session = getSession(exchange);
        String captcha = generateNumericCaptcha(4);
        session.setAttribute("captcha", captcha);

        // 產生驗證碼圖片
        int width = 100, height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 設定背景顏色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 設定字型
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.setColor(Color.BLACK);

        // 隨機干擾線
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            g.drawLine(rand.nextInt(width), rand.nextInt(height), rand.nextInt(width), rand.nextInt(height));
        }

        // 繪製驗證碼
        g.drawString(captcha, 20, 28);
        g.dispose();

        // 設定回應標頭
        exchange.getResponseHeaders().set("Content-Type", "image/png");
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

        // 回傳圖片
        try (OutputStream os = exchange.getResponseBody()) {
            ImageIO.write(image, "png", os);
        }
        return null;
    }

    private String generateNumericCaptcha(int length) {
        Random random = new Random();
        int min = (int) Math.pow(10, length - 1);
        int max = (int) Math.pow(10, length) - 1;
        return String.valueOf(random.nextInt(max - min + 1) + min);
    }

}
