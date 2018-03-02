package ren.shiwen.logdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"ren.shiwen.*"})
public class LogdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogdemoApplication.class, args);
	}
}
