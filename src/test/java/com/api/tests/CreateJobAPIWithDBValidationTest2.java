package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.DateTimeUtility.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
import com.api.response.model.CreateJobResponseModel;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.MapJobProblemDBModel;

import io.restassured.response.Response;

public class CreateJobAPIWithDBValidationTest2 {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;

	@BeforeMethod(description = "Creating the create job api request payload")
	public void setup() {
		customer = new Customer("palak", "Gupta", "7983945132", "", "gpalakagra@gmail.com", "");
		customerAddress = new CustomerAddress("D 404", "sunshine", "Kamla Nagar", "Perfect Classes", "Tej Nagar",
				"282005", "India", "Uttar pradesh");
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "93104456713400", "93104456713400",
				"93104456713400", getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.POOR_BATTERY_LIFE.getCode(), "Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONT_DESK.getCode(), WarrantyStatus.IN_WARRENTY.getCode(), OEM.GOGGLE.getCode(), customer,
				customerAddress, customerProduct, problemList);
	}

	@Test(description = "Verify if the create job api response is shown correctly", groups = { "api", "regression",
			"smoke" })
	public void createJobAPITest() throws IOException {
		CreateJobResponseModel createJobResponseModel = given().spec(requestSpecWithAuth(FD, createJobPayload)).when()
				.post("/job/create").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).body("data.job_number", startsWith("JOB_"))
				.extract().as(CreateJobResponseModel.class);

		System.out.println(createJobResponseModel);

		int tr_customer_id = createJobResponseModel.getData().getTr_customer_id();

		CustomerDBModel customerDataDromDB = CustomerDao.getCustomerInfo(tr_customer_id);
		System.out.println(customerDataDromDB);

		Assert.assertEquals(customerDataDromDB.getFirst_name(), customer.first_name());
		Assert.assertEquals(customerDataDromDB.getLast_name(), customer.last_name());
		Assert.assertEquals(customerDataDromDB.getMobile_number(), customer.mobile_number());
		Assert.assertEquals(customerDataDromDB.getMobile_number_alt(), customer.mobile_number_alt());
		Assert.assertEquals(customerDataDromDB.getEmail_id(), customer.email_id());
		Assert.assertEquals(customerDataDromDB.getEmail_id_alt(), customer.email_id_alt());

		System.out.println();
		CustomerAddressDBModel customerAddressFromDB = CustomerAddressDao
				.getCustomerAddress(customerDataDromDB.getTr_customer_address_id());
		Assert.assertEquals(customerAddressFromDB.getFlat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressFromDB.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressFromDB.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressFromDB.getLandmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressFromDB.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressFromDB.getPincode(), customerAddress.pincode());
		Assert.assertEquals(customerAddressFromDB.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressFromDB.getState(), customerAddress.state());

		int productId = createJobResponseModel.getData().getTr_customer_product_id();

		CustomerProductDBModel customerProductFromDB = CustomerProductDao.getCustomerProductInfoFromDB(productId);

		Assert.assertEquals(customerProductFromDB.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductFromDB.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductFromDB.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductFromDB.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductFromDB.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductFromDB.getMst_model_id(), customerProduct.mst_model_id());

		int tr_job_head_id = createJobResponseModel.getData().getId();
		MapJobProblemDBModel jobDataFromDB = MapJobProblemDao.getProblemDetails(tr_job_head_id);

		Assert.assertEquals(jobDataFromDB.getMst_problem_id(), createJobPayload.problems().get(0).id());
		Assert.assertEquals(jobDataFromDB.getRemark(), createJobPayload.problems().get(0).remark());

	}

}
