package com.example.nordicmotorhomes.service;

import com.example.nordicmotorhomes.repository.MotorhomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotorhomeService {
    @Autowired
    MotorhomeRepository motorhomeRepository;


}
