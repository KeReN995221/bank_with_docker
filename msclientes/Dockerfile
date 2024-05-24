FROM maven:3.9.6-sapmachine-21 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

ARG EUREKA_SERVER=localhost

ENTRYPOINT java -jar app.jar