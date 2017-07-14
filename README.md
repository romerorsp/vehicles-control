# Vehicles Control

This project aims to provide a referente about how me (Romero) as an architect would solve the architecture of controlling and viewing vehicles' acivity. 

## Based sprint-boot 1.5.x - Vehicles Control:
 
#### The application shows:
* The use of spring-boot;
* The use of websockets;
* The use of spring-integration;
* The use of hazelcast;
* Distributed/Paralell processing in a moderate scenario;
* JUnit test cases;
* Embedded launch of a spring-boot application through Docker.

#### TODO:
* Enrich the frontend;
* Create a cache map to Automata;
* Improve the JUnit Scenarios;
* Write tests to the frontend side.

## Getting Started

These instructions will teach you how to launch this application and simulate a vehicle's controller through an rich frontend with distriuted messages processing.

### Prerequisites

Make sure you have Java 1.8+, Maven, NPM (former Ngular Package Manager),  Docker and docker-compose installed in your environment.

#### Maven
```
sudo apt install maven
```

#### Java
Download the last version of the Java jdk's **.tar.gz** and follow the following instructions:
```
sudo tar vzxf /download/dir/jdk-package-name.tar.gz --directory=/usr/local/java/
sudo ln -s /usr/local/java/jdk-version /usr/local/java/current
export JAVA_HOME=/usr/local/java/current
export PATH=$PATH:$JAVA_HOME/bin
```

#### NPM
You can install npm by following the instructions in [the npmjs website](https://docs.npmjs.com/getting-started/installing-node).

#### Docker
Please follow [this link](https://docs.docker.com/engine/installation/linux/docker-ce/ubuntu/) to install Docker on your Ubuntu environment. The website will also provide you installation to other operational systems.

#### docker-compose
Please follow [this link](https://docs.docker.com/compose/install/) to install docker-compose in your environment. It's highly recommended to have **1.14.0+** installed.


### Installing


Building only the artifacts (docker images):

```
#run this command from your vehicles-control root path
mvn clean package -P docker
```
This will get 3 docker images installed in your environment: *tbaconsumer, tbawebsocket and tbafrontend*. Do a docker images to check the images:
```
docker images | grep tba
```
Create the network where the application will be running:
```
docker network create --driver=bridge --subnet=172.27.238.0/24 --gateway=172.27.238.1 ci_vpc
```
**ci_vpc** is the network name configured in the docker-compose.yml file.

After that, you can jump directly to the vehicles-control/docker fodler and raise the docker compose:
```
cd docker
#this will raise a rabbitmq instance for you, as well as the tba* images mentioned abobe
docker-compose up -d
```

You can check if rabbitMQ is runnig by either accessing [http://localhost:15672] (the default user and password are **admin/admin**) or by watching the logs:
```
docker logs rabbitmq -f
```
To watch the application's logs you can run:
```
docker logs tbaconsumer -f
```
```
docker logs tbawebsocket -f
```
```
#this one will only show you nginx access logs from http://localhost:9000 (or the base.href that you will have configured). 
docker logs tbafrontend -f
```
You can access the application at [http://localhost:9000] (if you followed the default instructions).


## Running the tests

By cleaning and packaging you're already doing the tests, unless you use -DskipTests to skip the them. Anyway, you still can run unit and integration tests separately.

### Unit tests

The unit tests will be ran without the Cucumber Tests by running:

```
mvn clean test
```
### Integration tests

The integration tests will be ran within the unit tests by running:

```
# this will clean test (unit) package and fire up Integration Tests (if there's any).
mvn clean verify
```

### Why did I choose this solution?
- By using websockets I was able to make the application available to any user within a browser and also to broadcast the vehicles' activity even if the viewer won't have control to "drive" the car. Any viewer can raise a new car instance that will be seen in all the other views.
- By using RabbitMQ I was able to distribute the processing of every change on vehicle's state and with help of [hazelcast](https://hazelcast.com/) the application could share the vehicles' state from the consumer layer to the websocket, making the data interchangeable and redundant, which is perfect in a scenario where both consumer and websocket can go down unexpectedly.
- By using Angular 4 I was able to program the entire UI layer using rich and modern typescript, which made me feel at home in a syntax very similar or friendly to a java developer. As a plus, the material design was made easy by just importing libraries to my application. No need to be a master UX designer to use it =)


## Deployment

This application is not intended to be deployed as an API, it's just a reference on what Romero can show as a blueprint implementation of a distributed processing application. Anyways, since we're using docker, this application is easily deployable on AWS, Google Cloud or homemade infrastructures that make a docker hub available.

## Built With

* [Java 1.8+](http://docs.oracle.com/javase/8/docs/) - Java Platform Standard Edition 8 Documentation
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring boot](https://docs.spring.io/spring-boot/docs/1.5.x/reference/htmlsingle/) - Spring Boot Reference Guide
* [Angular 4](https://angular.io/) - The newest Angular platform (at the moment 07/2007)
* [NPM](https://www.npmjs.com/) - Easy management of Angular/JS dependencies
* [Docker](https://www.docker.com/) - Make containable images to run in a virtualized environment.
* [docker-compose](https://docs.docker.com/compose/install/) - Compose many docker images in a single deployable structure.

## Versioning

Since this is just a testing application, we're not doing any management on the versioning.

## Authors

* **Rômero Ricardo de Sousa Pereira** - *Java Architect (Candidate)* - [JAVA PI LTDA]

## License

Check the applicable license.

## Acknowledgments

* This sentence is false.
