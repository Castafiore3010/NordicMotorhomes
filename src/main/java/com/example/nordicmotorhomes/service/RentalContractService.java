package com.example.nordicmotorhomes.service;

import com.example.nordicmotorhomes.model.InsertRentalContract;
import com.example.nordicmotorhomes.model.Motorhome;
import com.example.nordicmotorhomes.model.RentalContract;
import com.example.nordicmotorhomes.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class RentalContractService {
    @Autowired
    RentalContractRepository rentalContractRepository;


    public List<RentalContract> fetchAllRentalContracts() { return rentalContractRepository.fetchAllRentalContracts(); }

    public List<RentalContract> fetchAllFinishedRentalContracts() { return rentalContractRepository.fetchAllFinishedRentalContracts(); }

    public RentalContract findContractById(int id){
        return rentalContractRepository.findContractById(id);
    }

    public RentalContract deleteRentalContractById(int id){
        return rentalContractRepository.deleteRentalContractById(id);
    }

    public Integer rentalContractIdByStartEndPersonIdMotorhomeId(InsertRentalContract rentalContract) {return rentalContractRepository.rentalContractIdByStartEndPersonIdMotorhomeId(rentalContract);}

    public RentalContract updateRentalContract (RentalContract rentalContract){
        return rentalContractRepository.updateRentalContract(rentalContract);
    }
    public RentalContract insertRentalContract(InsertRentalContract rentalContract) { return rentalContractRepository.insertRentalContract(rentalContract);}

    public boolean motorhomeInContractInPeriod(Motorhome motorhome, LocalDateTime start_datetime, LocalDateTime end_datetime) { return rentalContractRepository.motorhomeInContractInPeriod(motorhome, start_datetime, end_datetime);}
}
