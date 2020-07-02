package com.dovile.springbootrest.springbootrest.controller;

import com.dovile.springbootrest.springbootrest.entities.BuildingRecords;
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
public class BuildingRecordsControllerIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "/api/v3";
    }

    @Test
    public void testGetAllBuildingRecords() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/records",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateRecord() {
        BuildingRecords record = new BuildingRecords();
        record.setAddress("Naugagardukas str. 1, Vilnius");
        record.setSize(200);
        record.setValue(30);
        ResponseEntity<BuildingRecords> postResponse = (ResponseEntity<BuildingRecords>) restTemplate.postForEntity(getRootUrl() + "/record?owner=Anna&property=Flat", record, BuildingRecords.class);
        System.out.println(postResponse);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
        assertEquals(postResponse.getBody().getOwner().getName(), "Anna");
    }

    @Test
    public void testUpdateRecord() {
        Integer id = 1;
        BuildingRecords record= restTemplate.getForObject(getRootUrl() + "/record/" + id, BuildingRecords.class);
        record.setValue(20);
        record.setSize(6);
        restTemplate.put(getRootUrl() + "/record/" + id, record);
        BuildingRecords updatedRecord = restTemplate.getForObject(getRootUrl() + "/record/" + id, BuildingRecords.class);
        assertNotNull(updatedRecord);
    }

    @Test
    public void testDeleteOwner() {
        Integer id = 1;
        BuildingRecords record = restTemplate.getForObject(getRootUrl() + "/record/" + id, BuildingRecords.class);
        assertNotNull(record);
        restTemplate.delete(getRootUrl() + "/record/" + id);
        try {
            record = restTemplate.getForObject(getRootUrl() + "/record/" + id, BuildingRecords.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

    @Test
    public void testGetTaxes() throws JSONException {
        Integer id = 3;
        String reponse = this.restTemplate.getForObject("/api/v3/taxes/" + id,String.class);
        System.out.println(reponse);
        assertEquals("4.65",reponse);

    }


}
