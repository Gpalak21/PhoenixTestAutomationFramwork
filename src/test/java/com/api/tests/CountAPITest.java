package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

public class CountAPITest {
	
	@Test(description="Verify if the count api response is shown correctly",groups= {"api","regression","smoke"})
	public void verifyCountAPIRequest() throws IOException {
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_OK())
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", Matchers.containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
	}
	
	@Test(description="Verify if the count api response is shown correctly for invalid token",groups= {"api","regression","smoke","negative"})
	public void countAPITest_missingAuth() throws IOException {
		given()
		.spec(requestSpec())
	.when()
		.get("/dashboard/count")
	.then()
		.spec(responseSpec_TEXT(401));
	}

}
