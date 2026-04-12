# Estágio de Build
FROM maven:3.9-eclipse-temurin-17 AS build
# Define o diretório de trabalho
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Executa o build
RUN mvn clean package -DskipTests

# Estágio de Execução
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]