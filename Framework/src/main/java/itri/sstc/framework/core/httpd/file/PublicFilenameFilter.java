package itri.sstc.framework.core.httpd.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * 標示為公開檔案，不需要檢查權限
 * @author schung
 */
public class PublicFilenameFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.equalsIgnoreCase("public.txt");
    }
    
}
