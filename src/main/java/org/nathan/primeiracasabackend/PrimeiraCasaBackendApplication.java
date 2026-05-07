package org.nathan.primeiracasabackend;

import org.nathan.primeiracasabackend.Config.CorsConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CorsConfigProperties.class)
public class PrimeiraCasaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimeiraCasaBackendApplication.class, args);
	}

}
