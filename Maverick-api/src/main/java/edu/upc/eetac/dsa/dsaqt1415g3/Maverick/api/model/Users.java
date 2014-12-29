package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLinks;


import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.Binding;




import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.MaverickRootAPIResource;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.MediaType;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.UserResource;

public class Users {
	//@InjectLinks({
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "getProfile", type=MediaType.MAVERICK_API_USER, method = "getProfile",  bindings = @Binding(name = "username", value = "${instance.username}"))
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "following", title = "Following", type = MediaType.MAVERICK_API_USER_COLLECTION, method = "getFollowing", bindings = @Binding(name = "username", value = "${instance.username}")),
		// })
	
	private String username;
	private String name;
	private List<Link> links;
	private String description;
	private String userpass;
	private String email;
	private String rolename;
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	private boolean loginSuccessful;
	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}
	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	

}
