FROM openjdk:17-jdk-slim
EXPOSE 8081
RUN mkdir -p /app
COPY /target/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]