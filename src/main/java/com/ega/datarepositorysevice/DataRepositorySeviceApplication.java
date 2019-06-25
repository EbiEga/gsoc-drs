package com.ega.datarepositorysevice;

import com.ega.datarepositorysevice.model.ServiceInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

@SpringBootApplication
public class DataRepositorySeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataRepositorySeviceApplication.class, args);
	}

}
