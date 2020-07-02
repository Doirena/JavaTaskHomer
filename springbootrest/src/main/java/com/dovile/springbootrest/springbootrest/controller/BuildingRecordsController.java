package com.dovile.springbootrest.springbootrest.controller;



import com.dovile.springbootrest.springbootrest.entities.BuildingRecords;
import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.entities.Property;
import com.dovile.springbootrest.springbootrest.exception.ResourceNotFoundException;
import com.dovile.springbootrest.springbootrest.service.BuildingRecordsService;
import com.dovile.springbootrest.springbootrest.service.OwnerService;
import com.dovile.springbootrest.springbootrest.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v3")
public class BuildingRecordsController {

    @Autowired
    private BuildingRecordsService recordsService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private PropertyService propertyService;


    @GetMapping("/records")
    public List<BuildingRecords> getAllBuildingRecords() {
        return recordsService.findAllrecords();
    }

    @PostMapping(value ="record",  params = {"owner","property"})
    public BuildingRecords createRecords(@RequestBody BuildingRecords record,
                                         @RequestParam(value = "owner") String owner,
                                         @RequestParam(value = "property") String property){
        record.setId(null);
        Property property1 = propertyService.findPropertyByType(property);
        if (property1 != null){
            record.setPropertyType(property1);
        }

        Owner owner1 = ownerService.findOwnerByName(owner);
        if (owner1 !=null){
            record.setOwner(owner1);
        }
        if (record.getOwner() == null){
            throw new ArithmeticException("Please insert correct Owner");
        }


        if (record.getPropertyType() == null){
            throw new ArithmeticException("Please insert correct Property Type");
        }
        return recordsService.createRecord(record);
    }


    @PutMapping("/record/{id}")
    public ResponseEntity<BuildingRecords> updateRecords(
            @PathVariable(value = "id") Integer recordId,
            @RequestBody BuildingRecords redordDetails) throws ResourceNotFoundException {

        BuildingRecords record = recordsService.findRecordById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found on: " + recordId));

        if (redordDetails.getAddress()!= null) {
            record.setAddress(redordDetails.getAddress());
        }
        if (redordDetails.getOwner() != null) {
            Owner owner = ownerService.findOwnerById(redordDetails.getOwner().getId()).
                    orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + redordDetails.getOwner().getId()));
            record.setOwner(redordDetails.getOwner());
        }

        if (redordDetails.getPropertyType()!= null) {
            Property property = propertyService.findPropertyById(redordDetails.getPropertyType().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + redordDetails.getPropertyType().getId()));
            record.setPropertyType(redordDetails.getPropertyType());
        }
        record.setSize(redordDetails.getSize());
        record.setValue(redordDetails.getValue());

        final BuildingRecords updatedRecord = recordsService.createRecord(record);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/record/{id}")
    public Map<String, Boolean> deleteOwner(
            @PathVariable(value = "id") Integer recordId) throws Exception {
        BuildingRecords record = recordsService.findRecordById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found on: " + recordId));

        recordsService.deleteRecordById(recordId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/taxes/{id}")
        public String getTaxes(@PathVariable(value = "id") Integer id) {
            String result = recordsService.CalculateTaxes(id);
            if (result == null){
                throw new ArithmeticException("Owner not found");
            }
        return result;
    }

}
