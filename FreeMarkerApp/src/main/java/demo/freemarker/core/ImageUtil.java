package demo.freemarker.core;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * 通用圖片處理工具類
 */
public class ImageUtil {

    /**
     * 儲存圖片並返回URL
     * @param imageData 圖片的 byte 陣列
     * @param uploadDir 根目錄
     * @param category 分類目錄 (如: "lesson/123", "steamImages/456")
     * @return 存儲後的圖片 URL
     * @throws IOException 發生錯誤時拋出異常
     */
    public static String saveImage(byte[] imageData, String uploadDir, String category) throws IOException {
        try {
            // 確保分類目錄存在
            Path categoryPath = Paths.get(uploadDir, category);
            if (!Files.exists(categoryPath)) {
                Files.createDirectories(categoryPath);
            }

            // 檢測圖片格式
            String fileExtension = detectImageFormat(imageData);
            if (fileExtension == null) {
                throw new IllegalArgumentException("無效的圖片格式");
            }

            // 生成唯一檔名
            String fileName = UUID.randomUUID().toString() + fileExtension;
            Path filePath = categoryPath.resolve(fileName);

            // 儲存圖片到伺服器
            Files.write(filePath, imageData);
            if (!Files.exists(filePath) || Files.size(filePath) != imageData.length) {
                throw new IOException("圖片儲存失敗，檔案大小異常");
            }

            // 確保返回的相對路徑使用 `/`
            String relativePath = filePath.toString().replace(uploadDir, "").replace("\\", "/");
            return "/html/dtxstore/images/lessons" + relativePath;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("儲存圖片時發生錯誤: " + e.getMessage());
        }
    }

    /**
     * 刪除圖片檔案
     * @param uploadDir 根目錄
     * @param imageUrl  要刪除的圖片 URL
     * @return 是否成功刪除
     */
    public static boolean deleteImageFile(String uploadDir, String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return false;
        }

        try {
            // 取得相對路徑
            String relativePath = imageUrl.replace("/html/dtxstore/images/lessons", "");
            Path imagePath = Paths.get(uploadDir, relativePath);

            // 確保路徑合法，避免誤刪除非授權路徑
            if (imagePath.toAbsolutePath().startsWith(Paths.get(uploadDir).toAbsolutePath())) {
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                    System.out.println("DEBUG: 刪除圖片檔案成功: " + imagePath);
                    return true;
                } else {
                    System.out.println("DEBUG: 圖片檔案不存在: " + imagePath);
                }
            } else {
                System.err.println("ERROR: 非法圖片路徑，防止刪除錯誤文件: " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 檢測圖片格式並返回副檔名
     * @param imageData 圖片的 byte 陣列
     * @return 副檔名 (.jpg, .png, .gif) 或 null
     */
    public static String detectImageFormat(byte[] imageData) {
        if (imageData == null || imageData.length < 8) { 
            return null;
        }

        try {
            if (imageData[0] == (byte) 0xFF && imageData[1] == (byte) 0xD8 && imageData[2] == (byte) 0xFF) {
                return ".jpg";
            } else if (imageData[0] == (byte) 0x89 && imageData[1] == (byte) 0x50 &&
                       imageData[2] == (byte) 0x4E && imageData[3] == (byte) 0x47) {
                return ".png";
            } else if (imageData[0] == (byte) 0x47 && imageData[1] == (byte) 0x49 &&
                       imageData[2] == (byte) 0x46 && imageData[3] == (byte) 0x38) {
                return ".gif";
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
