package springboot.usecase.one;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootUsecaseOneApplication {
	private static final Logger LOGGER = LogManager.getLogger(SpringbootUsecaseOneApplication.class);

	public static void main(String[] args) {
		LOGGER.info("<======Starting Fund Transfer Application=====>");
		SpringApplication.run(SpringbootUsecaseOneApplication.class, args);
	}

}
