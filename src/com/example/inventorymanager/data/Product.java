package com.example.inventorymanager.data;

public class Product {
	public static final int ENABLE = 1;
	public static final int DISABLE = 2;
	
	public static final String ENABLE_STRING = "Cho phép";
	public static final String DISABLE_STRING = "Vô hiệu hóa";
	
	private int id;
	private String barcode;
	private String name;
	private String description;
	private int price;
	private int qty;
	private String image;
	private int status;
	private int category_id;
	private String created_at;
	private String update_at;
	public Product(){
		
	}
	public Product(int id, String barcode, String name, String description, int price, int qty, String image, int status, int category_id, String created_at, String update_at){
		this.id = id;
		this.barcode = barcode;
		this.name = name;
		this.description = description;
		this.price = price;
		this.qty = qty;
		this.image = image;		
		this.status = status;
		this.category_id = category_id;
		this.created_at = created_at;
		this.update_at = update_at;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getStatus() {
		return status;
	}
	
	public String getStringStatus(){
		if(this.status == ENABLE){
			return ENABLE_STRING;
		}else {
			return DISABLE_STRING;
		}
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
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
	

}
