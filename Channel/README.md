# Communication Through Channel
Here We are going to See that how we can implement ManagedChannel to connect to another service

We Will do this With the help of two Microservice
1. GrpcServer -> this will implement a single endpoint
2. GrpcClient -> this will try to call the endpoint of GrpcServer.

Starts with GrpcServer

	1. create the maven project and add the basic dependency and build required for a Grpc
	
	2. create a Server.proto file in resources
	
	      syntax="proto3";
	      option java_package="org.example.grpc";
	      service TestService{
	         rpc checkCredential(credential) returns(responseMessage);
	         //if both username and password equals then responseMessage-> success 200 else failure 400
	         }
	       message credential{
	       string username=1;
	       string password=2;
	       }
	       message responseMessage{
	        string message=1;
	        int32 responseCode=2;
	        }
		
	3. maven-> lifecycle-> package  this will generate the stub classes for the proto file
	
	4. Implement the logic for the endpoint in a new class(let TestService.java) which will extend TestServiceGrpc.TestServiceImplBase
	    
	    public class TestService extends TestServiceGrpc.TestServiceImplBase {
   		@Override
    		public void checkCredential(Server.credential request, StreamObserver<Server.responseMessage> responseObserver) {

        	Server.responseMessage.Builder response=Server.responseMessage.newBuilder();

        	if(request.getUsername().equals(request.getPassword())){
           	 response.setMessage("Success").setResponseCode(200);
        	}
       	 else{
            	response.setMessage("Failure").setResponseCode(400);
        	}

        	responseObserver.onNext(response.build());
        	responseObserver.onCompleted();
   		 }
		}
	5. Start the server with help of main method
	
	     public class Main {
  		  public static void main(String[] args) throws IOException, InterruptedException {

        	      Server server= ServerBuilder.forPort(8080)
                	.addService(new TestService()).build(); //this Server class is from io.grpc
       	      server.start();

        	      System.out.println("Server started on :"+server.getPort());

       	      server.awaitTermination();
       	       }
       	   }
       	   

Now GrpcClient
          
           1. create the maven project and add the basic dependency and build required for a Grpc
           2. copy the .proto file(Server.proto) from GrpcServer resources to GrpcClient resources then maven-> lifecycle-> package
           3. we can test the Server by two methods 
           		a. by creating .proto file for GrpcClient then creating a service class and allocating a port and test in bloomRpc
           		b. use main method in simple way just to show the response in conslole.
           	Here we will show by b method
           4. in main method we will create the channel and call the endpoint of GrpcServer
               
                public class Main {
   		 public static void main(String[] args) {

     		   ManagedChannel channel= ManagedChannelBuilder.forAddress("localhost",8080).usePlaintext().build();
        	   TestServiceGrpc.TestServiceBlockingStub testStub= TestServiceGrpc.newBlockingStub(channel);

       	   Server.credential credential= Server.credential.newBuilder().setUsername("ajay").setPassword("ajay").build();
                  Server.responseMessage responseMessage=testStub.checkCredential(credential);
         
      		  //response from GrpcServer
       	  System.out.print(responseMessage.getMessage()+" "+responseMessage.getResponseCode());
   		 }
		}
          5. Now we can run the main method and see the response From GrpcServer
