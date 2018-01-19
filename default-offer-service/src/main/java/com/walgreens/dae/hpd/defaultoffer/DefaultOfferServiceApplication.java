package com.walgreens.dae.hpd.defaultoffer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DefaultOfferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DefaultOfferServiceApplication.class, args);
	}
}
