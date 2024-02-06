FROM eclipse-temurin:17-jre-focal
MAINTAINER refactorit.sk
COPY target/whatsapp-bot-0.0.1-SNAPSHOT.jar whatsapp-bot-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/whatsapp-bot-0.0.1-SNAPSHOT.jar"]