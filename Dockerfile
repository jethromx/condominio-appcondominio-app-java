# Etapa de construcción
FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /app

# Copiar archivos necesarios para la construcción
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Cambiar permisos de ejecución y  Descargar las dependencias
RUN chmod +x mvnw && ./mvnw dependency:go-offline


# Copiar el código fuente y construir la aplicación
COPY src ./src
RUN ./mvnw package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine as runner

WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=builder /app/target/*.jar app.jar

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Comando para ejecutar la aplicación
#ENTRYPOINT ["java", "-jar", "app.jar"]

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]