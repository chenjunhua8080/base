FROM amazoncorretto:8-alpine-jre

EXPOSE 8080

COPY target/*.jar /app/common.jar

CMD java -Dserver.port=80 -Xms512m -Xmx512m -server -jar /app/common.jar --spring.profiles.active=dev
