package bootcamp.hibernate_practical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class HibernatePracticalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernatePracticalApplication.class, args);
	}

}
