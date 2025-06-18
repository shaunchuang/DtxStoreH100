/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.ConfigPropertyAPI;
import demo.freemarker.api.DtxTagAPI;
import demo.freemarker.api.GoogleTranslateAPI;
import demo.freemarker.api.LessonAchievementAPI;
import demo.freemarker.api.LessonImageAPI;
import demo.freemarker.api.LessonMainInfoAPI;
import demo.freemarker.api.LessonNewsAPI;
import demo.freemarker.api.LessonStatisticsAPI;
import demo.freemarker.api.LessonSystemRequirementAPI;
import demo.freemarker.api.LessonTagAPI;
import demo.freemarker.core.HttpClientUtil;
import demo.freemarker.model.DtxTag;
import demo.freemarker.model.LessonAchievement;
import demo.freemarker.model.LessonImage;
import demo.freemarker.model.LessonMainInfo;
import demo.freemarker.model.LessonNews;
import demo.freemarker.model.LessonStatistics;
import demo.freemarker.model.LessonSystemRequirement;
import demo.freemarker.model.LessonSystemRequirement.RequirementType;
import demo.freemarker.model.LessonTag;
import demo.freemarker.model.LessonImage.LessonImageType;
import itri.sstc.framework.core.api.RESTfulAPI;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author zhush
 */
public class SteamAPI extends RESTfulAPI {

    private static final String STEAM_DETAIL_URL = "https://store.steampowered.com/api/appdetails?appids=";
    private static final String STEAM_API_URL = "https://api.steampowered.com/";
    private static final String STEAM_API_KEY = "C00C76A4DF536C38350D48B3D137AC28";
    private static final String STEAM_ID = "76561198057493703";

    public SteamAPI() {
    }

    @Override
    public String getContextPath() {
        return "/steam/api/";
    }

    @RESTfulAPIDefine(url = "import", methods = "post", description = "取得教案資訊並儲存")
    private String steamAppImport(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        String requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        System.out.println("Request Body: " + requestBody);
        // 解析 JSON
        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject(requestBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, -1);
            return null;
        }
        try {
            String url = jsonRequest.getString("url");
            String appId = extractAppId(url); // 假設該方法提取 AppID
            System.out.println("測試appId: " + appId);
            JSONObject gameData = getGameInfo(appId); // 獲取遊戲詳情
            System.out.println("測試gameData: " + gameData);

            // 處理主要教案數據
            LessonMainInfo lesson = extractLessonMainInfo(gameData, appId);
            LessonMainInfoAPI.getInstance().createLessonMainInfo(lesson);
            System.out.println("lessonId: " + lesson.getId());
            Long lessonId = lesson.getId();
            // 處理成就
            System.out.println("Start to get schema for game: " + appId);
            JSONObject schema = getSchemaForGame(appId);
            List<LessonAchievement> achievements = extractAchievements(schema, lessonId, appId);
            LessonAchievementAPI.getInstance().createLessonAchievementList(achievements);
            // 處理統計
            System.out.println("Start to get statistics for game: " + appId);
            List<LessonStatistics> statistics = extractStatistics(schema, lessonId);
            LessonStatisticsAPI.getInstance().createLessonStatisticsList(statistics);
            // 處理圖庫
            System.out.println("Start to get images for game: " + appId);
            List<LessonImage> images = extractLessonImages(gameData, lessonId, appId);
            LessonImageAPI.getInstance().createLessonImageList(images);
            /* 處理標籤 */
            // 語言標籤
            System.out.println("Start to get tags for game: " + appId);
            String[] languages = getLanguageList(gameData);
            saveLanguageTags(languages, lessonId);
            // 類型標籤
            if (gameData.has("genres")) {
                JSONArray genres = gameData.getJSONArray("genres");
                saveGenresTags(genres, lessonId);
            }
            // 其他標籤
            if (gameData.has("categories")) {
                JSONArray categories = gameData.getJSONArray("categories");
                saveCategoryTags(categories, lessonId);
            }
            // 開發者
            if (gameData.has("developers")) {
                JSONArray developers = gameData.getJSONArray("developers");
                saveDevelopersTags(developers, lessonId);
            }
            // 系統需求
            /**
             * * 平台(作業系統) **
             */
            if (gameData.has("platforms")) {
                JSONObject platforms = gameData.getJSONObject("platforms");
                if (platforms.getBoolean("windows")) {
                    DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName("Windows");
                    LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
                }
                if (platforms.getBoolean("mac")) {
                    DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName("Mac");
                    LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
                }
                if (platforms.getBoolean("linux")) {
                    DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName("Linux");
                    LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
                }
            }

            // 處理系統需求
            System.out.println("Start to get system requirements for game: " + appId);
            if (gameData.has("pc_requirements")) {
                Object pcReqObj = gameData.opt("pc_requirements");
                if (pcReqObj instanceof JSONObject) {
                    JSONObject pcRequirements = gameData.getJSONObject("pc_requirements");
                    saveSystemRequirements(pcRequirements, "Windows", lessonId);
                }
            }

            if (gameData.has("mac_requirements")) {
                Object macReqObj = gameData.opt("mac_requirements");
                if (macReqObj instanceof JSONObject) {
                    JSONObject macRequirements = gameData.getJSONObject("mac_requirements");
                    saveSystemRequirements(macRequirements, "Mac", lessonId);
                }
            }

            if (gameData.has("linux_requirements")) {
                Object linuxReqObj = gameData.opt("linux_requirements");
                if (linuxReqObj instanceof JSONObject) {
                    JSONObject linuxRequirements = gameData.getJSONObject("linux_requirements");
                    saveSystemRequirements(linuxRequirements, "Linux", lessonId);
                }
            }

            // 處理新聞
            System.out.println("Start to get news for game: " + appId);
            JSONObject newsData = getSteamAppNews(appId);
            saveNewsData(newsData, lesson.getId());

            responseJson.put("lessonName", lesson.getLessonName());
            responseJson.put("message", "Game imported successfully.");
            responseJson.put("success", Boolean.TRUE);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            return responseJson.toString();
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            e.printStackTrace();
            return null;
        }
    }

    private String extractAppId(String url) {
        // 檢查是否為有效的 URL
        if (url == null || !url.contains("/app/")) {
            throw new IllegalArgumentException("Invalid Steam URL: " + url);
        }
        // 提取 `/app/` 之後到下一個 `/` 之前的部分作為 AppID
        int appIndex = url.indexOf("/app/") + 5; // "/app/" 之後的索引
        int nextSlashIndex = url.indexOf("/", appIndex);
        if (nextSlashIndex == -1) {
            // 如果沒有下一個 `/`，直接取從 `/app/` 開始的剩餘部分
            return url.substring(appIndex);
        }
        return url.substring(appIndex, nextSlashIndex);
    }

    public JSONObject getGameInfo(String appId) {
        try {
            // 發送 GET 請求
            String url = STEAM_DETAIL_URL + appId + "&cc=TW&l=zh-TW";
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept-Language", "zh-TW");
            headers.put("User-Agent", "Mozilla/5.0");
            headers.put("Accept-Charset", "UTF-8");
            String response = null;
            try {
                response = HttpClientUtil.sendGetRequest(url, headers);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject gameJsonObject = new JSONObject(response);
            // 解析結果
            if (gameJsonObject.has(appId) && gameJsonObject.getJSONObject(appId).has("data")) {
                return gameJsonObject.getJSONObject(appId).getJSONObject("data");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getSchemaForGame(String appId) {
        String url = STEAM_API_URL + "ISteamUserStats/GetSchemaForGame/v2/?key=" + STEAM_API_KEY + "&appid=" + appId
                + "&l=tchinese";
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", "Mozilla/5.0");
        headers.put("Accept-Language", "zh-TW");
        headers.put("Accept-Charset", "UTF-8");
        String response = null;
        try {
            response = HttpClientUtil.sendGetRequest(url, headers);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject;
    }

    private LessonMainInfo extractLessonMainInfo(JSONObject gameData, String appId) {
        LessonMainInfo lesson = new LessonMainInfo();
        lesson.setLessonName(gameData.optString("name", "Unknown"));
        lesson.setLessonBrief(
                GoogleTranslateAPI.getInstance().translateString(gameData.optString("short_description", "Unknown")));
        lesson.setLessonDescription(
                GoogleTranslateAPI.getInstance()
                        .translateString(gameData.optString("detailed_description", "Unknown")));

        String headerImageUrl = gameData.optString("header_image", "");
        if (!headerImageUrl.isEmpty()) {
            System.out.println("headerImageUrl: " + headerImageUrl);
            String storedImagePath = downloadAndSaveImage(headerImageUrl, "steamImages/" + appId); // 假設存儲圖片);
            lesson.setHeaderImageUrl(storedImagePath); // 設定存儲的伺服器 URL
        }

        // 使用 parseReleaseDate 方法處理日期
        String releaseDateStr = gameData.getJSONObject("release_date").optString("date", "");
        System.out.println("releaseDateStr: " + releaseDateStr);
        lesson.setReleaseDate(parseReleaseDate(releaseDateStr));
        lesson.setMinAge(Integer.parseInt(gameData.optString("required_age", "0")));
        lesson.setPrice(gameData.getBoolean("is_free") ? 0.0
                : Double.parseDouble(gameData.getJSONObject("price_overview").optString("final_formatted", "0")
                        .replaceAll("[^\\d.]", "")));
        lesson.setExecutionId("steamApp/" + appId);
        lesson.setCsrrRate(gameData.optJSONObject("ratings") != null
                && gameData.getJSONObject("ratings").optJSONObject("csrr") != null
                        ? gameData.getJSONObject("ratings").getJSONObject("csrr").optString("rating", "")
                        : "");
        lesson.setCreateTime(new Date());
        lesson.setCreator(1L);
        lesson.setStatus(LessonMainInfo.LessonStatus.PUBLISHED);
        return lesson;
    }

    private List<LessonAchievement> extractAchievements(JSONObject schema, Long lessonId, String appId) {
        List<LessonAchievement> achievements = new ArrayList<>();

        try {
            // 檢查 game 和 availableGameStats 是否存在
            JSONObject game = schema.optJSONObject("game");
            if (game == null) {
                System.out.println("Game object is missing.");
                return achievements;
            }

            JSONObject availableGameStats = game.optJSONObject("availableGameStats");
            if (availableGameStats == null || !availableGameStats.has("achievements")) {
                System.out.println("No achievements data found.");
                return achievements;
            }

            JSONArray achievementsArray = availableGameStats.getJSONArray("achievements");
            int total = achievementsArray.length();
            // 遍歷成就列表
            for (int i = 0; i < achievementsArray.length(); i++) {
                JSONObject achievementData = achievementsArray.getJSONObject(i);
                LessonAchievement achievement = new LessonAchievement();

                // 填充 LessonAchievement 屬性
                achievement.setLessonId(lessonId);
                achievement.setApiName(achievementData.getString("name"));
                achievement.setDisplayName(
                        GoogleTranslateAPI.getInstance().translateString(achievementData.getString("displayName")));
                achievement.setDescription(
                        GoogleTranslateAPI.getInstance().translateString(achievementData.optString("description", "")));
                achievement.setUnlockValue(achievementData.optDouble("defaultvalue", 0.0));
                achievement.setHidden(achievementData.getInt("hidden") == 1);
                achievement.setUnlockedIconUrl(
                        downloadAndSaveImage(achievementData.getString("icon"), "steamImages/" + appId));
                achievement.setLockedIconUrl(
                        downloadAndSaveImage(achievementData.getString("icongray"), "steamImages/" + appId));
                achievement.setCreateTime(new Date());
                achievement.setCreator(1L);
                achievements.add(achievement);

                int progress = (int) (((double) (i + 1) / total) * 100);
                System.out.print("\rProgress: " + progress + "% (" + (i + 1) + "/" + total + ")   ");
                System.out.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return achievements;
    }

    private List<LessonStatistics> extractStatistics(JSONObject schema, Long lessonId) {
        List<LessonStatistics> statistics = new ArrayList<>();

        try {
            // 檢查 game 和 availableGameStats 是否存在
            JSONObject game = schema.optJSONObject("game");
            if (game == null) {
                System.out.println("Game object is missing.");
                return statistics;
            }

            JSONObject availableGameStats = game.optJSONObject("availableGameStats");
            if (availableGameStats == null || !availableGameStats.has("stats")) {
                System.out.println("No stats data found.");
                return statistics;
            }

            JSONArray statsArray = availableGameStats.getJSONArray("stats");
            int total = statsArray.length();
            // 遍歷統計數據陣列
            for (int i = 0; i < statsArray.length(); i++) {
                JSONObject statData = statsArray.getJSONObject(i);
                LessonStatistics statistic = new LessonStatistics();

                // 填充 LessonStatistics 屬性
                statistic.setLessonId(lessonId);
                statistic.setApiName(statData.getString("name"));
                statistic.setDisplayName(statData.optString("displayName", ""));
                // 檢查 defaultvalue 是否存在並轉換
                if (statData.has("defaultvalue")) {
                    statistic.setDefaultValue(statData.optDouble("defaultvalue"));
                } else {
                    statistic.setDefaultValue(null); // 沒有數值時設為 null
                }
                statistic.setCreateTime(new Date());
                statistic.setCreator(1L);
                statistics.add(statistic);
                int progress = (int) (((double) (i + 1) / total) * 100);
                System.out.print("\rProgress: " + progress + "% (" + (i + 1) + "/" + total + ")   ");
                System.out.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statistics;
    }

    private List<LessonImage> extractLessonImages(JSONObject gameData, Long lessonId, String appId) {
        JSONArray screenshots = gameData.optJSONArray("screenshots");
        JSONArray movies = gameData.optJSONArray("movies");
        List<LessonImage> images = new ArrayList<>();

        int screenshotTotal = screenshots != null ? screenshots.length() : 0;
        int movieTotal = movies != null ? movies.length() : 0;
        // 先處理影片 (僅取 mp4)
        for (int i = 0; movies != null && i < movieTotal; i++) {
            JSONObject movie = movies.getJSONObject(i);
            JSONObject mp4Obj = movie.optJSONObject("mp4");
            if (mp4Obj == null) {
                continue; // 若無 mp4 區塊，跳過
            }

            // 取最高畫質 "max"，若要 480p 則改成 mp4Obj.optString("480", "")
            String videoUrl = mp4Obj.optString("max", "");
            if (videoUrl.isEmpty()) {
                continue; // 若連結為空，跳過
            }

            // 移除 ? 之後的參數
            String cleanUrl = videoUrl.contains("?")
                    ? videoUrl.substring(0, videoUrl.indexOf('?'))
                    : videoUrl;

            LessonImage lessonImage = new LessonImage();
            lessonImage.setLessonId(lessonId);
            lessonImage.setFilePath(downloadAndSaveImage(cleanUrl, "steamVideos/" + appId));
            lessonImage.setCreateTime(new Date());
            lessonImage.setCreator(1L);
            lessonImage.setType(LessonImageType.VIDEO);
            images.add(lessonImage);

            int progress = (int) (((double) (i + 1) / movieTotal) * 100);
            System.out.print("\r影片擷取進度: " + progress + "% (" + (i + 1) + "/" + movieTotal + ")   ");
            System.out.flush();
        }
        for (int i = 0; i < screenshots.length(); i++) {
            String imageUrl = screenshots.getJSONObject(i).optString("path_full", "");
            LessonImage lessonImage = new LessonImage();
            lessonImage.setLessonId(lessonId);
            lessonImage.setFilePath(downloadAndSaveImage(imageUrl, "steamImages/" + appId));
            lessonImage.setCreateTime(new Date());
            lessonImage.setCreator(1L);
            lessonImage.setType(LessonImageType.IMAGE);
            images.add(lessonImage);
            int progress = (int) (((double) (i + 1) / screenshotTotal) * 100);
            System.out.print("\rProgress: " + progress + "% (" + (i + 1) + "/" + screenshotTotal + ")   ");
            System.out.flush();

        }
        return images;
    }

    private String[] getLanguageList(JSONObject gameData) {
        String supportedLanguages = gameData.getString("supported_languages");
        int indexOfBr = supportedLanguages.indexOf("<br>");
        if (indexOfBr != -1) {
            supportedLanguages = supportedLanguages.substring(0, indexOfBr);
        }
        String[] languagesArray = supportedLanguages.split(", ");
        return languagesArray;
    }

    private void saveLanguageTags(String[] languages, Long lessonId) {
        for (String language : languages) {
            boolean hasFullAudioSupport = language.contains("<strong>*</strong>");
            String languageName = language.replace("<strong>*</strong>", "").trim();

            // * 為有語音支援的語言
            if (DtxTagAPI.getInstance().isDtxTagExist(languageName + "(語音)") && hasFullAudioSupport) {
                DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName(languageName + "(語音)");
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
                // 沒有語音支援的
            } else if (DtxTagAPI.getInstance().isDtxTagExist(languageName)) {
                DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName(languageName);
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
            } else {
                // 儲存新語言
                DtxTag newDtxTag = new DtxTag(3, languageName, "");
                DtxTagAPI.getInstance().createDtxTag(newDtxTag);
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, newDtxTag.getId(), 1L, new Date()));
            }
        }
    }

    private void saveGenresTags(JSONArray genres, Long lessonId) {
        for (int i = 0; i < genres.length(); i++) {
            JSONObject genre = genres.getJSONObject(i);
            String genreName = genre.getString("description");
            if (DtxTagAPI.getInstance().isDtxTagExist(genreName)) {
                DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName(genreName);
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
            } else {
                // 儲存新類型
                DtxTag newDtxTag = new DtxTag(2, genreName, "");
                DtxTagAPI.getInstance().createDtxTag(newDtxTag);
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, newDtxTag.getId(), 1L, new Date()));
            }
        }
    }

    private void saveDevelopersTags(JSONArray developers, Long lessonId) {
        for (int i = 0; i < developers.length(); i++) {
            String developerName = developers.getString(i);
            if (DtxTagAPI.getInstance().isDtxTagExist(developerName)) {
                DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName(developerName);
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
            } else {
                // 儲存新類型
                DtxTag newDtxTag = new DtxTag(4, developerName, "");
                DtxTagAPI.getInstance().createDtxTag(newDtxTag);
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, newDtxTag.getId(), 1L, new Date()));
            }
        }
    }

    private void saveCategoryTags(JSONArray categories, Long lessonId) {
        for (int i = 0; i < categories.length(); i++) {
            JSONObject category = categories.getJSONObject(i);
            String categoryName = category.getString("description");
            if (DtxTagAPI.getInstance().isDtxTagExist(categoryName)) {
                DtxTag dtxTag = DtxTagAPI.getInstance().getDtxTagByName(categoryName);
                LessonTagAPI.getInstance().createLessonTag(new LessonTag(lessonId, dtxTag.getId(), 1L, new Date()));
            }
        }
    }

    private LessonSystemRequirement extractSystemRequirement(String requirementDetails, RequirementType requirementType,
            String platform, Long lessonId) {
        // 初始化系統需求物件
        LessonSystemRequirement requirement = new LessonSystemRequirement();
        requirement.setLessonId(lessonId);
        requirement.setPlatform(platform);
        requirement.setRequirementType(requirementType);

        // 使用正規表達式解析 HTML 字串
        Pattern typePattern = Pattern.compile("<strong>(最低配備|建議配備):</strong>");
        Matcher typeMatcher = typePattern.matcher(requirementDetails);
        if (typeMatcher.find()) {
            String type = typeMatcher.group(1).trim();
            System.out.println("需求類型: " + type);
        }

        // 匹配每個需求項目
        Pattern pattern = Pattern.compile("<li>(?:<strong>([^:]+):</strong>)?\\s*(.*?)</li>");
        Matcher matcher = pattern.matcher(requirementDetails);

        // 初始化各欄位
        String os = null;
        String processor = null;
        String memory = null;
        String graphics = null;
        String directX = null;
        String soundCard = null;
        String storage = null;
        String notes = null;

        // 遍歷匹配到的需求項目
        while (matcher.find()) {

            String tmpkey = matcher.group(1) != null ? matcher.group(1).trim() : "";
            String key = tmpkey.replace("*", "").trim();
            String value = matcher.group(2).trim();

            System.out.println("Key: " + key + ", Value: " + value);
            switch (key) {
                case "作業系統":
                    os = value.replace("<br>", "");
                    break;
                case "處理器":
                    processor = value.replace("<br>", "");
                    break;
                case "記憶體":
                    memory = value.replace("<br>", "");
                    break;
                case "顯示卡":
                    graphics = value.replace("<br>", "");
                    break;
                case "DirectX":
                    directX = value.replace("<br>", "");
                    break;
                case "音效卡":
                    soundCard = value.replace("<br>", "");
                    break;
                case "儲存空間":
                    storage = value.replace("<br>", "");
                    break;
                case "備註":
                    notes = value;
                    break;
                default:
                    break;
            }
        }

        // 設置需求物件的欄位
        requirement.setOperatingSystem(os);
        requirement.setProcessor(processor);
        requirement.setMemory(memory);
        requirement.setGraphicsCard(graphics);
        requirement.setDirectxVersion(directX);
        requirement.setSoundCard(soundCard);
        requirement.setStorageSpace(storage);
        requirement.setNotes(notes);

        // 打印解析結果
        System.out.println("Lesson ID: " + lessonId);
        System.out.println("Platform: " + platform);
        System.out.println("Requirement Type: " + requirementType);
        System.out.println("Operating System: " + os);
        System.out.println("Processor: " + processor);
        System.out.println("Memory: " + memory);
        System.out.println("Graphics Card: " + graphics);
        System.out.println("DirectX Version: " + directX);
        System.out.println("Sound Card: " + soundCard);
        System.out.println("Storage Space: " + storage);
        System.out.println("Notes: " + notes);

        return requirement;
    }

    private void saveSystemRequirements(JSONObject requirements, String platform, Long lessonId) {
        if (requirements.has("minimum")) {
            String minDetails = requirements.getString("minimum");
            LessonSystemRequirement minimumRequirement = extractSystemRequirement(minDetails, RequirementType.MINIMUM,
                    platform, lessonId);
            LessonSystemRequirementAPI.getInstance().createLessonSystemRequirement(minimumRequirement);
        }
        if (requirements.has("recommended")) {
            String recDetails = requirements.getString("recommended");
            LessonSystemRequirement recommendedRequirement = extractSystemRequirement(recDetails,
                    RequirementType.RECOMMENDED, platform, lessonId);
            LessonSystemRequirementAPI.getInstance().createLessonSystemRequirement(recommendedRequirement);
        }
    }

    public static JSONObject getSteamAppNews(String appId) {
        String url = STEAM_API_URL + "ISteamNews/GetNewsForApp/v2/?appid=" + appId + "&count=3&format=json&l=zh_TW";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept-Language", "zh-TW");
        headers.put("User-Agent", "Mozilla/5.0");
        headers.put("Accept-Charset", "UTF-8");
        String response = null;
        try {
            response = HttpClientUtil.sendGetRequest(url, headers);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("steam app news response: " + response);

        JSONObject jsonObject = new JSONObject(response);
        return jsonObject;
    }

    private void saveNewsData(JSONObject newsData, Long lessonId) {
        List<LessonNews> newsList = new ArrayList<>();
        JSONObject appNews = newsData.getJSONObject("appnews");
        JSONArray newsItems = appNews.getJSONArray("newsitems");
        for (int i = 0; i < newsItems.length(); i++) {
            JSONObject newsItem = newsItems.getJSONObject(i);
            LessonNews news = new LessonNews();

            news.setTitle(newsItem.getString("title"));
            news.setContent(newsItem.getString("contents")
                    .replace("\n", "\\n")
                    .replace("\r", "")
                    .replace("\"", "\\\""));

            // 修正 tags 儲存格式，確保是 List<String>
            if (newsItem.has("tags")) {
                JSONArray tagsArray = newsItem.getJSONArray("tags");
                List<String> tagsList = new ArrayList<>();
                for (int j = 0; j < tagsArray.length(); j++) {
                    tagsList.add(tagsArray.getString(j));
                }
                // 使用逗號分隔組成一個字串
                news.setTags(String.join(",", tagsList));
            } else {
                news.setTags(""); // 無 tags 時存入空字串
            }

            news.setAuthor(newsItem.optString("author", ""));
            news.setCreator(1L);
            news.setLessonId(lessonId);
            // Check if unixTime is valid
            Long unixTime = newsItem.optLong("date", 0L);
            if (unixTime > 0) {
                news.setCreateTime(new Date(unixTime * 1000)); // Convert unixTime to Date
            } else {
                news.setCreateTime(new Date()); // Set to current date if unixTime is invalid
            }
            newsList.add(news);
        }
        LessonNewsAPI.getInstance().createLessonNewsList(newsList);
    }

    private String downloadAndSaveImage(String imageUrl, String category) {
        try {
            String uploadDir = ConfigPropertyAPI.getInstance().getValueByKey("imagesDir");
            // 確保分類目錄存在
            Path categoryPath = Paths.get(uploadDir, category);
            if (!Files.exists(categoryPath)) {
                Files.createDirectories(categoryPath);
            }

            // 生成唯一檔名並下載圖片
            String fileExtension = imageUrl.substring(imageUrl.lastIndexOf("."));

            if (fileExtension.contains("?")) {
                fileExtension = fileExtension.substring(0, fileExtension.indexOf("?")); // 去除 URL 參數
            }

            String fileName = UUID.randomUUID().toString() + fileExtension;
            Path filePath = categoryPath.resolve(fileName);

            byte[] imageBytes = HttpClientUtil.sendGetRequestForBytes(imageUrl, new HashMap<>());

            // 儲存圖片到伺服器
            Files.write(filePath, imageBytes);

            // 確保返回的相對路徑使用 `/` 作為分隔符號
            String relativePath = filePath.toString().replace(uploadDir, "").replace("\\", "/");

            return "/images/lessons" + relativePath;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to download or save image: " + e.getMessage());
        }
    }

    private Date parseReleaseDate(String date) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy 年 M 月 d 日", Locale.TAIWAN);
        try {
            return inputFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
