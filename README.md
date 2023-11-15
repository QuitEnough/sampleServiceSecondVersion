# Sample Spring Boot Service SECOND VERSION

### Описание
Проект - имитация backend стороны продуктового сайта. В проекте есть:
* DAO layer,
* controllers layer,
* service layer,
* валидация входных данных в контроллере,
* логирование,
* кастомные exception,
* покрытие unit-тестами на 80%

### Endpoint-ы
В проекте 2 REST API endpoint-а:
* Первый endpoint возвращает product ID и name всех продуктов, у которых калорий меньше чем {данных, введенных в контроллере}.
* Первый endpoint возвращает product ID, name, and quantity, у которых количество больше, чем {quantity, взятое из запроса SQL}.

### Стек технологий
* Java
* Spring Boot
* PostgreSQL
* Flyway migration
* Docker


