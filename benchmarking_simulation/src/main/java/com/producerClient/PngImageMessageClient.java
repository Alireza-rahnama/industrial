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
import com.Adapter.RUTTER_GRPC.SeaScanServiceGrpc;
import com.google.common.base.Strings;
import com.google.protobuf.ByteString;
import com.google.protobuf.Empty;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.net.ssl.SSLException;

public class PngImageMessageClient {
	private ManagedChannel channel;
	private SeaScanServiceGrpc.SeaScanServiceStub asyncStub;
	private String accessToken = null;

	public PngImageMessageClient() {

		String targetUri = "abc.xyz.canadaeast.azurecontainer.io:50052";

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

		asyncStub = SeaScanServiceGrpc.newStub(channel);
	}

	public void sendPngImageStream() {
		Iterator<PngImageMessage> pngImageIterator = generatePngImageMessagesIterator();
		StreamObserver<Empty> streamObserver = new StreamObserver<>() {
			@Override
			public void onNext(Empty empty) {
				// Handle the response from the server if needed
				System.out.println("Received response from server");
			}

			@Override
			public void onError(Throwable throwable) {
				// Handle any errors that occur during the gRPC call
				System.out.println("Error occurred: " + throwable.getMessage());
			}

			@Override
			public void onCompleted() {
				// The server has completed sending responses
				System.out.println("Server has completed sending responses");
			}
		};

		StreamObserver<PngImageMessage> requestObserver = asyncStub.openPngImageStream(streamObserver);
		System.out.println("Server has completed sending responses");

		try {
			while (pngImageIterator.hasNext()) {
				PngImageMessage pngImageMessage = pngImageIterator.next();
				requestObserver.onNext(pngImageMessage);
				System.out.println("hello world!!");
			}
			requestObserver.onCompleted();
		} catch (Throwable throwable) {
			requestObserver.onError(throwable);
		}
	}

	private Iterator<PngImageMessage> generatePngImageMessagesIterator() {
		// This method generates and returns an iterator for a stream of PngImageMessage
		// instances
		// Replace this with your actual logic for generating PngImageMessage instances
		
		return new Iterator<PngImageMessage>() {
			private byte[] data;
			ByteString pngByteString;
			int currentIndex = 0;
			
			
			@Override
			public boolean hasNext() {
		        return currentIndex < data.length;
			}

			@Override
			public PngImageMessage next() {
				// Generate or fetch your PngImageMessage instance
//				int pngFileNumber = 144;
				
				String filePath = "./src/main/java/com/producerClient/Rutter.Adapter.GRPC.PngImageMessage.144.bin";
		        data = new byte[1]; //an array of bytes with 1 element to test
		        
		        try {
					data = Files.readAllBytes(Paths.get(filePath));
					pngByteString = ByteString.copyFrom(data);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        PngImageMessage pngImageMessage = PngImageMessage.newBuilder()
						// Set the properties of the PngImageMessage as needed
						.setPngData(pngByteString)
						.setSeascanSourceId("willGenerateUuidToBeAbleToReuseSameImageIfNeeded")
						.build();
		        
		        System.out.println("Congtratulations, You GenerateD Png Image Messages!");
		        
		        currentIndex++;
		        
				return pngImageMessage;
				
			}
		};
	}

	public void shutdown() {
		channel.shutdown();
	}

	public static void main(String[] args) {

		PngImageMessageClient client = new PngImageMessageClient();
		client.sendPngImageStream();
		client.shutdown();
	}
}
