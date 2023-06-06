package com.rutter;

import java.util.UUID;

public class RadarStation {
	
	@Override
	public String toString() {
		return "RadarStation [uniqeId=" + uniqeId + ", type=" + type + ", dataTransmitionInterval="
				+ dataTransmitionInterval + "]";
	}

	private final UUID uniqeId;
	private String type;
	private int dataTransmitionInterval;
	
	public RadarStation(String type) {
		super();
		this.uniqeId = UUID.randomUUID();
		this.type = type;
	}

	public UUID getUniqeId() {
		return uniqeId;
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
	
	public int getDataTransmitionInterval() {
		return dataTransmitionInterval;
	}
	

}
