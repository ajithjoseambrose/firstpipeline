FROM eclipse-temurin:17-jdk-alpine
ADD target/student-service-0.0.1-SNAPSHOT.jar student-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/student-service-0.0.1-SNAPSHOT.jar"]

