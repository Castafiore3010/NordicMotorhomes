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

    public boolean motorhomeInContract(int id) {
        String sql ="SELECT count(*) from rental_contracts where motorhome_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, id);
        return result > 0;
    }

    public Motorhome updateMotorhome(Motorhome motorhome){

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

    public Motorhome deleteMotorhome(int id) {
        String delete_sql = "DELETE from motorhomes where motorhome_id = ?";
        template.update(delete_sql, id);
        return null;

    }

    public Motorhome insertMotorhome(Motorhome motorhome) {
        String insert_sql = "INSERT INTO motorhomes (model_name, brand_name, capacity, odometer, price_id) VALUES (?,?,?,?,?)";
        template.update(insert_sql, motorhome.getModel_name(), motorhome.getBrand_name(), motorhome.getCapacity(), motorhome.getOdometer(), motorhome.getPrice_id());
        return null;
    }

}
