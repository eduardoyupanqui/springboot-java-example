#FROM maven:3.5-jdk-8-alpine as BUILD
#RUN ["mvn", "package", "-DskipTests"]



FROM openjdk:13



EXPOSE 8011
ADD ./target/ms-demo-0.0.1-SNAPSHOT.jar  ms-demo.jar
#ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","-Dspring.cloud.config.uri=http://x.x.x.x:8888/","/ms-demo.jar"]

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev", "/ms-demo.jar"]