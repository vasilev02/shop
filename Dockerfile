FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/shop-0.0.1.jar /app/shop-0.0.1.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/shop-0.0.1.jar"]