package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.SpecUtil;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() throws IOException {
		

		Customer customer = new Customer("palak", "Gupta", "7983945132","" , "gpalakagra@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D 404", "sunshine", "Kamla Nagar", "Perfect Classes", "Tej Nagar", "282005", "India", "Uttar pradesh");
		CustomerProduct customerProduct = new CustomerProduct("2026-03-02T18:30:00.000Z", "44567329913875", "44567329913875", "44567329913875", "2026-03-02T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message",equalTo("Job created successfully. "))
			.body("data.job_number",startsWith("JOB_"));
	}

}
