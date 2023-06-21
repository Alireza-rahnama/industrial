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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.time.Duration;
import java.time.Instant;

import javax.net.ssl.SSLException;

public class PngImageMessageProvider {
	private ManagedChannel channel;
	private SeaScanServiceGrpc.SeaScanServiceStub asyncStub;
	private String accessToken = null;
//	private Instant pngImageMessageGenerationStartTime;
//	private Instant pngImageMessageGenerationEndTime;

	String filesPath = "src/main/images/Rutter.Adapter.GRPC.PngImageMessage.";
	int pngFileNumber = 144;

	public PngImageMessageProvider() {

		String targetUri = "ABC.XYZ.canadaeast.azurecontainer.io:50052";

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

		List<String> receivedUniqueIds = new ArrayList<>(); // List to store received unique IDs

		Iterator<PngImageMessage> pngImageIterator = generatePngImageMessagesIterator(3);

		StreamObserver<Empty> streamObserver = new StreamObserver<>() {
			
			@Override
			public void onNext(Empty empty) {
				System.out.println("Received response from server");
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

		StreamObserver<PngImageMessage> requestObserver = asyncStub.openPngImageStream(streamObserver);

		while (pngImageIterator.hasNext()) {
			PngImageMessage pngImageMessage = pngImageIterator.next();
			requestObserver.onNext(pngImageMessage);
			System.out.println("Stream observer interface receives a pngImageMessage from the stream");

			// Add the unique ID to the list
			receivedUniqueIds.add(pngImageMessage.getSeascanSourceId());

		}

		requestObserver.onCompleted();
	}

	private Iterator<PngImageMessage> generatePngImageMessagesIterator(long numberOfPngImageMessages) {
		// This method generates and returns an iterator for a stream of
		// PngImageMessages

		return new Iterator<PngImageMessage>() {
			private byte[] data;
			private ByteString pngByteString;
			private int currentIndex = 0;

//			Instant pngImageMessageGenerationStartTime = Instant.now();

			@Override
			public boolean hasNext() {
				return currentIndex < numberOfPngImageMessages;

			}

			@Override
			public PngImageMessage next() {
				final String uniqueId = UUID.randomUUID().toString();
				ByteBuffer buffer = null;
				String nextFilePath = filesPath + pngFileNumber + ".bin";

				try {
					data = Files.readAllBytes(Paths.get(nextFilePath));
					pngByteString = ByteString.copyFrom(data);
					
//		            data = Files.readAllBytes(Paths.get(nextFilePath));
//		            buffer = ByteBuffer.wrap(data);

		            // Parse the HandshakeMessage from the binary data
				} catch (IOException e) {
					e.printStackTrace();
				}

				PngImageMessage pngImageMessage = PngImageMessage.newBuilder()
						.setPngData(pngByteString)
						.setSeascanSourceId(uniqueId)
						.build();
				
//	            PngImageMessage pngImageMessage = null;
//				try {
//					pngImageMessage = PngImageMessage.parseFrom(buffer);
//				} catch (InvalidProtocolBufferException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}


				currentIndex++;
//	            pngFileNumber++;

				return pngImageMessage;
			}
		};
	}

	public void shutdown() {
		channel.shutdown();

	}

}
