package com.walgreens.dae.hpd.uniquekey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UniquekeyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniquekeyServiceApplication.class, args);
	}
}
