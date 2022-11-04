# I.Question API

## Installatie

### Stap 1
+ Clone de repository via

```
git clone https://github.com/IPSEN2/iQuestion-API.git
cd ./iQuestion-API
```


### Stap 2
+ Creëer een bestand met de lokale configuratie (vul deze aan):

`./src/main/resources/application.properties`
```
jwt_secret=

spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=email
spring.mail.password=app_password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

server.error.include-message=always
```

### Stap 3
Download de Maven dependencies
```
./mvnw install
```

### Stap 4
Start de applicatie
```
./mvnw spring-boot:run
```