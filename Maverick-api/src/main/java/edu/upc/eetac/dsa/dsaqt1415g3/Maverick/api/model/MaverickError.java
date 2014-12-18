package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

public class MaverickError {
	private int status;
	private String message;
 //dos atributos, el status que corresponde al codigo de estado HTTP (400 o 500 si da error) 
	//Pojo constructor vacio,getters and setters y otro constructor
	public MaverickError() {
		super();
	}
 
	public MaverickError(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
 
	public int getStatus() {
		return status;
	}
 
	public void setStatus(int status) {
		this.status = status;
	}
 
	public String getMessage() {
		return message;
	}
 
	public void setMessage(String message) {
		this.message = message;
	}

}
