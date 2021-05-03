# **Процедура запуска тестов**

## **Предусловия**

Для запуска тестов должны быть установлены:

1. IntelliJ Idea Ultimate
1. Docker Desktop
1. Java не ниже версии 8.0

## **Порядок действий**

1. Клонировать [репозиторий](https://github.com/Warlokk/QA-Diploma),
1. Запустить контейнеры Node.Js, postgreSQL, mySql с помощью `"docker-compose up"`
1. Запустить приложение из папки `artifacts` командой:
    * `"java -jar aqa-shop.jar"` для работы с СУБД postgreSQL
    * `"java -jar -Dspring.profiles.active=mysql aqa-shop.jar"` для работы с СУБД mySQL
1. Запустить тесты одним из способов:
    * из командной строки `"gradlew clean test --info"` для работы с СУБД postgreSQL
    * из командной строки `"gradlew clean test -Durl="jdbc:mysql://localhost:3306/mysql" --info"` для работы с СУБД mySQL
1. Запустить создание отчета **AllureReport** командой `gradlew allureReport` и(опционально) `gradlew allureServe` для запуска отчёта.

[![Java CI with Gradle](https://github.com/Warlokk/QA-Diploma/actions/workflows/gradle.yml/badge.svg)](https://github.com/Warlokk/QA-Diploma/actions/workflows/gradle.yml)