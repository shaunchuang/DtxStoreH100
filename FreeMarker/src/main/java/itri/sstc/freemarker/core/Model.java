package itri.sstc.freemarker.core;

import java.util.HashMap;

/**
 * FreeMarker 資料模型
 *
 * @author schung
 */
public class Model extends HashMap<String, Object> {
    public void addAttribute(String key, Object obj) {
        this.put(key,obj);
    }
}
