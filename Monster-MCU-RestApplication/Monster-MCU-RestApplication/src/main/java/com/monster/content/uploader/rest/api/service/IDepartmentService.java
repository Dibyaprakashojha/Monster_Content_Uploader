package com.monster.content.uploader.rest.api.service;

import java.util.List;

import com.monster.content.uploader.rest.api.entity.Department;

/**
 * @author SivakumarK
 *
 */
public interface IDepartmentService {
	
	public String createRecord(Department department);
	public String updateRecord(Department department);
	public String deleteRecordById(int departmentId);
	public List<Department> retrieveAllRecords();
	public Department retrieveById(int departmentId);
	public void clearCache();
	public String bulkDelete(List<Integer> departmentId );
	public String bulkCreate(List<Department> departments);
	
}
