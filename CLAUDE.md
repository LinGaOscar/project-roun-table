# CLAUDE.md

Spring Boot 課程協作與管理平台（The Round Table），提供學生/老師/管理員三種角色的課程報名與後台管理。

## Commands

```bash
# 編譯與安裝
mvn clean install
./mvnw clean install   # 或使用 Maven Wrapper（免本機安裝 Maven）

# 啟動（預設 port 8080，需先備妥資料庫，見下方 Architecture）
mvn spring-boot:run
./mvnw spring-boot:run

# 測試
mvn test
```

## Architecture

- **框架**：Spring Boot 2.6.4 + Java 11，MVC 分層：`controller` → `service`/`service impl` → `repository`（Spring Data JPA）→ `entity`。
- **套件結構**（`src/main/java/com/javaclass/roundtable/`）：
  - `controller/`：前台（首頁、課程列表、報名、講師頁、老師端）與後台 `Admin*Controller`（課程/用戶/場地管理）分離。
  - `entity/`：`SysUser`、`SysRole`、`ClassTable`（課程）、`Venue`（場地）、`Enrollment`（報名紀錄）。
  - `service` / `service/*ServiceImpl`：介面 + 實作分離，建構子注入。
  - `repository/`：Spring Data JPA repository。
  - `config/WebSecurityConfig.java`：Spring Security 設定，`formLogin` + BCrypt，路由層級 RBAC（`/admin/**` → ADMIN、`/teacher/**` → TEACHER/ADMIN、`/my/**`、`/enroll/**` 等），CSRF 目前為 disable。
  - `exception/`：`BusinessException` + `GlobalExceptionHandler` 全局例外處理。
- **View 層**：Thymeleaf 模板（`src/main/resources/templates/`），前台/後台/老師端各自獨立目錄，`fragments/` 放共用 navbar/sidebar；靜態資源含 Bootstrap、jQuery、Vue 2（部分頁面用於互動）。
- **資料庫**：MariaDB/MySQL，連線設定在 `src/main/resources/application.properties`；`doc/DB_TABLE.sql`、`doc/DB_DATA.sql` 提供建表與初始資料（含 admin/lecturer01/user01 三個測試帳號，密碼皆為 `1234`）。
- **日誌**：SLF4J + Logback（`logback-spring.xml`），輸出至 `logs/roundtable.log`，每日輪替保留 30 天。
- **⚠️ 安全性提醒**：`application.properties` 目前內含明碼資料庫帳密，且 repo 為 public——正式環境或提交前應改用環境變數 / `application-local.properties`（加入 `.gitignore`）覆蓋，勿讓明碼密碼留在版控中。
