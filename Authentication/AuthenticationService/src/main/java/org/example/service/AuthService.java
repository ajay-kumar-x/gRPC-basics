package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.grpc.Auth;
import org.example.grpc.AuthServiceGrpc;

import java.util.HashMap;
import java.util.Map;

public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {
    static Map<String,String> credentialMap=new HashMap<>();

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
