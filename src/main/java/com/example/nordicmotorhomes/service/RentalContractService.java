package com.example.nordicmotorhomes.service;

import com.example.nordicmotorhomes.model.RentalContract;
import com.example.nordicmotorhomes.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RentalContractService {
    @Autowired
    RentalContractRepository rentalContractRepository;
}
