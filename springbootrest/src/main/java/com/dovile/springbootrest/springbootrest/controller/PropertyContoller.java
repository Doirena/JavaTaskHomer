package com.dovile.springbootrest.springbootrest.controller;


import com.dovile.springbootrest.springbootrest.entities.Property;
import com.dovile.springbootrest.springbootrest.exception.ResourceNotFoundException;
import com.dovile.springbootrest.springbootrest.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
public class PropertyContoller {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        return propertyService.findAllProperties();
    }

    @GetMapping("/property/{id}")
    public ResponseEntity<Property> getPropertyById(
            @PathVariable(value = "id") Integer propertyId) throws ResourceNotFoundException {
        Property property = propertyService.findPropertyById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));
        return ResponseEntity.ok().body(property);
    }

    @PostMapping("/property")
    public Property createProperty(@RequestBody Property property) {
        property.setId(null);
        return propertyService.createProperty(property);
    }

    @PutMapping("/property/{id}")
    public ResponseEntity<Property> updateProperty(
            @PathVariable(value = "id") Integer propertyId,
            @RequestBody Property propertyDetails) throws ResourceNotFoundException {
        Property property = propertyService.findPropertyById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));

        property.setTax_rate(propertyDetails.getTax_rate());
        property.setBuildingRecords(propertyDetails.getBuildingRecords());
        property.setType(propertyDetails.getType());


        final Property updatedProperty = propertyService.createProperty(property);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/property/{id}")
    public Map<String, Boolean> deleteProperty(
            @PathVariable(value = "id") Integer propertyId) throws Exception {
        Property property = propertyService.findPropertyById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));

        propertyService.deletePropertyById(propertyId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
