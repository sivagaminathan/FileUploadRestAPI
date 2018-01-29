# File Upload Rest WebService API

## Pre-Reqs

Set JAVA_HOME for your environment/OS

## Build and run the service

1. Clone this repository

```
git clone https://github.com/sivagaminathan/FileUploadRestAPI.git
```

2. Build the jar file and run tests. Run in [FileUploadAPI/](FileUploadAPI/) directory

```
mvnw clean package
```

This step creates an executable jar file, which is uploaded [here](FileUploadAPI/target/FileUploadRestAPI-0.0.1-SNAPSHOT.jar) for your convenience

3. Download and run [FileUploadRestAPI-0.0.1-SNAPSHOT.jar](FileUploadAPI/target/FileUploadRestAPI-0.0.1-SNAPSHOT.jar)

```
java -jar FileUploadRestAPI-0.0.1-SNAPSHOT.jar
```

----------------------------------------------------------------------------------------------

## Project Configurations

### Assumptions

1. In file FileService.java, destination directory for uploaded files is specified

```
UPLOADED_FOLDER = "D://test//"
```

2. FileSize is restricted via application.properties 

3. Exception is handled via  FileControllerAdvice.java

4. Writing files (meta & uploaded file) to disk is asynchronous

### application.properties

1. set prefix folder to /WEB-INF/jsp

```
spring.mvc.view.prefix:/WEB-INF/jsp/
```
  
2. set suffix file extensions to : .jsp

```
spring.mvc.view.suffix:.jsp
```

3. set multipart max file size : 10MB

```
spring.http.multipart.max-file-size=10MB
```

4. set multipart max request size : 10MB 

```
spring.http.multipart.max-request-size=10MB
```

5. To use default Spring mapping 

```
spring.resources.add-mappings=false
```

------------------------------------------------------------------------------------------------

## Dependencies 

### Maven dependencies 

All dependencies used in the project are in pom.xml & in depends.txt
Following list of dependencies are used: 
1. Mockito-all -----> mock test cases
2. Hateoas ----> provides easy to use API's 
3. commons-io -------> Apache commons
4. Junit ------------> Junit for unit testing
5. Jersey -----------> Jersey framework
6. Jasper -----------> Tomcat jasper is a jsp engine
7. Hamcrest ---------> Pattern Matcher in tests
8. Spring web & web services 

-------------------------------------------------------------------------------------------------

## How to make API request calls

### 1. Execute the application and once it is started, open a browser and go to

http://localhost:8080

### 2. To Upload files

http://localhost:8080/upload 

*Follow the instructions in the page  -- > choose a file --> click submit*

### 3. To LIST ALL UPLOADED FILES in the UPLOADED_FOLDER

http://localhost:8080/list

### 4. To read report of a File 

http://localhost:8080/report/x.txt/

### 5. To read a report of a File after removing keyword "blue"

http://localhost:8080/report/x.txt/blue

### 6. To read a report of a File after removing any given keyword

http://localhost:8080/report/x.txt/"keyword"

--------------------------------------------------------------------------------------------------

## Test Coverage

### FileReportTests.java

Covers complete unit tests for positive and negative scenarios

### FileServiceTests.java

Covers functional unit tests 

### FileUploadRESTWebAPITests.java

Placeholder file for writing test cases to test the API calls

-------------------------------------------------------------------------------------------------
