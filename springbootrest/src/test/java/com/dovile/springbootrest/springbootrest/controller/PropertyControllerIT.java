package com.dovile.springbootrest.springbootrest.controller;

import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.entities.Property;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "/api/v2";
    }

    @Test
    public void testGetAllProperties() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/properties",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllPropValue() throws JSONException {
        String response = restTemplate.getForObject(getRootUrl() + "/properties", String.class);
        JSONAssert.assertEquals("[{id:1},{id:2},{id:3},{id:4}]", response, false);
    }

    @Test
    public void testGetOnePropValue() throws JSONException {
        String response = restTemplate.getForObject(getRootUrl() + "/property/4", String.class);
        JSONAssert.assertEquals("{type:House}", response, false);
    }
    @Test
    public void testGetPropertyById() {
       Property property = restTemplate.getForObject(getRootUrl() + "/property/1", Property.class);
        assertNotNull(property);
    }

    @Test
    public void testCreateProperty() {
        Property property = new Property();
        property.setType("Flat");
        property.setTax_rate(20.5);
        ResponseEntity<Property> postResponse = restTemplate.postForEntity(getRootUrl() + "/property", property, Property.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateProperty() {
        Integer id = 1;
        Property property = restTemplate.getForObject(getRootUrl() + "/property/" + id, Property.class);
        property.setType("House");
        property.setType("Garden-House");

        restTemplate.put(getRootUrl() + "/property/" + id, property);
        Property updatedProperty = restTemplate.getForObject(getRootUrl() + "/property/" + id, Property.class);
        assertNotNull(updatedProperty);
    }

    @Test
    public void testDeleteProperty() {
        Integer id = 4;
        Property property = restTemplate.getForObject(getRootUrl() + "/property/" + id, Property.class);
        assertNotNull(property);
        restTemplate.delete(getRootUrl() + "/property/" + id);
        try {
            property = restTemplate.getForObject(getRootUrl() + "/property/" + id, Property.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
