# 本地開發

## 需求
- JDK 11
- Maven（或直接用內附的 `./mvnw` / `mvnw.cmd`，不需另外安裝）
- MariaDB 或 MySQL（本機或容器皆可）

## 資料庫設定
連線設定位於 `src/main/resources/application.properties`，目前預設指向 `jdbc:mariadb://localhost:3307/the_round_table`。

1. 準備一個可連線的 MariaDB/MySQL 實例，建立資料庫 `the_round_table`。
2. 依序執行 `doc/DB_TABLE.sql`（建表）與 `doc/DB_DATA.sql`（初始資料，含測試帳號）。
3. 確認 `application.properties` 內的 `spring.datasource.url` / `username` / `password` 與你的環境一致。

> **注意**：目前 `application.properties` 內含明碼帳密，且本 repo 為 public。本機測試可直接沿用，但正式部署或協作前建議改用環境變數（`SPRING_DATASOURCE_*`）或未進版控的 `application-local.properties` 覆蓋，避免明碼外洩。

## 啟動

```bash
mvn spring-boot:run
# 或
./mvnw spring-boot:run
```

預設監聽 8080（Spring Boot 預設值，專案未覆寫 `server.port`）。

## 測試帳號（密碼皆為 `1234`）
| 帳號 | 角色 |
| --- | --- |
| `admin` | ADMIN |
| `lecturer01` | TEACHER |
| `user01` | STUDENT |

## 測試

```bash
mvn test
```

目前僅有一個 Spring Boot context load smoke test（`RoundTableApplicationTests`），未涵蓋 controller/service 層邏輯。

## 結構筆記
- 前台頁面（首頁、課程列表、報名、講師頁）與後台頁面（`/admin/**`）的 controller 分開放，Thymeleaf 模板也對應分成 `templates/`（前台）、`templates/admin/`、`templates/teacher/` 三組。
- 權限判斷在 `WebSecurityConfig` 以 URL pattern 設定（非 method-level `@PreAuthorize`），新增頁面/API 時要記得同步加規則。
- 日誌輸出在 `logs/roundtable.log`（執行後自動產生，`.gitignore` 已排除 `logs/`）。
- `.metadata/`、`.mvn/` 為 IDE（STS/Eclipse）與 Maven wrapper 相關檔案，非核心程式碼。
