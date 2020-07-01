package com.dovile.springbootrest.springbootrest.service;

import com.dovile.springbootrest.springbootrest.entities.BuildingRecords;
import com.dovile.springbootrest.springbootrest.entities.Owner;
import com.dovile.springbootrest.springbootrest.entities.Property;
import com.dovile.springbootrest.springbootrest.repository.BuildingRecordsRepository;

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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BuildingRecordsServiceTest {

    @InjectMocks
    private BuildingRecordsService recordsService;

    @Mock
    private BuildingRecordsRepository recordsRepository;

    @Test
    public void shouldSavedRecord() {
        final BuildingRecords record = new BuildingRecords(null, "Naugardukas str. 1", 200,20 );
        given(recordsRepository.save(record)).willAnswer(invocation -> invocation.getArgument(0));
        BuildingRecords savedRecords = recordsService.createRecord(record);
        assertThat(savedRecords).isNotNull();
        verify(recordsRepository).save(any(BuildingRecords.class));
    }

    @Test
    public void shouldReturnAllRecords() {
        List<BuildingRecords> recordsList = new ArrayList();
        recordsList.add(new BuildingRecords(1, "Naugardukas str. 1", 100,10 ));
        recordsList.add(new BuildingRecords(2, "Naugardukas str. 2", 200,30 ));
        recordsList.add(new BuildingRecords(3, "Naugardukas str. 3", 300,20 ));
        given(recordsRepository.findAll()).willReturn(recordsList);
        List<BuildingRecords> expected = recordsService.findAllrecords();
        assertEquals(expected, recordsList);
    }

    @Test
    public void findRecordById(){
        final Integer id = 1;
        final BuildingRecords record =new BuildingRecords(1, "Naugardukas str. 1", 100,10 );
        given(recordsRepository.findById(id)).willReturn(Optional.of(record));
        final Optional<BuildingRecords> expected = recordsService.findRecordById(id);
        assertThat(expected).isNotNull();
    }

    @Test
    public void shouldDeleteRecord() {
        final Integer id = 1;
        recordsService.deleteRecordById(id);
        recordsService.deleteRecordById(id);
        verify(recordsRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void shouldCalculateRight(){
        final Integer id = 1;
        final String rez = "200";
        //given(recordsRepository.RealEstateTaxes(id)).willReturn(rez);
        when(recordsRepository.RealEstateTaxes(id)).thenReturn(rez);

        String expected = recordsService.CalculateTaxes(id);
        assertEquals("200",expected);
    }




}
