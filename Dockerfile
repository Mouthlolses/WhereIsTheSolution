# 1. Estágio de Build
FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Executa o build e lista o conteúdo gerado em build/libs para diagnóstico
RUN ./gradlew build -x test --no-daemon && ls -la build/libs/

# 2. Estágio de Execução
FROM eclipse-temurin:17-jre
EXPOSE 8080
WORKDIR /app

# Copia qualquer JAR gerado na pasta libs
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-app.jar

ENTRYPOINT ["java", "-jar", "/app/ktor-app.jar"]