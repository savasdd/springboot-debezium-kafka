package com.debezium;

import com.debezium.model.Parameter;
import com.debezium.repository.ParameterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDebeziumSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDebeziumSourceApplication.class, args);


	}

	@Bean
	CommandLineRunner lineRunner(ParameterRepository repository){
		return args -> {
			Parameter parameter= Parameter.builder().name("Type1").build();
			Parameter parameter1= Parameter.builder().name("Type2").build();
			Parameter parameter2= Parameter.builder().name("Type3").build();
			Parameter parameter3= Parameter.builder().name("Type4").build();

//			repository.save(parameter);
//			repository.save(parameter1);
//			repository.save(parameter2);
//			repository.save(parameter3);
		};
	}



}
