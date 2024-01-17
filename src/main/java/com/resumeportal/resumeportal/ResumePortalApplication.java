package com.resumeportal.resumeportal;

import com.resumeportal.resumeportal.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

@SpringBootApplication
@EnableJpaRepositories(basePackages ="com.resumeportal.resumeportal.repository")
public class ResumePortalApplication {

	public static void main(String[] args) {

		SpringApplication.run(ResumePortalApplication.class, args);
	}

}
