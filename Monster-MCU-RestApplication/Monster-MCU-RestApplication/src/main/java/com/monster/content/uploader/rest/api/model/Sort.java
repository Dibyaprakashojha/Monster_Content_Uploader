/**
 * 
 */
package com.monster.content.uploader.rest.api.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monster.content.uploader.rest.api.util.Directions;

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
public class Sort {
	@JsonProperty("sort_field")
	String Field;
	@JsonProperty("sort_direction")
//	@Enumerated(EnumType.STRING)
//	Directions directions;
	String direction;

}
