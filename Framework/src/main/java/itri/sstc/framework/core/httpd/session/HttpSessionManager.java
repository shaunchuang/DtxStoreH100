package itri.sstc.framework.core.httpd.session;

import com.sun.net.httpserver.HttpExchange;

import itri.sstc.framework.core.httpd.CookieParser;
import itri.sstc.framework.core.httpd.HttpdConfig;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author schung
 */
public class HttpSessionManager extends TimerTask {

    private final static Logger logger = Logger.getLogger(HttpSessionManager.class.getName());
    private final static HttpSessionManager INSTANCE = new HttpSessionManager();

    private final long scanPeriod; // 掃描週期
    private final int maxSessionListLength; // 最大 Session 列表長度
    private final long expiredTime; // 有效期限(s)
    private final String cookieDomain; // 

    private final Map<String, HttpSession> sessions = new HashMap<>();
    private Timer timer;

    public static HttpSessionManager getInstance() {
        return INSTANCE;
    }

    private HttpSessionManager() {
        this.scanPeriod = Long.parseLong(HttpdConfig.get("session.scanPeriod", "5000"));
        this.expiredTime = Integer.parseInt(HttpdConfig.get("session.expireTime", "1800")) * 1000L;
        this.maxSessionListLength = Integer.parseInt(HttpdConfig.get("session.maxlength", "20"));
        this.cookieDomain = HttpdConfig.get("session.cookieDomain", "");
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    /**
     * 啟動 HttpSessionManager
     */
    public void start() {
        sessions.clear();
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, scanPeriod);
        logger.log(Level.INFO, "啟用 HttpSessionManager OK");
    }

    /**
     * 停用 HttpSessionManager
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            sessions.clear();
        }
        logger.log(Level.INFO, "停止 HttpSessionManager OK");
    }

    public long getExpiredTime() {
        return expiredTime;
    }

    /**
     * 新增 HttpSession
     *
     * @param exchange
     * @return HttpSession
     */
    public HttpSession createSession(HttpExchange exchange) {
        if (sessions.size() > maxSessionListLength) {
            logger.log(Level.WARNING, "Session fulled");
            return null;
        }
        //
        HttpSession session = new HttpSession();
        sessions.put(session.getSessionId(), session);
        logger.log(Level.INFO, String.format("Create session %s for %s", session.getSessionId(), exchange.getRequestURI().getPath()));
        return session;
    }

    /**
     * 取得 HttpSession (SessionId紀錄在 Cookie 中)
     *
     * @param exchange
     * @param auto
     * @return HttpSession
     */
    public HttpSession getSession(HttpExchange exchange, boolean auto) {
        HttpSession session = null;
        Map<String, String> cookies = CookieParser.parse(exchange.getRequestHeaders());
        // 檢查 Client 端的 Cookie 是否包含 session?
        if (cookies.containsKey(HttpSession.COOKIE_NAME)) {
            String sessionId = cookies.get(HttpSession.COOKIE_NAME);
            session = sessions.get(sessionId);
        }
        if (auto) {
            session = createSession(exchange);
        }
        return session;
    }

    public boolean checkSession(String sessionId) {
        logger.log(Level.INFO, String.format("檢查 SessionId %s 是否有效", sessionId));
        HttpSession session = sessions.get(sessionId);
        if (session == null) {
            logger.log(Level.WARNING, String.format("SessionId %s 無效", sessionId));
            return false;
        } else {
            return true;
        }
    }

    /**
     * 移除 HttpSession
     *
     * @param exchange
     */
    public void removeSession(HttpExchange exchange) {
        HttpSession session = null;
        Map<String, String> cookies = CookieParser.parse(exchange.getRequestHeaders());
        // 檢查 Client 端的 Cookie 是否包含 session?
        if (cookies.containsKey(HttpSession.COOKIE_NAME)) {
            String sessionId = cookies.get(HttpSession.COOKIE_NAME);
            session = sessions.get(sessionId);
        }
        if (session != null) {
            logger.log(Level.INFO, String.format("Try to remove session %s", session.getSessionId()));
            sessions.remove(session.getSessionId());
            session.release();
        }
    }

    /**
     * 生成 Session ID
     *
     * @return Session ID
     */
    public synchronized final String createSessionId() {
        try {
            UUID uuid;
            String encodedUUID;
            while (true) {
                uuid = UUID.randomUUID();
                encodedUUID = URLEncoder.encode(String.format("SESSION_%s", uuid), "utf-8");
                if (!sessions.containsKey(encodedUUID)) {
                    break;
                }
            }
            return encodedUUID;
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    @Override
    public void run() {
        // logger.log(Level.INFO, String.format("HttpSession 數量 %d", sessions.size()));
        long scheduleTime = scheduledExecutionTime();
        if (System.currentTimeMillis() < (scheduleTime - 100)) {
            return;
        }
        //
        if (!sessions.isEmpty()) {
            HttpSession[] temp = sessions.values().toArray(new HttpSession[0]);
            for (HttpSession value : temp) {
                if (value.getLastAccessTime() < (System.currentTimeMillis() - expiredTime)) {
                    HttpSession session = sessions.remove(value.getSessionId());
                    session.release();
                }
            }
        }
    }

}
