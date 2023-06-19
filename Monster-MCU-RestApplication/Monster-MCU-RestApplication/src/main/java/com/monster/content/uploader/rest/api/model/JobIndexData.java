/**
 * 
 */
package com.monster.content.uploader.rest.api.model;

import org.apache.solr.common.SolrDocumentList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SumanD
 *
 */
@Getter
@Setter
public class JobIndexData {

	@JsonProperty("data")
	SolrDocumentList data;
//	@JsonProperty("facet_field_response_list")
//	@JsonInclude(Include.NON_NULL)
//	List<FacetFieldResponse> facetFieldResponseList;
	@JsonProperty("cursor")
	Cursor cursor;
	@JsonProperty("sort")
	Sort sort;
}