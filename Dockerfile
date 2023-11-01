FROM openjdk:20-ea-1-jdk-oracle
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]