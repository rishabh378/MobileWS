package com.application.mobile.ws.MobileWS;

import com.application.mobile.ws.MobileWS.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MobileWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileWsApplication.class, args);
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext()
	{
		return new SpringApplicationContext();
	}

	@Bean
	public AppProperties getAppProperties()
	{
		return new AppProperties();
	}
}
