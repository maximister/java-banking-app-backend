# JBank
JBank — Веб-приложение для симуляции банковских операций.
В данном репозитории располагается серверная чать приложения. клиентская часть расположена по [ссылке](https://github.com/maximister/java-banking-app-frontend)

Приложение развернуто и доступно по [ссылке](http://51.250.23.163:3000/)

### Описание
Веб-приложение для симуляции банковских переводов. Поддерживает операции создания счета, выпуска карты, переводов между своими счетами, переводы другим пользователям и генерацию платежных qr-кодов.

Примеры работы приложения ниже:
- главная страница
![image](https://github.com/user-attachments/assets/7b4fc243-c31e-4f20-aed8-528a0336139d)
- открытый счет с привязанной картой
![image](https://github.com/user-attachments/assets/c9d99022-6c2c-4d6b-b989-f9cb1370f97c)
- создание qr-кода
![image](https://github.com/user-attachments/assets/95980753-583a-4f5f-becc-38499d88b999)
- перевод по qr-коду
![image](https://github.com/user-attachments/assets/b83769a4-1325-40a1-85a8-b49883a73b52)
- перевод на другой счет
![image](https://github.com/user-attachments/assets/4dfb2058-82fe-49cc-ab45-b41778267d88)

## Стэк
- Java 21
- Spring Boot
- mongodb
- PostgreSQL
- Kafka
- Next.js
- Docker compose

## Архитектура
Приложение состоит из серверной части, реализованной с помощью Spring Framework, и клиентской части на Next.js.

Серверная часть состоит из нескольких компонентов:
- Auth service - приложение для авторизации пользователей;
- Client service - приложение для работы с банковским  аккаунтом пользователя;
- Accounting service - приложение для управления счетами и транзакциями;
- Notification service - приложение для отправки почтовых уведомлений пользователям;
- Gateway - служит единой точкой входа в серверную часть;
- Discovery service - помогает отслеживать актуальные адресы сервисов и их состояние для правильного роутинга в Gateway приложении.

Общая схема приложения представлена на слегка шакальном рисунке ниже:

![C4](https://github.com/user-attachments/assets/3d612da7-3af4-4e5d-9e22-341b62ea4352)

  
## Как запустить?
0) Убедиться, что у вас установлен maven, java, docker, npm;
1) Склонировать репозиторий серверной части:
```
git clone https://github.com/maximister/java-banking-app-backend.git
```
2) перейти в папку проекта и сбилдить проект:
```
cd java-banking-app-backend
 mvn clean install
```
3) Поднять docker-compose:
```
docker compose up
# или
docker compose up -d # запуск в фоне
```
Сервер будет доступен по адресу [http://localhost:8888/](http://localhost:8888/)

4) Склонировать репозиторий клиентской части:
```
git clone https://github.com/maximister/java-banking-app-frontend.git
```
5) Перейти в директорию проекта и установить зависимости:
```
cd java-banking-app-frontend
npm install
```
6) Сбилдить приложение:
```
npm run build
```
7) Запустить приложение:
```
npm start
```
Приложение будет доступно по адресу [http://localhost:3000/](http://localhost:3000/)

**Примечания**:
* Клиентская часть приложения будет ожидать сервер по адресу [http://localhost:8888/](http://localhost:8888/)
* Для корректной работы почтовых уведомлений необходимо в docker-compose.yaml передать корректный email (обязательно gmail адрес) и пароль для приложения [тут](https://github.com/maximister/java-banking-app-backend/blob/master/docker-compose.yml#L191)
* создать пароль от почты для приложения можно по [ссылке](https://myaccount.google.com/apppasswords) (необходима включенная двухфактоная авторизация)

## Контакты
Автор: Ревенко Максим Сергеевич
Email: [revenkomacsim@yandex.ru](mailto:revenkomacsim@yandex.ru)
