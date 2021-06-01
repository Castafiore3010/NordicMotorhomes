package com.example.nordicmotorhomes.repository;

import com.example.nordicmotorhomes.model.Price;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
@Repository
public class PriceRepository {
    @Autowired
    JdbcTemplate template;

    /**
     * @author Emma
     * @param id used for DML
     * @return select from DML
     */

    public Price findPriceById(int id) {
        String find_sql = "SELECT * FROM prices WHERE price_id = ?";
        return template.queryForObject(find_sql,new BeanPropertyRowMapper<>(Price.class), id);
    }

}
