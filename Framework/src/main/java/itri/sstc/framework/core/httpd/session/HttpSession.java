package itri.sstc.framework.core.httpd.session;

import java.util.HashMap;
import java.util.Map;

/**
 * Http Session
 *
 * @author schung
 */
public final class HttpSession {

    public final static String COOKIE_NAME = "JSESSION";

    private final String sessionId;
    private final long createTime;
    private final Map<String, Object> values;
    private long lastAccessTime;

    public HttpSession(String sessionId) {
        if (sessionId == null) {
            this.sessionId = HttpSessionManager.getInstance().createSessionId();
        } else {
            this.sessionId = sessionId;
        }
        this.createTime = System.currentTimeMillis();
        this.values = new HashMap<>();
        this.lastAccessTime = System.currentTimeMillis();
    }

    public HttpSession() {
        this(null);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        values.clear();
    }

    public final void release() {
        values.clear();
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void updateLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    public boolean contains(String name) {
        return values.containsKey(name);
    }

    public Object getValue(String name) {
        return values.get(name);
    }

    public void setValue(String name, Object value) {
        values.put(name, value);
    }

    public final String toCookieString() {
        String cookieDomain = HttpSessionManager.getInstance().getCookieDomain();
        return String.format("%s=%s;path=/;Domain=%s", COOKIE_NAME, sessionId, cookieDomain);
    }

    public void setAttribute(String name, Object value) {
        values.put(name, value);
    }
    
    public Object getAttribute(String name) {
        return values.get(name);
    }
    
}
