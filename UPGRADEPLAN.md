# The Round Table 架構升級計劃

## 目標

將現有的單站式架構分離為「前台用戶端」與「後台中控台」兩套體系，實現權限導向的路由分離。

---

## 1. URL 路由分離設計

### 前台 (User Portal)

| 功能 | URL |
|------|-----|
| 首頁 | `/` |
| 課程列表 | `/class` |
| 課程詳情 | `/class/{id}` |
| 講師頁面 | `/lecturer/{id}` |
| 我的課程 | `/my/classes` |
| 報名 | `/enroll/{classId}` |
| 登入/登出 | `/login`, `/logout` |

### 後台 (Admin Console)

| 功能 | URL |
|------|-----|
| 儀表板 | `/admin` |
| 課程管理 | `/admin/class` |
| 課程編輯 | `/admin/class/edit/{id}` |
| 場地管理 | `/admin/venue` |
| 場地編輯 | `/admin/venue/edit/{id}` |
| 用戶管理 | `/admin/user` |
| 用戶編輯 | `/admin/user/edit/{id}` |
| 講師管理 | `/admin/lecturer` |

---

## 2. 權限模型

| 角色 | 權限說明 |
|------|----------|
| `ROLE_STUDENT` (學生) | 瀏覽課程、報名課程、查看已報名課程 |
| `ROLE_TEACHER` (老師) | 具備學生權限 + 創建自己的課程 + 管理個人資料 + 專屬講師頁面 |
| `ROLE_ADMIN` (管理員) | 完整後台權限：管理所有學生、老師、課程、教室 |

### 角色功能對照表

| 功能 | 學生 | 老師 | 管理員 |
|------|:----:|:----:|:------:|
| 瀏覽課程列表 | ✓ | ✓ | ✓ |
| 報名課程 | ✓ | ✓ | - |
| 查看已報名課程 | ✓ | ✓ | - |
| 創建課程 | - | ✓ (僅自己的) | ✓ (全部) |
| 編輯課程 | - | ✓ (自己的) | ✓ (全部) |
| 刪除課程 | - | ✓ (自己的) | ✓ (全部) |
| 管理教室/場地 | - | - | ✓ |
| 管理用戶 (學生/老師) | - | - | ✓ |
| 發起後台 /admin | - | - | ✓ |

### Security 路由規則

```java
.antMatchers("/css/**", "/js/**", "/images/**", "/login").permitAll()
.antMatchers("/admin/**").hasRole("ADMIN")
.antMatchers("/teacher/**").hasAnyRole("TEACHER", "ADMIN")
.antMatchers("/my/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
.antMatchers("/enroll/**").hasAnyRole("STUDENT", "TEACHER")
.antMatchers("/class/**").permitAll()
.anyRequest().authenticated()
```

---

## 3. 程式碼調整清單

### 3.1 Security 配置

**檔案**: `src/main/java/com/javaclass/roundtable/config/WebSecurityConfig.java`

- 新增 `/admin/**` 路徑規則，限定 ADMIN 角色存取
- 新增 `/lecturer/**` 路徑規則，限定 LECTURER/ADMIN 角色
- 調整 login success 路由邏輯（依角色導向不同頁面）

### 3.2 控制器重構

| 現有控制器 | 目標路徑 | 說明 |
|------------|----------|------|
| `ClassTableController` | `/admin/class` | 後台課程管理 (Admin) |
| `ClassTableController` | `/teacher/class` | 老師創建/管理自己的課程 |
| `VenueController` | `/admin/venue` | 後台場地管理 (Admin) |
| `UserTableController` | `/admin/user` | 後台用戶管理 (Admin) |
| `MyClassesController` | `/my/classes` | 前台我的課程 |
| `LecturerController` | `/teacher/{id}` | 前台講師頁面 |
| 新增 `AdminController` | `/admin` | 後台儀表板 |

### 3.3 老師課程權限控制

老師只能管理自己創建的課程，須在 Service 層過濾：

```java
// ClassTableService 新增方法
List<ClassTable> findByTeacherId(Long teacherId);
void deleteIfOwner(Long classId, Long teacherId);
```

### 3.3 視圖目錄重組

**現有結構**:
```
templates/
├── index.html
├── class_table.html
├── venue_table.html
├── user_table.html
└── ...
```

**目標結構**:
```
templates/
├── index.html                 # 前台首頁
├── class_table.html           # 前台課程列表
├── my_classes.html           # 前台我的課程
├── lecturer_profile.html      # 前台講師頁面
├── login.html                 # 登入頁（共用）
├── error.html                 # 錯誤頁
├── admin/
│   ├── index.html             # 後台儀表板
│   ├── class_table.html       # 課程管理
│   ├── class_edit.html        # 課程編輯
│   ├── venue_table.html       # 場地管理
│   ├── venue_edit.html        # 場地編輯
│   ├── user_table.html        # 用戶管理
│   ├── user_edit.html         # 用戶編輯
│   └── lecturer_table.html    # 講師管理
└── fragments/
    ├── navbar.html            # 前台導覽列
    ├── admin_sidebar.html     # 後台側邊欄
    └── admin_navbar.html      # 後台導覽列
```

### 3.4 導覽列權限顯示

**前台 navbar.html**:
- 未登入: 顯示「登入」
- STUDENT: 顯示「課程」、「我的課程」、「登出」
- TEACHER: 顯示「課程」、「我的課程」、「講師頁面」、「登出」
- ADMIN: 顯示「課程」、「我的課程」、「後台管理」、「登出」

**後台 admin_sidebar.html**:
- 僅 ADMIN 可見
- 包含：儀表板、課程管理、場地管理、用戶管理

---

## 4. 實作順序

### Phase 1: Security 與路由
1. 修改 `WebSecurityConfig.java` 新增權限規則
2. 新增 `/admin` 儀表板控制器與頁面
3. 調整現有管理頁面路徑轉發

### Phase 2: 視圖重組
1. 建立 `templates/admin/` 目錄
2. 移動管理頁面至 admin 目錄
3. 建立後台 Layout (sidebar + navbar)

### Phase 3: 導覽列優化
1. 重構 navbar.html 加入 Thymeleaf sec:authorize
2. 建立 admin_sidebar.html
3. 測試各角色登入後的選單顯示

### Phase 4: 控制器對應
1. 將 ClassTableController @RequestMapping 改為 `/admin/class`
2. 將 VenueController @RequestMapping 改為 `/admin/venue`
3. 將 UserTableController @RequestMapping 改為 `/admin/user`
4. 確保所有表單 action 路徑正確

---

## 5. 預期成果

- 前台 URL 簡潔直觀，適合一般用戶操作
- 後台 URL 以 `/admin` 開頭，易於權限管理
- 角色導向的選單顯示，提升使用體驗
- 未來可進一步擴充為獨立部署的 SPA 後台
