package com.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDemo2 {
	private static final String COUNTRY ="India";

	public static void main(String[] args) {
		
		Faker faker = new Faker(new Locale("en-IND"));
		
		String fname=faker.name().firstName();
		String lname=faker.name().lastName();
		String mobilenumber=faker.numerify("798#######");
		String emailAddress=faker.internet().emailAddress();
		
		Customer customer = new Customer(fname, lname, mobilenumber, null, emailAddress, null);
		System.out.println(customer);
		
		String flatNumber = faker.numerify("###");
		String apartmentName=faker.address().streetName();
		String streetName=faker.address().streetName();
		String landmark=faker.address().streetName();
		String area=faker.address().streetName();
		String pincode=faker.numerify("#####");
		String state=faker.address().state();
		
		CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area, pincode, COUNTRY, state);
		System.out.println(customerAddress);
		
		String dop=DateTimeUtility.getTimeWithDaysAgo(10);
		String imei_serial_number=faker.numerify("###############");
		String popurl = faker.internet().url();
		
		CustomerProduct customerProduct = new CustomerProduct(dop, imei_serial_number, imei_serial_number, imei_serial_number, popurl, 1, 1);
		System.out.println(customerProduct);
		
		String remark=faker.lorem().sentence(10);
		Random random = new Random();
		int problemId=random.nextInt(27)+1;
		
		
		Problems problems = new Problems(problemId, remark);
	
		
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);
		
		CreateJobPayload payload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemsList);
		

	}

}
