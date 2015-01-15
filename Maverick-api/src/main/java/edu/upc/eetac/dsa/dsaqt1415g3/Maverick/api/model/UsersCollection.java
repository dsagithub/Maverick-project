package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;


import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.MediaType;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.SongResource;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.UserResource;


public class UsersCollection {
@InjectLinks({
		
	@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "users Collection", title = "getAllUsers"),
	@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "self", title = "users", type = MediaType.MAVERICK_API_USER_COLLECTION),
	

	
		
		
		
		
		})
	
	
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
