# Stage 1: Build with Maven
FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app
COPY . .
ENV MYSQL_PATIENT_USER=root
ENV PROD_PASSWORD=password

RUN mvn clean package -DskipTests

# Stage 2: Use AdoptOpenJDK as a base image
FROM openjdk:17-oracle
WORKDIR /app
COPY --from=builder /app/target/patient-0.0.1.jar .
EXPOSE 8080
ENV MYSQL_PATIENT_USER=$MYSQL_PATIENT_USER
ENV PROD_PASSWORD=$PROD_PASSWORD
CMD ["java", "-jar", "patient-0.0.1.jar"]