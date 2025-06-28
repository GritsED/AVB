FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
WORKDIR /app
COPY target/users-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
