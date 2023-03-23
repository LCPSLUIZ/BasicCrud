# Java Crud for POCs

## Tecnologies used
- Java
- Spring Framework
- Spring Data JPA
- Spring Logging
- Amazon RDS (Relational Database Service) with Postgres Engine


## The Json Format

{
"firstName" : "String",

"lastName" : "String",

"passWord" : "String",

"eMail" : "String with email format and must be unique",

"phoneNumber" : "String at the max of 20 characters and must be unique"

}

## DataBase information
- The Database that I have used here are configured to accept only my IP through the Route Table, Security Group, and VPC.
- Therefore, the only change that need to do to use this API is the **application.properties** archive.