package com.sachinlakshitha.springbootjdbc.service;

import com.sachinlakshitha.springbootjdbc.dto.CustomerDto;
import com.sachinlakshitha.springbootjdbc.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Boolean save(CustomerDto customerDto);
    List<CustomerDto> findAll();
    CustomerDto findById(String id);
    Boolean update(CustomerDto customerDto);
    Boolean delete(String id);
}
