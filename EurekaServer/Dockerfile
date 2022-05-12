FROM openjdk:17-alpine3.14
EXPOSE 8761
ADD /target/EurekaServer-0.1.jar EurekaServer.jar
ENTRYPOINT ["java","-jar","EurekaServer.jar"]