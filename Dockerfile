FROM amazoncorretto:22.0.2-alpine3.20
COPY ./build/libs/audio_sr-1.0.jar audio_sr.jar
ENTRYPOINT ["java", "-jar", "/audio_sr.jar"]
