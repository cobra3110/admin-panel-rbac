# Кадровый учёт с админ-панелью и динамическими правами доступа

Учебный проект на **Java 17 + Spring Boot 3**, демонстрирующий:
-  динамические права доступа (RBAC) с настройкой через админ-панель;
-  принципы **SOLID** на примере реального кода;
-  четыре паттерна проектирования: **Стратегия**, **Фабрика**, **Строитель**, **Наблюдатель**.

##  Быстрый старт

```bash
git clone https://github.com/your-username/admin-panel-rbac.git
cd admin-panel-rbac
./mvnw spring-boot:run
```

Откройте [http://localhost:8080](http://localhost:8080)

### Тестовые учётные записи

| Логин | Пароль | Роль | Права |
|---|---|---|---|
| `admin` | `admin123` | ADMIN | Все права |
| `hr_manager` | `hr123` | HR_MANAGER | Просмотр, создание, редактирование сотрудников и документов |
| `clerk` | `clerk123` | CLERK | Только просмотр |

##  Архитектура

- **Модель прав:** RBAC (User → Role → Permission)
- **Динамическая загрузка:** права загружаются из БД в `CustomUserDetailsService`
- **Админ-панель:** управление ролями и разрешениями через веб-интерфейс
- **Два уровня защиты:** URL-правила в `SecurityConfig` + `@PreAuthorize` на методах

##  Принципы SOLID

| Принцип | Реализация |
|---|---|
| **S** — Single Responsibility | `EmployeeValidator`, `EmployeeService`, `EmployeeController` — каждый класс отвечает за одну задачу |
| **O** — Open/Closed | Новые форматы экспорта добавляются через `ExportStrategy` без изменения существующего кода |
| **L** — Liskov Substitution | Все реализации `ValidationStrategy<T>` взаимозаменяемы |
| **I** — Interface Segregation | Узкие интерфейсы `ValidationStrategy<T>`, `ExportStrategy<T>` |
| **D** — Dependency Inversion | `EmployeeService` зависит от `ValidationStrategy`, а не от конкретной реализации |

##  Паттерны проектирования

### 1. Стратегия (Strategy) — `service/validation/`
Инкапсуляция алгоритмов валидации для разных сущностей через `ValidationStrategy<T>`.

### 2. Фабрика (Factory) — `factory/`
Создание стратегий экспорта (CSV/PDF) через `ExportFactory`, который автоматически собирает все реализации из Spring-контекста.

### 3. Строитель (Builder) — `dto/EmployeeDTO`
Пошаговое создание иммутабельного DTO с валидацией на этапе `build()`.

### 4. Наблюдатель (Observer) — `event/`
Публикация событий через `ApplicationEventPublisher` и подписка через `@EventListener` (аудит + email-уведомления).

##  Технологии

- Java 17
- Spring Boot 3.2
- Spring Security 6
- Spring Data JPA
- H2 Database
- Thymeleaf + thymeleaf-extras-springsecurity6
- Maven

##  Лицензия

MIT