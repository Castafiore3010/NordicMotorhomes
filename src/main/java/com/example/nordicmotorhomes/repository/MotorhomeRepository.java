package com.example.nordicmotorhomes.repository;
import com.example.nordicmotorhomes.model.Motorhome;
import com.example.nordicmotorhomes.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Marc,Emma,Samavia, Michael
 * @version 1.0
 */
@Repository
public class MotorhomeRepository {
    @Autowired
    JdbcTemplate template;

    /**
     * @author Emma
     * @return list of motorhomes
     */
    public List<Motorhome> fetchAllMotorhomes () { //get all motorhomes
        return template.query("SELECT * FROM motorhomes", new BeanPropertyRowMapper<>(Motorhome.class));
    }

    /**
     * @author Samavia
     * @param id  used for DML
     * @return a Motorhome object
     */
    public Motorhome findMotorhomeById(int id) { //get specific motorhome
        String sql = "SELECT * FROM motorhomes WHERE motorhome_id = ?";
        RowMapper<Motorhome> motorhomeRowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        return template.queryForObject(sql, motorhomeRowMapper, id);
    }

    /**
     * @author Michael
     * @param id used for DML
     * @return boolean, decides if motorhome is in Contract
     */
    public boolean motorhomeInContract(int id) { // check if motorhome is in contract (for safe delete)
        String sql ="SELECT count(*) from rental_contracts where motorhome_id = ?";
        Integer result = template.queryForObject(sql, Integer.class, id);
        return result > 0;
    }

    /**
     * @author Marc
     * @param motorhome object to update
     * @return null
     */
    public Motorhome updateMotorhome(Motorhome motorhome){ // update database

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

    /**
     * @author Emma
     * @param id  used for DML
     * @return null
     */
    public Motorhome deleteMotorhome(int id) { // delete from database
        String delete_sql = "DELETE from motorhomes where motorhome_id = ?";
        template.update(delete_sql, id);
        return null;

    }

    /**
     * @author Emma
     * @param motorhome used for DML
     * @return null
     */
    public Motorhome insertMotorhome(Motorhome motorhome) { // write data to database
        String insert_sql = "INSERT INTO motorhomes (model_name, brand_name, capacity, odometer, price_id) VALUES (?,?,?,?,?)";
        template.update(insert_sql, motorhome.getModel_name(), motorhome.getBrand_name(), motorhome.getCapacity(), motorhome.getOdometer(), motorhome.getPrice_id());
        return null;
    }

}
