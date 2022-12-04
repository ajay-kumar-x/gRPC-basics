#In this Project we are going to see How we can integrate Authentication with the help of JwtToken and Interceptor.

We will demonstrate this through two microservice

 1. Authentication Service -> which will generate the JWT token for particular credential provided by user
 2. MyService -> Here we will implement the interceptor which will validate the signature and claim of JWT token on Metatdata if valid then only we will able to proceed to MyService app
 
 
 Starts with Authentication Service:
 
    1. Add the basic dependendy and build script for gRPC with <dependency of jjwt> and <dependency of jaxb-api > then right click ->maven-> reload project
    2. Auth.proto file then maven->lifecycle->package
    
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

    6. Now we can use BloomRpc to check our service with the help of Auth.proto file. On valid credential it will generate the JWT Token.


 Now, Creating MyService:
     
     1. Add the basic dependency for Grpc then right click ->maven-> reload project
     
     2. create a Test.proto file in resources folder then maven-> lifecycle->reload project
          syntax="proto3";
          option java_package="org.example.grpc";
          service TestService{
             rpc home(Empty) returns(responseMessage);
             }
           message Empty{}
           message responseMessage{
              string message=1;
               }
     
     3. create an interceptor class(let serverInterceptor) which will implements the  ServerInterceptor Interface
         import io.grpc.*;
         import io.jsonwebtoken.*;
         import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;
         public class serverInterceptor implements ServerInterceptor {
         
         public static String UID; //email claim which we will use to show in response
         private JwtParser jwtParser = Jwts.parser().setSigningKey("my secret key");
          @Override
          public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        // extract token from metadata

        String token = metadata.get(Metadata.Key.of("Token",ASCII_STRING_MARSHALLER));
        Status status = Status.OK;

        if(token==null)
            status = Status.UNAUTHENTICATED.withDescription("Token is missing");
        else if(!token.startsWith("Bearer"))
            status = Status.UNAUTHENTICATED.withDescription("unknown authorization token type");
        else {
            Jws<Claims> claimsJws = null;
            try {
                claimsJws = jwtParser.parseClaimsJws(token.substring(6).trim());
            } catch (JwtException e) {
                status = Status.UNAUTHENTICATED.withDescription(e.getMessage()).withCause(e);
            }
            if(claimsJws!=null)
            {
                Context context = Context.current().withValue(Context.key("TokenData"),
                        claimsJws.getBody().getSubject());


                    serverInterceptor.UID= (String) claimsJws.getBody().get("email");

                    return Contexts.interceptCall(context,serverCall,metadata,serverCallHandler);

            }
        }
        serverCall.close(status, new Metadata());
        return new ServerCall.Listener<ReqT>() {
        };
        }
        }
     
     4. Create a service class(let MyService) which will extend the TestServiceGrpc.TestServiceImplBase
          public class MyService extends TestServiceGrpc.TestServiceImplBase {
          @Override
          public void home(Test.Empty request, StreamObserver<Test.responseMessage> responseObserver) {

         Test.responseMessage.Builder response=Test.responseMessage.newBuilder();
         response.setMessage("Welcome :"+ serverInterceptor.UID);

         responseObserver.onNext(response.build());
         responseObserver.onCompleted();
        }
        }
    
    5. Add the service and interceptor and start the server with the help of main method
         public class Main {
          public static void main(String[] args) throws IOException, InterruptedException {
          Server server= ServerBuilder.forPort(8081)
                .addService(new MyService())
                .intercept(new serverInterceptor()).build();
          server.start();

        System.out.println("MyService started on :"+server.getPort());

        server.awaitTermination();
        }
        }

    6. Use BloomRpc to check the service with the help of Test.proto file
        Here we have to give the Token in metaData in form of key value pair like this
           { "Token": "Bearer theJwtTokenGenerated" }

