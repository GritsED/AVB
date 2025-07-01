FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
WORKDIR /app
COPY target/companies-1.0-SNAPSHOT-exec.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
