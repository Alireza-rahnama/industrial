package com.rutter;

import com.producerClient.ConsumerClient;
import com.producerClient.PngImageMessageProvider;

public class SimualtionMultiThreadDriver {
	
	public SimualtionMultiThreadDriver() {
        Thread senderThread = new Thread(new SenderRunnable());
        Thread receiverThread = new Thread(new ReceiverRunnable());

        senderThread.start();
        receiverThread.start();
	}
	
	
	private class SenderRunnable implements Runnable {
	    @Override
	    public void run() {
			PngImageMessageProvider client = new PngImageMessageProvider();
			client.sendPngImageStream();
			client.shutdown();
	    }
	}

	private class ReceiverRunnable implements Runnable {
	    @Override
	    public void run() {
			ConsumerClient consumerClient = new ConsumerClient();
			consumerClient.receivePngImageStream();
			consumerClient.shutdown();
	    }
	}


}
