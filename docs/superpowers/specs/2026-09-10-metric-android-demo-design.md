# Metric Android Demo — дизайн

Дата: 2026-09-10
Статус: утверждён

## Цель

Demo-приложение для Android, которое раскрывает функциональные возможности
[Metric](https://github.com/biosshot/metric) — Sentry-совместимой observability-платформы
на Rust — через официальный Sentry Android SDK. Приложение служит наглядной демонстрацией:
ошибки, трейсы, релизы, ProGuard-деобфускация, контекст/breadcrumbs, user feedback и
продвинутые механики.

## Стек и версии

| Компонент | Выбор |
|-----------|-------|
| Язык | Kotlin |
| UI | Jetpack Compose (Material 3) |
| Навигация | Navigation Compose (многоэкранная) |
| minSdk | 26 |
| target SDK | актуальный (последний стабильный) |
| Язык интерфейса | Русский |
| Sentry SDK | `io.sentry:sentry-android` **8.50.1** (+ okhttp / navigation-интеграции) |
| Sentry Gradle Plugin | `io.sentry.android.gradle` **6.21.0** |
| DSN | Ввод в UI → DataStore → `SentryAndroid.init` |

Версия SDK 8.50.1 закреплена сознательно: в документации Metric 0.1.6 в качестве
протестированного указан `sentry-java` 8.50.1. Android SDK — тот же `sentry-java`.
Более новые ветки (9.x/10.x) не протестированы с Metric.

## Экраны

1. **Настройка** — поле ввода DSN, кнопка «Подключить», статус инициализации,
   кнопка «Сбросить».
2. **Главная** — список категорий фич.
3. **Ошибки и крэши** — `captureException`, необработанный крэш, уровни
   (warning/info/error), исключение с кастомным сообщением.
4. **Трейсы / Performance** — ручная транзакция, вложенные/дочерние спаны,
   медленная операция, HTTP-запрос (настраиваемый URL, по умолчанию httpbin.org).
5. **Релизы и сессии** — отображение текущего release, сброс/перезапуск сессии,
   release health.
6. **Source maps / ProGuard** — деобфускация через намеренный крэш в release-сборке
   (mapping грузит Gradle Plugin).
7. **Breadcrumbs / контекст** — breadcrumbs, теги, extra, user (id/email),
   setContext, изменения scope.
8. **User feedback** — диалог user feedback, attachments (скриншот/текст).
9. **Продвинутые механики** — кастомный span, семплинг, fingerprint/группировка,
   beforeSend-фильтрация.

## Ограничения Metric 0.1.6 (фиксируются в README)

- **Профилирование не поддерживается** (в Known limits: "Profiling is not supported",
  в Supported features: "Outside the current roadmap"). Поэтому `profilesSampleRate`
  не используется; demo ограничено трейсами.
- **ProGuard**: Metric поддерживает базовый mapping/source maps через Symbolicator
  (Medium/High), но "Advanced ProGuard processing is not included".
- **Session Replay** — только browser SDK (`@sentry/browser`), в Android demo не включается.
- **Android официально не заявлен** в SDK compatibility (протестирован `sentry-java`
  8.50.1), поэтому SDK закреплён на 8.50.1.

## Механики реализации

- **DSN**: стартовый экран; ввод → сохранить в DataStore → `SentryAndroid.init` с
  `dsn`, `release`, `environment`, `tracesSampleRate`.
- **HTTP**: OkHttp + Sentry OkHttp-интеграция (спаны запросов автоматически) +
  ручной span.
- **ProGuard/release**: release-сборка с включённым R8; Sentry Gradle Plugin с
  `authToken` из `sentry.properties`/env (`SENTRY_AUTH_TOKEN`), `org`, `project`,
  авто-загрузка mapping + source context.
- **Attachments**: `Sentry.captureScreenshot` + кастомный attachment.

## Файлы

- Gradle KTS: `settings.gradle.kts`, `build.gradle.kts`, `app/build.gradle.kts`,
  `gradle/libs.versions.toml`, `gradle.properties`.
- `sentry.properties` — git-ignored (содержит auth token).
- Код: `MainActivity`, `App`, `NavGraph`, `SentryManager`, экраны-демо, `strings.xml`.
- `README.md` — сборка, ввод DSN, сценарий демонстрации каждой фичи, примечания об
  ограничениях (профилирование и пр.).
