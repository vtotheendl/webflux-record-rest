# webflux-record-rest
Webflux based rest api for parsing and reading records.

### Overview
This is a simple application that parses input files in several formats. 
And then exposes that data via REST endpoints. 
A POST REST endpoint is also available to add more records. 

Files contain records, and the records can be delimited by either a space, comma, or pipe character.
*	The pipe-delimited file lists each record as follows:  
LastName | FirstName | Gender | FavoriteColor | DateOfBirth
*	The comma-delimited file looks like this:  
LastName, FirstName, Gender, FavoriteColor, DateOfBirth
*	The space-delimited file looks like this:   
LastName FirstName Gender FavoriteColor DateOfBirth

REST Endpoints
*	POST /records - Post a single data line in any of the 3 formats supported by your existing code
*	GET /records/gender - returns records sorted by gender
*	GET /records/birthdate - returns records sorted by birthdate
*	GET /records/name - returns records sorted by name

### Execution
The easiest way to run this code is via the gradle bootRun task.

`$ ./gradlew bootRun --args=records.txt,records2.txt,records3.txt`

Where args parameters are the files that should be parsed and loaded.  As many files can be specified as desired, each file should be separated by a comma.  

You can add your own files to add more data. All test files are located here:  
webflux-record-rest/account/src/main/resources

Once the application is running then the REST endpoints are accessible via port 8080.
* http://localhost:8080/records/gender
* http://localhost:8080/records/name
* http://localhost:8080/records/birthdate


### Implementation
This is a SpringBoot application written in Java 8.
The code is fully reactive and relies on Spring's WebFlux Framework.
The underlying data store is an Embedded Reactive MongoDB.

### Test Code
Unit tests were written in JUnit 5. 
Test code coverage is 98%.


### Example Execution

$ pwd
/code/webflux-record-rest/account
$ ./gradlew bootRun --args=records.txt,records2.txt,records3.txt

> Task :bootRun  
 :: Spring Boot ::        (v2.2.6.RELEASE)     
2020-04-29 21:06:17.779 ERROR 9714 --- [           main] com.vendel.record.Loader                 : Error processing line 5, invalid data, please correct and resubmit. [Bad, Lori, F]  
2020-04-29 21:06:17.804 ERROR 9714 --- [           main] com.vendel.record.Loader                 : Error processing line 4, invalid data, please correct and resubmit. [Slightfoot, Bad, F, Grey, 19a-90]  
2020-04-29 21:06:17.813 ERROR 9714 --- [           main] com.vendel.record.Loader                 : Error processing line 5, invalid data, please correct and resubmit. [Slightfoot, Bad, F, Grey, 1907-39]   
        OUTPUT 1 Gender then Name ASC           ******************************************************             
Record [lastName=Johanson, firstName=Jane, gender=F, favoriteColor=Pink, dateOfBirth=1961-04-07]  
Record [lastName=Johnson, firstName=Joan, gender=F, favoriteColor=Green, dateOfBirth=1980-09-12]  
Record [lastName=Lightfoot, firstName=Lori, gender=F, favoriteColor=Purple, dateOfBirth=1970-03-04]  
Record [lastName=Slightfoot, firstName=Alicia, gender=F, favoriteColor=Grey, dateOfBirth=1907-10-09]  
Record [lastName=Unger, firstName=Suzie, gender=F, favoriteColor=Bright, dateOfBirth=1961-11-15]  
Record [lastName=Yellowhead, firstName=Blaise, gender=F, favoriteColor=Pink, dateOfBirth=2001-07-06]  
Record [lastName=Bluefoot, firstName=Barry, gender=M, favoriteColor=Purple, dateOfBirth=1981-02-07]  
Record [lastName=Flinstone, firstName=Fred, gender=M, favoriteColor=Orange, dateOfBirth=1953-01-07]  
Record [lastName=Hilo, firstName=Hector, gender=M, favoriteColor=Orange, dateOfBirth=1979-07-20]  
Record [lastName=Redhand, firstName=Sam, gender=M, favoriteColor=Blue, dateOfBirth=1908-12-17]  
Record [lastName=Ruble, firstName=Barney, gender=M, favoriteColor=Green, dateOfBirth=1913-10-06]  
        OUTPUT 2 Date of Birth   ASC            ******************************************************            
Record [lastName=Slightfoot, firstName=Alicia, gender=F, favoriteColor=Grey, dateOfBirth=1907-10-09]  
Record [lastName=Redhand, firstName=Sam, gender=M, favoriteColor=Blue, dateOfBirth=1908-12-17]  
Record [lastName=Ruble, firstName=Barney, gender=M, favoriteColor=Green, dateOfBirth=1913-10-06]  
Record [lastName=Flinstone, firstName=Fred, gender=M, favoriteColor=Orange, dateOfBirth=1953-01-07]  
Record [lastName=Johanson, firstName=Jane, gender=F, favoriteColor=Pink, dateOfBirth=1961-04-07]  
Record [lastName=Unger, firstName=Suzie, gender=F, favoriteColor=Bright, dateOfBirth=1961-11-15]  
Record [lastName=Lightfoot, firstName=Lori, gender=F, favoriteColor=Purple, dateOfBirth=1970-03-04]  
Record [lastName=Hilo, firstName=Hector, gender=M, favoriteColor=Orange, dateOfBirth=1979-07-20]  
Record [lastName=Johnson, firstName=Joan, gender=F, favoriteColor=Green, dateOfBirth=1980-09-12]  
Record [lastName=Bluefoot, firstName=Barry, gender=M, favoriteColor=Purple, dateOfBirth=1981-02-07]  
Record [lastName=Yellowhead, firstName=Blaise, gender=F, favoriteColor=Pink, dateOfBirth=2001-07-06]  
        OUTPUT 3 Lastname DESC                  ******************************************************            
Record [lastName=Yellowhead, firstName=Blaise, gender=F, favoriteColor=Pink, dateOfBirth=2001-07-06]  
Record [lastName=Unger, firstName=Suzie, gender=F, favoriteColor=Bright, dateOfBirth=1961-11-15]  
Record [lastName=Slightfoot, firstName=Alicia, gender=F, favoriteColor=Grey, dateOfBirth=1907-10-09]  
Record [lastName=Ruble, firstName=Barney, gender=M, favoriteColor=Green, dateOfBirth=1913-10-06]  
Record [lastName=Redhand, firstName=Sam, gender=M, favoriteColor=Blue, dateOfBirth=1908-12-17]  
Record [lastName=Lightfoot, firstName=Lori, gender=F, favoriteColor=Purple, dateOfBirth=1970-03-04]  
Record [lastName=Johnson, firstName=Joan, gender=F, favoriteColor=Green, dateOfBirth=1980-09-12]  
Record [lastName=Johanson, firstName=Jane, gender=F, favoriteColor=Pink, dateOfBirth=1961-04-07]  
Record [lastName=Hilo, firstName=Hector, gender=M, favoriteColor=Orange, dateOfBirth=1979-07-20]  
Record [lastName=Flinstone, firstName=Fred, gender=M, favoriteColor=Orange, dateOfBirth=1953-01-07]  
Record [lastName=Bluefoot, firstName=Barry, gender=M, favoriteColor=Purple, dateOfBirth=1981-02-07]   

