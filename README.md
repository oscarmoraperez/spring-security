# Java Mentoring Program: Spring Security

This maven-based project contains the exercises defined in the 'spring-security' module of Java Mentoring program.

0) To run the project one have the following options:

- mvn package. Compiles and builds the docker image of two applications required in the homework: <em>demo-app, secret-provider</em>

1) The following apps are provided in two different modules:
- **demo-app** Provides a 3 simple API endpoint: `GET /about`, `GET /info`, `GET /admin` protected using Spring Security via a DB-backed user service.
- **secret-provider** Provides a 2 simple API endpoint: `GET /otp`, `GET /secret-information/otp-code`.

Both applications require a PostgreSQL DB running instance. For the sake of simplicity each module provides a folder called `docker` with a ready to go docker-compose script that launches a postgreSQL instance. To launch it, just run:

`$docker-compose up -d`

2) Application 1: **demo-app** 

The first part of the homework is implemented in the demo-app Spring application. It includes the following:
- Custom security implementation via backing the users (+roles) in a postgreSQL instance. 
- /info endpoint only visible to users with authorities: VIEW_INFO
- /about endpoint non-protected
- /admin endpoint only visible to users with authorities: VIEW_ADMIN
- Brute force protector. It saves the number of wrong authentications tried by a user. If a specific threshold is reached, the user is locked. It is a custom implementation follwoing the generic interfaces provides by Spring: `org.springframework.security.core.userdetails.UserDetailsService` and `org.springframework.security.core.userdetails`. 
- /locked-users displays the users locked (by the brute force protector)
- Recurrent job (using Spring Scheduling) that unlocks user which the last login is older than 5 minutes ago.
- /login and /logout endpoints (based on Spring Security)

3) Application 3: **secret-provider**

The second part of the homework requirements is implemented in the *secret-provider* Spring application. It includes the following:

- Custom security implementation via backing the users (+roles) in a postgreSQL instance.
- /otp creates, persists and provide one time password (1 minute old) to access to endpoint /secret-information
- /secret-information/{otp} provides access to a secret information if the otp code is valid. It immediatly deletes the opt code from the database. (Only one single use)
- Recurrent job (using Spring Scheduling) that deletes unused Otp code (older than one minute)
- To access to all the endpoints (/opt, /secret-information/{otp}) the STANDARD authority is required
- /login and /logout endpoints (based on Spring Security)
