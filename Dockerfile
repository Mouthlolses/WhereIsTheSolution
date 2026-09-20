# 1. Estágio de Build usando Gradle e JDK 17
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew shadowJar --no-daemon

# 2. Estágio de Execução da imagem final
FROM openjdk:17-slim
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-app.jar
ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]