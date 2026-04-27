package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.api.utils.ConfigManager.*;

public class DatabaseManager {

	private static final String DB_URL = getProperty("DB_URL");
	private static final String DB_USERNAME = getProperty("DB_USERNAME");
	private static final String DB_PASSWORD = getProperty("DB_PASSWORD");
	private volatile static Connection conn; //ANy update will happen to this conn reference will all threads will be aware of it

	private DatabaseManager() {

	}

	public static void createConnection() throws SQLException, IOException {

		if (conn == null) {
			synchronized (DatabaseManager.class) {
				if (conn == null) {
					conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				}
				System.out.println(conn);
			}
		}

	}

}
