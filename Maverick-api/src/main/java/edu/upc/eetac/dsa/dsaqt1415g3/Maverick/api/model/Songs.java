package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;




import java.sql.Date;
import java.util.List;

import javax.ws.rs.core.Link;

public class Songs {
	

	private List<Link> links;
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

	

	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
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
	
	private long lastModified;
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	public long getCreationTimestamp() {
		return creationTimestamp;
	}
	public void setCreationTimestamp(long creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}
	private long creationTimestamp;
 

	
}
