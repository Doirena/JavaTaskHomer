package com.dovile.springbootrest.repository;

import com.dovile.springbootrest.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository <Property, Integer> {
}
