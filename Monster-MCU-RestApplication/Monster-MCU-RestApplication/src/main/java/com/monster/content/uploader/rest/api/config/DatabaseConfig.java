/**
 * 
 */
package com.monster.content.uploader.rest.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author SumanD
 *
 */
@Configuration
public class DatabaseConfig {
	@Value("${DATABASE_URL}")
	private String databaseUrl;

	@Value("${DATABASE_USERNAME}")
	private String username;

	@Value("${DATABASE_PASSWORD}")
	private String password;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		// ... Configure additional properties such as driver class, connection pool,
		// etc.
		return dataSource;
	}

}
