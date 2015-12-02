package com.example.inventorymanager.data;

public class Order {
	public static final int STATE_NEW = 1;
	public static final int STATE_PROCESSING = 2;
	public static final int STATE_COMPLETE = 3;
	public static final int STATE_CANCELED = 4;
	
	private int id;
	private int totalPrice;
	private int totalQty;
	private String date;
	private int state;
	
	public Order(){
		
	}
	
	public Order(int id, int totalPrice, int totalQty, String date, int state){
		this.id = id;
		this.totalPrice = totalPrice;
		this.totalQty = totalQty;
		this.date = date;
		this.state = state;
	}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
