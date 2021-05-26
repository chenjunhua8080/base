FROM openjdk:8-jdk-alpine

ADD target/common-1.0.0-SNAPSHOT.jar common.jar
EXPOSE 8056
ENTRYPOINT ["java","-jar","common.jar"]