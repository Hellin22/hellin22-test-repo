# 1. Java 17 slim 이미지
FROM openjdk:17-jdk-slim

# 2. JAR 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]