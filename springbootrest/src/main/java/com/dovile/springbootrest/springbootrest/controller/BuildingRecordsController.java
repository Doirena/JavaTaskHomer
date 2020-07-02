package com.dovile.springbootrest.springbootrest.controller;



import com.dovile.springbootrest.springbootrest.entities.BuildingRecords;
import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.entities.Property;
import com.dovile.springbootrest.springbootrest.exception.ResourceNotFoundException;
import com.dovile.springbootrest.springbootrest.service.BuildingRecordsService;
import com.dovile.springbootrest.springbootrest.service.OwnerService;
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
@RequestMapping("/api/v3")
@Api(value="Building records controller ", description ="create, update, delete and get the whole list of buildings records. Calculate the total yearly real estate tax by owner's id.")
public class BuildingRecordsController {

    @Autowired
    private BuildingRecordsService recordsService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private PropertyService propertyService;


    @GetMapping("/records")
    @ApiOperation(value = "List of current building's records", response=List.class )
    public List<BuildingRecords> getAllBuildingRecords() {
        return recordsService.findAllrecords();
    }

    @PostMapping(value ="record",  params = {"owner","property"})
    @ApiOperation(value = "Create a new building record", response=BuildingRecords.class)
    public BuildingRecords createRecords(
            @ApiParam(value = "Insert information about the building", required = true)@RequestBody BuildingRecords record,
            @ApiParam(value = "Choose the owner name", required = true)@RequestParam(value = "owner") String owner,
            @ApiParam(value = "Choose the propety type", required = true)@RequestParam(value = "property") String property){
        record.setId(null);
        Property property1 = propertyService.findPropertyByType(property);
        if (property1 != null){
            record.setPropertyType(property1);
        }else{
            throw new ArithmeticException("Please insert a correct Property Type");
        }

        Owner owner1 = ownerService.findOwnerByName(owner);
        if (owner1 !=null){
            record.setOwner(owner1);
        }else{
            throw new ArithmeticException("Please insert a correct Owner");
        }
        return recordsService.createRecord(record);
    }


    @PutMapping("/record/{id}")
    @ApiOperation(value = "Update the building's information", response=BuildingRecords.class)
    public ResponseEntity<BuildingRecords> updateRecords(
            @PathVariable(value = "id") Integer recordId,
            @ApiParam(value = "Choose the building's record id", required = true)  @RequestBody BuildingRecords redordDetails) throws ResourceNotFoundException {

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
    @ApiOperation(value = "Delete the building record", response=BuildingRecords.class)
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
    @ApiOperation(value = "Calculate the total yearly real estate tax by owner's id", response=String.class)
        public String getTaxes(@PathVariable(value = "id") Integer id) {
            String result = recordsService.CalculateTaxes(id);
            if (result == null){
                throw new ArithmeticException("Owner not found");
            }
        return result;
    }

}
