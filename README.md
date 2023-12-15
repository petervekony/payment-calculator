# Crosskey Monthly Payment Calculator Coding Test

This is my submission of the coding test for the Java developer trainee position
at Crosskey. The main goal was to create a Java application that accepts a text
file formatted as a CSV file, with predetermined headers, calculates the monthly
payment the loan would cost to the customer, and prints it out in a set way:

---

Prospect 1: CustomerName wants to borrow X € for a period of Z years and pay E €
each month

---

Extra features I implemented:

- creating a web interface
- hosting it in a cloud service

## Main features

The project uses:

- Maven as build tool
- Java version 17
- Spring Boot version 3.2.0
- Docker and Docker Compose

## Modules

The project consists of three modules:

- core: contains business logic, data model, exception classes
- cli: contains the cli application's main class and the exception handler
- web: contains the Spring Boot application that enables the web interface

## Building the application

If you want to build the project on your own machine to run the CLI application,
you need to have Java 17 and Maven installed. The web version can be built and
run containerized, using Docker, by building the image from the Dockerfile in
the project's root directory.

### Running the CLI application

After cloning the repository, from the root of the project run:

```
mvn clean package
```

In the build stage the project uses Maven Shade to create an uber JAR that
contains the core dependencies, so the project can be run with the command:

```
java -jar ./cli/target/cli-0.0.1-SNAPSHOT.jar {filePath}
```

where "{filePath}" is the absolute path to the txt/csv file.

### Running the web interface

After running the application, there is one endpoint available:
"/api/calculate", which accepts a form-data body with the file attached under
the "file" key.

##### Building and running it on the host machine

The Spring Boot application can be run by building the application running the
same _"mvn clean package"_ command, and:

```
java -jar ./web/target/web-0.0.1-SNAPSHOT.jar
```

The app listens on port 8080 by default.

##### Running it using Docker

If you have Docker installed, you can build an image from the Dockerfile and run
it in a container:

```
docker build -t payment-calculator .

docker run -p 8080:8080 payment-calculator
```

This way, the server listens on port 8080.

##### Running it using Docker Compose w/ nginx

If you want to have HTTPS, you can run the app together with nginx in a Docker
Compose network:

```
docker-compose build

docker-compose up
```

For this to work, you need to set three environment variables on the machine you are hosting it:

- CERT_PATH: absolute path to the directory where the SSL certificates are
  placed
- SSL_CERT_NAME: name of the SSL certificate file
- SSL_CERT_KEY_NAME: name of the SSL certificate key file

### Hosted version

The hosted version of the application is available on the
[https://petervekony.live/api/calculate](https://petervekony.live/api/calculate)
URL. It's hosted on a Digital Ocean droplet, using Docker Compose and an nginx
reverse proxy that handles HTTPS. You need to use an API tool to access it (like
Postman or HTTPie), and you can send the file as form-data with "file" as key.
The response is an array of objects with the original fields, with the
calculated "monthlyPayment" and an extra "totalCumulativeInterest" calculated,
like this:

```json
[
  {
    "customer": "Juha",
    "totalLoan": 1000.0,
    "interest": 5.0,
    "years": 2,
    "monthlyPayment": 43.87,
    "totalCumulativeInterest": 52.88
  },
  {
    "customer": "Karvinen",
    "totalLoan": 4356.0,
    "interest": 1.27,
    "years": 6,
    "monthlyPayment": 62.87,
    "totalCumulativeInterest": 170.64
  },
  {
    "customer": "Claes Månsson",
    "totalLoan": 1300.55,
    "interest": 8.67,
    "years": 2,
    "monthlyPayment": 59.22,
    "totalCumulativeInterest": 120.73
  },
  {
    "customer": "Clarencé,Andersson",
    "totalLoan": 2000.0,
    "interest": 6.0,
    "years": 4,
    "monthlyPayment": 46.97,
    "totalCumulativeInterest": 254.56
  }
]
```

## Author

- Péter Vékony
