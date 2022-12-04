package com.example;

import com.example.grpc.Test;
import com.example.grpc.TestServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TestService extends TestServiceGrpc.TestServiceImplBase {
    @Override
    public void greet(Test.Empty request, StreamObserver<Test.responseMessage> responseObserver) {
        Test.responseMessage.Builder response=Test.responseMessage.newBuilder();

        response.setMessage("Welcome to testing");

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
