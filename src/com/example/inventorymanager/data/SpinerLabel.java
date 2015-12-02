package com.example.inventorymanager.data;

import android.util.Log;

public class SpinerLabel {
	private int value;
	private String label;

	public SpinerLabel() {

	}

	public SpinerLabel(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String toString(){
		return this.label;
	}
	
	 public boolean equals(Object obj) {
	        if (obj instanceof SpinerLabel)	        
	        	return ((SpinerLabel) obj).getValue() == this.getValue();	        
	        else
	            return false;	    
	 }
}
