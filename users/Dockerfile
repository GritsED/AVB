#FROM eclipse-temurin:21-jre-jammy
#VOLUME /tmp
#WORKDIR /app
#COPY target/users-1.0-SNAPSHOT-exec.jar app.jar
#ENTRYPOINT ["java", "-jar", "app.jar"]
FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]
