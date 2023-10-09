FROM maven:3.5-jdk-8-alpine as builder

# 在这个目录下面工作
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Run the web service on container startup.
#执行操作
CMD ["java","-jar","/app/target/yhapi-backed-0.0.1-SNAPSHOT.jar","--spring.profiles.active=prod"]