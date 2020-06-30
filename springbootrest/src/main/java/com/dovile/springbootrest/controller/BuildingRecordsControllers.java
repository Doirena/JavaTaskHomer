package com.dovile.springbootrest.controller;


import com.dovile.springbootrest.entities.BuildingRecords;
import com.dovile.springbootrest.entities.Owner;
import com.dovile.springbootrest.entities.Property;
import com.dovile.springbootrest.exception.ResourceNotFoundException;
import com.dovile.springbootrest.repository.BuildingRecordsRepository;
import com.dovile.springbootrest.repository.OwnerRepository;
import com.dovile.springbootrest.repository.PropertyRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class BuildingRecordsControllers {
    @Autowired
    private BuildingRecordsRepository buildingRecordsRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PropertyRepository propertyRepository;


    @GetMapping("records")
    public List<BuildingRecords> getAllBuildingRecords() {
        return buildingRecordsRepository.findAll();
    }

    @PostMapping(value ="record",  params = {"owner","property"})
    @Transactional
    public BuildingRecords createRecords(@RequestBody BuildingRecords record,
                                         @RequestParam(value = "owner") String owner,
                                         @RequestParam(value = "property") String property){
        record.setId(null);
        Property property1 = propertyRepository.findBYType(property);
        if (property1 != null){
            record.setPropertyType(property1);
        }

        Owner owner1 = ownerRepository.findBYName(owner);
        if (owner1 !=null){
            record.setOwner(owner1);
        }
        if (record.getOwner() == null){
            throw new ArithmeticException("Please insert correct Owner");
        }

        if (record.getPropertyType() == null){
            throw new ArithmeticException("Please insert correct Property Type");
        }
        return buildingRecordsRepository.save(record);
    }


    @PutMapping("/record/{id}")
    @Transactional
    public ResponseEntity<BuildingRecords> updateRecords(
            @PathVariable(value = "id") Integer recordId,
            @RequestBody BuildingRecords redordDetails) throws ResourceNotFoundException {

        BuildingRecords record = buildingRecordsRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found on: " + recordId));

        if (redordDetails.getAddress()!= null) {
            record.setAddress(redordDetails.getAddress());
        }
        if (redordDetails.getOwner() != null) {
            Owner owner = ownerRepository.findById(redordDetails.getOwner().getId()).orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + redordDetails.getOwner().getId()));
            record.setOwner(redordDetails.getOwner());
        }

        if (redordDetails.getPropertyType()!= null) {
            Property property = propertyRepository.findById(redordDetails.getPropertyType().getId()).orElseThrow(() -> new ResourceNotFoundException("Property not found on: " + redordDetails.getPropertyType().getId()));
            record.setPropertyType(redordDetails.getPropertyType());
        }
        record.setSize(redordDetails.getSize());
        record.setValue(redordDetails.getValue());

        final BuildingRecords updatedRecord = buildingRecordsRepository.save(record);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("record/{id}")
    @Transactional
    public Map<String, Boolean> deleteOwner(
            @PathVariable(value = "id") Integer recordId) throws Exception {
        BuildingRecords record = buildingRecordsRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found on: " + recordId));

        buildingRecordsRepository.delete(record);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("taxes/{id}")
        public String getTaxes(@PathVariable(value = "id") Integer id) {
            String rezult = buildingRecordsRepository.RealEstateTaxes(id);
            if (rezult == null){
                throw new ArithmeticException("Owner not found");
            }
        return rezult;
    }

}
