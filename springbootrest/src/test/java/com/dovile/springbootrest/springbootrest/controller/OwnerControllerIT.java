package com.dovile.springbootrest.springbootrest.controller;

import com.dovile.springbootrest.springbootrest.entities.Owner;
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
public class OwnerControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "/api/v1";
    }
    @Test
    public void testGetAllOwners() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/owners",
                HttpMethod.GET, entity, String.class);
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAllOwnersName() throws JSONException {
        String response = restTemplate.getForObject(getRootUrl() + "/owners", String.class);
        JSONAssert.assertEquals("[{name:Ben}, {name:Anna}, {name:Victoria}, {name:John}]", response, false);
    }

    @Test
    public void testGetOwnerById() {
        Owner owner= restTemplate.getForObject(getRootUrl() + "/owner/1", Owner.class);
        System.out.println(owner.getName());
        assertNotNull(owner);
    }

    @Test
    public void testCreateOwner() {
        Owner owner = new Owner();
        owner.setName("Dovile");
        ResponseEntity<Owner> postResponse = restTemplate.postForEntity(getRootUrl() + "/owner", owner, Owner.class);
        System.out.println(owner.getName());
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateOwner() {
        int id = 1;
        Owner owner = restTemplate.getForObject(getRootUrl() + "/owner/" + id, Owner.class);
        owner.setName("Dovile");
        owner.setName("Dovile1");
        restTemplate.put(getRootUrl() + "/owner/" + id, owner);
        Owner updatedOwner = restTemplate.getForObject(getRootUrl() + "/owner/" + id, Owner.class);
        assertNotNull(updatedOwner);
    }

    @Test
    public void testDeleteOwner() {
        int id = 2;
        Owner owner = restTemplate.getForObject(getRootUrl() + "/owner/" + id, Owner.class);
        assertNotNull(owner);
        restTemplate.delete(getRootUrl() + "/owner/" + id);
        try {
            owner = restTemplate.getForObject(getRootUrl() + "/owner/" + id, Owner.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
