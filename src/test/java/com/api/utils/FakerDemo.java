package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Faker faker = new Faker(new Locale("en-IND"));
		String firstName = faker.name().firstName();
		String lastName=faker.name().lastName();
		System.out.print(firstName+" ");
		System.out.println(lastName);
		
		String buildingNo=faker.address().buildingNumber();
		System.out.println(buildingNo);
		
		System.out.println(faker.address().streetAddress());
		System.out.println(faker.address().streetName());
		System.out.println(faker.address().city());
		System.out.println(faker.number().digits(5));
		System.out.println(faker.numerify("704#######"));
		
		System.out.println(faker.internet().emailAddress());
		System.out.println(faker.phoneNumber().cellPhone());
		

	}

}
