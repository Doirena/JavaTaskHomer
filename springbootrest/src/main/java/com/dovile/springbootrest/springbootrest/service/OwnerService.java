package com.dovile.springbootrest.springbootrest.service;

import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    public Optional<Owner> findOwnerById(Integer id) {
        return ownerRepository.findById(id);
    }

    public Owner createOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public void deleteOwnerById(Integer id) {
        ownerRepository.deleteById(id);
    }

    public Owner findOwnerByName(String ownerName) {
        return ownerRepository.findBYName(ownerName);
    }

}
