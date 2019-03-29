*Translator*

For start application clone repo, then build<br>
```
mvn spring-boot:run
```

try<br>
localhost:8080/translate?text=Эта%20программа%20работает?&from=ru&to=en<br>
localhost:8080/translate?text=how%20to%20close%20this%20app%20without%20SIGKILL&from=en&to=ru
 LOCALHOST:8080/RU/EN/SOME TEXT
to see your request history use <br>
localhost:8080/translate/show_base <br>
(this app uses in-memory database hsqlDb)
