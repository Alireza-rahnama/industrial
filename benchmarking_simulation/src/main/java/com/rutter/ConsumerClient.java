package com.rutter;

import java.util.concurrent.atomic.AtomicLong;

public class ConsumerClient {
	private static final AtomicLong uniqueIdGenerator = new AtomicLong();
	private long id;
	private String type;
	
	public ConsumerClient(String type) {
		this.id = uniqueIdGenerator.getAndIncrement();
		this.type = type;

	}

	@Override
	public String toString() {
		return "ConsumerClient [id=" + id + ", type=" + type + "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
