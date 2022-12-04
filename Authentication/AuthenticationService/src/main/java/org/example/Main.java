package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.service.AuthService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Server server= ServerBuilder.forPort(8080).addService(new AuthService()).build();
        server.start();


        System.out.println("Auth Server started on :"+server.getPort());

        server.awaitTermination();
    }
}