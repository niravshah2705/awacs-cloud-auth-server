FROM openjdk:13-jdk-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /
COPY . /
COPY ${JAR_FILE} app.jar
ADD Dockerfile.key /keys.txt
RUN base64 -d /keys.txt > /keys.json
ENV GOOGLE_APPLICATION_CREDENTIALS="/keys.json"
ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=80
EXPOSE 80
EXPOSE 9345/udp
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-jar","/app.jar"]
