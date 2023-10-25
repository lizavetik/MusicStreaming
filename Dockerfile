FROM openjdk:17-alpine
ARG JAR_FILE=target/MusicStreaming-0.0.1-SNAPSHOT.jar
RUN mkdir /music
WORKDIR /music
COPY ${JAR_FILE} /music
ENTRYPOINT java -jar /music/MusicStreaming-0.0.1-SNAPSHOT.jar