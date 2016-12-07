###Sample REST API
This is a sample REST API built with Spring Boot and Spring MVC. It provides GET and POST endpoints to retrieve and save the Product Entity. 

####Setup

#####Prerequisites

1. JDK 1.8
2. Apache Maven 3.x

#####Steps

1. Check out the project using git
2. Run the Maven command 
   $mvn spring-boot:run

####API

######GET /products
	Returns all the products in JSON array.

######POST /products 
	Accepts Product JSON and creates new Product. Returns product as JSON. Throws 400 error if product name not provided.

######GET /products/{code} 
	Returns product as JSON matching given code. Throws 404 if product not found. 

######GET /products/{code} 
	Returns products that have given tag as JSON array. May return empty list.
	
####Other implementations
The following projects implement the same API, with different technologies.

1. https://github.com/shankarps/ProductApiSwagger (Swagger and Spring Boot)
2. https://github.com/shankarps/ProductApiSpringBoot (Spring Boot and JAX-RS)