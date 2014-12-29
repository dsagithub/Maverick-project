package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Link;


public class SongsCollection {
	private List<Link> links;
	private List<Songs> songs;
	//private long newestTimestamp;
	//private long oldestTimestamp;
	
	public SongsCollection() {
		super();
		songs = new ArrayList<>();
	}
	
	public void addSong(Songs song) {
		songs.add(song);
	}
	
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public List<Songs> getSongs() {
		return songs;
	}
	public void setSongs(List<Songs> songs) {
		this.songs = songs;
	}
	/*public long getNewestTimestamp() {
		return newestTimestamp;
	}
	public void setNewestTimestamp(long newestTimestamp) {
		this.newestTimestamp = newestTimestamp;
	}
	public long getOldestTimestamp() {
		return oldestTimestamp;
	}
	public void setOldestTimestamp(long oldestTimestamp) {
		this.oldestTimestamp = oldestTimestamp;
	}*/
	public void add(Songs song) {
		songs.add(song);
	}

}
