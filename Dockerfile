FROM maven:3.8.1-openjdk-11 AS build
COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app
RUN mvn clean package -DskipTests

# Use the official OpenJDK image to run the app
FROM openjdk:11-ea-19-jre-slim
COPY --from=build /app/target/lab4op-0.0.1-SNAPSHOT.jar /usr/local/lib/lab4op.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/usr/local/lib/lab4op.jar"]