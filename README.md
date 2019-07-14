# gsoc-drs
Public repository for the GSOC DRS project

The Data Repository Service (DRS) API provides a generic interface to data repositories 
so data consumers, including workflow systems, can access data in a single, 
standard way regardless of where it’s stored and how it’s managed. 
The main goal of this project is the generation of a prototype server for DRS API that 
follows the [current draft specification of the DRS](https://github.com/ga4gh/data-repository-service-schemas/tree/develop)

[Link to GSOC project page](https://summerofcode.withgoogle.com/projects/?sp-search=dils#4919156430864384)

## How to run it: 
Requirements: Docker

 1. **Make useful version of all template files**  
    - Create application.properties from application.template.properties
    - Create docker-compose.yml from docker-compose-template.yml  
    
 2. **Fill credentials in files that you created**  
    - spring.datasource.username/password in application.properties fill with your postgres credentials
    - POSTGRES_PASSWORD\USER docker-compose.yml also fill with your postgres credentials
    
 3. **Start service**: run in the root of project:  
  ```docker-compose up```  
 
 ## How to test it:
 Requirements: Maven, JDK 8
 
  - Add and fill testApplication.properties(from testApplication.template.properties) to  src/test/resources/
  - Run in the root of project:  
  ```mvn test -DtrimStackTrace=false```
    