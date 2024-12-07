#Iamgen de Java
FROM openjdk:17-jdk-slim
#Establecer un DIrectorio de trabajo
WORKDIR /app
#Copiar mi archivo JAR a mi contendor
COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway-0.0.1-SNAPSHOT.jar
#Exponer nuestro Puerto apra consumirlo desde nuestro host
EXPOSE 8080

#Comando Para ejecutar la aplicación
ENTRYPOINT ["java","-jar", "/app/api-gateway-0.0.1-SNAPSHOT.jar"]