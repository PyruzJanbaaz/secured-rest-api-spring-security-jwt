# Secured-RestAPI-SpringSecurity-Jwt
To minimize security risks, REST service operators should restrict connecting clients to the minimum capabilities required for the service. This starts with restricting supported HTTP methods to make sure that misconfigured or malicious clients can’t perform any actions beyond the API specification and permitted access level. For example, if the API only allows GET requests, POST and other request types should be rejected with the response code 405 Method not allowed.

I create a Spring Boot project for protect rest api by spring security and JWT. You need the following tools and technologies to develop the same.

    Spring-Boot 2.1.3.RELEASE
    Spring-security 2.1.7.RELEASE
    Lombok 1.18.6
    JavaSE 1.8
    Mapstruct 1.3.0.Final
    Maven 3.3.9


# Dependencies
Open the pom.xml file fro spring boot configuration:

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	
and dpendencies:
      
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter</artifactId>
	</dependency>
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-web</artifactId>
	</dependency>
    <dependency>
        <!-- Starter for using Spring Security -->
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>    
	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter-test</artifactId>
	    <scope>test</scope>
	</dependency>
    
    
Also I make properties files for development and deployment environment and set the profile in the application accordingly, so it will pick the respective properties file.
    
# Usage
Run the project and go to http://localhost:9090 on your browser!

