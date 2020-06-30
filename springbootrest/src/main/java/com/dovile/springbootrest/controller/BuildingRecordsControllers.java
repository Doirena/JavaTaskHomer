package com.dovile.springbootrest.controller;


import com.dovile.springbootrest.entities.BuildingRecords;
import com.dovile.springbootrest.entities.Owner;
import com.dovile.springbootrest.repository.BuildingRecordsRepository;
import com.dovile.springbootrest.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class BuildingRecordsControllers {
    @Autowired
    private BuildingRecordsRepository buildingRecordsRepository;

    @Autowired
    private OwnerRepository ownerRepository;

//    @GetMapping("records/{id}")
//    public List<BuildingRecords> getAllBuildingRecords(@PathVariable("id") int id) {
//        return ownerRepository.findById(id).get().getBuildingRecords();
//    }

    @GetMapping("records")
    public List<BuildingRecords> getAllBuildingRecords() {
        return buildingRecordsRepository.findAll();
    }


}
