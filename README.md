# ZigZag-BE

## **Configuration:**

- Сlone this project into your local folder and open the project in an IDE.
- Setup new connection in "src/main/resources/application.properties" and set your: driver, url, username and password
- Structure of properties file
  - spring.jpa.hibernate.ddl-auto=create-drop
  - spring.datasource.url=YOUR_URL
  - spring.datasource.username=YOUR_USERNAME
  - spring.datasource.password=YOUR_PASSWORD
  - spring.datasource.driver-class-name=YOUR_DRIVER
  - spring.jpa.show-sql=true
  - spring.jpa.open-in-view=false

- Run a project
- To see the structure of REST endpoints you can go to http://localhost:8080/swagger-ui/
