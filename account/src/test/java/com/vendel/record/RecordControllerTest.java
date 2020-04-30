package com.vendel.record;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = RecordController.class)
class RecordControllerTest {
	
	@MockBean
	RecordService service;	
	 
    @Autowired
    private WebTestClient webClient;
    
    private static Record testR1;	 
    private static Record testR2;	
    private static Record testR3;
    
    @BeforeAll
    public static void setup() throws Exception {
          testR1 = new Record("TestLast","TestFirst","M","Purple","1981-02-07");	 
          testR2= new Record("TestLast2","TestFirst2","F","Purple","1981-03-07");	
          testR3= new Record("TestLast3","TestFirst3","F","Cherry","1981-12-06");	 
    }

    
    @Test
    void testGetAllRecordsByLastName() throws Exception {    
        List<Record> list = new ArrayList<Record>();
        list.add(testR1);
        list.add(testR2);
        Flux<Record> recordFlux = Flux.fromIterable(list);  
         
        Mockito
            .when(service.getAllRecordsByLastName())
            .thenReturn(recordFlux);
 
        webClient.get().uri("/records/name")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Record.class)
            .contains(testR1)
            .contains(testR2);
         
        Mockito.verify(service, times(1)).getAllRecordsByLastName();
    }

    @Test
    void testGetAllRecordsByGender() throws Exception { 
        List<Record> list = new ArrayList<Record>();
        list.add(testR1);
        Flux<Record> recordFlux = Flux.fromIterable(list);  
         
        Mockito
            .when(service.getAllRecordsByGender())
            .thenReturn(recordFlux);
 
        webClient.get().uri("/records/gender")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Record.class)
            .contains(testR1);
         
        Mockito.verify(service, times(1)).getAllRecordsByGender();
    } 
    
    @Test
    void testGetAllRecordsByDateOfBirth() throws Exception { 
        List<Record> list = new ArrayList<Record>();
        list.add(testR1);
        list.add(testR3);
        Flux<Record> recordFlux = Flux.fromIterable(list);  
         
        Mockito
            .when(service.getAllRecordsByDateOfBirth())
            .thenReturn(recordFlux);
 
        webClient.get().uri("/records/birthdate")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Record.class)
            .contains(testR1)
            .contains(testR3);
         
        Mockito.verify(service, times(1)).getAllRecordsByDateOfBirth();
    } 
    
    @Test
    void testAddRecord() throws Exception { 
        Mockito.when(service.addRecord(testR1)).thenReturn(Mono.just(testR1));
         
        webClient.post().uri("/records")
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(testR1)
	        .exchange()
	        .expectStatus().isOk();
         
        Mockito.verify(service, times(1)).addRecord(testR1);
    }     

    
    
}
