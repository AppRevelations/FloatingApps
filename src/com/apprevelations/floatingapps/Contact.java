package com.apprevelations.floatingapps;

public class Contact {
	
	//private variables
	String _name;
	int _status;
	
	// Empty constructor
	public Contact(){
		
	}
	// constructor
	public Contact(int status, String name){
		this._status = status;
		this._name = name;
	}
	
	
	// getting ID
	public int getStatus(){
		return this._status;
	}
	
	// setting id
	public void setStatus(int status){
		this._status = status;
	}
	
	// getting name
	public String getName(){
		return this._name;
	}
	
	// setting name
	public void setName(String name){
		this._name = name;
	}
	
}
