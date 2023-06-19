/**
 * 
 */
package com.monster.content.uploader.rest.api.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.internal.build.AllowSysOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.azure.core.util.Context;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.QueueServiceClient;
import com.azure.storage.queue.QueueServiceClientBuilder;
import com.azure.storage.queue.models.QueueItem;
import com.azure.storage.queue.models.QueueMessageItem;
import com.azure.storage.queue.models.QueueProperties;
import com.azure.storage.queue.models.QueueStorageException;
import com.azure.storage.queue.models.SendMessageResult;
import com.monster.content.uploader.rest.api.entity.JobDetails;
import com.monster.content.uploader.rest.api.mapper.SolrModalMapper;
import com.monster.content.uploader.rest.api.model.JobTransactionInfo;
import com.monster.content.uploader.rest.api.model.SolrModel;
import com.monster.content.uploader.rest.api.repository.JobDetailsRepository;
import com.monster.content.uploader.rest.api.util.JobIndexQueueMessage;

/**
 * @author SumanD
 *
 */
@Service
public class AzureQueueServiceImpl implements IAzureQueueService {

	@Autowired
	Environment environment;

	@Autowired
	JobDetailsRepository jobDetailsRepository;

	private final int MAX_MESSAGES = 32;

	private final int BatchSize = 100;

//	@Autowired
//	Environment environment;
//
//	String endpoint = environment.getProperty("queue.endpoint");
//	String queueName = environment.getProperty("queue.queueName");

	QueueClient queueClient = new QueueClientBuilder().endpoint("https://monstermcupoc.queue.core.windows.net")
			.queueName("monster-content-uploader-job-index-queue")
			.sasToken(
					"si=monstermcupoc&spr=https&sv=2022-11-02&sig=Q9iK9abFvUNHA4yS%2BAvrDYD0Gh%2B%2F6rOm73HMuaQLjW0%3D")
			.buildClient();

	private final static Logger logger = LoggerFactory.getLogger(AzureQueueServiceImpl.class);

	@Override
	public JobIndexQueueMessage sendMessageToQueue(String jobId, String Operation, String Date)
			throws QueueStorageException {
		JobIndexQueueMessage qeuMessage = new JobIndexQueueMessage(jobId, Operation, Date);
		try {

			SendMessageResult sendMessageResult = queueClient.sendMessage(qeuMessage.toString());
			logger.info("Send message id: {}", sendMessageResult.getMessageId());

			QueueMessageItem queueMessageItem = queueClient.receiveMessage();
			logger.info("Received message: {}", new String(queueMessageItem.getBody().toBytes()));
		} catch (QueueStorageException e) {
			System.out.println(e.getMessage());
		}
		return qeuMessage;
	}

	@SuppressWarnings("unused")
	@Override
	public String ReceiveMessageFomQueue() throws QueueStorageException {

		List<JobDetails> lstjobQueue = new ArrayList<>();
		List<JobIndexQueueMessage> listJobIndex = new ArrayList<>();
		List<String> lstStr = new ArrayList<>();
		List<String> jobId = new ArrayList<>();
		// Map for Processing
		HashMap<Integer, JobDetails> jobsIndexDataInfoMap = new HashMap<Integer, JobDetails>();
		try {
			Thread.sleep(5000L);
			if (MAX_MESSAGES > 0) {
				int currentRecord = 0;

				int totalrecord = (int) Math.ceil((double) BatchSize / MAX_MESSAGES);
				System.out.println("tot -->" + totalrecord);
				while (currentRecord < totalrecord) {
					for (QueueMessageItem msg : queueClient.receiveMessages(MAX_MESSAGES, Duration.ofSeconds(35),
							Duration.ofSeconds(1), new Context("key1", "value1"))) {

//						System.out.println(msg.getBody().toString());

						lstStr.add(msg.getBody().toString());
						System.out.println("lst  " + lstStr);
						Set<String> mySet = new HashSet<>(lstStr);
						System.out.println("myset " + mySet);
						JobIndexQueueMessage jobQueue = new JobIndexQueueMessage();

//						jobsIndexDataInfoMap.put(currentRecord, );
						lstStr.add(msg.getBody().toString());

						System.out.println("lst " + lstStr);
						System.out.println("lst size---> " + lstStr.size());
					}

					System.out.println("currentRecord = " + currentRecord);
					currentRecord++;
					int numValue = 100003;
					System.out.println("inside while");
					List<JobDetails> lstString = jobDetailsRepository.findAll();

//						System.out.println("Job details Lst "+lstString);
					// Iterate the Data and Prepare for Processing
					for (JobDetails jobDetails : lstString) {

						if (jobDetails != null) {

							// check the hash map any value if there add it to this map

						}

					}

				}
				List<Integer> jobIdList = new ArrayList<Integer>();

				if (jobsIndexDataInfoMap.size() > 0) {
					Iterator<Integer> jobsForProcessingItr = jobsIndexDataInfoMap.keySet().iterator();
					while (jobsForProcessingItr.hasNext()) {
						int jobId1 = jobsForProcessingItr.next();
						JobDetails jobTrasInfoObj = jobsIndexDataInfoMap.get(jobId1);
					}
				}
				for (String item : lstStr) {
//				    System.out.println("ibsaijcbasb "+"\n"+item);
				}

				/*
				 * JobDetails jobDetails = jobDetailsRepository.findById(numValue).get();
				 * SolrModalMapper solrMapper = new SolrModalMapper(); SolrModel solrModel =
				 * solrMapper.SolrCustomMapping(jobDetails);
				 * 
				 * System.out.println("solrMapper " + solrModel.toString());
				 * 
				 * IStartIndex iStartIndex = new StartIndexImpl();
				 * 
				 * try { iStartIndex.indexData(environment.getProperty("domainName"),
				 * solrModel.toString()); } catch (Exception e) { return e.getMessage(); }
				 * 
				 * // Delete the message queueClient.deleteMessage(msg.getMessageId(),
				 * msg.getPopReceipt());
				 */

			} else {
				System.out.println("No Message in the Queue");
			}

		} catch (InterruptedException e1) {
			System.out.println("ReceiveMessageFomQueue Error -->" + e1.getMessage());
		}

		return "Received";
	}

	@Override
	public long getQueueLength() throws QueueStorageException {
		try {

			QueueProperties properties = queueClient.getProperties();
			long messageCount = properties.getApproximateMessagesCount();
			return messageCount;
		} catch (QueueStorageException e) {
			System.out.println(e.getMessage());
			return 0;
		}
	}

	public void listQueues() {
		try {
			QueueServiceClient queueServiceClient = new QueueServiceClientBuilder()
					.endpoint("https://monstermcupoc.queue.core.windows.net")
					.sasToken(
							"si=monstermcupoc&spr=https&sv=2022-11-02&sig=Q9iK9abFvUNHA4yS%2BAvrDYD0Gh%2B%2F6rOm73HMuaQLjW0%3D")
					.buildClient();

			// Loop through the collection of queues.
			for (QueueItem queue : queueServiceClient.listQueues()) {
				// Output each queue name.
				System.out.println(queue.getName());
			}
		} catch (QueueStorageException e) {
			// Output the exception message and stack trace
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void dequeueMessage() {
		try {
			// Get the first queue message
			QueueMessageItem message = queueClient.receiveMessage();

			// Check for a specific string
			if (null != message) {
				System.out.println("Dequeing message: " + message.getMessageId());

				// Delete the message
				queueClient.deleteMessage(message.getMessageId(), message.getPopReceipt());
			} else {
				System.out.println("No visible messages in queue");
			}
		} catch (QueueStorageException e) {
			// Output the exception message and stack trace
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void listdequeueMessages() {
		try {

			// The maximum number of messages to retrieve is 20
			final int MAX_MESSAGES = 20;

			// Retrieve 20 messages from the queue with a
			// visibility timeout of 300 seconds (5 minutes)
			for (QueueMessageItem message : queueClient.receiveMessages(MAX_MESSAGES, Duration.ofSeconds(300),
					Duration.ofSeconds(1), new Context("key1", "value1"))) {
				// Do processing for all messages in less than 5 minutes,
				// deleting each message after processing.
				System.out.println("Dequeing message: " + message.getBody().toString());
				queueClient.deleteMessage(message.getMessageId(), message.getPopReceipt());
			}
		} catch (QueueStorageException e) {
			// Output the exception message and stack trace
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// ***********************************************************//
	public Map<String, Set<String>> processedJobs = new HashMap<>();

	public void processMessages() {
		
		try {
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (MAX_MESSAGES > 0) {
			int currentRecord = 0;

			Map<String, String> jobIndexinfo = new HashMap<>();
			int totalrecord = (int) Math.ceil((double) BatchSize / MAX_MESSAGES);
			System.out.println("tot -->" + totalrecord);

			while (currentRecord < totalrecord) {
				System.out.println("current Rec = " + currentRecord);
				JobTransactionInfo jobinfo = new JobTransactionInfo();

				for (QueueMessageItem message : queueClient.receiveMessages(MAX_MESSAGES, Duration.ofSeconds(35),
						Duration.ofSeconds(1), new Context("key1", "value1"))) {
					String messageContent = message.getBody().toString();
					System.out.println("messageContent = " + messageContent);

					// Process the message content (e.g., parse jobId, Operation, createdDate)

					// Extract the operation value from the message
					String operation = extractOperationFromMessage(messageContent);

					// Extract the jobId from the message
					String jobId = extractJobIdFromMessage(messageContent);

					// Delete the message from the queue after processing
					queueClient.deleteMessage(message.getMessageId(), message.getPopReceipt());

//					// Check if the job has already been processed with the same operation
//					if ("delete".equalsIgnoreCase(operation) &&isJobIdProcessedWithCreate(jobId) &&isJobIdProcessedWithUpdate(jobId)) {
//						// Job has already been processed with "update" operation, skip processing
//						continue;
//					}
//					// Check if the job has already been processed with the same operation
//					if ("delete".equalsIgnoreCase(operation)  && isJobIdProcessedWithUpdate(jobId)) {
//						// Job has already been processed with "update" operation, skip processing
//						continue;
//					}
					
					// Check if the job has already been processed with the same operation
					if ("update".equalsIgnoreCase(operation) && isJobIdProcessedWithUpdate(jobId)) {
						// Job has already been processed with "update" operation, skip processing
						continue;
					}

					// Perform operations based on the operation value
					if ("create".equalsIgnoreCase(operation)) {
						// Perform create operation
						performCreateOperation(messageContent);
						markJobIdAsProcessedWithCreate(jobId);
					} else if ("update".equalsIgnoreCase(operation)) {
						// Perform update operation
						performUpdateOperation(messageContent);
						markJobIdAsProcessedWithUpdate(jobId);
					} else if ("delete".equalsIgnoreCase(operation)) {
						// Perform delete operation
						performDeleteOperation(messageContent);
					} else {
						// Handle unknown operation
						handleUnknownOperation(operation);
					}
					
				}
				currentRecord ++;
			}
		}

//            messages = queueClient.receiveMessages(MAX_MESSAGES, Duration.ofSeconds(35),
//					Duration.ofSeconds(1), new Context("key1", "value1"));
	}

	private boolean isJobIdProcessedWithUpdate(String jobId) {
		Set<String> operations = processedJobs.get(jobId);
		return operations != null && operations.contains("update");
	}
	private boolean isJobIdProcessedWithCreate(String jobId) {
		Set<String> operations = processedJobs.get(jobId);
		return operations != null && operations.contains("create");
	}

	private void markJobIdAsProcessedWithUpdate(String jobId) {
		Set<String> operations = processedJobs.computeIfAbsent(jobId, k -> new HashSet<>());
		operations.add("update");
	}

	private void markJobIdAsProcessedWithCreate(String jobId) {
		Set<String> operations = processedJobs.computeIfAbsent(jobId, k -> new HashSet<>());
		operations.add("update");
	}

	private String extractOperationFromMessage(String messageContent) {
		// Parse the message content and extract the operation value
		// Assuming the message format is
		// "jobId=122,Operation=update,createdDate=2023-06-03 19:47:18"
		String[] keyValuePairs = messageContent.split(",");
		for (String keyValuePair : keyValuePairs) {
			String[] parts = keyValuePair.split("=");
			if (parts.length == 2 && "operation".equalsIgnoreCase(parts[0])) {
				return parts[1];
			}
		}
		return null; // or throw an exception if operation value is not found
	}

	private String extractJobIdFromMessage(String messageContent) {
		// Parse the message content and extract the jobId value
		// Assuming the message format is
		// "jobId=122,Operation=update,createdDate=2023-06-03 19:47:18"
		String[] keyValuePairs = messageContent.split(",");
		for (String keyValuePair : keyValuePairs) {
			String[] parts = keyValuePair.split("=");
			if (parts.length == 2 && "jobId".equalsIgnoreCase(parts[0])) {
				return parts[1];
			}
		}
		return null; // or throw an exception if jobId value is not found
	}

	private void performUpdateOperation(String messageContent) {
		// Perform the update operation based on the message content
		// Implement your logic here to update the data in Solr
		// This method will be called only when the operation value is "update"

		// Make the necessary API call(s) to update the data in Solr
		// ...
		System.out.println("hii from Update");
		String jobId = extractJobIdFromMessage(messageContent);

		System.out.println("jobId " + jobId);
		int job_id = Integer.parseInt(jobId);
		JobDetails jobDetails = jobDetailsRepository.findById(job_id).get();
		System.out.println();
		SolrModalMapper solrMapper = new SolrModalMapper();
		SolrModel solrModel = solrMapper.SolrCustomMapping(jobDetails);

		System.out.println("solrMapper " + solrModel.toString());

		try {
			System.out.println("into try");
			IStartIndex startIndex = new StartIndexImpl();
			startIndex.updateData(environment.getProperty("domainName"), jobDetails);
		} catch (Exception e) {
			System.out.println("error --->" + e.getMessage());
		}
	}

	private void performCreateOperation(String messageContent) {
		// Perform the update operation based on the message content
		// Implement your logic here to update the data in Solr
		// This method will be called only when the operation value is "update"

		// Make the necessary API call(s) to update the data in Solr
		// ...
		System.out.println("hii from Create");
		String jobId = extractJobIdFromMessage(messageContent);
		int job_id = Integer.parseInt(jobId);
		JobDetails jobDetails = jobDetailsRepository.findById(job_id).get();
		SolrModalMapper solrMapper = new SolrModalMapper();
		SolrModel solrModel = solrMapper.SolrCustomMapping(jobDetails);

		System.out.println("solrMapper " + solrModel.toString());

		try {
			IStartIndex startIndex = new StartIndexImpl();
			startIndex.indexData(environment.getProperty("domainName"), solrModel.toString());
		} catch (Exception e) {
			System.out.println("error --->" + e.getMessage());
		}
	}

	private void performDeleteOperation(String messageContent) {
		// Perform the delete operation based on the message content
		// This method will be called only when the operation value is "delete"

		// Make the necessary API call(s) to delete the data in Solr
		// ...
		System.out.println("hii from delete");
		String jobId = extractJobIdFromMessage(messageContent);

		try {
			IStartIndex startIndex = new StartIndexImpl();
			startIndex.deleteData(environment.getProperty("domainName"), jobId);
		} catch (Exception e) {
			System.out.println("error --> " + e.getMessage());
		}
	}

	private void handleUnknownOperation(String operation) {
		// Handle the case when the operation is unknown or unsupported
		// Implement your logic here
		// ...
		System.out.println("No Operation for the Job....");
	}
}
