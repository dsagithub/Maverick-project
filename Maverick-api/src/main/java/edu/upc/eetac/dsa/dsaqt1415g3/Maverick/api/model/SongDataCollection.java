package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.ArrayList;
import java.util.List;

public class SongDataCollection {

	private List<SongData> songs = new ArrayList<>();

	public List<SongData> getAudio() {
		return songs;
	}

	public void setAudio(List<SongData> song) {
		this.songs = song;
	}
	public void addSong(SongData song) {
		songs.add(song);
	}
	
}


