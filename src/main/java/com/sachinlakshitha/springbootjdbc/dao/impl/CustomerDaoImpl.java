package com.sachinlakshitha.springbootjdbc.dao.impl;

import com.sachinlakshitha.springbootjdbc.dao.CustomerDao;
import com.sachinlakshitha.springbootjdbc.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDaoImpl implements CustomerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDatasource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Integer save(Customer customer) {
        String sql = "INSERT INTO CUSTOMER (ID,NAME,EMAIL) VALUES(:id,:name,:email)";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", customer.getId());
        mapSqlParameterSource.addValue("name", customer.getName());
        mapSqlParameterSource.addValue("email", customer.getEmail());

        return namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM CUSTOMER";
        return jdbcTemplate.query(sql, (rs, rowNum) -> getCustomer(rs));
    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM CUSTOMER", Integer.class);
    }

    @Override
    public Page<Customer> findAllByPage(Pageable page) {
        String sql = "SELECT * FROM CUSTOMER LIMIT " + page.getOffset() + " , " + page.getPageSize();
        List<Customer> customers = jdbcTemplate.query(sql, (rs, rowNum) -> getCustomer(rs));
        return new PageImpl<Customer>(customers, page, count());
    }

    @Override
    public List<Customer> findAllBySort(Sort sort) {
        Sort.Order order = sort.toList().get(0);
        String sql = "SELECT * FROM CUSTOMER ORDER BY " + order.getProperty() + " " + order.getDirection().name();
        return jdbcTemplate.query(sql, (rs, rowNum) -> getCustomer(rs));
    }

    @Override
    public Page<Customer> findAllBySortAndPage(Pageable page) {
        Sort.Order order = !page.getSort().isEmpty() ? page.getSort().toList().get(0) : Sort.Order.by("ID");
        List<Customer> customers = jdbcTemplate.query("SELECT * FROM CUSTOMER ORDER BY " + order.getProperty() + " "
                        + order.getDirection().name() + " LIMIT " + page.getOffset() + " , " + page.getPageSize(),
                (rs, rowNum) -> getCustomer(rs));
        return new PageImpl<Customer>(customers, page, count());
    }

    @Override
    public Optional<Customer> findById(String id) {
        String sql = "SELECT * FROM CUSTOMER WHERE ID=:id";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        return namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id),
                (rs, rowNum) -> Optional.of(getCustomer(rs)));
    }

    private Customer getCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getString("ID"));
        customer.setName(rs.getString("NAME"));
        customer.setEmail(rs.getString("EMAIL"));
        customer.setCreatedTime(rs.getTimestamp("CREATED_TIME"));
        return customer;
    }

    @Override
    public Integer update(Customer customer) {
        String sql = "UPDATE CUSTOMER SET NAME=:name,EMAIL=:email WHERE ID=:id";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", customer.getId());
        mapSqlParameterSource.addValue("name", customer.getName());
        mapSqlParameterSource.addValue("email", customer.getEmail());

        return namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public Integer delete(String id) {
        String sql = "DELETE FROM CUSTOMER WHERE ID=:id";

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);

        return namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }
}
