# Step 1: Use a base image with OpenJDK 17 (JDK version for building and JRE version for running)
FROM openjdk:17-jdk-slim AS build

# Step 2: Install Maven
RUN apt-get update && apt-get install -y maven

# Step 3: Set the working directory inside the container for the build process
WORKDIR /app

# Step 4: Copy the Maven build file (pom.xml) to the container
COPY pom.xml .

# Step 5: Download dependencies (not building yet, just downloading the necessary dependencies)
RUN mvn dependency:go-offline

# Step 6: Copy the rest of the application source code into the container
COPY src /app/src

# Step 7: Build the application (packaging the application as a JAR file)
RUN mvn clean package -DskipTests

# Step 8: Use the same base image for running the application
FROM openjdk:17-jdk-slim

# Step 9: Set the working directory inside the container for runtime
WORKDIR /app

# Step 10: Copy the built JAR file from the previous step to the runtime container
COPY --from=build /app/target/weatherapp-0.0.1-SNAPSHOT.jar weatherapp.jar

# Step 11: Expose the port on which the Spring Boot app will run
EXPOSE 8080

# Step 12: Run the application using the Java command
ENTRYPOINT ["java", "-jar", "weatherapp.jar"]
