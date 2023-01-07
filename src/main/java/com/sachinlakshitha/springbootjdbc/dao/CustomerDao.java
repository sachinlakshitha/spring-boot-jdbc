package com.sachinlakshitha.springbootjdbc.dao;

import com.sachinlakshitha.springbootjdbc.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    Integer save(Customer customer);
    List<Customer> findAll();
    Optional<Customer> findById(String id);
    Integer update(Customer customer);
    Integer delete(String id);
}
