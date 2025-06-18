package itri.sstc.freemarker.core;

import itri.sstc.framework.core.httpd.session.HttpSession; 
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap; 

public class CacheUtils {

    private static final CacheUtils INSTANCE = new CacheUtils();
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, CacheItem>> tenantCache = new ConcurrentHashMap<>();

    private CacheUtils() {
    }

    public static CacheUtils getInstance() {
        return INSTANCE;
    }

    // 添加緩存  
    public boolean put(String tenantId, String servletName, String where, Object value, long ttlMillis) {
        try {
            String key = generateKey(servletName, where);
            long expirationTime = System.currentTimeMillis() + ttlMillis;

            // Replace lambda expression with an anonymous inner class
            if (!tenantCache.containsKey(tenantId)) {
                tenantCache.put(tenantId, new ConcurrentHashMap<String, CacheItem>());
            }
            tenantCache.get(tenantId).put(key, new CacheItem(value, expirationTime));
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public static boolean put(HttpSession session, String servletName, String where, Object value, long ttlMillis) {
        if (session != null) {
            String tenantId = (String) session.getValue(Constants.SESSION_CURRENT_TENANT_ID);
            // 提取 tenantId
            return INSTANCE.put(tenantId, servletName, where, value, ttlMillis); // 呼叫單例的 put 方法
        }
        return false;
    }

    public static boolean put(RequestData request, String servletName, String where, Object value, long ttlMillis) {
        return INSTANCE.put(request.session, servletName, where, value, ttlMillis); // 呼叫單例的 put 方法
    }

    // 獲取緩存
    public Object get(String tenantId, String servletName, String where) {
        try {
            String key = generateKey(servletName, where);
            ConcurrentHashMap<String, CacheItem> tenantData = tenantCache.get(tenantId);

            if (tenantData != null) {
                CacheItem item = tenantData.get(key);
                if (item != null && !item.isExpired()) {
                    return item.getValue();
                }
            }
        } catch (Exception ex) {
        }

        return null; // 緩存不存在或已過期
    }

    public static Object get(HttpSession session, String servletName, String where) {
        if (session != null) {
            String tenantId = (String) session.getValue(Constants.SESSION_CURRENT_TENANT_ID); // 提取 tenantId
            return INSTANCE.get(tenantId, servletName, where); // 呼叫單例的 get 方法
        }
        return null;
    }

    public static Object get(RequestData request, String servletName, String where) {
        return INSTANCE.get(request.session, servletName, where); // 呼叫單例的 get 方法
    }

    // 移除指定租戶的緩存
    public void remove(String tenantId, String servletName, String where) {
        String key = generateKey(servletName, where);
        ConcurrentHashMap<String, CacheItem> tenantData = tenantCache.get(tenantId);
        if (tenantData != null) {
            tenantData.remove(key);
        }
    }

    public static void remove(RequestData request, String servletName) {
        INSTANCE.remove(request.session, servletName); // 呼叫單例的 get 方法 
    }

    public static void remove(HttpSession session, String servletName) {
        if (session != null) {
            String tenantId = (String) session.getValue(Constants.SESSION_CURRENT_TENANT_ID); // 提取 tenantId
            INSTANCE.remove(tenantId, servletName); // 呼叫單例的 get 方法
        }
    }

    // 移除指定租戶的緩存
    public void remove(String tenantId, String servletName) {
        ConcurrentHashMap<String, CacheItem> tenantData = tenantCache.get(tenantId);
        if (tenantData != null) {
            Iterator<String> iterator = tenantData.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key.startsWith(servletName)) {
                    iterator.remove(); // 使用迭代器安全地移除元素
                }
            }
        }
    }

    // 清空指定租戶的全部緩存
    public void clearTenant(String tenantId) {
        tenantCache.remove(tenantId);
    }

    // 清空所有租戶的緩存
    public void clearAll() {
        tenantCache.clear();
    }

    private String generateKey(String servletName, String where) {
        return servletName + "|" + where;
    }
}
