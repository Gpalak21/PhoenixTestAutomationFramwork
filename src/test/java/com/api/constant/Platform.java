package com.api.constant;

public enum Platform {
	
	FST(1),
	FRONT_DESK(2);
	
int code;
	
	Platform(int code){
		this.code=code;
	}
	
	public int getCode() {
		return code;
	}

}
