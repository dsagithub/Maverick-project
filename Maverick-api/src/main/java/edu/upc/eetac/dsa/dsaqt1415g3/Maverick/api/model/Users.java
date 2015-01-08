package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLinks;


import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.Binding;



import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.MediaType;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.SongResource;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.UserResource;



public class Users {
	@InjectLinks({
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "getProfile", type=MediaType.MAVERICK_API_USER, method = "getProfile",  bindings = @Binding(name = "username", value = "${instance.username}")),
		@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Perfil", type = MediaType.MAVERICK_API_USER, method = "getprofile", bindings = @Binding(name = "username", value = "${instance.username}")), 
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "users", title = "create user", type = MediaType.MAVERICK_API_USER),
		@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "users", title = "createFollower", type = MediaType.MAVERICK_API_USER_COLLECTION, method = "createFollower", bindings = @Binding(name = "username", value = "${instance.username}")),
		@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "following", title = "Following", type = MediaType.MAVERICK_API_USER_COLLECTION, method = "getFollowing", bindings = @Binding(name = "username", value = "${instance.username}")),
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "song", title = "create song", type = javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA),
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "songs Collection", title = "getAllSongs"),
		//@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "my songs", title = "get my songs", type = MediaType.MAVERICK_API_SONG, method = " SearchCancionbyUsername"), 
		//@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "songs following", title = "get Songs following", type = MediaType.MAVERICK_API_SONG_COLLECTION, method = "getCancionesFollowing" , bindings = @Binding(name = "username", value = "${instance.username}")), 
		//@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "songs style", title = "get songs style", type = MediaType.MAVERICK_API_SONG, method = "SearchStyles"), 
		//@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "songs likes", title = "get songs likes", type = MediaType.MAVERICK_API_SONG, method = "getLikes"), 
		//@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "create like", title = "create like", type = MediaType.MAVERICK_API_SONG ,method = "createLike", bindings = @Binding(name = "song_name", value = "${instance.song_name}")),
		//@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "Comment", title = "create comment", type = MediaType.MAVERICK_API_COMMENT, method = "createComment", bindings = @Binding(name = "song_name", value = "${instance.song_name}")),
		
		})
	private List<Link> links; //atributo con getters y setters
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	private String username;
	private String name;

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
	
	

}
