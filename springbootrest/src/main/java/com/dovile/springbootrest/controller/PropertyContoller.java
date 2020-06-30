package com.dovile.springbootrest.controller;


import com.dovile.springbootrest.entities.Property;
import com.dovile.springbootrest.exception.ResourceNotFoundException;
import com.dovile.springbootrest.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class PropertyContoller {

    @Autowired
    private PropertyRepository propertyRepository;

    @GetMapping("properties")
    public List<Property> getAllBuildingRecords() {
        return propertyRepository.findAll();
    }

    @GetMapping("/property/{id}")
    public ResponseEntity<Property> getPropertyById(
            @PathVariable(value = "id") Integer propertyId) throws ResourceNotFoundException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));
        return ResponseEntity.ok().body(property);
    }

    @PostMapping("/property")
    @Transactional
    public Property createProperty(@RequestBody Property property) {
        property.setId(null);
        return propertyRepository.save(property);
    }

    @PutMapping("/property/{id}")
    @Transactional
    public ResponseEntity<Property> updateProperty(
            @PathVariable(value = "id") Integer propertyId,
            @RequestBody Property propertyDetails) throws ResourceNotFoundException {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));

        property.setTax_rate(propertyDetails.getTax_rate());
        property.setBuildingRecords(propertyDetails.getBuildingRecords());
        property.setType(propertyDetails.getType());


        final Property updatedProperty = propertyRepository.save(property);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/property/{id}")
    @Transactional
    public Map<String, Boolean> deleteProperty(
            @PathVariable(value = "id") Integer propertyId) throws Exception {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));

        propertyRepository.delete(property);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
