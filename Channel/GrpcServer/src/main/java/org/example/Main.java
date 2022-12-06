package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.service.TestService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server= ServerBuilder.forPort(8080)
                .addService(new TestService()).build(); //this Server class is from io.grpc
        server.start();

        System.out.println("Server started on :"+server.getPort());

        server.awaitTermination();

    }
}