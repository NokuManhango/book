# Use official Maven image to build the app
FROM maven:3.8.4-openjdk-11 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the project source code into the container
COPY src /app/src

# Package the application (creates the .jar file)
RUN mvn clean package -DskipTests

# Use an OpenJDK image for the final stage
FROM openjdk:11-jre-slim

# Copy the packaged .jar file from the build stage
COPY --from=build /app/target/your-app-name.jar /app/your-app-name.jar

# Expose the port the app will run on
EXPOSE 8080

# Run the .jar file
CMD ["java", "-jar", "/app/your-app-name.jar"]
