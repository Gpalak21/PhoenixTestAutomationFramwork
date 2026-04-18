package com.api.tests;

import static com.api.utils.ConfigManager.getProperty;
import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

public class LoginAPITest {
	
	//Created by Palak Gupta
	
	UserCredentials userCredentials;
	
	@BeforeMethod
	public void setup() {
		userCredentials = new UserCredentials("iamfd", "password");
	}
	
	@Test(description="Verifying if login API is working for user FD", groups= {"api","regression","smoke"})
	public void loginAPITest() throws IOException {
			
		given()
			.spec(requestSpec(userCredentials))
		.when()
			.post("/login")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
			
	}
	
	
	

}
