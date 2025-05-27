# Этап сборки всего проекта
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

# Образ для discovery-service
FROM eclipse-temurin:21-jdk-alpine AS discovery
WORKDIR /app
COPY --from=builder /build/discovery-service/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]

# Образ для gateway
FROM eclipse-temurin:21-jdk-alpine AS gateway
WORKDIR /app
COPY --from=builder /build/gateway/target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]

# Образ для auth-service
FROM eclipse-temurin:21-jdk-alpine AS auth
WORKDIR /app
COPY --from=builder /build/auth-service/target/*.jar app.jar
EXPOSE 8885
ENTRYPOINT ["java", "-jar", "app.jar"]

# Образ для accounting-service
FROM eclipse-temurin:21-jdk-alpine AS accounting
WORKDIR /app
COPY --from=builder /build/accounting-service/target/*.jar app.jar
EXPOSE 8887
ENTRYPOINT ["java", "-jar", "app.jar"]

# Образ для client-service
FROM eclipse-temurin:21-jdk-alpine AS client
WORKDIR /app
COPY --from=builder /build/client-service/target/*.jar app.jar
EXPOSE 8886
ENTRYPOINT ["java", "-jar", "app.jar"]

# Образ для notification-service
FROM eclipse-temurin:21-jdk-alpine AS notification
WORKDIR /app
COPY --from=builder /build/notification-service/target/*.jar app.jar
EXPOSE 8884
ENTRYPOINT ["java", "-jar", "app.jar"]