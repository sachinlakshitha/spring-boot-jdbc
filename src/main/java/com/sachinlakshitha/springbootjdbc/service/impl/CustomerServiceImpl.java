package com.sachinlakshitha.springbootjdbc.service.impl;

import com.sachinlakshitha.springbootjdbc.dao.CustomerDao;
import com.sachinlakshitha.springbootjdbc.dto.CustomerDto;
import com.sachinlakshitha.springbootjdbc.model.Customer;
import com.sachinlakshitha.springbootjdbc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao;
    private ModelMapper modelMapper;

    @Override
    public Boolean save(CustomerDto customerDto) {
        return customerDao.save(convertToEntity(customerDto)) > 0;
    }

    @Override
    public List<CustomerDto> findAll() {
        return customerDao.findAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public List<CustomerDto> findAllByPage(Pageable page) {
        return customerDao.findAllByPage(page).stream().map(this::convertToDto).toList();
    }

    @Override
    public List<CustomerDto> findAllBySort(Sort sort) {
        return customerDao.findAllBySort(sort).stream().map(this::convertToDto).toList();
    }

    @Override
    public List<CustomerDto> findAllBySortAndPage(Pageable page) {
        return customerDao.findAllBySortAndPage(page).stream().map(this::convertToDto).toList();
    }

    @Override
    public CustomerDto findById(String id) {
        return customerDao.findById(id).map(this::convertToDto).orElse(null);
    }

    @Override
    public Boolean update(CustomerDto customerDto) {
        return customerDao.update(convertToEntity(customerDto)) > 0;
    }

    @Override
    public Boolean delete(String id) {
        return customerDao.delete(id) > 0;
    }

    public CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    public Customer convertToEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }
}
