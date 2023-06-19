/**
 * 
 */
package com.monster.content.uploader.rest.api.service;

import com.azure.storage.queue.models.QueueStorageException;
import com.monster.content.uploader.rest.api.util.JobIndexQueueMessage;

/**
 * @author SumanD
 *
 */
public interface IAzureQueueService {

	JobIndexQueueMessage sendMessageToQueue(String jobId, String Operation, String Date) throws QueueStorageException;

	String ReceiveMessageFomQueue() throws QueueStorageException;

	long getQueueLength() throws QueueStorageException;

	public void listQueues();

	public void dequeueMessage();

	public void listdequeueMessages();
	
	public void processMessages();

}
