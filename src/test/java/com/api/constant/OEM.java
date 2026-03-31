package com.api.constant;

public enum OEM {
	
	GOGGLE(1),
	APPLE(2);
	
	int code;
	
	OEM(int code){
		this.code=code;
	}

	public int getCode() {
		return code;
	}
}
