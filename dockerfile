FROM openjdk:8-jdk-alpine
EXPOSE 8001
COPY "target/ms-product-0.0.1-SNAPSHOT.jar" "app.jar"
ENTRYPOINT ["java","-jar","app.jar"]