FROM openjdk:17-jre-slim
WORKDIR /app
COPY target/ac2-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD java XX:+UseContainerSupport -Xmx512m -jar app.jar