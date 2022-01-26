
## Apache Kafka


What is Apache Kafka?

	Apache Kafka is an open-source stream-processing software platform developed by LinkedIn and donated to the Apache Software Foundation, written in Scala and Java. Kafka critically depends on Apache ZooKeeper to share information via a Zookeeper cluster. Kafka stores basic metadata in Zookeeper. Apache Kafka is publish-subscribe based fault tolerant messaging system. For more https://kafka.apache.org/intro.html.

	There are two ways of configuration of Kafka.

	1. Single Node-Single Broker Configuration. It means single ZooKeeper instance and single Kafka broker instance.

	2. Single Node-Multiple Brokers Configuration. It means single ZooKeeper instance and multiple Kafka broker instances.



### Kafka Download 

	kafka_2.13-2.4.0.tgz http://archive.apache.org/dist/kafka/2.4.0/


### Kafka Unzip Installation

	$ tar -zxf kafka_2.13-2.4.0.tgz


### Kafka Configuration

	1. ./config/zookeeper.properties
	2. ./config/server.properties


### ZooKeeper Server Start
	
	$ ./bin/zookeeper-server-start.sh config/zookeeper.properties
	
	Note: Kafka server sends heartbeat to ZooKeeper 

	Note: Make sure following properties are exists

	tickTime=2000
	dataDir=/java/Apache/ZooKeeper/data
	initLimit=10
	syncLimit=5
	clientPort=2181

### Kafka Server Start

	$ ./bin/kafka-server-start.sh config/server.properties
	
	Note: make sure following lines are enabled/modified in the ./config/server.properties
	
	listeners=PLAINTEXT://:9092

	log.dirs=/java/Apache/Kafka/kafka_2.13-2.4.0/kafka-logs



### ZooKeeper Server Stop

	$ ./bin/zookeeper-server-stop.sh config/zookeeper.properties


### Kafka Server Stop

	$ ./bin/kafka-server-stop.sh config/server.properties


## Single Node-Single Broker Configuration


### Start ZooKeeper and Kafka servers

	Use above commands.


### Create Kafka Topic

	$ ./bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic My_1st_Kafka_Topic

	Note: Here Replication Factor is 1, because we have only one brokers running. 


### List of existing Kafka Topic

	$ ./bin/kafka-topics.sh --list --bootstrap-server localhost:9092


### Start Producer to Send Messages into Topic

	$ ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic My_1st_Kafka_Topic


### Start Consumer to Receive Messages From Topic

	$ ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic My_1st_Kafka_Topic --from-beginning


### Delete a Topic

	$ ./bin/kafka-topics.sh --bootstrap-server localhost:9092 --delete --topic Hello-kafka My_1st_Kafka_Topic


## Single Node-Multiple Brokers Configuration


### Step 1: Configure multiple Kafka broker instances

	By default already there is a Kafka broker instance with following configurations in ./config/server.properties
	
	broker.id=0
	listeners=PLAINTEXT://:9092
	log.dirs=/java/Apache/Kafka/kafka_2.13-2.4.0/kafka-logs


### Step 2: Create 1st additional Kafka broker instance by adding new configuration file ./config/server-1.properties and add following configurations

	broker.id=1
	listeners=PLAINTEXT://:9093
	log.dirs=/java/Apache/Kafka/kafka_2.13-2.4.0/kafka-logs-1


### Step 3: Create 2nd additional Kafka broker instance by adding new configuration file ./config/server-2.properties and add following configurations

	broker.id=2
	listeners=PLAINTEXT://:9094
	log.dirs=/java/Apache/Kafka/kafka_2.13-2.4.0/kafka-logs-2


### Note
	
	Now there are three Kafka broker instances, 1 is default broker instance and other twos are additionally configured.


### Kafka Multi-Broker Cluster Server Start

	$ ./bin/kafka-server-start.sh config/server.properties

	$ ./bin/kafka-server-start.sh config/server-1.properties

	$ ./bin/kafka-server-start.sh config/server-2.properties

	Note: broker.id=0 is the leader.


### Create New Topic
	
	$ ./bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 3 --partitions 1 --topic My_1st_Replicated_Kafka_Topic

	Note: Here Replication Factor is 3, because we have 3 brokers running.


### Note:

	- The “leader" node - This node responsible for all reads and writes for the given partition. Each node will be the leader for a randomly selected portion of the partitions.
	- The “replicas" nodes - These node replicate the log for this partition regardless of whether they are the leader or even if they are currently alive.
	- The “isr” - This is set of "in-sync" replicas nodes. This is the subset of the replicas list that is currently alive and caught-up to the leader.

	In this context, “broker.id=0” is the leader. All the brokers replicate the topic.


### List Existing Kafka Topics

	$ ./bin/kafka-topics.sh --list --bootstrap-server localhost:9092


### To know which broker is doing what?
	
	$ ./bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic My_1st_Replicated_Kafka_Topic
	

### Start Producer to Send Messages into Replicated Topic

	$ ./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic My_1st_Replicated_Kafka_Topic


### Start Consumer to Receive Messages From Replicated Topic

	$ ./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic My_1st_Replicated_Kafka_Topic --from-beginning


## Kafka Java Client Program for Producer & Consumer


### Maven initial setup

	$ mvn dependency:tree
	$ mvn eclipse:eclipse
	

### 1. Run following java program to produce message/data to topic:My_1st_Replicated_Kafka_Topic

	KafkaProducerClient.java
	
	
### 2. Run following java program to consume message/data from topic:My_1st_Replicated_Kafka_Topic

	KafkaConsumerClient.java