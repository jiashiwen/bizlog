package ren.shiwen.logclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springbootes5Application {

	public static void main(String[] args) {
		System.setProperty("rocketmq.client.log.loadconfig", "false");
		SpringApplication.run(Springbootes5Application.class, args);
	}
}
