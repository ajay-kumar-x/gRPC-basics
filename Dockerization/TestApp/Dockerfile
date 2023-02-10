FROM openjdk:19
EXPOSE 8080
ADD ./target/TestApp-1.0-SNAPSHOT-jar-with-dependencies.jar TestApp-1.0-SNAPSHOT-jar-with-dependencies.jar
ENTRYPOINT ["java","-jar", "TestApp-1.0-SNAPSHOT-jar-with-dependencies.jar"]
