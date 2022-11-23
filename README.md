# ZLI - Multi-User-Applikationen realisieren - booking-planner-backend-v2

This is the backend for the booking-planner application. It is a simple REST API that allows to create, read, update and
delete bookings and users. https://github.com/marco-karpf/booking-planner-backend-v2

This project uses Quarkus, the Supersonic Subatomic Java Framework. If you want to learn more about Quarkus, please
visit its website: https://quarkus.io/ .

## Setup

1. Create a copy (fork) of this project and suggestively the project `booking-planner-frontend`. https://github.com/marco-karpf/booking-planner-frontend
1. Make sure you have postgres server running in on `port 5432` e.g. in a docker container .
1. Open the project in your desired ide.

## Startup

1. Start the project with the following command: `./mvnw compile quarkus:dev`
1. Backend listens on http://localhost:8080.
1. Check out the api documentations on  http://localhost:8080/q/swagger-ui/.
1. Optional: package the application using `./mvnw package`.

## Database

The data is stored in a PostgreSQL database. In the development environment, this is configured in the
application.properties file.
> **_NOTE:_** Please check the application.properties file for the database configuration.

## Database setup

- db-Kind:`postgres`
- username: `marco`
- password: `1234`
- database-jdbc-url: `jdbc:postgresql://localhost:5432/db_bookings`

## automated testings

Automated tests can be executed with `./mvnw quarkus:test`. For the automated tests, the `import.sql`is used for the
database setup.

## Mock Data

<table>
 <tr>
    <td><b style="font-size:15px">Admin</b></td>
    <td><b style="font-size:15px">Member</b></td>
 </tr>
 <tr>
    <td>

- `email` john.doe@mail.com
- `username` john
- `password` 123
    </td>
    <td>
- `email` john.doe@mail.com
- `username` john
- `password` 123
    </td>
 </tr>
</table>
