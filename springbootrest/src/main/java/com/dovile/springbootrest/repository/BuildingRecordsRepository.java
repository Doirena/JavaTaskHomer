package com.dovile.springbootrest.repository;
import com.dovile.springbootrest.entities.BuildingRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BuildingRecordsRepository extends JpaRepository <BuildingRecords, Integer> {

}
