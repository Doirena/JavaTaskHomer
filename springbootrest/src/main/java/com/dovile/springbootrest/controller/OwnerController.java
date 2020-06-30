package com.dovile.springbootrest.controller;

import com.dovile.springbootrest.entities.Owner;
import com.dovile.springbootrest.exception.ResourceNotFoundException;
import com.dovile.springbootrest.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/owners")
    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<Owner> getUserById(
            @PathVariable(value = "id") Integer ownerId) throws ResourceNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));
        return ResponseEntity.ok().body(owner);
    }

    @PostMapping("/owner")
    @Transactional
    public Owner createOwner(@RequestBody Owner owner) {
        owner.setId(null);
        return ownerRepository.save(owner);
    }

    @PutMapping("/owner/{id}")
    @Transactional
    public ResponseEntity<Owner> updateOwner(
            @PathVariable(value = "id") Integer ownerId,
            @RequestBody Owner ownerDetails) throws ResourceNotFoundException {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));
        owner.setName(ownerDetails.getName());
        owner.setBuildingRecords(ownerDetails.getBuildingRecords());

        final Owner updatedOwner = ownerRepository.save(owner);
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/owner/{id}")
    @Transactional
    public Map<String, Boolean> deleteOwner(
            @PathVariable(value = "id") Integer ownerId) throws Exception {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found on: " + ownerId));

        ownerRepository.delete(owner);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
