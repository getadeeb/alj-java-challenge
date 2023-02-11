# syntax=docker/dockerfile:experimental
FROM adoptopenjdk/openjdk15 as base
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

FROM base as test
CMD ["./mvnw", "test"]

FROM base as build
RUN ./mvnw package