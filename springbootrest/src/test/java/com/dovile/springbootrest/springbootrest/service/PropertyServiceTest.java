package com.dovile.springbootrest.springbootrest.service;


import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.entities.Property;
import com.dovile.springbootrest.springbootrest.repository.PropertyRepository;
import com.dovile.springbootrest.springbootrest.service.PropertyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PropertyServiceTest {

    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private PropertyRepository propertyRepository;

    @Test
    public void shouldSavedProperty() {
        final Property property = new Property(null, "Flat", 20);
        given(propertyRepository.save(property)).willAnswer(invocation -> invocation.getArgument(0));
        Property savedProperty = propertyService.createProperty(property);
        assertThat(savedProperty).isNotNull();
        verify(propertyRepository).save(any(Property.class));
    }

    @Test
    public void shouldReturnAllProperties() {
        List<Property> propertyList = new ArrayList();
        propertyList.add(new Property(1, "Flat", 10));
        propertyList.add(new Property(2, "House", 20));
        propertyList.add(new Property(3, "Apartament", 30));
        given(propertyRepository.findAll()).willReturn(propertyList);
        List<Property> expected = propertyService.findAllPropertys();
        assertEquals(expected, propertyList);
    }

    @Test
    public void findPropertyById(){
        final Integer id = 1;
        final Property property = new Property(1, "Flat", 10);
        given(propertyRepository.findById(id)).willReturn(Optional.of(property));
        final Optional<Property> expected = propertyService.findPropertyById(id);
        assertThat(expected).isNotNull();
    }

    @Test
    public void findPropertyByType(){
        final String type = "Flat";
        final Property property = new Property(1, "Flat", 10);
        given(propertyRepository.findBYType(type)).willReturn(property);
        final Property expected = propertyService.findPropertyByType(type);
        assertThat(expected).isNotNull();
    }

    @Test
    public void shouldDeleteProperty() {
        final Integer id = 1;
        propertyService.deletePropertyById(id);
        propertyService.deletePropertyById(id);
        verify(propertyRepository, atLeastOnce()).deleteById(id);
    }


}
