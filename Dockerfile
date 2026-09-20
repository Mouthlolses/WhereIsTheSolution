# 1. Estágio de Build usando JDK 21
FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Compila apenas o módulo :server
RUN chmod +x gradlew
RUN ./gradlew :server:build -x test --no-daemon

# 2. Estágio de Execução usando JRE 21
FROM eclipse-temurin:21-jre
EXPOSE 8080
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /home/gradle/src/server/build/libs/*.jar /app/ktor-app.jar

ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]