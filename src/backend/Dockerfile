FROM adoptopenjdk/openjdk11:jre-11.0.19_7
WORKDIR /tmp
COPY . ./backend
WORKDIR /tmp/backend
RUN chmod +x gradlew
RUN ./gradlew :workshop-service:build
RUN cp ./workshop-service/build/libs/workshop-service-0.0.1-SNAPSHOT.jar workshop-service.jar
ENTRYPOINT ["java","-jar","/tmp/backend/workshop-service.jar"]
EXPOSE 8180
