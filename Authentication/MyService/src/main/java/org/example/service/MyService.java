package org.example.service;

import io.grpc.stub.StreamObserver;
import org.example.grpc.Test;
import org.example.grpc.TestServiceGrpc;
import org.example.interceptor.serverInterceptor;

public class MyService extends TestServiceGrpc.TestServiceImplBase {
    @Override
    public void home(Test.Empty request, StreamObserver<Test.responseMessage> responseObserver) {

        Test.responseMessage.Builder response=Test.responseMessage.newBuilder();
         response.setMessage("Welcome :"+ serverInterceptor.UID);

         responseObserver.onNext(response.build());
         responseObserver.onCompleted();
    }
}
