package iiproject.jobhunting;

import iiproject.jobhunting.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class JobHuntingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobHuntingApplication.class, args);
	}

}
