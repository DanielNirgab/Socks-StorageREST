
# Автоматизация учета склада носков

REST приложение для автоматизации учёта носков на складе магазина

### Функциональность

Кладовщик имеет возможность:

- учесть приход и отпуск носков;
- узнать общее количество носков определенного цвета и состава в данный момент времени.

### Документация

- Open API определение SwaggerUI: http://localhost:8080/api/swagger-ui/index.html
- JavaDoc доступен: https://danielnirgab.github.io/Socks-StorageREST/javadoc/
- Проект использует контейнеризацию с помощью Docker Compose для PostgreSQL

### Список URL HTTP-методов
Параметры запроса передаются в URL:

- color — цвет носков, строка;
- operation — оператор сравнения значения количества хлопка в составе носков, одно значение из: moreThan, lessThan, equal;
- cottonPart — значение процента хлопка в составе носков из сравнения.

POST /api/socks/income - Регистрирует приход носков на склад.

POST /api/socks/outcome - Регистрирует отпуск носков со склада

GET /api/socks?color=red&operation=moreThan&cottonPart=90 - Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса

#### Примеры запросов:

- /api/socks?color=red&operation=moreThan&cottonPart=90 — должен вернуть общее количество красных носков с долей хлопка более 90%;
- /api/socks?color=black&operation=lessThan?cottonPart=10 — должен вернуть общее количество черных носков с долей хлопка менее 10%.