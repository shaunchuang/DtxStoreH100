package demo.freemarker.handler.restfulapi;

import com.sun.net.httpserver.HttpExchange;
import demo.freemarker.api.PointAllocationAPI;
import demo.freemarker.api.PointUsageAPI;
import demo.freemarker.core.PointUtil;
import demo.freemarker.core.SecurityUtils;
import demo.freemarker.model.PointAllocation;
import demo.freemarker.model.PointUsage;
import demo.freemarker.model.User;
import itri.sstc.framework.core.api.RESTfulAPI;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 積分相關的 API
 *
 * @author user
 */
public class PointsAPI extends RESTfulAPI {

    @Override
    public String getContextPath() {
        return "/points/api/";
    }

    @RESTfulAPIDefine(url = "statistics", methods = "get", description = "獲取用戶積分統計數據")
    private String getPointsStatistics(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        
        try {
            // 獲取當前用戶
            User currentUser = SecurityUtils.getCurrentUser(exchange);
            if (currentUser == null) {
                responseJson.put("success", Boolean.FALSE);
                responseJson.put("message", "用戶未登入");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return responseJson.toString();
            }
            
            Long userId = currentUser.getId();
            
            // 獲取積分歷史
            Map<String, Object> history = PointUtil.getInstance().getUserPointHistory(userId);
            
            // 計算總獲得的積分
            List<PointAllocation> allocations = (List<PointAllocation>) history.get("allocations");
            Long totalEarned = allocations.stream()
                    .mapToLong(PointAllocation::getPoints)
                    .sum();
            
            // 計算總使用的積分
            List<PointUsage> usages = (List<PointUsage>) history.get("usages");
            Long totalUsed = usages.stream()
                    .mapToLong(PointUsage::getTotalUsed)
                    .sum();
            
            // 計算連續登入天數
            long loginStreak = calculateLoginStreak(allocations);
            
            // 構建響應
            responseJson.put("success", Boolean.TRUE);
            responseJson.put("totalEarned", totalEarned);
            responseJson.put("totalUsed", totalUsed);
            responseJson.put("loginStreak", loginStreak);
            responseJson.put("currentPoints", PointUtil.getInstance().getUserAvailablePoints(userId));
            
        } catch (Exception e) {
            System.err.println("Error in getPointsStatistics: " + e.getMessage());
            e.printStackTrace();
            responseJson.put("success", Boolean.FALSE);
            responseJson.put("message", "獲取積分統計失敗: " + e.getMessage());
        }
        
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseJson.toString();
    }
    
    @RESTfulAPIDefine(url = "allocations", methods = "get", description = "獲取用戶積分獲取記錄")
    private String getPointAllocations(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        
        try {
            // 獲取當前用戶
            User currentUser = SecurityUtils.getCurrentUser(exchange);
            if (currentUser == null) {
                responseJson.put("success", Boolean.FALSE);
                responseJson.put("message", "用戶未登入");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return responseJson.toString();
            }
            
            Long userId = currentUser.getId();
            
            // 獲取分頁參數
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int size = Integer.parseInt(params.getOrDefault("size", "10"));
            
            // 獲取所有積分分配記錄
            List<PointAllocation> allAllocations = PointAllocationAPI.getInstance().listPointAllocation().stream()
                    .filter(a -> a.getUserId().equals(userId))
                    .sorted(Comparator.comparing(PointAllocation::getAllocatedAt).reversed())
                    .collect(Collectors.toList());
            
            // 計算總頁數
            int totalItems = allAllocations.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            
            // 分頁處理
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, totalItems);
            
            List<PointAllocation> pagedAllocations = fromIndex < totalItems 
                    ? allAllocations.subList(fromIndex, toIndex) 
                    : new ArrayList<>();
            
            // 將積分分配記錄轉換為 JSON 數組
            JSONArray allocationsArray = new JSONArray();
            for (PointAllocation allocation : pagedAllocations) {
                JSONObject allocationJson = new JSONObject();
                allocationJson.put("id", allocation.getId());
                allocationJson.put("userId", allocation.getUserId());
                allocationJson.put("points", allocation.getPoints());
                allocationJson.put("remainingPoints", allocation.getRemainingPoints());
                allocationJson.put("sourceType", allocation.getSourceType());
                allocationJson.put("description", allocation.getDescription());
                allocationJson.put("allocatedAt", allocation.getAllocatedAt().getTime());
                
                if (allocation.getExpireAt() != null) {
                    allocationJson.put("expireAt", allocation.getExpireAt().getTime());
                }
                
                allocationsArray.put(allocationJson);
            }
            
            // 構建響應
            responseJson.put("success", Boolean.TRUE);
            responseJson.put("allocations", allocationsArray);
            responseJson.put("totalItems", totalItems);
            responseJson.put("totalPages", totalPages);
            responseJson.put("currentPage", page);
            
        } catch (Exception e) {
            System.err.println("Error in getPointAllocations: " + e.getMessage());
            e.printStackTrace();
            responseJson.put("success", Boolean.FALSE);
            responseJson.put("message", "獲取積分獲取記錄失敗: " + e.getMessage());
        }
        
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseJson.toString();
    }
    
    @RESTfulAPIDefine(url = "usages", methods = "get", description = "獲取用戶積分使用記錄")
    private String getPointUsages(HttpExchange exchange) throws IOException {
        JSONObject responseJson = new JSONObject();
        
        try {
            // 獲取當前用戶
            User currentUser = SecurityUtils.getCurrentUser(exchange);
            if (currentUser == null) {
                responseJson.put("success", Boolean.FALSE);
                responseJson.put("message", "用戶未登入");
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                return responseJson.toString();
            }
            
            Long userId = currentUser.getId();
            
            // 獲取分頁參數
            Map<String, String> params = queryToMap(exchange.getRequestURI().getQuery());
            int page = Integer.parseInt(params.getOrDefault("page", "1"));
            int size = Integer.parseInt(params.getOrDefault("size", "10"));
            
            // 獲取所有積分使用記錄
            List<PointUsage> allUsages = PointUsageAPI.getInstance().listPointUsage().stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .sorted(Comparator.comparing(PointUsage::getUsedAt).reversed())
                    .collect(Collectors.toList());
            
            // 計算總頁數
            int totalItems = allUsages.size();
            int totalPages = (int) Math.ceil((double) totalItems / size);
            
            // 分頁處理
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, totalItems);
            
            List<PointUsage> pagedUsages = fromIndex < totalItems 
                    ? allUsages.subList(fromIndex, toIndex) 
                    : new ArrayList<>();
            
            // 將積分使用記錄轉換為 JSON 數組
            JSONArray usagesArray = new JSONArray();
            for (PointUsage usage : pagedUsages) {
                JSONObject usageJson = new JSONObject();
                usageJson.put("id", usage.getId());
                usageJson.put("userId", usage.getUserId());
                usageJson.put("usageType", usage.getUsageType());
                usageJson.put("description", usage.getDescription());
                usageJson.put("totalUsed", usage.getTotalUsed());
                usageJson.put("usedAt", usage.getUsedAt().getTime());
                
                usagesArray.put(usageJson);
            }
            
            // 構建響應
            responseJson.put("success", Boolean.TRUE);
            responseJson.put("usages", usagesArray);
            responseJson.put("totalItems", totalItems);
            responseJson.put("totalPages", totalPages);
            responseJson.put("currentPage", page);
            
        } catch (Exception e) {
            System.err.println("Error in getPointUsages: " + e.getMessage());
            e.printStackTrace();
            responseJson.put("success", Boolean.FALSE);
            responseJson.put("message", "獲取積分使用記錄失敗: " + e.getMessage());
        }
        
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        return responseJson.toString();
    }
    
    /**
     * 將查詢字符串轉換為 Map
     */
    private Map<String, String> queryToMap(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyMap();
        }
        
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=", 2);
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
    
    /**
     * 計算連續登入天數
     * 注意：這是一個簡化的實現，實際項目可能需要更準確的算法
     */
    private long calculateLoginStreak(List<PointAllocation> allocations) {
        try {
            // 篩選出每日登入獎勵的記錄
            List<PointAllocation> loginAllocations = allocations.stream()
                    .filter(a -> "daily_login".equals(a.getSourceType()))
                    .sorted(Comparator.comparing(PointAllocation::getAllocatedAt).reversed())
                    .collect(Collectors.toList());
            
            if (loginAllocations.isEmpty()) {
                return 0;
            }
            
            // 檢查最近一次是否是今天
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Date today = new Date();
            Date mostRecentLogin = loginAllocations.get(0).getAllocatedAt();
            
            // 如果最近的登入不是今天，則檢查是否是昨天
            String todayStr = formatter.format(today);
            String mostRecentStr = formatter.format(mostRecentLogin);
            
            if (!todayStr.equals(mostRecentStr)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                cal.add(Calendar.DATE, -1);
                String yesterdayStr = formatter.format(cal.getTime());
                
                // 如果最近的登入也不是昨天，說明連續登入已經中斷
                if (!yesterdayStr.equals(mostRecentStr)) {
                    return 0;
                }
            }
            
            // 計算連續登入天數
            int streak = 1; // 至少有一天
            Date lastDate = loginAllocations.get(0).getAllocatedAt();
            
            for (int i = 1; i < loginAllocations.size(); i++) {
                Date currentDate = loginAllocations.get(i).getAllocatedAt();
                
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(lastDate);
                cal1.add(Calendar.DATE, -1);
                
                Calendar cal2 = Calendar.getInstance();
                cal2.setTime(currentDate);
                cal2.set(Calendar.HOUR_OF_DAY, 0);
                cal2.set(Calendar.MINUTE, 0);
                cal2.set(Calendar.SECOND, 0);
                cal2.set(Calendar.MILLISECOND, 0);
                
                // 檢查兩個日期是否相差一天
                if (isSameDay(cal1, cal2)) {
                    streak++;
                    lastDate = currentDate;
                } else {
                    break; // 連續登入中斷
                }
            }
            
            return streak;
            
        } catch (Exception e) {
            System.err.println("Error calculating login streak: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 檢查兩個日期是否是同一天
     */
    private boolean isSameDay(Calendar cal1, Calendar cal2) {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}
