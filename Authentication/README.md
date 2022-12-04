In this we are going to see How we can integrate Authentication with the help of JwtToken and Interceptor.

We will demonstrate this through two microservice

 1. Authentication Service -> which will generate the JWT token for particular credential provided by user
 2. MyService -> Here we will implement the interceptor which will validate the signature and claim of JWT token on Metatdata if valid then only we will able to proceed to MyService app
 
 
 Starts with Authentication Service:
 
    1. Add the basic dependendy and build script for gRPC with <dependency of jjwt> and <dependency of jaxb-api >
    2. Auth.proto file
    
        syntax="proto3";
        option java_package="org.example.grpc";
              
         service AuthService{
          rpc login(Credential) returns(responseMessage);
             }
           message Credential{
              string email=1;
              string password=2;
              }
           message responseMessage{
              string message=1;
              string token=2;
              }
     
     3. Create a JwtTokenGenerator class which will help in generating the JwtToken for a particular claim and signature
           public class JwtTokenGenerator {
              public static String jwtToken(String tokenClaim)
              {
                 return Jwts.builder()
                .setSubject("Any subject we can set like login")
                .claim("email",tokenClaim)
                .setIssuedAt(new Date(new Date().getTime())).setExpiration(Date.from(ZonedDateTime.now().plusMinutes(10).toInstant()))
                .signWith(SignatureAlgorithm.HS256,"my secret key").compact();
                }
             }

            //Note-> while claiming this JwtToken we can find the uniqueness with the help of claim key-value pair
            // signature of this token should be same as in Interceptor which we will create in MyService
    
    4. create a service class with any name like "AuthService" which will extend "AuthServiceGrpc.AuthServiceImplBase" where we will write the logic for login and generate the Jwt based on request.
    
         public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {
         
             static Map<String,String> credentialMap=new HashMap<>(); //list of credentials
    	     @Override
   	     public void login(Auth.Credential request, StreamObserver<Auth.responseMessage> responseObserver) {

      		  Auth.responseMessage.Builder response=Auth.responseMessage.newBuilder();

       	 if(credentialMap.containsKey(request.getEmail())){
         	   if(credentialMap.get(request.getEmail()).trim().equals(request.getPassword().trim())){

          	     String jwtToken= JwtTokenGenerator.jwtToken(request.getEmail());
           	    response.setToken(jwtToken).setMessage("Logged in successFully as: "+request.getEmail());
           		 }else{
             	   response.setMessage("Invalid Credential!");
          		  }
      		  }else{
          	  response.setMessage("User doesn't exists.");
       	 }
	
      		  responseObserver.onNext(response.build());
      		  responseObserver.onCompleted();

  		  }

   	 public AuthService() {
    		    credentialMap.put("ajaykr1729@gmail.com","1234");
      		    credentialMap.put("test","test");
  		  }

	}
	
	
    5. Add the service and start the server with the help of main method
    
        public class Main {
          public static void main(String[] args) throws IOException, InterruptedException {

          Server server= ServerBuilder.forPort(8080).addService(new AuthService()).build();
          server.start();


          System.out.println("Auth Server started on :"+server.getPort());

          server.awaitTermination();
            }
        }

    


