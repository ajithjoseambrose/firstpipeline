FROM eclipse-temurin:17-jdk-alpine
ADD target/student-service.jar student-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/student-service.jar"]