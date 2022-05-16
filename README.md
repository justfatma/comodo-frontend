## Used software and versions
Java, Spring Boot, Maven, Restful API, Thymeleaf, Html

## How to start
Application.java --> Run As --> Java Application

## Databases
The comodo-frontend project itself does not have a database.                  <br/>
It uses the databases of the following microservices for listing, adding, deleting, updating records. <br/>

http://localhost:9000/h2-console/login.jsp       	comodo-login-service    <br/>
http://localhost:9001/h2-console/login.jsp       	comodo-group-service    <br/>
http://localhost:9002/h2-console/login.jsp   	    comodo-todo-service     <br/>

## Additional Information
The **comodo-frontend** project must have a login and register page. Groups and todos belonging to the logged in user should be able to be displayed. <br/>
In the current situation, all users, groups and todos can be viewed as if the admin is logged in.










