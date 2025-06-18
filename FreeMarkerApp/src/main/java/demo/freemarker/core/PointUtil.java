package demo.freemarker.core;

import java.util.*;
import demo.freemarker.api.*;
import demo.freemarker.model.*;

/**
 * 用於管理用戶積分操作的實用類
 */
public class PointUtil {
    private static final PointUtil INSTANCE = new PointUtil();
    private static final int DEFAULT_EXPIRY_DAYS = 365; // 默認積分過期時間為1年
    
    public static PointUtil getInstance() {
        return INSTANCE;
    }
    
    private PointUtil() {
        // 私有構造函數以實現單例模式
    }
    
    /**
     * 從特定來源為用戶添加積分
     */
    public PointAllocation addPoints(Long userId, Long points, String sourceType, String description, Integer expiryDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, expiryDays != null ? expiryDays : DEFAULT_EXPIRY_DAYS);

        PointAllocation allocation = new PointAllocation();
        allocation.setUserId(userId);
        allocation.setPoints(points);
        allocation.setRemainingPoints(points);
        allocation.setSourceType(sourceType);
        allocation.setDescription(description);
        allocation.setAllocatedAt(new Date());
        allocation.setExpireAt(calendar.getTime());

        PointAllocationAPI.getInstance().createPointAllocation(allocation);
        updateUserTotalPoints(userId);
        return allocation;
    }
    
    /**
     * 使用用戶積分，並確保足夠可用積分
     */
    public PointUsage usePoints(Long userId, Long points, String usageType, String description) {
        if (!hasEnoughPoints(userId, points)) {
            return null;
        }

        PointUsage usage = new PointUsage();
        usage.setUserId(userId);
        usage.setUsageType(usageType);
        usage.setDescription(description);
        usage.setUsedAt(new Date());
        usage.setTotalUsed(points);

        PointUsageAPI.getInstance().createPointUsage(usage);
        long deductedPoints = allocatePointUsage(userId, usage.getId(), points);

        if (deductedPoints < points) {
            return null; // 未成功扣除足夠點數，可能有數據競爭或計算錯誤
        }

        updateUserTotalPoints(userId);
        return usage;
    }
    
    /**
     * 檢查用戶是否有足夠的可用積分
     */
    public boolean hasEnoughPoints(Long userId, Long requiredPoints) {
        return getUserAvailablePoints(userId) >= requiredPoints;
    }
    
    /**
     * 獲取用戶當前可用積分餘額（優化為 SQL 查詢）
     */
    public Long getUserAvailablePoints(Long userId) {
        return PointAllocationAPI.getInstance().getUserAvailablePoints(userId);
    }
    

    private long allocatePointUsage(Long userId, Long usageId, Long pointsToUse) {
        List<PointAllocation> allocations = getAllActiveAllocations(userId);
        long remainingToUse = pointsToUse;
        List<PointUsageDetail> details = new ArrayList<>();
        
        for (PointAllocation allocation : allocations) {
            if (remainingToUse <= 0) break;
            long available = allocation.getRemainingPoints();
            if (available <= 0) continue;
            
            long pointsFromThisAllocation = Math.min(available, remainingToUse);
            PointUsageDetail detail = new PointUsageDetail();
            detail.setUsageId(usageId);
            detail.setAllocationId(allocation.getId());
            detail.setUsedPoint(pointsFromThisAllocation);
            details.add(detail);
            
            allocation.setRemainingPoints(available - pointsFromThisAllocation);
            remainingToUse -= pointsFromThisAllocation;
        }

        if (details.isEmpty()) return 0;
        PointUsageDetailAPI.getInstance().batchCreate(details);
        
        PointAllocationAPI.getInstance().batchUpdate(allocations);
        
        return pointsToUse - remainingToUse;
    }
    
    private void updateUserTotalPoints(Long userId) {
        try {
            Long availablePoints = getUserAvailablePoints(userId);
            System.out.println("availablePoints：" + availablePoints);
            if (availablePoints == null) {
                availablePoints = 0L;
            }
    
            User user = UserAPI.getInstance().getUser(userId);
            if (user != null) {
                System.out.println("update user points");
                user.setCurrentPoints(availablePoints);
                UserAPI.getInstance().updateUser(user);
            }
        } catch (Exception ex) {
            System.err.println("Error updating user total points: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    
    /**
     * 獲取用戶的所有活躍分配（未過期，有剩餘積分）
     */
    private List<PointAllocation> getAllActiveAllocations(Long userId) {
        return PointAllocationAPI.getInstance().listActivePointAllocations(userId);
    }

     /**
     * 為用戶添加每日登入積分，如果今天已經獲得過，則不再添加
     * 
     * @param userId 用戶ID
     * @return 創建的PointAllocation物件，如果發生錯誤或今天已經獲得過則返回null
     */
    public PointAllocation awardDailyLoginPoints(Long userId) {
        try {

            // 檢查用戶是否存在
            User user = UserAPI.getInstance().getUser(userId);
            if (user == null) {
                return null;
            }
            
            // 使用API方法檢查是否為當天首次登入
            if (!UserAPI.getInstance().isFirstLoginOfDay(userId)) {
                // 今天已經登入過，不再獎勵積分
                return null;
            }
            
            System.out.println("start to add points");
            // 若無分配記錄存在，則使用默認每日登入積分數量
            Long pointsToAward = 10L;
            
            // 為用戶添加積分，使用默認1年(365天)過期期限
            return addPoints(userId, pointsToAward, "daily_login", "每日首次登入獎勵", DEFAULT_EXPIRY_DAYS);
            
        } catch (Exception ex) {
            System.err.println("Error awarding daily login points: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

     /**
     * 為用戶上傳教案添加積分
     * 
     * @param userId 用戶ID
     * @return 創建的PointAllocation物件
     */
    public PointAllocation awardLessonUploadPoints(Long userId) {
        try {
            // 檢查用戶是否存在
            User user = UserAPI.getInstance().getUser(userId);
            if (user == null) {
                return null;
            }
            
            // 上傳教案默認獎勵50積分
            Long pointsToAward = 50L;
            
            // 為用戶添加積分，使用默認1年過期期限
            return addPoints(userId, pointsToAward, "lesson_upload", "成功上傳教案獎勵", DEFAULT_EXPIRY_DAYS);
            
        } catch (Exception ex) {
            System.err.println("Error awarding lesson upload points: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 為用戶評論/評分教案添加積分
     * 
     * @param userId 用戶ID
     * @return 創建的PointAllocation物件
     */
    public PointAllocation awardLessonRatingPoints(Long userId) {
        try {
            // 檢查用戶是否存在
            User user = UserAPI.getInstance().getUser(userId);
            if (user == null) {
                return null;
            }
            
            // 評論教案默認獎勵5積分
            Long pointsToAward = 5L;
            
            // 為用戶添加積分，使用默認1年過期期限
            return addPoints(userId, pointsToAward, "lesson_rating", "評論/評分教案獎勵", DEFAULT_EXPIRY_DAYS);
            
        } catch (Exception ex) {
            System.err.println("Error awarding lesson rating points: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 獲取用戶的積分歷史，包括分配和使用記錄
     * 
     * @param userId 用戶ID
     * @return 包含分配和使用列表的映射
     */
    public Map<String, Object> getUserPointHistory(Long userId) {
        Map<String, Object> history = new HashMap<>();
        
        // 獲取該用戶的所有分配
        List<PointAllocation> allocations = getAllAllocationsForUser(userId);
        history.put("allocations", allocations);
        
        // 獲取該用戶的所有使用記錄
        List<PointUsage> usages = getAllUsagesForUser(userId);
        history.put("usages", usages);
        
        return history;
    }

    /**
     * 獲取用戶的所有積分使用記錄
     */
    private List<PointUsage> getAllUsagesForUser(Long userId) {
        List<PointUsage> userUsages = new ArrayList<>();
        List<PointUsage> allUsages = PointUsageAPI.getInstance().listPointUsage();
        
        for (PointUsage usage : allUsages) {
            if (usage.getUserId().equals(userId)) {
                userUsages.add(usage);
            }
        }
        
        return userUsages;
    }

        /**
     * 獲取用戶的所有積分分配
     */
    private List<PointAllocation> getAllAllocationsForUser(Long userId) {
        List<PointAllocation> userAllocations = new ArrayList<>();
        List<PointAllocation> allAllocations = PointAllocationAPI.getInstance().listPointAllocation();
        
        for (PointAllocation allocation : allAllocations) {
            if (allocation.getUserId().equals(userId)) {
                userAllocations.add(allocation);
            }
        }
        
        return userAllocations;
    }
}