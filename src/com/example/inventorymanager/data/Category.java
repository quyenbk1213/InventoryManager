package com.example.inventorymanager.data;

import java.io.Serializable;

public class Category implements Serializable {
	public static final int ENABLE = 1;
	public static final int DISABLE = 2;
	
	public static final String ENABLE_STRING = "Cho phép";
	public static final String DISABLE__STRING = "Vô hiệu hóa";
	
	private int id;
	private String name;
	private String description;
	private int status;
	private int count;
	private String created_at;
	private String update_at;
	public Category(){
		
	}
	public Category(int id, String name, String description, int status, int count, String created_at, String update_at) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.count = count;
		this.created_at = created_at;
		this.update_at = update_at;
	}
	public Category(String name, String description, int status) {
		this.name = name;
		this.description = description;
		this.status = status;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	
	public String getStringStatus(){
		if(this.status == ENABLE){
			return ENABLE_STRING;
		}else {
			return DISABLE__STRING;
		}
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
