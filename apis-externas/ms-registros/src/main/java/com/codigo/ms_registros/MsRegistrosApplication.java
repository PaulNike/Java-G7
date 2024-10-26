package com.codigo.ms_registros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRegistrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRegistrosApplication.class, args);
	}

}
