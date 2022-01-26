package com.example.vertx.kafka.client;

import java.util.HashMap;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.kafka.client.consumer.KafkaConsumer;

public class KafkaConsumerClient extends AbstractVerticle {

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		System.out.println("welcome to KafkaConsumerClient Example");
		Runner.runExample(KafkaConsumerClient.class);
	}

	@Override
	public void start(Promise<Void> startFuture) throws Exception {
		super.start(startFuture);
		String topicName = "my-kafka-topic";
		String grp = "my-kafka-group";
		Map<String, String> config = new HashMap<>();
		config.put("bootstrap.servers", "localhost:30054");
		config.put("group.id", grp);
		config.put("auto.offset.reset", "earliest");
		config.put("enable.auto.commit", "false");
		// config.put("auto.commit.interval.ms", "1000");
		config.put("session.timeout.ms", "30000");
		config.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		config.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		KafkaConsumer<String, String> consumer = KafkaConsumer.create(vertx, config);
		
		//consumer.subscribe(topicName);
		
		consumer.subscribe(topicName, ar -> {
			 if (ar.succeeded()) {
				 System.out.println("Subscribed");
			 } else {
				 System.out.println("Could not subscribe: " + ar.cause().getMessage());
			 }
			});
		
		 consumer.handler(record -> {
			  System.out.println("Processing key=" + record.key() + ",value=" + record.value() +
			    ",partition=" + record.partition() + ",offset=" + record.offset());
			});
		
		// subscribe to several topics with list
//		Set<String> topics = new HashSet<>();
//		topics.add(topicName);
//		topics.add("topicName_2");
//		topics.add("topicName_3");
//		consumer.subscribe(topics);

		// or using a Java regex
//		Pattern pattern = Pattern.compile(topicName + "\\d");
//		consumer.subscribe(pattern);
		
		
		
	}
}