package com.producerClient;

import io.grpc.CallOptions;

import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ClientCall.Listener;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import com.Adapter.RUTTER_GRPC.DataMessageTypes.PngImageMessage;
import com.Adapter.RUTTER_GRPC.GrpcMessageTypes.OpenImageStreamMessage;
import com.Adapter.RUTTER_GRPC.S6WebServiceGrpc;
import com.Adapter.RUTTER_GRPC.S6WebServiceGrpc.S6WebServiceStub;
import com.Adapter.RUTTER_GRPC.SeaScanServiceGrpc;
import com.Adapter.RUTTER_GRPC.SeaScanServiceGrpc.SeaScanServiceStub;
import com.google.common.base.Strings;
import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.time.Duration;
import java.time.Instant;

import javax.net.ssl.SSLException;

public class ConsumerClient {
	private ManagedChannel channel;
	private S6WebServiceStub asyncStub;
	
	private ManagedChannel channel2;
	private SeaScanServiceStub seaScanAsyncStub;
	private String accessToken = "No Token";
//	private Instant pngImageMessageGenerationStartTime;
//	private Instant pngImageMessageGenerationEndTime;

	String filesPath = "src/main/images/Rutter.Adapter.GRPC.PngImageMessage.";
	int pngFileNumber = 144;

	public ConsumerClient() {

		String targetUri = "ABC.XYZ.canadaeast.azurecontainer.io:50051";
XS
		SslContext sslContext = null;
		try {
			sslContext = GrpcSslContexts.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		} catch (SSLException e) {
			e.printStackTrace();
			return;
		}

		ClientInterceptor interceptor = new ClientInterceptor() {
			@Override
			public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method,
					CallOptions callOptions, Channel next) {
				return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
						next.newCall(method, callOptions)) {
					@Override
					public void start(Listener<RespT> responseListener, Metadata headers) {
						if (!Strings.isNullOrEmpty(accessToken)) {
							headers.put(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER),
									"Bearer " + accessToken);
						}
						super.start(responseListener, headers);
					}
				};
			}
		};

		channel = NettyChannelBuilder.forTarget(targetUri).sslContext(sslContext).intercept(interceptor).build();
		asyncStub = S6WebServiceGrpc.newStub(channel);

	}

	public void receivePngImageStream() {

		List<String> receivedUniqueIds = new ArrayList<>(); // List to store received unique IDs

//		Iterator<PngImageMessage> pngImageIterator = generatePngImageMessagesIterator(3);

		StreamObserver<PngImageMessage> streamObserver = new StreamObserver<>() {
			
			@Override
			public void onNext(PngImageMessage pngImageMessage) {
				System.out.println("Received response from Node");
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("Error occurred: " + throwable.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("Server has completed sending responses");
				System.out.println("Received response with unique IDs: " + receivedUniqueIds);
			}
		};
		


//		StreamObserver<PngImageMessage> requestObserver = seaScanAsyncStub.openPngImageStream(streamObserver);
		
		OpenImageStreamMessage openImageStreamMessage = OpenImageStreamMessage.newBuilder().build();
		asyncStub.openPngImageStream(openImageStreamMessage, streamObserver);

		streamObserver.onCompleted();
	}

	public void shutdown() {
		channel.shutdown();

	}

}

