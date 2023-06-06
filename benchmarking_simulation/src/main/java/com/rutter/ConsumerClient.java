package com.rutter;

import java.util.UUID;

public class ConsumerClient {
	private final UUID id;
	private String type;
	
	public ConsumerClient(String type) {
		this.id = UUID.randomUUID();
		this.type = type;

	}

	@Override
	public String toString() {
		return "ConsumerClient [id=" + id + ", type=" + type + "]";
	}

	public UUID getId() {
		return id;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
