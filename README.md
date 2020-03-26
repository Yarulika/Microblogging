# Microblogging Assignment (inspired by Twitter)

Web application for publishing short messages: posts and comments to them.
The whole system consists of two parts: 
1) Backend on Java (current repo: Java web application provides a REST API)
2) Frontend on React (https://github.com/bkrmadtya/microblogging-frontend)


## Main system functions:

1) Creating an account in the system
2) Following other users
3) Adding posts
4) Presentation (Wall) of posts
5) Liking and commenting posts
6) Sharing posts

## Implemented REST API has list of methods for:

1) Avatar Controller
2) Comment Controller
3) Follower Controller
4) Post Controller
5) User Controller

Above methods are documented and can be explored in:

**Swagger UI:**
```
localhost:8080/swagger-ui.html
```
Swagger provides easy way to test for development

## Technologies used:

1) Java
2) Spring Boot
3) Rest API
4) Swagger UI
5) Lombok library
6) MySQL
7) Flyway

## Future work:

1) Add Spring Security for proper handling authentication and authorisation
2) Use media server or just apache to save and restore avatars for not to depend on application
3) Add logging
4) Deploy in docker container
5) Add more functionality: tags, blocking users

## Development requirement:

JDK 8
MySQL
Change database username and password in properties files and test properties file

## Deployment requirment:

JRE 8
MySQL

## Testing Application:

Use swagger ui to test functionality: http://localhost:8080/swagger-ui.html
Use Postman or any RESTful APIs tool
