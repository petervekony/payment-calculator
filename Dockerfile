FROM --platform=linux/amd64 maven:3.9.6-eclipse-temurin-17 as build

WORKDIR /app

COPY . ./

RUN mvn clean package -DskipTests

FROM bellsoft/liberica-openjdk-alpine:latest 

RUN apk add --no-cache bash

WORKDIR /app

COPY --from=build /app/web/target/web-*.jar web.jar

CMD ["java", "-jar", "web.jar"]
