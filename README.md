# metric-android-sample

Demo Android-приложение, раскрывающее функциональные возможности
[Metric](https://github.com/biosshot/metric) — Sentry-совместимой observability-платформы
на Rust — через официальный Sentry Android SDK.

Сопутствующий репозиторий: [metric-sentry-alternative-yc-k8s](https://github.com/patsevanton/metric-sentry-alternative-yc-k8s).

## Что демонстрирует

| Экран | Возможности Metric |
|-------|--------------------|
| Ошибки и крэши | `captureException`, необработанный крэш, уровни (info/warning/error), кастомное сообщение |
| Трейсы / Performance | ручная транзакция, вложенные спаны, медленная операция, HTTP-запрос (спаны OkHttp) |
| Релизы и сессии | текущий release/environment, перезапуск и завершение сессии, событие с тегом release |
| Source maps / ProGuard | деобфускация стектрейса через намеренный крэш в release-сборке |
| Breadcrumbs / контекст | breadcrumbs, теги, extra, user, setContext, scope |
| User feedback | user feedback, attachments, скриншоты |
| Продвинутые механики | кастомный span, fingerprint (группировка), beforeSend (изменение/отбрасывание) |

## Стек

- Kotlin + Jetpack Compose (Material 3), Navigation Compose
- `minSdk 26`, `compileSdk 35`
- Sentry Android SDK **8.50.1** (`sentry-android`, `sentry-okhttp`, `sentry-compose-android`)
- Sentry Android Gradle Plugin **6.21.0**

## Сборка

Требования: JDK 17, Android SDK (platform 35, build-tools 35.0.1).

```bash
# debug-сборка (ProGuard отключён)
./gradlew assembleDebug
```

### Release-сборка с ProGuard-маппингом

Release-сборка включает R8/ProGuard. Sentry Gradle Plugin автоматически создаёт release
и загружает ProGuard mapping-файл в Metric (для деобфускации стектрейсов).

Создайте `sentry.properties` из примера и заполните токен:

```bash
cp sentry.properties.example sentry.properties
# заполните defaults.url, defaults.org, defaults.project, auth.token
```

Либо задайте переменные окружения:

```bash
export SENTRY_AUTH_TOKEN=... \
       SENTRY_ORG=... \
       SENTRY_PROJECT=... \
       SENTRY_URL=https://metric.example.com
```

Затем:

```bash
./gradlew assembleRelease
```

## Запуск

1. Установите APK на устройство/эмулятор.
2. На экране настройки вставьте DSN из Metric (проект → **Connect an SDK**).
   DSN имеет вид `https://PROJECT_KEY@metric.example.com/PROJECT_ID`.
3. Нажмите «Подключить». SDK инициализируется, DSN сохраняется локально.
4. Откройте нужную категорию и нажмите кнопку-триггер; событие/трейс уйдёт в Metric.

События и трейсы появляются в Metric асинхронно (Issues, Traces). Проверяйте спустя
несколько секунд.

## Важные ограничения Metric 0.1.6

- **Профилирование не поддерживается.** В [Known limits](https://biosshot.github.io/metric/known-limits)
  указано «Profiling is not supported», а в
  [Supported features](https://biosshot.github.io/metric/supported-capabilities) — вне текущего
  roadmap. Поэтому в приложении `profilesSampleRate` не задаётся, и профили в demo не используются.
- **ProGuard.** Metric поддерживает базовый mapping/source maps через Symbolicator
  (профили Medium/High), но «Advanced ProGuard processing is not included». Базовую
  деобфускацию демо покрывает.
- **Session Replay.** Поддерживается только browser SDK (`@sentry/browser`); в Android demo
  не включается.
- **Совместимость SDK.** Android официально не заявлен в
  [SDK compatibility](https://biosshot.github.io/metric/compatibility); протестирован
  `sentry-java` **8.50.1** (тот же код, что и Android SDK). Поэтому SDK закреплён на 8.50.1,
  а не на последней версии.

## Структура

- `app/src/main/java/com/example/metricdemo/` — исходный код
  - `SentryManager.kt` — инициализация SDK из DSN, beforeSend
  - `Demos.kt` — триггеры ошибок, контекста, фидбека
  - `Traces.kt` — транзакции, спаны, HTTP
  - `*Screen.kt` — экраны демо
- `app/build.gradle.kts` — конфигурация Sentry Gradle Plugin
- `sentry.properties.example` — шаблон конфигурации для загрузки mapping
