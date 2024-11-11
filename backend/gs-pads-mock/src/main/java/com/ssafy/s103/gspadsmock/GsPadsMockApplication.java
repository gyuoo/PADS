package com.ssafy.s103.gspadsmock;

import com.ssafy.s103.gspadsmock.domain.migrate.service.ProductMigrationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class GsPadsMockApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(GsPadsMockApplication.class, args);
	}

}
