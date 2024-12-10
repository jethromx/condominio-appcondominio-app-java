# Etapa de construcción
FROM eclipse-temurin:21-jdk as builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Cambiar permisos de ejecución
RUN chmod +x mvnw

# Descargar las dependencias
RUN ./mvnw dependency:go-offline

# Copiar el código fuente y construir la aplicación
COPY src ./src
RUN ./mvnw package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]