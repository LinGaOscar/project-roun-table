# The Round Table Project

## 專案簡介
**The Round Table** 是一個基於 Spring Boot 的課程協作與管理平台。本專案旨在提供一個直觀、安全的環境，讓行政人員能夠管理課程與場地，讓講師能夠展示專業背景，並讓用戶能夠輕鬆報名感興趣的課程。

## 核心功能
*   **課程管理 (Class Scheduling)**: 支援課程的新增、編輯、刪除，並可指派特定的場地與講師。
*   **多場地管理 (Venue Management)**: 管理教學空間，包含容納人數、地址與設備描述。
*   **講師個人頁面 (Lecturer Profile)**: 專屬的講師介紹頁面，展示 Bio、專業領域及其負責的所有課程。
*   **報名系統 (Enrollment System)**: 提供即時報名功能，具備名額限制檢查與重複報名驗證。
*   **安全性與權限控制**: 整合 Spring Security，實作基於角色的存取控制 (RBAC) 與 BCrypt 密碼加密。
*   **全局異常處理與日誌**: 統一的錯誤攔截機制與 SLF4J/Logback 日誌追蹤。

## 技術棧
*   **Backend**: Java 11, Spring Boot 2.6.4, Spring Security, Spring Data JPA
*   **Frontend**: Thymeleaf, Bootstrap 5, Vue 2 (部分組件), Font-Awesome
*   **Database**: MariaDB / MySQL
*   **Tools**: Lombok, Maven, SLF4J

## 快速啟動
### 1. 資料庫準備
請依序執行 `doc/` 目錄下的 SQL 腳本：
1.  執行 `doc/DB_TABLE.sql` 建立資料表結構。
2.  執行 `doc/DB_DATA.sql` 匯入初始資料。

### 2. 初始帳號 (密碼均為 1234)
| 帳號 | 角色 | 權限說明 |
| :--- | :--- | :--- |
| `admin` | ROLE_ADMIN | 擁有所有管理權限 (場地、用戶、課程) |
| `lecturer01` | ROLE_USER | 一般用戶，具備講師身分 |
| `user01` | ROLE_USER | 一般報名用戶 |

## 資料庫結構 (Database Schema)
本專案包含以下核心資料表：

| 資料表名稱 | 說明 | 關鍵欄位 |
| :--- | :--- | :--- |
| `venue` | **場地管理** | 名稱、容納人數、地址 |
| `sys_role` | **權限角色** | 角色代碼、功能描述 |
| `sys_user` | **使用者/講師** | 帳號、加密密碼、講師 Bio、頭像 |
| `class_table` | **課程排程** | 標題、時間、關聯場地與講師、名額限制 |
| `enrollment` | **報名紀錄** | 使用者與課程關聯、報名時間、狀態 |

### 3. 編譯與執行
```bash
mvn clean install
mvn spring-boot:run
```

## 日誌監控與診斷 (Logging & Diagnostics)
系統使用 SLF4J 與 Logback 進行日誌管理，所有運行資訊與錯誤紀錄均會被持久化。

### 1. 日誌位置
*   **主日誌檔**: `logs/roundtable.log`
*   **歷史日誌**: `logs/roundtable.yyyy-MM-dd.log` (每日自動切換，保留 30 天)

### 2. 日誌判讀方式
日誌格式如下：
`[時間] [執行緒] [級別] [類別名稱] - [訊息內容]`

*   **INFO**: 紀錄正常業務流程（如：用戶登入成功、儲存新課程）。
*   **WARN**: 紀錄預期內的業務異常（如：`BusinessException`、報名名額已滿、重複報名、登入失敗）。
*   **ERROR**: 紀錄系統崩潰或未預期的程式錯誤，包含完整的 **Stack Trace**，是排查 Bug 的主要依據。
*   **DEBUG**: 紀錄詳細的開發除錯資訊（目前針對 `com.javaclass.roundtable` 包開啟）。

## 專案演進紀錄 (DevOptimizer Highlights)
1.  **架構重構**: 將 Setter 注入改為 **Constructor Injection**，並實作介面導向設計。
2.  **效能優化**: 將課程排序邏輯從 Stream API 移至 **Database-level Sorting**。
3.  **安全性升級**: 移除手動 Session 檢查，全面整合 **Spring Security** 與 **BCrypt**。
4.  **UI 整合**: 引入 Thymeleaf Fragment 統一導覽列，並優化了報名回饋 UI。

---
*Developed & Optimized by 菱角小龍瞎 (DevOptimizer)*
