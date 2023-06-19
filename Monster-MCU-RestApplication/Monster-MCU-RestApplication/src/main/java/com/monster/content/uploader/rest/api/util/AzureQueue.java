//package com.monster.content.uploader.rest.api.util;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//
//import com.azure.core.util.Context;
//import com.azure.storage.queue.QueueClient;
//import com.azure.storage.queue.QueueClientBuilder;
//import com.azure.storage.queue.QueueServiceClient;
//import com.azure.storage.queue.QueueServiceClientBuilder;
//import com.azure.storage.queue.models.PeekedMessageItem;
//import com.azure.storage.queue.models.QueueItem;
//import com.azure.storage.queue.models.QueueMessageItem;
//import com.azure.storage.queue.models.QueueProperties;
//import com.azure.storage.queue.models.QueueStorageException;
//import com.azure.storage.queue.models.SendMessageResult;
//import com.monster.content.uploader.rest.api.entity.JobDetails;
//import com.monster.content.uploader.rest.api.mapper.SolrModalMapper;
//import com.monster.content.uploader.rest.api.model.SolrModel;
//import com.monster.content.uploader.rest.api.repository.JobDetailsRepository;
//import com.monster.content.uploader.rest.api.service.StartIndex;
//import com.monster.content.uploader.rest.api.service.StartIndexImpl;
//
//@Configuration
//public class AzureQueue {
//
//	@Autowired
//	JobDetailsRepository jobDetailsRepository;
//
//	private final static Logger logger = LoggerFactory.getLogger(AzureQueue.class);
//
////	private final static String queueName = "monster-mcu";
////	private final static String endpoint = "https://sivamcu.queue.core.windows.net/monster-mcu";
////	private final static String connectStr = "DefaultEndpointsProtocol=https;AccountName=sivamcu;AccountKey=bJF7rFmLJqvFwommAxbAfVi3zNNCyvaKzbWzoPh1F7u26u/tRnrqYHZSKJST8CTgCvtWqF85mctx+AStzXT5BQ==;EndpointSuffix=core.windows.net";
////
////	QueueClient queueClient = new QueueClientBuilder().connectionString(connectStr).endpoint(endpoint)
////			.queueName(queueName).buildClient();
//
//	private Environment environment = null;
//	private QueueClient queueClient = null;
//
//	@Autowired
//	public AzureQueue(Environment environment) {
//		this.environment = environment;
//		String endpoint = environment.getProperty("queue.endpoint");
////        String sasToken = environment.getProperty("queue.sasToken");
//		String queueName = environment.getProperty("queue.queueName");
//		this.queueClient = new QueueClientBuilder().endpoint(endpoint).queueName(queueName).sasToken(
//				"si=monstermcupoc&spr=https&sv=2022-11-02&sig=Q9iK9abFvUNHA4yS%2BAvrDYD0Gh%2B%2F6rOm73HMuaQLjW0%3D")
//				.buildClient();
//	}
//
//	@Async
//	public String peekQueueMessage() {
//		QueueMessageItem queueMessageItem = null;
//		List<String> lst = new ArrayList<>();
//		try {
//			System.out.println("hii inside peek");
//			// Instantiate a QueueClient which will be
//			// used to create and manipulate the queue
//
//			// Peek at the first message
//			PeekedMessageItem peekedMessageItem = queueClient.peekMessage();
//			System.out.println("Peeked message dequeue count: " + peekedMessageItem.getMessageId().getBytes());
//			System.out.println("Peeked message getMessageId: " + peekedMessageItem.getMessageId());
//			queueMessageItem = queueClient.receiveMessage();
//			logger.info("Received message: {}", new String(queueMessageItem.getBody().toBytes()));
//
//			System.out.println("***" + queueMessageItem.getBody().toString());
//
//			// The maximum number of messages to retrieve is 20
//			final int MAX_MESSAGES = 3;
//
//			// Retrieve 20 messages from the queue with a
//			// visibility timeout of 300 seconds (5 minutes)
//			for (QueueMessageItem message : queueClient.receiveMessages(MAX_MESSAGES, Duration.ofSeconds(300),
//					Duration.ofSeconds(1), new Context("key1", "value1"))) {
//				// Do processing for all messages in less than 5 minutes,
//				// deleting each message after processing.
//				System.out.println("Dequeing message: " + message.getMessageId());
//			}
//
//		} catch (QueueStorageException e) {
//			// Output the exception message and stack trace
//			System.out.println("error is -- " + e.getMessage());
//			return "There is no message to peek";
//		}
//		return new String(queueMessageItem.getBody().toBytes());
//	}
//    @Async
//	@SuppressWarnings("unused")
//	public void receiveMessage() {
//		AzureQueue az = new AzureQueue(environment);
//		az.getQueueLength();
//		
//		if(az.getQueueLength()>0) {
//
//		String val = "";
//		List<String> lst = new ArrayList<>();
//
//		final int MAX_MESSAGES = 32;
//		
//		try {
//		
//		// Create a thread pool with 20 threads
////		ExecutorService executorService = Executors.newFixedThreadPool(MAX_MESSAGES);
////
//		for (QueueMessageItem msg : queueClient.receiveMessages(MAX_MESSAGES, Duration.ofSeconds(20),
//				Duration.ofSeconds(1), new Context("key1", "value1"))) {
//			
////			// Create a new task to process the message
////		    Runnable task = () -> {
////		        System.out.println(" message: \n"+ msg.getBody().toString()+" --> "+Thread.currentThread().getName());
////		   
//
//
//			System.out.println(msg.getBody().toString());
//			String value = "";
//			if (null != msg) {
//				// Define the regular expression pattern
//				String pattern = "jobId=(\\d+)";
//
//				// Create a Pattern object
//				Pattern regex = Pattern.compile(pattern);
//				String idValue = "";
//				int numValue;
//
//				// Create a Matcher object
//				Matcher matcher = regex.matcher(msg.getBody().toString());
//
//				// Check if a match is found
//				if (matcher.find()) {
//					// Retrieve the matched ID value
//					idValue = matcher.group(1);
//
//					// Print the ID value
//					System.out.println("ID: " + idValue);
//
//					// Split the string by commas
//					String[] keyValuePairs = msg.getBody().toString().split(",");
//					// Iterate over the key-value pairs
//					for (String keyValuePair : keyValuePairs) {
//						// Split each pair by the equal sign
//						String[] parts = keyValuePair.split("=");
//						String key = parts[0].trim();
//						value = parts[1].trim();
//
//						// Check if the key is "Operation"
//						if (key.equals("Operation")) {
//							System.out.println("Operation value: " + value);
//							break; // Exit the loop if the desired field is found
//						}
//					}
//					if (value.equalsIgnoreCase("Create")) {
//						System.out.println("hi from create");
//						numValue = 0;
//						numValue = Integer.parseInt(idValue);
//
//						JobDetails jobDetails = jobDetailsRepository.findById(numValue).get();
//						SolrModalMapper solrMapper = new SolrModalMapper();
//						SolrModel solrModel = solrMapper.SolrCustomMapping(jobDetails);
//
//						System.out.println("solrMapper " + solrModel.toString());
//
//						StartIndex st = new StartIndexImpl();
//						try {
//							st.indexData(environment.getProperty("domainName"),solrModel.toString());
//						} catch (Exception e) {
//							System.out.println("error --->" + e.getMessage());
//						}
//
//					} else if (value.equalsIgnoreCase("Delete")) {
//						System.out.println("hii from delete");
//
//						StartIndex st = new StartIndexImpl();
//						try {
//							st.deleteData(environment.getProperty("domainName"), idValue);
//						} catch (Exception e) {
//							System.out.println("error --> " + e.getMessage());
//						}
//					} else if (value.equalsIgnoreCase("Update")) {
//						System.out.println("hii from update");
//						numValue = 0;
//						numValue = Integer.parseInt(idValue);
//						JobDetails jobDetails = jobDetailsRepository.findById(numValue).get();
//						SolrModalMapper solrMapper = new SolrModalMapper();
//						SolrModel solrModel = solrMapper.SolrCustomMapping(jobDetails);
//
//						System.out.println("solrMapper " + solrModel.toString());
//
//						StartIndex st = new StartIndexImpl();
//						try {
//							st.updateData(environment.getProperty("domainName"), jobDetails);
//						} catch (Exception e) {
//							System.out.println("error --->" + e.getMessage());
//						}
//					}
//
//				} else {
//					System.out.println("Job ID not found");
//				}
//
//				// Delete the message
//				 queueClient.deleteMessage(msg.getMessageId(), msg.getPopReceipt());
//			} else {
//				System.out.println("No visible messages in queue");
//				
//			}
////		    };
////		    // Submit the task to the thread pool
////		    executorService.submit(task);
//		}
//		}catch(Exception e) {
//			System.out.println("error --> "+e.getMessage());
//		}
//		}
//		else {
//			System.out.println("There is no message to read in Queue");
//		}
//		// Shutdown the thread pool
////		executorService.shutdown();
//	}
//	@Async
//	public String addingMessageInQueue(String details, String Operation, String date) throws InterruptedException {
//
//		// Using the QueueClient object, call the create method to create the queue in
//		// your storage account.
//		try {
//		String data = "jobId=" + details + "," + "Operation=" + Operation + "," + "CreatedDate=" + date;
//
//		SendMessageResult sendMessageResult = queueClient.sendMessage(data);
//		logger.info("Send message id: {}", sendMessageResult.getMessageId());
//
//		QueueMessageItem queueMessageItem = queueClient.receiveMessage();
//		logger.info("Received message: {}", new String(queueMessageItem.getBody().toBytes()));
//
//		return new String(queueMessageItem.getBody().toBytes());
//		}catch(QueueStorageException e) {
//			return e.getMessage();
//		}
//	}
//
//	void updateQueueMessage() {
//
//		try {
//			// The maximum number of messages to retrieve is 32
//			final int MAX_MESSAGES = 32;
//
//			// Iterate through the queue messages
//			for (QueueMessageItem message : queueClient.receiveMessages(MAX_MESSAGES)) {
//				// Check for a specific string
//				if (message.getMessageText().equals("Welcom")) {
//					// Update the message to be visible in 30 seconds
//					queueClient.updateMessage(message.getMessageId(), message.getPopReceipt(), "hii",
//							Duration.ofSeconds(MAX_MESSAGES));
//					System.out
//							.println(String.format("Found message: \'%s\' and updated it to \'%s\'", "Welcom", "hii"));
//					break;
//				}
//			}
//		} catch (QueueStorageException e) {
//			// Output the exception message and stack trace
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
//
//	long getQueueLength() {
//		try {
//
//			QueueProperties properties = queueClient.getProperties();
//			long messageCount = properties.getApproximateMessagesCount();
//
//			System.out.println(String.format("Queue length: %d", messageCount));
//			return messageCount;
//		} catch (QueueStorageException e) {
//			// Output the exception message and stack trace
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//			return 0;
//		}
//	}
//
//	void listQueues() {
//		try {
//			QueueServiceClient queueServiceClient = new QueueServiceClientBuilder()
//					.endpoint("https://monstermcupoc.queue.core.windows.net")
//					.sasToken(
//							"si=monstermcupoc&spr=https&sv=2022-11-02&sig=Q9iK9abFvUNHA4yS%2BAvrDYD0Gh%2B%2F6rOm73HMuaQLjW0%3D")
//					.buildClient();
//
//			// Loop through the collection of queues.
//			for (QueueItem queue : queueServiceClient.listQueues()) {
//				// Output each queue name.
//				System.out.println(queue.getName());
//			}
//		} catch (QueueStorageException e) {
//			// Output the exception message and stack trace
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
//
//	@Async
//	public void dequeueMessage() {
//		try {
//			// Get the first queue message
//			QueueMessageItem message = queueClient.receiveMessage();
//
//			// Check for a specific string
//			if (null != message) {
//				System.out.println("Dequeing message: " + message.getMessageId());
//
//				// Delete the message
//				queueClient.deleteMessage(message.getMessageId(), message.getPopReceipt());
//			} else {
//				System.out.println("No visible messages in queue");
//			}
//		} catch (QueueStorageException e) {
//			// Output the exception message and stack trace
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
//
//	public void listdequeueMessages() {
//		try {
//
//			// The maximum number of messages to retrieve is 20
//			final int MAX_MESSAGES = 20;
//
//			// Retrieve 20 messages from the queue with a
//			// visibility timeout of 300 seconds (5 minutes)
//			for (QueueMessageItem message : queueClient.receiveMessages(MAX_MESSAGES, Duration.ofSeconds(300),
//					Duration.ofSeconds(1), new Context("key1", "value1"))) {
//				// Do processing for all messages in less than 5 minutes,
//				// deleting each message after processing.
//				System.out.println("Dequeing message: " + message.getMessageText());
//				queueClient.deleteMessage(message.getMessageId(), message.getPopReceipt());
//			}
//		} catch (QueueStorageException e) {
//			// Output the exception message and stack trace
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
//
//}
