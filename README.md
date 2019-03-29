<m2>**Translator**</m2>

For start application clone repo, then<br>
```
mvn spring-boot:run
```
*Try*<br>
<a href= "localhost:8080/translate?text=Эта%20программа%20работает?&from=ru&to=en">localhost:8080/translate?text=Эта%20программа%20работает?&from=ru&to=en</a><br>
<a href= "localhost:8080/translate?text=how%20to%20close%20this%20app%20without%20SIGKILL&from=en&to=ru">localhost:8080/translate?text=how%20to%20close%20this%20app%20without%20SIGKILL&from=en&to=ru</a>

*To see your request history use <br>
<a href="localhost:8080/translate/show_base">localhost:8080/translate/show_base</a><br>
(this app uses in-memory database hsqlDb)
