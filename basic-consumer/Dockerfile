FROM openjdk:11-jre-slim
RUN groupadd -r spring && useradd --no-log-init -r -g spring spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
