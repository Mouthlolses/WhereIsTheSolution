# 1. Estágio de Build
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Executa o build sem daemon
RUN ./gradlew buildFatJar --no-daemon || ./gradlew shadowJar --no-daemon || ./gradlew build -x test --no-daemon

# 2. Estágio de Execução
FROM eclipse-temurin:17-jre
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/ktor-app.jar || COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-app.jar
ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]