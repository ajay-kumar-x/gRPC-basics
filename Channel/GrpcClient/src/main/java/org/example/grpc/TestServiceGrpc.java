package org.example.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: Server.proto")
public final class TestServiceGrpc {

  private TestServiceGrpc() {}

  public static final String SERVICE_NAME = "TestService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.example.grpc.Server.credential,
      org.example.grpc.Server.responseMessage> getCheckCredentialMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "checkCredential",
      requestType = org.example.grpc.Server.credential.class,
      responseType = org.example.grpc.Server.responseMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.example.grpc.Server.credential,
      org.example.grpc.Server.responseMessage> getCheckCredentialMethod() {
    io.grpc.MethodDescriptor<org.example.grpc.Server.credential, org.example.grpc.Server.responseMessage> getCheckCredentialMethod;
    if ((getCheckCredentialMethod = TestServiceGrpc.getCheckCredentialMethod) == null) {
      synchronized (TestServiceGrpc.class) {
        if ((getCheckCredentialMethod = TestServiceGrpc.getCheckCredentialMethod) == null) {
          TestServiceGrpc.getCheckCredentialMethod = getCheckCredentialMethod = 
              io.grpc.MethodDescriptor.<org.example.grpc.Server.credential, org.example.grpc.Server.responseMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "TestService", "checkCredential"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.Server.credential.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.example.grpc.Server.responseMessage.getDefaultInstance()))
                  .setSchemaDescriptor(new TestServiceMethodDescriptorSupplier("checkCredential"))
                  .build();
          }
        }
     }
     return getCheckCredentialMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TestServiceStub newStub(io.grpc.Channel channel) {
    return new TestServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TestServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new TestServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TestServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new TestServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class TestServiceImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     *if both username and password equals then responseMessage-&gt; success 200 else failure 400
     * </pre>
     */
    public void checkCredential(org.example.grpc.Server.credential request,
        io.grpc.stub.StreamObserver<org.example.grpc.Server.responseMessage> responseObserver) {
      asyncUnimplementedUnaryCall(getCheckCredentialMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCheckCredentialMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                org.example.grpc.Server.credential,
                org.example.grpc.Server.responseMessage>(
                  this, METHODID_CHECK_CREDENTIAL)))
          .build();
    }
  }

  /**
   */
  public static final class TestServiceStub extends io.grpc.stub.AbstractStub<TestServiceStub> {
    private TestServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TestServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TestServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TestServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     *if both username and password equals then responseMessage-&gt; success 200 else failure 400
     * </pre>
     */
    public void checkCredential(org.example.grpc.Server.credential request,
        io.grpc.stub.StreamObserver<org.example.grpc.Server.responseMessage> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCheckCredentialMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TestServiceBlockingStub extends io.grpc.stub.AbstractStub<TestServiceBlockingStub> {
    private TestServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TestServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TestServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TestServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     *if both username and password equals then responseMessage-&gt; success 200 else failure 400
     * </pre>
     */
    public org.example.grpc.Server.responseMessage checkCredential(org.example.grpc.Server.credential request) {
      return blockingUnaryCall(
          getChannel(), getCheckCredentialMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TestServiceFutureStub extends io.grpc.stub.AbstractStub<TestServiceFutureStub> {
    private TestServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private TestServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TestServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new TestServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     *if both username and password equals then responseMessage-&gt; success 200 else failure 400
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.example.grpc.Server.responseMessage> checkCredential(
        org.example.grpc.Server.credential request) {
      return futureUnaryCall(
          getChannel().newCall(getCheckCredentialMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CHECK_CREDENTIAL = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TestServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TestServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CHECK_CREDENTIAL:
          serviceImpl.checkCredential((org.example.grpc.Server.credential) request,
              (io.grpc.stub.StreamObserver<org.example.grpc.Server.responseMessage>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TestServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.example.grpc.Server.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TestService");
    }
  }

  private static final class TestServiceFileDescriptorSupplier
      extends TestServiceBaseDescriptorSupplier {
    TestServiceFileDescriptorSupplier() {}
  }

  private static final class TestServiceMethodDescriptorSupplier
      extends TestServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TestServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TestServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TestServiceFileDescriptorSupplier())
              .addMethod(getCheckCredentialMethod())
              .build();
        }
      }
    }
    return result;
  }
}
