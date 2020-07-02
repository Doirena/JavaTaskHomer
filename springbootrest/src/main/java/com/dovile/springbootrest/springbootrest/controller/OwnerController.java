package com.dovile.springbootrest.springbootrest.controller;

import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.exception.ResourceNotFoundException;

import com.dovile.springbootrest.springbootrest.service.OwnerService;
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
public class OwnerController {

    @Autowired
    private OwnerService ownerService;


    @GetMapping("/owners")
    public List<Owner> getAllOwners() {
       return ownerService.findAllOwners();
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<Owner> getOwnerById(
            @PathVariable(value = "id") Integer ownerId) throws ResourceNotFoundException {
        Owner owner = ownerService.findOwnerById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));
        return ResponseEntity.ok().body(owner);
    }

    @PostMapping("/owner")
    public Owner createOwner(@RequestBody @Validated Owner owner) {
        owner.setId(null);
        return ownerService.createOwner(owner);
    }

    @PutMapping("/owner/{id}")
    public ResponseEntity<Owner> updateOwner(
            @PathVariable(value = "id") Integer ownerId,
            @RequestBody Owner ownerDetails) throws ResourceNotFoundException {
        Owner owner = ownerService.findOwnerById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));
        owner.setName(ownerDetails.getName());
        owner.setBuildingRecords(ownerDetails.getBuildingRecords());

        final Owner updatedOwner = ownerService.createOwner(owner);
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/owner/{id}")
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
