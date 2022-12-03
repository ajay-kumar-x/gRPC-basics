Requirements -> java (java --version), maven (mvn --version)
 1. create a maven project 
 2. Add Dependency in pom.xml
 	grpc-netty-shaded   <groupId>io.grpc ------- for htt2 protocol
	
 	protobuf-java      <groupId>com.google.protobuf ------- 
	
 	grpc-protobuf      <groupId>io.grpc -------
	
 	grpc-stub          <groupId>io.grpc ---Stub classes for GRPC which are exposed to developers and provides type-safe bindings.
	
 	javax-annotation-api <groupId>javax.annotation -----
	
 3. Add build script in pom.xml where we give the location where the .proto file is and where should Stub classes should be generated
 
        <build>
        <defaultGoal>clean generate-sources compile install</defaultGoal>
        <plugins>
            <!-- compile proto file into java files. -->
            <plugin>
                <groupId>com.github.os72</groupId>
                <artifactId>protoc-jar-maven-plugin</artifactId>
                <version>3.6.0.1</version>
                <executions>
                    <execution>
                        <phase>generate-sources</phase>
                        <goals>
                            <goal>run</goal>
                        </goals>
                        <configuration>
                            <includeMavenTypes>direct</includeMavenTypes>

                            <inputDirectories>
                                <include>src/main/resources</include>
                            </inputDirectories>

                            <outputTargets>
                                <outputTarget>
                                    <type>java</type>
                                    <outputDirectory>src/main/java</outputDirectory>
                                </outputTarget>
                                <outputTarget>
                                    <type>grpc-java</type>
                                    <pluginArtifact>io.grpc:protoc-gen-grpc-java:1.15.0</pluginArtifact>
                                    <outputDirectory>src/main/java</outputDirectory>
                                </outputTarget>
                            </outputTargets>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.10.1</version>
            </plugin>
        </plugins>
     </build>
  
  4. now right click on pom.xml -> maven-> reload project
  
  5. create .proto file in resources folder(where we decided in build script) Which is the base of our microservice or project where we will define
     the servies and endpoints 
     e.g. Test.proto
          syntax ="proto3";                        
          option java_package="com.example.grpc";  //location where the stub classes will be generated
          service TestService{
            rpc greet(Empty) returns(responseMessage);
            }
          message Empty{}
          message responseMessage{
             string message=1;
             }
       
  6. goto  maven -> lifecycle-> package
       this will generate 1 Java class for the .proto file (here "Test.java") and n java grpc class for n services in the .proto file(here 1 class only  because only one service which will be "TestServiceGrpc.java") here is the "TestSeriveImplBase" which we will extend the write the logics for the endpoints
       
  7.  create a service class with any name(let "TestService") where will extend the "TestSeriveImplBase" and write the logic
  
       public class TestService extends TestServiceGrpc.TestServiceImplBase {
         @Override
    	 public void greet(Test.Empty request, StreamObserver<Test.responseMessage> responseObserver) {
       	 Test.responseMessage.Builder response=Test.responseMessage.newBuilder();

        	response.setMessage("Welcome to testing");

        	responseObserver.onNext(response.build());
        	responseObserver.onCompleted();
   	 }
	}
   
   8. final step we will set the server port and add the servie in Main class which will be entry point
        
        public class Main {
   	 public static void main(String[] args) throws IOException, InterruptedException {
         Server server= ServerBuilder.forPort(8080).addService(new TestService()).build();
         server.start();

         System.out.println("Test Server Started on port:"+server.getPort());

         server.awaitTermination();
   	 }
	}
 	
   9. run the Main class and use BloomRpc with proto file to test the microservice 
