package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.grpc.Server;
import org.example.grpc.TestServiceGrpc;

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
