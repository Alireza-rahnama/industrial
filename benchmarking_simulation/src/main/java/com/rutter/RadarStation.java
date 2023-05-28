package com.rutter;

import java.util.concurrent.atomic.AtomicInteger;

public class RadarStation {
	
	@Override
	public String toString() {
		return "RadarStation [uniqeId=" + uniqeId + ", type=" + type + ", dataTransmitionInterval="
				+ dataTransmitionInterval + "]";
	}

	private static final AtomicInteger uniqueIdGenerator = new AtomicInteger();
	private int uniqeId;
	private String type;
	private int dataTransmitionInterval;
	
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
	
	public void setDataTransmitionInterval(int dataTransmitionInterval) {
		this.dataTransmitionInterval = dataTransmitionInterval;
	}
	

}
