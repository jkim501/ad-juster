# ad-juster homework

Ad-juster's technical test

## Getting Started

After cloning the repo, use mvnw to build and package
To build execute :
```
./mvnw clean package
```

## Executing

You can execute the test by running the jar in the /target folder
```
java -jar homework-1.0.0.jar
```
This will launch Spring Boot locally and execute the problems.

I decided to use an in-memory database so it can run stand alone without having to connect to a database.

If you decide to connect to a database, I left the application.properties I used to connect to my local mysql database.

You can use that as a reference or change it to connect to your own database to push the data in.

After executing, a 'campaign.csv' file should be created in the folder where the jar file was executed.


## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/)
* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Joshua Kim**
