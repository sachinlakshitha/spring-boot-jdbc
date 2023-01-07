package com.sachinlakshitha.springbootjdbc;

import com.sachinlakshitha.springbootjdbc.dto.CustomerDto;
import com.sachinlakshitha.springbootjdbc.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.UUID;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class SpringBootJdbcApplication implements CommandLineRunner {
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("[SAVE]");
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(UUID.randomUUID().toString());
		customerDto.setName("Sachin Lakshitha");
		customerDto.setEmail("sachin@email.com");
		customerDto.setCreatedTime(new Date());
		Boolean isSaved = customerService.save(customerDto);

		if (isSaved) {
			log.info("Customer [{}] saved successfully", customerDto.getName());
		} else {
			log.info("Customer [{}] saving failed", customerDto.getName());
		}

		log.info("[FIND_BY_ID] {}", customerService.findById(customerDto.getId()));

		log.info("[FIND_ALL] {}", customerService.findAll());

		log.info("[UPDATE]");
		customerDto.setName("Sachin Lakshitha (Updated)");
		Boolean isUpdated = customerService.update(customerDto);

		if (isUpdated) {
			log.info("Customer [{}] updated successfully", customerDto.getName());
		} else {
			log.info("Customer [{}] updating failed", customerDto.getName());
		}

		log.info("[FIND_BY_ID] {}", customerService.findById(customerDto.getId()));

		log.info("[DELETE]");
		Boolean isDeleted = customerService.delete(customerDto.getId());

		if (isDeleted) {
			log.info("Customer [{}] deleted successfully", customerDto.getName());
		} else {
			log.info("Customer [{}] deleting failed", customerDto.getName());
		}

		log.info("[FIND_ALL] {}", customerService.findAll());
	}
}
