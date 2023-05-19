package com.rutter;

public class RadarStation {
	private int uniqeId;
	private String type;
	
	public RadarStation(int uniqeId, String type) {
		super();
		this.uniqeId = uniqeId;
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
