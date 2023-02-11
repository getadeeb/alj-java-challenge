FROM adoptopenjdk/openjdk15 as base
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

FROM base as build
RUN ./mvnw clean package
