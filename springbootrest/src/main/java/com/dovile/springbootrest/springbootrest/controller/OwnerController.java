package com.dovile.springbootrest.springbootrest.controller;

import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.exception.ResourceNotFoundException;

import com.dovile.springbootrest.springbootrest.service.OwnerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
@Api(value="Owner controller ", description ="create, update, delete and get an owner by id. Get the whole list of owners.")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;


    @GetMapping("/owners")
    @ApiOperation(value = "List of the current owners", response=List.class )
    public List<Owner> getAllOwners() {
       return ownerService.findAllOwners();
    }

    @GetMapping("/owner/{id}")
    @ApiOperation(value = "Get an owner by id", response=Owner.class)
    public ResponseEntity<Owner> getOwnerById(
            @PathVariable(value = "id") Integer ownerId) throws ResourceNotFoundException {
        Owner owner = ownerService.findOwnerById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));
        return ResponseEntity.ok().body(owner);
    }

    @PostMapping("/owner")
    @ApiOperation(value = "Create a new owner", response=Owner.class)
    @ApiResponses(value = {
            @ApiResponse(code = 504, message =  "System is overloaded... Wait a bit"),
            @ApiResponse(code = 505, message =  "Out of owner material")
    })
    public Owner createOwner(
            @ApiParam(value = "Add the owner name", required = true) @RequestBody @Validated Owner owner) {
        owner.setId(null);
        return ownerService.createOwner(owner);
    }

    @PutMapping("/owner/{id}")
    @ApiOperation(value = "Update the owner", response=Owner.class)
    public ResponseEntity<Owner> updateOwner(
            @PathVariable(value = "id") Integer ownerId,
           @ApiParam(value = "Choose the owner id", required = true)  @RequestBody Owner ownerDetails) throws ResourceNotFoundException {
        Owner owner = ownerService.findOwnerById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));
        owner.setName(ownerDetails.getName());
        owner.setBuildingRecords(ownerDetails.getBuildingRecords());

        final Owner updatedOwner = ownerService.createOwner(owner);
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/owner/{id}")
    @ApiOperation(value = "Delete the owner", response=Owner.class)
    public Map<String, Boolean> deleteOwner(
            @PathVariable(value = "id") Integer ownerId) throws Exception {
        Owner owner = ownerService.findOwnerById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));

        ownerService.deleteOwnerById(ownerId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
