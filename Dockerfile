# Multi-stage Dockerfile for jjajuka-back Spring Boot Application

# Stage 1: Build Stage
FROM gradle:8.10-jdk21 AS builder

WORKDIR /app

# Copy Gradle wrapper and configuration files first for better layer caching
COPY gradlew .
COPY gradle gradle/
COPY build.gradle .
COPY settings.gradle .

# Download dependencies (this layer will be cached if dependencies don't change)
RUN ./gradlew dependencies --no-daemon || true

# Copy application source code
COPY src src/

# Build the application
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime Stage
FROM eclipse-temurin:21-jre

WORKDIR /app

# Create a non-root user for security
RUN groupadd -r jjajuka && useradd -r -g jjajuka jjajuka

# Copy the JAR file from build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Change ownership to non-root user
RUN chown jjajuka:jjajuka app.jar

# Switch to non-root user
USER jjajuka

# Expose application port
EXPOSE 8080

# Set default Spring profile (can be overridden)
ENV SPRING_PROFILES_ACTIVE=default

# Health check (adjust the endpoint based on your actuator configuration)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
