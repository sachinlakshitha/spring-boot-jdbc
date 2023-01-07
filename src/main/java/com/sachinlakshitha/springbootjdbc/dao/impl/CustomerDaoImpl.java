package com.sachinlakshitha.springbootjdbc.dao.impl;

import com.sachinlakshitha.springbootjdbc.dao.CustomerDao;
import com.sachinlakshitha.springbootjdbc.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
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
