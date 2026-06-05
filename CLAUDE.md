# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build
./mvnw clean package

# Run (requires MariaDB on localhost:3307)
./mvnw spring-boot:run

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=RoundTableApplicationTests

# Skip tests during build
./mvnw clean package -DskipTests
```

## Architecture

Spring Boot MVC application with Thymeleaf templating. Three user roles: STUDENT, TEACHER, ADMIN.

**Package root**: `com.javaclass.roundtable`

**Layer pattern**: `Controller → Service (interface + impl) → Repository (Spring Data JPA) → Entity`

**Key packages:**
- `controller/` — 13 controllers split between public, user (`/my/**`), teacher (`/teacher/**`), and admin (`/admin/**`) namespaces
- `service/` — Interface-first services; use constructor injection, not `@Autowired` field injection
- `entity/` — 5 JPA entities: `SysUser`, `SysRole`, `Venue`, `ClassTable`, `Enrollment`
- `repository/` — Spring Data JPA repositories with custom query methods
- `exception/` — `BusinessException` (checked) + `GlobalExceptionHandler` for controller advice
- `config/WebSecurityConfig.java` — Role-based route guards; `/admin/**` = ADMIN, `/teacher/**` = TEACHER+ADMIN, `/my/**` = all authenticated

**Templates**: `src/main/resources/templates/` — Thymeleaf with Bootstrap 5. Fragments are used for nav/layout reuse.

**Static assets**: `src/main/resources/static/`

## Database

MariaDB, default port **3307** (not 3306 — see `application.properties`). Schema and seed data live in `doc/DB_TABLE.sql` and `doc/DB_DATA.sql`.

Default test credentials: `admin/1234`, `lecturer01/1234`, `user01/1234`.

## Logging

SLF4J/Logback. DEBUG level for `com.javaclass.roundtable`. Logs written to `logs/roundtable.log` (daily rollover, 30-day retention). Config: `src/main/resources/logback-spring.xml`.

## Planned Refactor

`UPGRADEPLAN.md` documents a planned frontend/backend separation with URL restructuring and controller reorganization. Check it before making architectural changes to avoid conflicts with the upgrade path.
