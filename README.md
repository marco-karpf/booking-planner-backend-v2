# ZLI - Multi-User-Applikationen realisieren - booking-planner-backend-v2 

This is the backend for the booking-planner application. It is a simple REST API that allows to create, read, update and delete bookings and users.
This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## First Steps
1. Create a copy (fork) of this project and suggestively the project `booking-planner-frontend`.
1. Make sure you have Postgres server running in a docker container e.g.
1. Open the project in your desired ide.
1. Start the prject with the following command: `./mvnw compile quarkus:dev`
1. Backend  listens on http://localhost:8080.
1. Check out the api documentations on  http://localhost:8080/q/swagger-ui/.
1. Optional: package  the application using `./mvnw package`.

## Database
The data is stored in a PostgreSQL database. In the development environment, this is configured in the application.properties file.
> **_NOTE:_** Please check the application.properties file for the database configuration.

### Database setup
- db-Kind:`postgres`
- username: `marco`
- password: `1234`
- database-jdbc-url: `jdbc:postgresql://localhost:5432/db_bookings`

## automated testings
Automated tests can be executed with `./mvnw quarkus:test`. For the automated tests, the `import.sql`is used for the database setup.
