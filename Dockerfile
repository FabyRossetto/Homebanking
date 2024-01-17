

# Utiliza una imagen base de Alpine Linux
FROM alpine:latest

# Instala OpenJDK y otras dependencias necesarias
RUN apk --no-cache add openjdk8-jre

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR de la aplicación (asegúrate de que se genera en el directorio "target")
COPY target/Homebanking-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto en el que se ejecutará tu aplicación (si es necesario)
EXPOSE 8080

# Inicia la aplicación Java
CMD ["java", "-jar", "app.jar"]

