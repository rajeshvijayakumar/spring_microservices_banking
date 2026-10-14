# Stage 1: Build the JAR file using Maven and a compatible JDK version
FROM maven:3.9.16-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build and package the application while skipping unit tests for speed
RUN mvn clean package -DskipTests

# Stage 2: Create the final lightweight, working runtime image with full Linux tools
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Install curl, wget, and bash cleanly using Alpine's native package manager
RUN apk update && apk add --no-cache curl wget bash
# Copy the compiled JAR file from Stage 1 into this clean container
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]