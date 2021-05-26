package com.example.nordicmotorhomes.service;


import com.example.nordicmotorhomes.model.ContactPoint;
import com.example.nordicmotorhomes.repository.ContactPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactPointService {
    @Autowired
    ContactPointRepository contactPointRepository;

    public List<ContactPoint> fetchAllContactPoints() {return contactPointRepository.fetchAllContactPoints();}

    public List<ContactPoint> fetchAllValidContactPoints() {return contactPointRepository.fetchAllValidContactPoints(); }

    public ContactPoint updateContactPoint(ContactPoint contactPoint){
        return contactPointRepository.updateContactPoint(contactPoint);
    }
    public ContactPoint insertContactPoint(ContactPoint contactPoint) {
        return contactPointRepository.insertContactPoint(contactPoint);
    }
    public Integer contactPointIdByLatAndlng(double[] coordinates) { return contactPointRepository.contactPointIdByLatAndLng(coordinates); }

    public ContactPoint findContactPointById(int id){
        return contactPointRepository.findContactPointById(id);
    }
}
