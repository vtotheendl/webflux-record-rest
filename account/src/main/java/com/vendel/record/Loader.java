package com.vendel.record;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Order(value = 1)
@Slf4j
@Component
public class Loader implements CommandLineRunner {

	public static final String SPACE = " ";
	public static final String COMMA = ",";
	public static final String PIPE = "\\|";
	public static final int COLUMN_SIZE = 5; 
	
	@Autowired RecordRepository repository;
	
	public Loader(RecordRepository recordRepository) {
		this.repository = recordRepository;
	}
	 
    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {     	
        	for(String filename : args[0].split(",")) {
        		loadDataFromFile(filename);
        	}            
        }             
    }	
   

    /**
     * Open the input filename and load any valid data rows from the file into the data store.
     * 
     * @param inputFilename
     * @throws IOException
     * @throws URISyntaxException
     */
    protected void loadDataFromFile(String inputFilename) throws IOException, URISyntaxException {
       int lineNbr = 1;	
	   BufferedReader in =
		        Files.newBufferedReader(Paths.get(ClassLoader.getSystemResource(inputFilename).toURI()));
	   
	   String line = in.readLine();
	   if (null == line || line.isEmpty()) {
		   log.error("Empty file. No data to load.");
		   return; 
	   }
	   String delimiter = parseDelimiter(line);
	   readLine(line.split(delimiter), lineNbr).ifPresent(r -> repository.save(r).subscribe());

	   while ((line = in.readLine()) != null) {	
		   readLine(line.split(delimiter), ++lineNbr).ifPresent(r -> repository.save(r).subscribe());
	   }
	   
	}
    
    /**
     * Read in a line of data and transform to model. 
     * 
     * @param data
     * @param lineNbr
     * @return
     */
    protected Optional<Record> readLine(String[] data, int lineNbr) {
    	Optional<Record> record = Optional.empty();
    	try {
    		record = Optional.of(new Record((String) data[0], (String) data[1], (String) data[2], (String) data[3], (String) data[4]));
    	} catch(Exception e) {
    		log.error("Error processing line {}, invalid data, please correct and resubmit. {}", lineNbr, data);
    	}
    	return record;
    }

    /**
     * Determine one of three delimiters that can be used to format the data in a file. 
     * 
     * @param line1
     * @return
     */
	protected String parseDelimiter(String line1) {
	   if (null==line1 || line1.isEmpty()) {
		   throw new RuntimeException("Empty line. Nothing to parse ");		 
	   }			
	   String[] delimiters = new String[] {SPACE, COMMA, PIPE};
	   String fileDelimiter = "";
	   
	   for(String delimiter : delimiters) {
		  String[] results = line1.split(delimiter);
		  
		  if (results.length == COLUMN_SIZE) {
			  fileDelimiter = delimiter; 
			  break;
		  }
	   }
	   
	   if (fileDelimiter.isEmpty()) {
		   throw new RuntimeException("File is incorrectly formatted and doesn't use an acceptable delimiter. ");		 
	   }	
	   
	   return fileDelimiter;
	}
	
}
