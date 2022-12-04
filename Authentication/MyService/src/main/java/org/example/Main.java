package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.interceptor.serverInterceptor;
import org.example.service.MyService;

import java.io.IOException;

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