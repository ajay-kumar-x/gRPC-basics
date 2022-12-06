package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.Server;
import org.example.grpc.TestServiceGrpc;

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