package com.vendel.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Order(value = 2)
@Component
public class RecordReader implements CommandLineRunner {

	@Autowired
	RecordService service;
	 
    @Override
    public void run(String... args) throws Exception {

        System.out.println("	OUTPUT 1 Gender then Name ASC		******************************************************		");        
        service.getAllRecordsByGender().blockFirst();
        service.getAllRecordsByGender().doOnNext(System.out::println).blockLast(); 

        System.out.println("	OUTPUT 2 Date of Birth   ASC    	******************************************************		");
        service.getAllRecordsByDateOfBirth().doOnNext(System.out::println).blockLast(); 
        
        System.out.println("	OUTPUT 3 Lastname DESC      		******************************************************		");
        service.getAllRecordsByLastName().doOnNext(System.out::println).blockLast(); 
                             
    }	
    
 


	
}
