FROM openjdk:17.0.2-slim-buster
COPY target/*.jar app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","app.jar"]