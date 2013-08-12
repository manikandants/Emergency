package com.mani.emergency;

public class Contact {
	int id;
	String name;
	String phoneNumber;
	
	public Contact() {
		
	}
	Contact(int id, String name, String phoneNumber){
		this.id = id;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	Contact(String name, String phoneNumber){
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getPhoneNumber(){
		return this.phoneNumber;
	}
	public void setId(int id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
}
