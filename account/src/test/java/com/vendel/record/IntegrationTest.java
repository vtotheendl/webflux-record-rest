package com.vendel.record;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class IntegrationTest {

	@Autowired
	RecordRepository repository;
	 
    @Autowired
    private WebTestClient webClient;
    
    private static Record testR1;	 
    private static Record testR2;	
    private static Record testR3;
    
    @BeforeAll
    public static void setup() throws Exception {
          testR1 = new Record("TestLast","TestFirst","M","Purple","1960-02-07");	 
          testR2= new Record("TestLast2","TestFirst2","F","Purple","1990-03-07");	
          testR3= new Record("TestLast3","TestFirst3","F","Cherry","1912-12-07");                     
    }

    @Test
    void testAddAndRetrieveRecords() throws Exception {    
    	repository.deleteAll();
    	
        webClient.post().uri("/records")
        	.contentType(MediaType.APPLICATION_JSON)
        	.bodyValue(testR1)
        	.exchange()
        	.expectStatus().isOk();
   
        webClient.post().uri("/records")
        	.contentType(MediaType.APPLICATION_JSON)
        	.bodyValue(testR2)
        	.exchange()
        	.expectStatus().isOk();
        
        webClient.post().uri("/records")
        	.contentType(MediaType.APPLICATION_JSON)
        	.bodyValue(testR3)
        	.exchange()
        	.expectStatus().isOk();        
        
 
        List<Record> list = new ArrayList<Record>();
        list.add(testR3);
        list.add(testR2);
        list.add(testR1);
        webClient.get().uri("/records/name")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(Record.class)
            .isEqualTo(list);

        list.clear();
        list.add(testR2);
        list.add(testR3);
        list.add(testR1);        
        webClient.get().uri("/records/gender")
        	.header(HttpHeaders.ACCEPT, "application/json")
        	.exchange()
        	.expectStatus().isOk()
        	.expectBodyList(Record.class)
        	.isEqualTo(list);
        
        list.clear();
        list.add(testR3);
        list.add(testR1);
        list.add(testR2);          
        webClient.get().uri("/records/birthdate")
        .header(HttpHeaders.ACCEPT, "application/json")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Record.class)
        .isEqualTo(list);        
        
    }
    
    
}
