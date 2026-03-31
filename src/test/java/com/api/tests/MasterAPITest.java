package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	
	@Test(description="Verify if the master api response is shown correctly",groups= {"api","regression","smoke"})
	public void verifyMasterAPIRequest() throws IOException {
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.post("/master")
		.then()
			.spec(responseSpec_OK())
			.body("message",equalTo("Success"))
			.body("data", notNullValue())
			.body("data", hasKey("mst_oem"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", greaterThan(0))
			.body("data.mst_model.size()", equalTo(3))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", everyItem(notNullValue()))
			.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));
			
			
	}
	
	
	@Test(description="Verify if the master api response is shown correctly for invalid token",groups= {"api","regression","smoke","negative"})
	public void invalidToken_masterAPIRequest() throws IOException {
		given()
		.spec(requestSpec())
	.when()
		.post("/master")
	.then()
		.spec(responseSpec_TEXT(401));
		
	}

}
