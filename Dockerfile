FROM openjdk:8-jdk-alpine
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ADD Dockerfile.key /app/keys.txt
RUN base64 -d /app/keys.txt > /app/keys.json
ENV GOOGLE_APPLICATION_CREDENTIALS="/app/keys.json"
ENTRYPOINT ["java","-jar","/app.jar"]
