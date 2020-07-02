package com.dovile.springbootrest.springbootrest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "property")
@NamedQueries({
        @NamedQuery(name = "Property.findBYType", query = "SELECT p FROM Property p WHERE p.type =: property")})
@ApiModel(description = "This is Property class, has type, tax rate ")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("This is property id (Integer format)")
    private Integer id;
    @ApiModelProperty("This is property type (String format)")
    private String type;
    @ApiModelProperty("This is property tax rate (Double format)")
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

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", tax_rate=" + tax_rate +
                ", buildingRecords=" + buildingRecords +
                '}';
    }
}
