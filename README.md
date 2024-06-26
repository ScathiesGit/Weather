# Проект "Погода"

Веб-приложение для просмотра текущей погоды. Пользователь может зарегистрироваться и добавить в коллекцию один или несколько локаций (городов, сёл, других пунктов), после чего главная страница приложения начинает отображать список локаций с их текущей погодой.

## Перед запуском в Intellij Idea запустить redis

## Технологии и инструменты
- Spring (MVC, Security)
- Postgres
- Redis 
- Maven
- Hibernate

## Функционал приложения

Работа с пользователями:
- Регистрация
- Авторизация
- Logout

Работа с локациями:
- Поиск
- Добавление в список
- Просмотр списка локаций, для каждой локации отображается название и температура
- Удаление из списка

### Главная страница

- Заголовок
    - Для неавторизованных пользователей - кнопки регистрации и авторизации
    - Для авторизованных пользователей - логин текущего пользователя и кнопка Logout
- Контент
    - Поле ввода для поиска локации по названию
    - Список добавленных локаций. Каждый элемент списка отображает название, текущую температуру и кнопку "удалить"

### Страница результатов поиска локаций по названию

Переход на эту страницу осуществляется в результате заполнения поля ввода на главной странице, либо на странице результатов поиска.

Содержимое:
- Заголовок, такой же как на главной странице
- Поле ввода для поиска по названию - такое же, как на главной странице, чтобы не возвращаться туда для каждого нового поиска
- Список найденных локаций с кнопкой "добавить". При нажатии на кнопку происходит переход на главную страницу

### Остальное

- Страницы с формами регистрации и авторизации

### Таблица `Users`

| Колонка  | Тип     | Комментарий                                                                                |
|----------|---------|--------------------------------------------------------------------------------------------|
| ID       | int     | Айди пользователя, автоинкремент, первичный ключ                                           |
| Login    | Varchar | Логин пользователя, username или email                                                     |
| Password | Varchar | Хранить пароль в открытом виде небезопасно, лучше использовать шифрование, например BCrypt |

### Таблица `Locations`

Локации пользователя, в которых он хочет знать погоду. Одна и та же локация может повторяться для нескольких пользователей.

| Колонка   | Тип     | Комментарий                                 |
|-----------|---------|---------------------------------------------|
| ID        | int     | Айди локации, автоинкремент, первичный ключ |
| Name      | Varchar | Название                                    |
| UserId    | int     | Пользователь, добавивший эту локацию        |
| Latitude  | Decimal | Широта локации                              |
| Longitude | Decimal | Долгота локации                             |


