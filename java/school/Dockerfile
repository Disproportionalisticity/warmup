# STAGE 1: Build the native binary
FROM ghcr.io/graalvm/native-image-community:21 AS builder

WORKDIR /build

# Copy maven files first for caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

# Copy source and build
COPY src ./src
RUN ./mvnw -Pnative native:compile -DskipTests

# STAGE 2: Create the tiny runner image
FROM debian:bookworm-slim

WORKDIR /app

# Copy the binary from the builder stage
COPY --from=builder /build/target/school .

# Give the binary a limit (Non-heap memory is handled differently here)
ENTRYPOINT ["./school"]