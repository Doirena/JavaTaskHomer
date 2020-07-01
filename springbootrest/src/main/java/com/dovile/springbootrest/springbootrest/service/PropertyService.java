package com.dovile.springbootrest.springbootrest.service;



import com.dovile.springbootrest.springbootrest.entities.Property;
import com.dovile.springbootrest.springbootrest.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> findAllPropertys() {
        return propertyRepository.findAll();
    }

    public Optional<Property> findPropertyById(Integer id) {
        return propertyRepository.findById(id);
    }

    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    public void deletePropertyById(Integer id) {
        propertyRepository.deleteById(id);
    }

    public Property findPropertyByType(String type) {
        return propertyRepository.findBYType(type);
    }



}
