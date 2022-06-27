#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
ADD . ./tempservice
WORKDIR /tempservice
RUN mvn clean install -DskipTests

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build ./tempservice/target/*.jar tempservice.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/tempservice.jar"]