package com.dovile.springbootrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "property")
@NamedQueries({
        @NamedQuery(name = "Property.findBYType", query = "SELECT p FROM Property p WHERE p.type =: property")})

public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String type;
    private double tax_rate;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "propertyType")
    private List<BuildingRecords> buildingRecords;

    public Property() {
    }

    public Property(Integer id) {
        this.id = id;
    }

    public Property(Integer id, String type, int tax_rate) {
        this.id = id;
        this.type = type;
        this.tax_rate = tax_rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(double tax_rate) {
        this.tax_rate = tax_rate;
    }

    public List<BuildingRecords> getBuildingRecords() {
        return buildingRecords;
    }

    public void setBuildingRecords(List<BuildingRecords> buildingRecords) {
        this.buildingRecords = buildingRecords;
    }


}
