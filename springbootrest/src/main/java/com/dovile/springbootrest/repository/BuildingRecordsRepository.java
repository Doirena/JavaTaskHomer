package com.dovile.springbootrest.repository;
import com.dovile.springbootrest.entities.BuildingRecords;
import com.dovile.springbootrest.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BuildingRecordsRepository extends JpaRepository <BuildingRecords, Integer> {

    @Query(name = "BuildingRecords.RealEstateTaxes")
    String RealEstateTaxes (@Param("id") Integer id);

}
