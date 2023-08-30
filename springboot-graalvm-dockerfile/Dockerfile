FROM findepi/graalvm:java17-native

MAINTAINER Vino


RUN mkdir -p /vino/spring-boot-demos/logs  \
    /vino/spring-boot-demos/temp

WORKDIR /vino/spring-boot-demos
ARG PROFILES

ENV SERVER_PORT=8899
ENV log.home=/vino/spring-boot-demos/logs
ENV SPRING_PROFILES_ACTIVE=${PROFILES}
ENV APPLICATION_NAME=spring-boot-demos
EXPOSE ${SERVER_PORT}


ADD ./target/spring-boot-demos.jar ./app.jar

ENTRYPOINT ["java", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-Dserver.port=${SERVER_PORT}", \
            "-jar", "app.jar"]