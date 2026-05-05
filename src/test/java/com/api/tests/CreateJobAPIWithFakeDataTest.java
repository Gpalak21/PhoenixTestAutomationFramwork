package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadDBModel;

public class CreateJobAPIWithFakeDataTest {
	
	private CreateJobPayload createJobPayload;
	private final static String COUNTRY="India";
	
	@BeforeMethod
	public void setup() {
		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
	}
	
	
	@Test(description="Verify if the create job api response is shown correctly",groups= {"api","regression","smoke","faker"})
	public void createJobAPITest() throws IOException {
		
		int customerId=given()
			.spec(requestSpecWithAuth(FD, createJobPayload))
		.when()
			.post("/job/create")
		.then()
			.spec(responseSpec_OK())
			.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message",equalTo("Job created successfully. "))
			.body("data.job_number",startsWith("JOB_"))
			.extract().body().jsonPath().getInt("data.tr_customer_id");
		
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB=CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(actualCustomerDataInDB.getFirst_name(), expectedCustomerData.first_name());
		Assert.assertEquals(actualCustomerDataInDB.getLast_name(), expectedCustomerData.last_name());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number(), expectedCustomerData.mobile_number());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number_alt(), expectedCustomerData.mobile_number_alt());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id(), expectedCustomerData.email_id());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(), expectedCustomerData.email_id_alt());
		
		CustomerAddressDBModel customerAddressFromDB=CustomerAddressDao.getCustomerAddress(actualCustomerDataInDB.getTr_customer_address_id());
		Assert.assertEquals( customerAddressFromDB.getFlat_number(),createJobPayload.customer_address().flat_number());
		Assert.assertEquals( customerAddressFromDB.getApartment_name(),createJobPayload.customer_address().apartment_name());
		Assert.assertEquals( customerAddressFromDB.getStreet_name(),createJobPayload.customer_address().street_name());
		Assert.assertEquals( customerAddressFromDB.getLandmark(),createJobPayload.customer_address().landmark());
		Assert.assertEquals( customerAddressFromDB.getArea(),createJobPayload.customer_address().area());
		Assert.assertEquals( customerAddressFromDB.getPincode(),createJobPayload.customer_address().pincode());
		Assert.assertEquals( customerAddressFromDB.getCountry(),createJobPayload.customer_address().country());
		Assert.assertEquals( customerAddressFromDB.getState(),createJobPayload.customer_address().state());
		
		
		
		JobHeadDBModel jobHeadDataFromDB=JobHeadDao.getJobHeadData(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
	}

}
