FROM openjdk:17-jdk-slim AS build

WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./

COPY src ./src

RUN ./gradlew build -x test

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /workspace/app/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]