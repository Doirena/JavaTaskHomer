package com.dovile.springbootrest.springbootrest.repository;

import com.dovile.springbootrest.springbootrest.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository <Property, Integer> {

    @Query(name = "Property.findBYType")
    Property findBYType (@Param("property") String property);
}
