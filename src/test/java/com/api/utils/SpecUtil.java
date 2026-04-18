package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import com.api.constant.Role;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {
	
	//GET -- DEL
	public static RequestSpecification requestSpec() throws IOException {
		RequestSpecification rs=new RequestSpecBuilder()
			.setBaseUri(getProperty("BASE_URI"))
			.setContentType(ContentType.JSON)
			.setAccept(ContentType.JSON)
			.log(LogDetail.URI)
			.log(LogDetail.METHOD)
			.log(LogDetail.HEADERS)
			.log(LogDetail.BODY)
			.build();
		return rs;
	}
	
	public static RequestSpecification requestSpec(Object payload) throws IOException {
		RequestSpecification rs=new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.setBody(payload)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return rs;
		
	}
	
	
	
	public static RequestSpecification requestSpecWithAuth(Role role) throws IOException {
		RequestSpecification rs=new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", AuthTokenProvider.getToken(role))
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return rs;
	}
	
	public static RequestSpecification requestSpecWithAuth(Role role, Object payload) throws IOException {
		RequestSpecification rs=new RequestSpecBuilder()
		.setBaseUri(getProperty("BASE_URI"))
		.setContentType(ContentType.JSON)
		.setAccept(ContentType.JSON)
		.addHeader("Authorization", AuthTokenProvider.getToken(role))
		.setBody(payload)
		.log(LogDetail.URI)
		.log(LogDetail.METHOD)
		.log(LogDetail.HEADERS)
		.log(LogDetail.BODY)
		.build();
		return rs;
	
	}
	
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification=new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(200)
			.expectResponseTime(lessThan(2000L))
			.log(LogDetail.ALL)
			.build();
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification=new ResponseSpecBuilder()
			.expectContentType(ContentType.JSON)
			.expectStatusCode(statusCode)
			.expectResponseTime(lessThan(2000L))
			.log(LogDetail.ALL)
			.build();
		return responseSpecification;
	}
	
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification=new ResponseSpecBuilder()
			.expectStatusCode(statusCode)
			.expectResponseTime(lessThan(2000L))
			.log(LogDetail.ALL)
			.build();
		return responseSpecification;
	}
	

}
