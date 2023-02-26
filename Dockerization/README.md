1. create a basic gRPC hello world app
2. Add the below plugin to pom.xml
   this will generate the Jar with dependency
   Note-> specify the path of Main class in <mainClass> tag. it directly looks for Main class in src/main/java

   <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.10.1</version>
  </plugin>
  <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                    <archive>
                        <manifest>
                            <mainClass>org.example.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>


3. maven-> lifecycle-> package

4. create a Dockerfile in the app directory
5. add the below line in that file

   FROM openjdk:19
   EXPOSE 8080
   ADD ./target/TestApp-1.0-SNAPSHOT-jar-with-dependencies.jar TestApp-1.0-SNAPSHOT-jar-with-dependencies.jar
   ENTRYPOINT ["java","-jar", "TestApp-1.0-SNAPSHOT-jar-with-dependencies.jar"]

6. create a docker-compose.yml outside the app directory

    which will look like this   

   version: "2"
   services:
   mytestappservice:
   build: TestApp/
   container_name: testapp_container
   restart: always
   network_mode: "host"
   hostname: localhost
   ports:
   - 8080:8080

7. run the command in terminal 
     docker-compose up -d

