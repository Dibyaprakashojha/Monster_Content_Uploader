/**
 * 
 */
package com.monster.content.uploader.rest.api.schedular;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.monster.content.uploader.rest.api.service.IAzureQueueService;

/**
 * @author SumanD
 *
 */
@Component
public class JobIndexSchedular {

	@Autowired
	Environment environment;
	
	@Autowired
	IAzureQueueService azureQueueService;

	/**
	* Calls the Azure Queue to receive messages from the queue. This is a blocking call and will return after 5 seconds
	*/
	@Scheduled(fixedDelayString = "${TimeDelay}")
	public void callAzureQueueReceiveMessage() throws Exception {
		LocalDateTime localDateTime = LocalDateTime.now();
		ZoneId zoneId = ZoneId.of("UTC");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		String utcDate = localDateTime.atZone(zoneId).format(formatter);

		long totalQueueSize=azureQueueService.getQueueLength();
		

		// This method is used to process messages from the queue.
		if(totalQueueSize >0) {
			System.out.println("Queue total Length = "+totalQueueSize+" at time ="+utcDate);
		try {
			azureQueueService.processMessages();
		}catch(Exception e) {
			System.out.println("error --> "+e.getMessage());
		}
		}else {
			System.out.println("No Messages in the Queue at time "+utcDate);
		}
	}

}
