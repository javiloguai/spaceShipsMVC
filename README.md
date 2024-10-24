# Famous Spaceships Test

Technical Test Spring Boot for W2M World2Meet

This application has been implemented using:

#### Spring boot 3.4

#### spring security

#### java Open JDK 21

#### maven

#### JWT token

#### Swagger open API

#### Mockito

#### Junit

#### Integration Test

#### JPA

#### H2 database

#### lombok

#### mapstruct

#### spring AOP

#### cache

#### liquibase

#### TDD

#### Docker containers

#### Kafka queue messaging

## Java version

> java version used to compile is Temurin JDK 21+35 (LTS)

## IDE

> IntelliJ IDEA 2023.1.2 (Community Edition)

## To start API on an IDE's Springboot embedded server

> > Be sure that spring.kafka.bootstrap-servers in applications.properties is set to localhost:9092
> > run mvn clean install
>
> > run SpaceShipsApplication class with IDE's Springboot embedded Server.

## To start API on a docker container

> > Be sure that spring.kafka.bootstrap-servers in applications.properties is set to kafka:9092
> > run mvn clean install
>
> > run docker build -t spacehips_api .
>
> > docker-compose up --build -d
>
> > A Kafka broker and Zookepper is embedded on docker

## Testing

> JUnit tests are performed on 'mvn clean install'

> Integration Test should be performed on 'mvn clean install' or 'mvn verify' but I am just able to execute it via IDE
> menú context. PATH to Integration Test is in '
> spaceShipsMVC.src.test.java.com.w2m.spaceShips.infrastructure.restapi.controllers.integrationtests'

## To access H2 DB console

> URL : http://localhost:8080/h2-ui/
>
> > JDBC URL: jdbc:h2:mem:spaceshipsW2Mdb
>
> > user is sa
>
> > password is sa
>
>
> default data and users are loaded on startup. Default users are:
> > user: admin
>>
> > password :admin

>
> > user: user
>>
> > password :user

>
>> Due to a lack of time ROLE is not used.

## Swagger api

> URL : http://localhost:8080/swagger-ui/index.html

> URL : http://localhost:8080/v3/api-docs

#### Gets All the spaceships

> GET : http://localhost:8080/api/spaceships

#### Page All the spaceships

> GET : http://localhost:8080/api/spaceships/page

#### Page All the spaceships by name

> GET : http://localhost:8080/api/spaceships/byName/page

#### Gate All the spaceships by a particular equipment

> GET : http://localhost:8080/api/spaceships/byShipEquipment

#### Get spaceship by id

> GET : http://localhost:8180/api/spaceships/1

#### Create new spaceship

> POST : http://localhost:8080/api/spaceships

```
{
"name": "string",
"mediaShow": "string",
"equipment": [
"ANTIMATTER_ENGINE","LASER_BLASTER"
]
}
```

#### Update an existing spaceship

> PUT : http://localhost:8080/api/spaceships/1

```
{
"name": "string",
"mediaShow": "string",
"equipment": [
"ANTIMATTER_ENGINE","LASER_BLASTER"
]
}
```

#### Adds an equipment item to an existing spaceship by providing its id

> PATCH : http://localhost:8080/api/spaceships/1?equipment=INVISIBILITY

```
Available values : SUPRALUMINUM_HYPERSPEED, QUANTUM_LEAP, 
WORMHOLE_DETECTOR, INVULNERABILITY_SHIELD, ARTIFICIAL_GRAVITY_GENERATOR, 
CARGO_HOLD, ANTIMATTER_ENGINE, WARP_ENGINE, INVISIBILITY, 
CRYOGENIC_CAPSULES, ARTIFICIAL_INTELLIGENCE_MATRIX, LASER_BLASTER, 
DEATH_RAY, EMP_ELECTROMAGNETIC_PULSE_GENERATOR, TERRAFORMING_EQUPMENT
```

#### Deletes Spaceship by id

> DELETE : http://localhost:8080/api/spaceships/1

#### Deletes All Spaceships

> DELETE : http://localhost:8080/api/spaceships

## Authentication

#### Register new user

> POST : http://localhost:8180/auth/register

```
{
   "user":"javier",
   "pasword":"javier",
   "role":"USER or ADMIN"
}
```

#### Login user

> POST : http://localhost:8180/auth/login

```
{
   "user":"javier",
   "pasword":"javier"
}
```



