# =========================
# STAGE 1 : Build
# =========================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Optimisation du cache Maven
COPY pom.xml .
RUN mvn dependency:go-offline

# Copier le code source
COPY src ./src

# Build
RUN mvn clean package -DskipTests

# =========================
# STAGE 2 : Runtime
# =========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
