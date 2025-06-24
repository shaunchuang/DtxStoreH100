# 選擇與 JDK 相同版本的官方 JRE 基底映像
FROM eclipse-temurin:17-jre

WORKDIR /app

# 複製 target 內所有檔案
COPY target/ ./

# 複製上一層的設定檔、金鑰、靜態資源與資料夾
COPY ../*.properties ./
COPY ../*.keystore ./
COPY ../html ./html
COPY ../ftl ./ftl
COPY ../logs ./logs
COPY ../upload ./upload

# 預設啟動指令
CMD ["java", "-Djava.awt.headless=true", "-jar", "FreeMarkerApp-1.0.0.jar"]
