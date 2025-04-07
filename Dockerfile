# Use official Java image from Docker Hub
FROM openjdk:17-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built .jar file into the container
COPY target/bookie.jar /app/bookie.jar

# Define the command to run the application
CMD ["java", "-jar", "bookie.jar"]
