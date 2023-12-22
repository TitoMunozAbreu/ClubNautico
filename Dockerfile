FROM eclipse-temurin:18-jre-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Crear la imagen del proyecto abrir en gitBash
# docker build -t clubnautico:1.0 .

# comprobar que se ha creado
# docker image list
