## Example using Vertx 4.2.3 for Kafka Client


### Used technology stack in this example

	1  Vertx 4.2.3
	2  Vertx 4.2.3 Kafka Client
	3  Vertx Config 4.2.3
	4  Vertx Config Hocon 4.2.3
	5  Vertx 4.2.3 Hazel Cast
	6  OpenJDK 17
	7  Apache Maven 3.8.3
	

### Maven initial setup

	$ mvn dependency:tree
	$ mvn eclipse:eclipse


### Build application jar or war

	$ mvn clean package
		

### 1. Run following java program to produce message/data to topic

	KafkaProducerClient.java
	
	
### 2. Run following java program to consume message/data from topic

	KafkaConsumerClient.java
