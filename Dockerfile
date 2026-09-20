# 1. Estágio de Build
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Garante permissão de execução no wrapper do Gradle e roda o build exibindo o erro real se falhar
RUN chmod +x gradlew && ./gradlew build -x test --no-daemon --stacktrace

# 2. Estágio de Execução
FROM eclipse-temurin:17-jre
EXPOSE 8080
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-app.jar
ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]