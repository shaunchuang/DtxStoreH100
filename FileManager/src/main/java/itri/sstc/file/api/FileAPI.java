package itri.sstc.file.api;

import itri.sstc.framework.core.Config;
import itri.sstc.framework.core.api.API;
import itri.sstc.framework.core.api.API.APIDefine;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 檔案管理 API
 *
 * @author schung
 */
public class FileAPI implements API {

    protected static String ROOT = Config.get("filemanager.folder", "/temp/");

    public final static FileAPI INSTANCE = new FileAPI();

    public final static FileAPI getInstance() {
        return INSTANCE;
    }

    private FileAPI() {
        logger.info(String.format("Create FileAPI. Root=%s", ROOT));
    }

    @Override
    public String getName() {
        return "FileAPI";
    }

    @Override
    public String getVersion() {
        return "20250212.01";
    }

    @Override
    public String getDescription() {
        return "檔案管理 API";
    }

    /**
     * 列出指定路徑下的所有檔案
     *
     * @param path 指定路徑
     * @return null: 非目錄, List<File>: 檔案列表
     */
    @APIDefine(description = "列出指定路徑下的所有檔案")
    public List<File> list(String path) throws IOException {
        File dir = new File(String.format("%s/%s", ROOT, path));
        // 加入安全檢查，目錄是否為可存取
        if (!dir.exists() || !dir.isDirectory()) {
            throw new FileNotFoundException();
        }
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isHidden()) {
                    return false;
                }
                //
                if (file.isFile()) {
                    if (file.isHidden()) {
                        return false;
                    } else if (!file.canRead()) {
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return true;
                }
            }
        });
        return Arrays.asList(files);
    }

    @APIDefine(description = "指定檔案是否存在?")
    public boolean isFileExist(String path, String filename) throws FileNotFoundException {
        File file = new File(String.format("%s/%s", ROOT, path), filename);
        return file.exists();
    }

    /**
     * 取得指定檔案
     *
     * @param path 指定路徑
     * @param filename 檔案名稱
     * @return 檔案
     */
    @APIDefine(description = "取得指定檔案")
    public File getFile(String path, String filename) throws FileNotFoundException {
        File file = new File(String.format("%s/%s", ROOT, path), filename);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        return file;
    }

    /**
     * 儲存指定檔案
     *
     * @param path 指定路徑
     * @param file 指定檔案
     * @return 檔案
     */
    @APIDefine(description = "儲存指定檔案")
    public boolean saveFile(String path, File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        //
        File target = new File(String.format("%s/%s", ROOT, path), file.getName());
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(target);
        // Copy
        try {
            BufferedInputStream input = new BufferedInputStream(fis);
            byte[] temp = new byte[4096];
            int size;
            while (true) {
                size = input.read(temp);
                if (size > 0) {
                    fos.write(temp, 0, size);
                } else {
                    break;
                }
            }
            fos.flush();
            fos.close();
            return true;
        } catch (IOException ex) {
            throw ex;
        }
    }

}
