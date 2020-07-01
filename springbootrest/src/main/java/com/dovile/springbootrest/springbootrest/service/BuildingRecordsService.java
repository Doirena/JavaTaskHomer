package com.dovile.springbootrest.springbootrest.service;

import com.dovile.springbootrest.springbootrest.entities.BuildingRecords;
import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.repository.BuildingRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BuildingRecordsService {

    @Autowired
    private BuildingRecordsRepository recordsRepository;


    public List<BuildingRecords> findAllrecords() {
        return recordsRepository.findAll();
    }

    public Optional<BuildingRecords> findRecordById(Integer id) {
        return recordsRepository.findById(id);
    }

    public BuildingRecords createRecord(BuildingRecords records) {
        return recordsRepository.save(records);
    }

    public void deleteRecordById(Integer id) {
        recordsRepository.deleteById(id);
    }

    public String CalculateTaxes(Integer id){
        return recordsRepository.RealEstateTaxes(id);
    }

}
