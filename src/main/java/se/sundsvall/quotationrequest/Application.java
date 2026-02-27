package se.sundsvall.quotationrequest;

import org.springdoc.core.configuration.SpringDocHateoasConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import se.sundsvall.dept44.ServiceApplication;

import static org.springframework.boot.SpringApplication.run;

@ServiceApplication(exclude = SpringDocHateoasConfiguration.class)
@EnableFeignClients
public class Application {
	public static void main(String... args) {
		run(Application.class, args);
	}
}
