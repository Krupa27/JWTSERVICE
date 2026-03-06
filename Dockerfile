# Step 1: Use a stable, production-ready JDK 17 image
FROM eclipse-temurin:17-jdk-jammy

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy Maven wrapper and pom.xml first to leverage Docker cache
# This prevents re-downloading dependencies if only code changes
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Step 4: Copy the source code and build the application
COPY src ./src
RUN ./mvnw package -DskipTests

# Step 5: Handle the JAR name dynamically
# Moves whatever JAR was created (e.g., jwt-0.0.1-SNAPSHOT.jar) to a fixed name
RUN cp target/*.jar app.jar

# Step 6: Expose the port (Render uses the $PORT env var, but this is good practice)
EXPOSE 8081

# Step 7: Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]