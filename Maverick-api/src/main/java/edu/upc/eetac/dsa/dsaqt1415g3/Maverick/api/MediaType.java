package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

public interface MediaType {
	//intercambiamos recursos en un determinado formato de datos (json),de manera que es un derivado de json para indicar el formato
	public final static String MAVERICK_API_USER = "application/vnd.maverick.api.user+json"; //usuario
	public final static String MAVERICK_API_USER_COLLECTION = "application/vnd.maverick.api.user.collection+json"; //coleción usuarios
	public final static String MAVERICK_API_SONG = "application/vnd.maverick.api.song+json"; //sting
	public final static String MAVERICK_API_SONG_COLLECTION = "application/vnd.maverick.api.song.collection+json"; //coleción stings
	public final static String MAVERICK_API_ERROR = "application/vnd.dsa.maverick.error+json";
}
