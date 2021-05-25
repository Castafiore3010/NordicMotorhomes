package com.example.nordicmotorhomes.service;

import com.example.nordicmotorhomes.model.Price;
import com.example.nordicmotorhomes.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    @Autowired
    PriceRepository priceRepository;


    public Price findPriceById(int id) { return priceRepository.findPriceById(id);}
}
