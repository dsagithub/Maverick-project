package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;




import java.sql.Date;
import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;

import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.MediaType;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.SongResource;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.UserResource;

public class Songs {
	@InjectLinks({
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "getProfile", type=MediaType.MAVERICK_API_USER, method = "getProfile",  bindings = @Binding(name = "username", value = "${instance.username}")),
		@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "self edit", title = "Perfil", type = MediaType.MAVERICK_API_USER, method = "getprofile", bindings = @Binding(name = "username", value = "${instance.username}")), 
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "users", title = "create user", type = MediaType.MAVERICK_API_USER),
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "users", title = "createFollower", type = MediaType.MAVERICK_API_USER_COLLECTION, method = "createFollower", bindings = @Binding(name = "username", value = "${instance.username}")),
		//@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "following", title = "Following", type = MediaType.MAVERICK_API_USER_COLLECTION, method = "getFollowing", bindings = @Binding(name = "username", value = "${instance.username}")),
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "song", title = "create song", type = javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA),
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "songs Collection", title = "getAllSongs"),
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "my songs", title = "get my songs", type = MediaType.MAVERICK_API_SONG, method = " SearchCancionbyUsername"), 
		//@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "songs following", title = "get Songs following", type = MediaType.MAVERICK_API_SONG_COLLECTION, method = "getCancionesFollowing" , bindings = @Binding(name = "username", value = "${instance.username}")), 
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "songs style", title = "get songs style", type = MediaType.MAVERICK_API_SONG, method = "SearchStyles"), 
		 
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "createLike", title = "createLike", type = MediaType.MAVERICK_API_SONG ,method = "createLike", bindings = @Binding(name = "song_name", value = "${instance.song_name}")),
		@InjectLink(resource = SongResource.class, style = Style.ABSOLUTE, rel = "Comment", title = "create comment", type = MediaType.MAVERICK_API_COMMENT, method = "createComment", bindings = @Binding(name = "song_name", value = "${instance.song_name}")),
		@InjectLink(value = "/songs/likes?username={username}", style = Style.ABSOLUTE, rel = "songs likes", title = "get songs likes", type = MediaType.MAVERICK_API_SONG, method = "getLikes",bindings ={ @Binding(name = "username", value = "${instance.username}")}) ,
		//@InjectLink(value = "/songs/likes?followername={followername}", style = Style.ABSOLUTE, rel = "songs likes following", title = "get songs likes following", type = MediaType.MAVERICK_API_SONG, method = "getLikes",bindings ={ @Binding(name = "followername", value = "${instance.followername}")}) ,
		
		
		})
	

	private List<Link> links;
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	private String songid;
	private String username;
	private String song_name;
	private String album;
	private String description;
	private String style;
	private String songURL;
	private int likes;
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}

	private Date last_modified;
	public Date getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}

	

	
	public String getSongid() {
		return songid;
	}
	public void setSongid(String songid) {
		this.songid = songid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSong_name() {
		return song_name;
	}
	public void setSong_name(String song_name) {
		this.song_name = song_name;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	/*
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	*/
	public String getSongURL() {
		return songURL;
	}
	
	public void setSongURL(String songURL) {
		this.songURL = songURL;
	}
	
	public long getFechacomment() {
		return fechacomment;
	}
	public void setFechacomment(long fechacomment) {
		this.fechacomment = fechacomment;
	}
	private long lastModified;
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public long getCreationTimestamp() {
		return creationTimestamp;
	}
	private long fechacomment;
	private String text;
	private int commentid;

	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	private long creationTimestamp;
 

	
}
