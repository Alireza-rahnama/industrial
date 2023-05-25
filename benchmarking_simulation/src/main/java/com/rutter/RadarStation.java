package com.rutter;

import java.util.concurrent.atomic.AtomicInteger;

public class RadarStation {
	
	private static final AtomicInteger uniqueIdGenerator = new AtomicInteger();
	private int uniqeId;
	private String type;
	
	public RadarStation(String type) {
		super();
		this.uniqeId = uniqueIdGenerator.getAndIncrement();
		this.type = type;
	}

	public int getUniqeId() {
		return uniqeId;
	}

	public void setUniqeId(int uniqeId) {
		this.uniqeId = uniqeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

}
