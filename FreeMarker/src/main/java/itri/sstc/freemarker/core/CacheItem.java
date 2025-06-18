package itri.sstc.freemarker.core;

public class CacheItem {
    private Object value;
    private long expirationTime;

    public CacheItem(Object value, long expirationTime) {
        this.value = value;
        this.expirationTime = expirationTime;
    }

    public Object getValue() {
        return value;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}

