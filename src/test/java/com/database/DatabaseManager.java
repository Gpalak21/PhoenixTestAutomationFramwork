package com.database;

import static com.api.utils.ConfigManager.getProperty;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseManager {

	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource ds;
	private static final String DB_URL = getProperty("DB_URL");
	private static final String DB_USERNAME = getProperty("DB_USERNAME");
	private static final String DB_PASSWORD = getProperty("DB_PASSWORD");
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE"));
	private static final int CONNECTION_TIMEOUT_IN_SECS = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SECS"));
	private static final int IDLE_TIMEOUT_IN_SECS = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SECS"));
	private static final int MAX_LIFE_TIME_IN_MINS = Integer
			.parseInt(ConfigManager.getProperty("MAX_LIFE_TIME_IN_MINS"));
	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");
	private static Connection conn; // ANy update will happen to this conn reference will all threads will be aware
									// of it

	private DatabaseManager() {

	}

	private static void initializePool() {

		if (ds == null) {
			synchronized (DatabaseManager.class) {
				if (ds == null) {
					hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USERNAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SECS * 1000);
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_IN_SECS * 1000);
					hikariConfig.setMaxLifetime(MAX_LIFE_TIME_IN_MINS * 60 * 1000);
					hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);

					ds = new HikariDataSource(hikariConfig);

				}

			}
		}

	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;

		if (ds == null) {
			initializePool();
		}

		else if (ds.isClosed()) {
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
		
			conn = ds.getConnection();
		
		return conn;
	}

}
