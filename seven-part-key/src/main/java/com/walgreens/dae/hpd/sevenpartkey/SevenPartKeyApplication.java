package com.walgreens.dae.hpd.sevenpartkey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SevenPartKeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SevenPartKeyApplication.class, args);
	}
}
