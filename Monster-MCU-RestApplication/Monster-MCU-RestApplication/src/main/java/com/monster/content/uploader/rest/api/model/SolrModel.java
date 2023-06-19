package com.monster.content.uploader.rest.api.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@ToString
@Component
public class SolrModel {
	
	public int job_id;
	public String job_name;
	public String brand;
	public String country;
	public String department_name;
	public String product_line;
	public String job_status;
	public LocalDateTime created_date;
	public int user_id;
	public String user_name;
	@Override
	public String toString() {
		return "[job_id=" + job_id + ", job_name=" + job_name + ", brand=" + brand + ", country=" + country
				+ ", department_name=" + department_name + ", product_line=" + product_line + ", job_status="
				+ job_status + ", created_date=" + created_date + ", user_id=" + user_id + ", user_name=" + user_name
				+ "]";
	}
	
	
}
