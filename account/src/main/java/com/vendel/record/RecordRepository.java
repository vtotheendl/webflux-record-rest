package com.vendel.record;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;  

public interface RecordRepository extends ReactiveSortingRepository<Record, Long> {  
}
