package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtility.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.ServiceLocation;
import com.api.constant.WarrantyStatus;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.SpecUtil.*;

public class CreateJobAPITest {
	
	CreateJobPayload createJobPayload;
	
	@BeforeMethod(description="Creating the create job api request payload")
	public void setup() {
		Customer customer = new Customer("palak", "Gupta", "7983945132","" , "gpalakagra@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("D 404", "sunshine", "Kamla Nagar", "Perfect Classes", "Tej Nagar", "282005", "India", "Uttar pradesh");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "44997119913875", "44997119913875", "44997119913875", getTimeWithDaysAgo(10), 
				Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.POOR_BATTERY_LIFE.getCode(), "Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), WarrantyStatus.IN_WARRENTY.getCode(), OEM.GOGGLE.getCode(), customer, customerAddress, customerProduct, problemList);
	}
	
	
	
	@Test(description="Verify if the create job api response is shown correctly",groups= {"api","regression","smoke"})
	public void createJobAPITest() throws IOException {
		
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
