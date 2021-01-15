# Development process

In this document I describe the steps taken on the development of this API.

* ####Identifying business entities
    The first approach on the development was to identify all the business
    units which would take part in the application. Once these entities were
    clearly established, I defined the relationships between them and created 
    a ER model with the details used later to create the DB.  
* ####Creation of UML class diagram and assigning class responsibilities
    With the entities already identified, and the ERM created, I moved to the next
    layer of abstraction creating a UML diagram class and noting there the relationships
    between classes. With this diagram I also distributed the responsibilities for each
    class.
* ####Deciding which technologies to use
    - Why Springboot? 
        
         It provides embedded container support, making the development to have fewer 
         configurations letting you to focus just on the code. As well, provides a flexible
         way to configure Java Beans, configurations, database transactions, and manages 
         REST endpoints. In short words, complete, fast and easy to use.
    
    - Why h2db?
        
        I chose to use h2db, an in memory database because of its very simple 
        implementation and integration with Spring Boot. Also, using a traditional
        database would have involved overhead for this case. Something I skipped 
        just to keep simplicity, was foreign keys.
    
    - Why Swagger? 
        
        It provides a simple but complete UI for documentation and API testing.
        It is very easy to integrate with Spring Boot rest APIs, making documentation
        fast and clear.
    
* ####Writing tests first for TDD approach
    Adopting TDD is always useful to write bug free(ish) code. This methodology 
    takes a bit more time in the beginning but makes development far easier since it 
    improves code cleanness.
* ####Writing the rest of the code
    Writing the code based on the previous tests.
* ####RE-Running tests and writing docs
    Once all the code is ready, I double-checked all tests were green and proceeded 
    to write the docs. In this step I also performed QA checks and tested the application
    fully from Swagger generated interface. 

