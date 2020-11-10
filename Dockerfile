FROM openjdk:13-jdk-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /
COPY . /
COPY ${JAR_FILE} app.jar
ADD Dockerfile.key /keys.txt
RUN base64 -d /keys.txt > /keys.json
ENV GOOGLE_APPLICATION_CREDENTIALS="/keys.json"

EXPOSE 8181
ENTRYPOINT ["java","-jar","/app.jar"]
