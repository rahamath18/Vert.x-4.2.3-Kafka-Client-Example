package com.example.vertx.kafka.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;

public class KafkaProducerClient extends AbstractVerticle {

	// Convenience method so you can run it in your IDE
	public static void main(String[] args) {
		System.out.println("welcome to KafkaProducerClient Example");
		Runner.runExample(KafkaProducerClient.class);
	}

	@Override
	public void start(Promise<Void> startFuture) throws Exception {
		super.start(startFuture);

		String topicName = "my-kafka-topic";

		Map<String, String> config = new HashMap<>();
		config.put("bootstrap.servers", "localhost:30054");
		config.put("acks", "all");
		config.put("retries", "0");
		config.put("batch.size", "16384");
		config.put("linger.ms", "1");
		config.put("buffer.memory", "33554432");
		config.put("acks", "1");
		config.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		config.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		String key = "end-" + Math.random();
		String value = "{\"value\": \"Test message - " + new Date().toString() + "\"}";
		System.out.println(key + " = " + value);

		KafkaProducer<String, String> producer = KafkaProducer.create(this.vertx, config);

		KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(topicName, key, value);
		producer.write(record);

		// produce to several topics with list
//		Set<String> topics = new HashSet<>();
//		topics.add(topicName);
//		topics.add("topicName_2");
//		topics.add("topicName_3");
//		topics.forEach(topic -> {
//			KafkaProducerRecord<String, String> record = KafkaProducerRecord.create(topic, key, value);
//			producer.write(record);
//		});

		startFuture.complete();
		producer.close();
		System.out.println("Message sent successfully");

	}
}