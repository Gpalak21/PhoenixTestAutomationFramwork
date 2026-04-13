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
import java.util.Locale;
import java.util.Random;

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
import com.api.utils.DateTimeUtility;
import com.github.javafaker.Faker;

import static com.api.utils.SpecUtil.*;

public class CreateJobAPITest2 {

	CreateJobPayload payload;
	private static final String COUNTRY = "India";

	@BeforeMethod(description = "Creating the create job api request payload")
	public void setup() {

		Faker faker = new Faker(new Locale("en-IND"));

		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String mobilenumber = faker.numerify("798#######");
		String emailAddress = faker.internet().emailAddress();

		Customer customer = new Customer(fname, lname, mobilenumber, "", emailAddress, "");

		String flatNumber = faker.numerify("###");
		String apartmentName = faker.address().streetName();
		String streetName = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().streetName();
		String pincode = faker.numerify("#####");
		String state = faker.address().state();

		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area,
				pincode, COUNTRY, state);

		String dop = DateTimeUtility.getTimeWithDaysAgo(10);
		String imei_serial_number = faker.numerify("###############");
		String popurl = faker.internet().url();

		CustomerProduct customerProduct = new CustomerProduct(dop, imei_serial_number, imei_serial_number,
				imei_serial_number, popurl, 1, 1);

		String remark = faker.lorem().sentence(10);
		Random random = new Random();
		int problemId = random.nextInt(27) + 1;

		Problems problems = new Problems(problemId, remark);

		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);

		 payload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemsList);
	}

	@Test(description = "Verify if the create job api response is shown correctly", groups = { "api", "regression",
			"smoke" })
	public void createJobAPITest() throws IOException {

		given().spec(requestSpecWithAuth(FD, payload)).when().post("/job/create").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"));
	}

}
