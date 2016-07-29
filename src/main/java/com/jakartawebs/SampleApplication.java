package com.jakartawebs;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

/**
 * <p>Web application initializer for application deployable into weblogic server.
 * 
 * <p>We must directly implementing {@link WebApplicationInitializer} even thought {@link SpringBootServletInitializer}
 * already implement it as stated on <a href="http://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html#howto-weblogic">here</a>
 * 
 * @author zakyalvan
 */
@SpringBootApplication
public class SampleApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
	private static final Class<SampleApplication> SOURCE = SampleApplication.class;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SOURCE).profiles("staging");
	}
	
	/**
	 * Main class, so that we still can start this application in embedded mode.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SpringApplicationBuilder(SOURCE).profiles("dev").web(true).run(args);
	}
}
