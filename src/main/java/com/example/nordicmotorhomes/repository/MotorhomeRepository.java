package com.example.nordicmotorhomes.repository;
import com.example.nordicmotorhomes.model.Motorhome;
import com.example.nordicmotorhomes.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MotorhomeRepository {
    @Autowired
    JdbcTemplate template;

public List<Motorhome> fetchAllMotorhomes () {
    return template.query("SELECT * FROM motorhomes", new BeanPropertyRowMapper<>(Motorhome.class));
}

public Motorhome findMotorhomeById(int id) {
    String sql = "SELECT * FROM motorhomes WHERE motorhome_id = ?";
    RowMapper<Motorhome> motorhomeRowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
    return template.queryForObject(sql, motorhomeRowMapper, id);
}

    public Motorhome updateMotorhome (Motorhome motorhome){

        String motorhomes_sql = "UPDATE motorhomes SET "+
                " model_name='"+motorhome.getModel_name()+"',"+
                " brand_name='"+motorhome.getBrand_name()+"', " +
                " capacity='"+motorhome.getCapacity()+"', " +
                " odometer='"+motorhome.getOdometer()+"', " +
                " price_id='"+motorhome.getPrice_id()+"'" +
                " WHERE motorhome_id = "+ motorhome.getMotorhome_id();
        template.update(motorhomes_sql);
        return null;
    }

}
