package edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api;

/**
 * Created by david on 02/01/2015.
 */
public interface MediaType {
    public final static String MAVERICK_API_USER = "application/vnd.maverick.api.user+json"; //usuario
    public final static String MAVERICK_API_USER_COLLECTION = "application/vnd.maverick.api.user.collection+json"; //coleción usuarios
    public final static String MAVERICK_API_SONG = "application/vnd.maverick.api.song+json";
    public final static String MAVERICK_API_SONG_COLLECTION = "application/vnd.maverick.api.song.collection+json"; //coleción stings
    public final static String MAVERICK_API_COMMENT = "application/vnd.maverick.api.comment+json";
    public final static String MAVERICK_API_ERROR = "application/vnd.dsa.maverick.error+json";
}