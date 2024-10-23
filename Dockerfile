FROM eclipse-temurin:21_35-jdk

EXPOSE 8080

COPY target/*.jar spaceShips_api.jar

ENTRYPOINT ["java", "-jar", "spaceShips_api.jar"]