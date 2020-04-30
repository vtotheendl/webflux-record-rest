package com.vendel.record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller   
public class RecordController {  
	
	    @Autowired
	    RecordService service;
	    
	    @RequestMapping(path = "/records") 
	    @PostMapping()  
	    public @ResponseBody  
	    Mono<Record> addRecord(@RequestBody Record record) { 	       
	    	return service.addRecord(record);
	    }  
	    
	    @RequestMapping(path = "/records/name") 
	    @GetMapping()  
	    public @ResponseBody  
	    Flux<Record> getAllRecordsByLastName() {  
	    	return service.getAllRecordsByLastName();
	    }
	    
	    @RequestMapping(path = "/records/gender") 
	    @GetMapping()  
	    public @ResponseBody  
	    Flux<Record> getAllRecordsByGender() {  
	    	return service.getAllRecordsByGender(); 
	    }
	    
	    @RequestMapping(path = "/records/birthdate") 
	    @GetMapping()  
	    public @ResponseBody  
	    Flux<Record> getAllRecordsByBirthdate() {  
	    	return service.getAllRecordsByDateOfBirth();
	    }	    
	}	
