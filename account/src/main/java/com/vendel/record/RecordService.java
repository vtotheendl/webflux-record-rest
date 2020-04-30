package com.vendel.record;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class RecordService {

	@Autowired RecordRepository repository;

    public Flux<Record> getAllRecordsByLastName() {
        return repository.findAll(Sort.by(new Sort.Order(DESC, "lastName")));
    }
    
    public Flux<Record> getAllRecordsByDateOfBirth() {
        return repository.findAll(Sort.by(new Sort.Order(ASC, "dateOfBirth")));
    }
    
  
    public Flux<Record> getAllRecordsByGender() {
        List<Sort.Order> genderNameOrder = new ArrayList<>();
        genderNameOrder.add(new Sort.Order(ASC, "gender"));
        genderNameOrder.add(new Sort.Order(ASC, "lastName"));
        return repository.findAll(Sort.by(genderNameOrder));
    }
    
    public Mono<Record> addRecord(Record record) {  
        return repository.save(record);  
    }  
    
}
