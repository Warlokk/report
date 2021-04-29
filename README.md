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
    * `"java -jar -Dspring.profiles.active=mySql aqa-shop.jar"` для работы с СУБД mySQL, также необходимо в классе OrderTest установить значение переменной `mySql = true`
1. Запустить тесты одним из способов:
    * из командной строки `"gradlew clean test allureReport"`
    * из интерфейса IntelliJ Idea в классе OrderTest.