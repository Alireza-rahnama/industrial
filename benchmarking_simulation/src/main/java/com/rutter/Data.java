package com.rutter;

public class Data {
	
	private String type;
	private long id;
	private String source;
	
	public Data(String type, long id, String source) {
		super();
		this.type = type;
		this.id = id;
		this.source = source;
	}
	

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	@Override
	public String toString() {
		return "Data [type=" + type + ", id=" + id + ", source=" + source + "]";
	}	

}
