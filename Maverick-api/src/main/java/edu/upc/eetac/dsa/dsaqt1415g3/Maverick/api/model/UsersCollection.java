package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;
public class UsersCollection {
	
	
	private List<Link> links;
	private List<Users> users;
	
	public UsersCollection() {
		super();
		users = new ArrayList<>();
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<Users> getUsers() {
		return users;
	}
	public void setUsers(List<Users> users) {
		this.users = users;
	}
	public void addUser(Users user){
		users.add(user);	
	}

}
