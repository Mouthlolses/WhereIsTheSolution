# 1. Estágio de Build
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Compila APENAS o módulo :server ignorando os testes e a parte Android
RUN chmod +x gradlew
RUN ./gradlew :server:build -x test --no-daemon

# 2. Estágio de Execução
FROM eclipse-temurin:17-jre
EXPOSE 8080
WORKDIR /app

# Copia o JAR gerado dentro do módulo server
COPY --from=build /home/gradle/src/server/build/libs/*.jar /app/ktor-app.jar

ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]