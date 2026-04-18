package com.api.tests.datadriven;

import static com.api.utils.ConfigManager.getProperty;
import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserPOJO;

public class LoginAPITestDataDrivenTest {
	
	
	@Test(description="Verifying if login API is working for user FD", 
			groups= {"api","regression","smoke","csv"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "loginAPIDataProvider"
			)
	public void loginAPITest(UserPOJO userpojo) throws IOException{
			
		given()
			.spec(requestSpec(userpojo))
		.when()
			.post("/login")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
			
	}
	
	
	

}
