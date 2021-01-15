# BestLib Library System


## General info
Simple library system application created with Java and Spring Boot 2 framework.
The application offers basic CRUD operations as well as internationalization and account registration process.
It is secured by Spring Security and it connects to external MySQL database.

This is my first "real" application that served as the first programming experience.


## Technologies
Project is created with:
* Java 11
* Spring Boot 2
* Spring framework
* Thymeleaf
* HTML, CSS
* MySQL
* JUnit 5, Mockito
* Maven


## Launch
There are two ways to run the application:
* you can execute com.kamilpomietlo.libraryapp.LibraryApp within IDE which starts a webserver on http://localhost:8080
* you can simply launch it on Heroku at this link: https://bestlib-app.herokuapp.com (it may take up to 30 seconds to start)


## Additional information
You can check all application functionalities by logging into all account types. Pre created test accounts have the following credentials:
* USER:
  * e-mail: user@example.pl
  * password: 123
* LIBRARIAN:
  * e-mail: librarian@example.pl
  * password: 456
* ADMIN:
  * e-mail: admin@example.pl
  * password: 789
  
You can create only USER account through the regular registration process.