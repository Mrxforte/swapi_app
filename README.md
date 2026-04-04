# SWAPI Explorer (Jetpack Compose)

Профессиональное Android-приложение на Kotlin + Jetpack Compose по API [swapi.dev](https://swapi.dev/), построенное по Clean Architecture.

## Что реализовано

- Kotlin + Jetpack Compose
- Clean Architecture: data / domain / presentation
- DI: Hilt
- REST: Retrofit + Moshi
- Кеш: Room
- Offline-first поведение:
  - сначала показываем данные из локальной базы
  - затем обновляем из сети
  - если сети нет и кеш пустой, показываем понятное сообщение
- Splash screen
- Onboarding screen (первый запуск)
- Главный экран персонажей
- Детализация персонажа
- Settings экран:
  - переключение темы (System / Light / Dark)
- Profile экран:
  - редактирование имени пользователя
- Индикаторы загрузки
- Сообщение при пустом результате
- Навигация назад на всех вторичных экранах
- Портретная ориентация (portrait-only)

## Архитектура

### Data layer
- `remote`: API и DTO
- `local`: Room DB, DAO, entities
- `repository`: реализация репозиториев
- `preferences`: DataStore

### Domain layer
- `model`
- `repository` (контракты)
- `usecase`

### Presentation layer
- Compose UI, ViewModel, Navigation, Theme

## Нестандартные решения

- В `Splash` добавлена небольшая управляемая задержка, чтобы старт выглядел более естественно и не был "морганием".
- Цветовая палитра подобрана под sci-fi атмосферу, но без перегруза интерфейса.

## Запуск

1. Откройте проект в Android Studio (или VS Code + Android toolchain)
2. Выполните sync Gradle
3. Запустите `app` на эмуляторе/устройстве

## Дополнительно

Если потребуется, могу добавить:
- пагинацию для API
- pull-to-refresh
- unit-тесты для repository/use-case
- screenshot tests для Compose
