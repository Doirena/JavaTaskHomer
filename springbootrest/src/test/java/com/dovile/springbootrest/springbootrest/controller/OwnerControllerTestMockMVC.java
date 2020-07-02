package com.dovile.springbootrest.springbootrest.controller;


import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.service.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OwnerController.class)
public class OwnerControllerTestMockMVC {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllOwners() throws Exception {
        given(ownerService.findAllOwners()).willReturn(
                Arrays.asList(new Owner(1, "Mike"),
                        new Owner(2, "Mike2"))
        );

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/owners")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:1, name:Mike}, {id:2, name: Mike2}]"))
                .andReturn();
    }

    @Test
    public void getOwnerByIdTest() throws Exception {
        Owner owner = new Owner();
        owner.setName("Mike");

        when(ownerService.findOwnerById(anyInt())).thenReturn(Optional.of(owner));

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/owner/1"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mike"))
                .andExpect(status().isOk());

    }

    @Test
    public void createOwnerTest() throws Exception {
        Owner owner = new Owner();
        owner.setName("Mike");

        when(ownerService.createOwner(any(Owner.class))).thenReturn(owner);


        mvc.perform(MockMvcRequestBuilders.post("/api/v1/owner")
                .content(new ObjectMapper().writeValueAsString(owner))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
//                .andExpect(status().isCreated())
                .andExpect(status().isOk());


    }

    @Test
    public void updateOwnerTest() throws Exception {
        Integer ownerId = 1;
        Owner owner = new Owner(ownerId, "Mike");
        given(ownerService.findOwnerById(ownerId)).willReturn(Optional.of(owner));
        given(ownerService.createOwner(any(Owner.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mvc.perform(MockMvcRequestBuilders.put("/api/v1/owner/{id}", owner.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mike"));
    }

    @Test
    public void deleteOwnerTest() throws Exception {
        Integer ownerId = 1;
        Owner owner = new Owner(ownerId, "Mike");
        given(ownerService.findOwnerById(ownerId)).willReturn(Optional.of(owner));
        doNothing().when(ownerService).deleteOwnerById(owner.getId());

        this.mvc.perform(MockMvcRequestBuilders.delete("/api/v1/owner/{id}", owner.getId()))
                .andExpect(status().isOk());

    }

}
