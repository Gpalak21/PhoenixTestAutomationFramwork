package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


import java.io.IOException;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import groovyjarjarpicocli.CommandLine.Spec;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class MasterAPITest {
	
	@Test
	public void verifyMasterAPIRequest() throws IOException {
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
			.post("/master")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message",equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", greaterThan(0))
			.body("data.mst_model.size()", equalTo(3))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
			
			
	}
	
	
	@Test
	public void invalidToken_masterAPIRequest() throws IOException {
		given()
		.spec(SpecUtil.requestSpec())
	.when()
		.post("/master")
	.then()
		.spec(SpecUtil.responseSpec_TEXT(401));
		
	}

}
