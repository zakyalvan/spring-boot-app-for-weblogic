package com.jakartawebs.validation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Simple validation configuration.
 * 
 * @author zakyalvan
 */
@Configuration
public class ValidationConfiguration {
	@Bean
	LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		return validator;
	}
	
	@Bean
	MethodValidationPostProcessor methodValidationPostProcessor() {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		return postProcessor;
	}
}
