package com.mborg.mailMerge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/*
 @author monChrome
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class MailMergeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailMergeApplication.class, args);
	}
}
