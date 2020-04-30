package com.vendel.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class RecordTest {
	
	
	@Test
	void testReadLine() throws Exception {
		String lastName = "Lastname";
		String firstName = "Firstname";
		String gender = "Gender";
		String color = "Color";
		String dob = "2000-02-01";
		String[] data = new String[] {lastName,firstName,gender,color,dob};
		Record actual = new Record(data[0],data[1],data[2],data[3],data[4]);
		
		Record expected = new Record();
		expected.setLastName(lastName);
		expected.setFirstName(firstName);
		expected.setGender(gender);
		expected.setFavoriteColor(color);
		expected.setDateOfBirth(Record.df.parse(dob));
		
		assertEquals(expected,actual);
		assertTrue(actual.equals(expected));

	}
	
	
}


