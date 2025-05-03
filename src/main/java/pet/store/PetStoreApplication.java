package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// This is a convenience annotation that combines three annotations: @Configuration, @EnableAutoConfiguration, 
//and @ComponentScan. It indicates that this is a Spring Boot application and triggers auto-configuration, 
//component scanning, and allows for defining extra configuration on the application class.


// This defines a public class named PetStoreApplication. This class serves as the entry point
// for the Spring Boot application.

@SpringBootApplication
public class PetStoreApplication {

// This is the main method, which is the entry point for any Java application. It is the method that 
// gets called when the application starts.
	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);

	}

}
