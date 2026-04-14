# backend_team_A
HARRO EVERUNYAN
Здешняя инструкция актуальна только для тестирования самого бэка, прикрепляю ссылку на типадеплой:
https://github.com/Clementineqq/tipa_deploy_team_A




 

# 1. собрать образы
docker-compose build --no-cache

# 1.1. Если на камплюктере нема мавена, то 
mvnw.cmd clean package -DskipTests

# 1.2. а если есть, то собрать проект (-пропустить тесты, если ошибки ибанут)
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
