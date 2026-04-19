package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.api.request.model.UserCredentials;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReaderUtil {
	public static <T> Iterator<T> loadJSON(String fileName, Class<T[]> clazz) {

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

		ObjectMapper objectMapper = new ObjectMapper();
		T[] userCredsArray;
		List<T> list = null;
		try {
			userCredsArray = objectMapper.readValue(is, clazz);
			list= Arrays.asList(userCredsArray);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		

		return list.iterator();

	}

}
