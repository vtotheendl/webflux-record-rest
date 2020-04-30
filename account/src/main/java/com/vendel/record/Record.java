package com.vendel.record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;  

@Document
@Data
@NoArgsConstructor
public class Record {  
	private String lastName;  
    private String firstName;  
    private String gender;  
    private String favoriteColor;  
    private Date dateOfBirth;
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    
    public Record(String lastName, String firstName, String gender, String favoriteColor, String dateOfBirth) throws ParseException {
    	this.lastName = lastName;
    	this.firstName = firstName;
    	this.gender = gender;
    	this.favoriteColor = favoriteColor;
    	this.dateOfBirth = Record.df.parse(dateOfBirth);
    }
    
	@Override
	public String toString() {
		return "Record [lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender + ", favoriteColor="
				+ favoriteColor + ", dateOfBirth=" + Record.df.format(this.dateOfBirth) + "]";
	}
    
    
}
