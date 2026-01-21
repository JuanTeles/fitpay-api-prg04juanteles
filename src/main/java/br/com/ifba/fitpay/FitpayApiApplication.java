package br.com.ifba.fitpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FitpayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitpayApiApplication.class, args);
	}

}
