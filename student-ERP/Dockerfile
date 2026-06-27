FROM maven:3.9.11-eclipse-temurin-25 AS builder

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .
COPY src src

RUN chmod +x mvnw && ./mvnw clean package -DskipTests


FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=builder /app/target/StudentERP.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java","-jar","app.jar"]