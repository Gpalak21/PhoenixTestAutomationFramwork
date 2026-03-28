package com.api.tests;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class CountAPITest {
	
	@Test
	public void verifyCountAPIRequest() throws IOException {
		given()
			.baseUri(getProperty("BASE_URI"))
			.header("Authorization",AuthTokenProvider.getToken(Role.FD))
			.accept(ContentType.JSON)
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.body("message",equalTo("Success"))
			.time(lessThan(1000L))
			.body("data", notNullValue())
			.body("data.size()", equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", everyItem(not(blankOrNullString())))
			.body("data.key", Matchers.containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"));
	}
	
	@Test
	public void countAPITest_missingAuth() throws IOException {
		given()
		.baseUri(getProperty("BASE_URI"))
		.accept(ContentType.JSON)
		.log().uri()
		.log().method()
		.log().headers()
	.when()
		.get("/dashboard/count")
	.then()
		.log().all()
		.statusCode(401);
	}

}
