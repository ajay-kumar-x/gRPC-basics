package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server= ServerBuilder.forPort(8080).addService(new TestService()).build();
        server.start();

        System.out.println("Test Server Started on port:"+server.getPort());

        server.awaitTermination();
    }
}