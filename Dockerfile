FROM openjdk:17-alpine
WORKDIR /app
COPY target/*.jar /app/app.jar
EXPOSE ${PORT}
CMD java -XX:+UseContainerSupport -Xmx512m -jar app.jar --spring.profiles.active=${ENV} -Dserver.port={$PORT}