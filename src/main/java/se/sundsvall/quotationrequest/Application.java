package se.sundsvall.quotationrequest;

import static org.springframework.boot.SpringApplication.run;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import se.sundsvall.dept44.ServiceApplication;

@ServiceApplication
@EnableFeignClients
@EnableCaching
public class Application {
	public static void main(String... args) {
		run(Application.class, args);
	}
}
