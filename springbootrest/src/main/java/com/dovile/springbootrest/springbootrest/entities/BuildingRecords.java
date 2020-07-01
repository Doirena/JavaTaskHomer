package com.dovile.springbootrest.springbootrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "building_records")
@NamedQueries(value = {
        @NamedQuery(name = "BuildingRecords.RealEstateTaxes", query = "SELECT SUM(b.value*(p.tax_rate/100)) FROM BuildingRecords b join b.owner o join b.propertyType p WHERE (o.id = :id)")})


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class BuildingRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;
    private double size;
    private double value;
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Owner owner;
    @JoinColumn(name = "property_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Property propertyType;


    public BuildingRecords() {
    }
    public BuildingRecords(Integer id) {
        this.id = id;
    }

    public BuildingRecords(Integer id, String address, double size, double value) {
        this.id = id;
        this.address = address;
        this.size = size;
        this.value = value;
    }

    public BuildingRecords(Integer id, String address, double size, double value, Owner owner, Property property) {
        this.id = id;
        this.address = address;
        this.size = size;
        this.value = value;
        this.owner = owner;
        this.propertyType = property;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Property getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(Property propertyType) {
        this.propertyType = propertyType;
    }

}
