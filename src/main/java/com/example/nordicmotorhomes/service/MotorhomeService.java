package com.example.nordicmotorhomes.service;

import com.example.nordicmotorhomes.model.Motorhome;
import com.example.nordicmotorhomes.repository.MotorhomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorhomeService {
    @Autowired
    MotorhomeRepository motorhomeRepository;

public List<Motorhome> fetchAllMotorhomes () {
    return motorhomeRepository.fetchAllMotorhomes();
}

public Motorhome findMotorhomeById(int id) {
    return motorhomeRepository.findMotorhomeById(id);
}

public Motorhome updateMotorhome(Motorhome motorhome) {
    return motorhomeRepository.updateMotorhome(motorhome);
}

}
