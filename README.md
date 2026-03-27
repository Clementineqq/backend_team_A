# backend_team_A
HARRO EVERUNYAN

пипипиппупу
я не дизигнер, но для теста докера такие-то команды



# 1. собрать образы
docker-compose build --no-cache

# 1.2. собрать проект (пропустить тесты, если ошибки ибанут)
mvn clean package -DskipTests

# 3. запустить
docker-compose up -d

# остановить
docker-compose down

# остановить + удалить данные БД
docker-compose down -v

# смотреть логи
docker-compose logs -f


# шобы просто локально запустить
mvn spring-boot:run