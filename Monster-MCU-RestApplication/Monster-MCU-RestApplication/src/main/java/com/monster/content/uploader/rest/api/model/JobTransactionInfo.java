/**
 * 
 */
package com.monster.content.uploader.rest.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SumanD
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JobTransactionInfo {
	
	public boolean isInserted;
	public boolean isUpdated;
	public boolean isDeleted;
	public String JobId;

}
