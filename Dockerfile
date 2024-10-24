FROM eclipse-temurin:21_35-jdk

WORKDIR /spaceships_api

EXPOSE 8080

COPY target/*.jar spaceships_api.jar

ENTRYPOINT ["java", "-jar", "spaceships_api.jar"]