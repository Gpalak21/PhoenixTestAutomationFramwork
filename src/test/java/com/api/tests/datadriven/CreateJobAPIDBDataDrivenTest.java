package com.api.tests.datadriven;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;

public class CreateJobAPIDBDataDrivenTest {
	
	CreateJobPayload createJobPayload;
	
	
	
	@Test(description="Verify if the create job api response is shown correctly",groups= {"api","regression","smoke","csv"},
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider="CreateJobAPIDBDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) throws IOException {
		
		given()
			.spec(requestSpecWithAuth(FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message",equalTo("Job created successfully. "))
			.body("data.job_number",startsWith("JOB_"));
	}

}
