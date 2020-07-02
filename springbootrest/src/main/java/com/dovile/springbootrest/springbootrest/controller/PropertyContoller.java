package com.dovile.springbootrest.springbootrest.controller;


import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.entities.Property;
import com.dovile.springbootrest.springbootrest.exception.ResourceNotFoundException;
import com.dovile.springbootrest.springbootrest.service.PropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v2")
@Api(value="Property type controller ", description ="create, update, delete and get property type by id. Get the whole list of properties types.")
public class PropertyContoller {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("/properties")
    @ApiOperation(value = "List of current properties types", response=List.class )
    public List<Property> getAllProperties() {
        return propertyService.findAllProperties();
    }

    @GetMapping("/property/{id}")
    @ApiOperation(value = "Get a property type by id", response=Property.class)
    public ResponseEntity<Property> getPropertyById(
            @PathVariable(value = "id") Integer propertyId) throws ResourceNotFoundException {
        Property property = propertyService.findPropertyById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));
        return ResponseEntity.ok().body(property);
    }

    @PostMapping("/property")
    @ApiOperation(value = "Create a new propety type", response=Property.class)
    public Property createProperty(
            @ApiParam(value = "Insert the property type and tax rate", required = true) @RequestBody Property property) {
        property.setId(null);
        return propertyService.createProperty(property);
    }

    @PutMapping("/property/{id}")
    @ApiOperation(value = "Update the property type", response=Property.class)
    public ResponseEntity<Property> updateProperty(
            @PathVariable(value = "id") Integer propertyId,
            @ApiParam(value = "Choose the propety type id", required = true) @RequestBody Property propertyDetails) throws ResourceNotFoundException {
        Property property = propertyService.findPropertyById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + propertyId));

        property.setTax_rate(propertyDetails.getTax_rate());
        property.setBuildingRecords(propertyDetails.getBuildingRecords());
        property.setType(propertyDetails.getType());


        final Property updatedProperty = propertyService.createProperty(property);
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/property/{id}")
    @ApiOperation(value = "Delete the property type", response=Owner.class)
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
