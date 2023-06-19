///**
// * 
// */
//package com.monster.content.uploader.rest.api.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import com.azure.storage.queue.QueueClient;
//import com.azure.storage.queue.QueueClientBuilder;
//
///**
// * @author SumanD
// *
// */
//@Configuration
//public class AzureConfig {
//	
//	@Autowired
//	Environment environment;
////	@Value("${queue.endpoint}")
//	private String endpoint;
////	@Value("${queue.queueName}")
//	private String queueName;
////	@Value("${queue.sasToken}")
//	private String sasToken;
//	
//	
//    public AzureConfig(String endpoint, String queueName, String sasToken) {
//        this.endpoint = environment.getProperty("queue.endpoint");
//        this.queueName = environment.getProperty("queue.queueName");
//        this.sasToken = environment.getProperty("queue.sasToken");
//    }
//    @Bean
//	public QueueClient getQueueClient() {
//		return new QueueClientBuilder().endpoint(endpoint).queueName(queueName).sasToken(sasToken).buildClient();
//	}
//
//}
