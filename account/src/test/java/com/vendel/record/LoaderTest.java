package com.vendel.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
class LoaderTest {
	
	Loader uut;
	Record record;
	String[] data;
	
	@MockBean
	RecordRepository repository;
	
	@BeforeEach
	public void setup() throws Exception {
		uut = new Loader(repository);
		data = new String[] {"Lastname","Firstname","Gender","Color","2000-01-01"};
		record = new Record(data[0],data[1],data[2],data[3],data[4]);		
	}
	
	@Test
	void testReadLine() throws Exception {
		Optional<Record> actual = uut.readLine(data, 1);
		assertEquals(record,actual.get());
	}
	
	@Test
	void testReadLineBadData() throws Exception {
		String[] badData = new String[] {"Lastname","Firstname","Gender","Color","Junk0"};		
		Optional<Record> actual = uut.readLine(badData, 1);
		assertFalse(actual.isPresent());		
	}
	
	@Test
	void testLoadDataFromFile() throws Exception {		
		Mockito
      		.when(repository.save(record))
      		.thenReturn(Mono.just(record));      		
		uut.loadDataFromFile("records4.txt");	
		Mockito.verify(repository, times(2)).save(record);
	}	
	
	@Test
	void testLoadEmptyDatafile() throws Exception {
		uut.loadDataFromFile("empty.txt");
		Mockito.verify(repository, times(0)).save(record);
	}	
	
	@Test
	void testLoadBadDataFile() throws Exception {
		uut.loadDataFromFile("recordsBad.txt");
		Mockito.verify(repository, times(0)).save(record);
	}	
	
	@Test
	void testFileRunner() throws Exception {		
		Mockito
      		.when(repository.save(record))
      		.thenReturn(Mono.just(record)); 
		uut.run("records4.txt,recordsBad.txt");
		Mockito.verify(repository, times(2)).save(record);
	}		
	@Test
	void testFileRunnerEmpty() throws Exception {
		uut.run("empty.txt,recordsBad.txt");
		Mockito.verify(repository, times(0)).save(record);
	}	
	@Test
	void testFileInvalidDelim() throws Exception {		
		Assertions.assertThrows(RuntimeException.class, ()-> {uut.run("recordsInvalidDelimit.txt");});
	}		
	
	@Test
	void testNullDelim() throws Exception {		
		Assertions.assertThrows(RuntimeException.class, ()-> {uut.parseDelimiter(null);});
	}	
	@Test
	void testEmptyDelim() throws Exception {		
		Assertions.assertThrows(RuntimeException.class, ()-> {uut.parseDelimiter("");});
	}		
}


