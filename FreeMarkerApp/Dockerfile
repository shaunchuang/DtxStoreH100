# 選擇與 JDK 相同版本的官方 JRE 基底映像
FROM eclipse-temurin:17-jre

# 建議放在乾淨資料夾，方便查看
WORKDIR /app

# 將 target/ 內所有檔案複製進映像
# 假設 Dockerfile 與 target/ 同層
COPY target/ ./

# 預設啟動指令
CMD ["java", "-Djava.awt.headless=true", "-jar", "FreeMarkerApp-1.0.0.jar"]