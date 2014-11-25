package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

public interface MediaType {
	//intercambiamos recursos en un determinado formato de datos (json),de manera que es un derivado de json para indicar el formato
	public final static String MAVERICK_API_USER = "application/vnd.maverick.api.user+json"; //usuario
	public final static String BEETER_API_USER_COLLECTION = "application/vnd.beeter.api.user.collection+json"; //coleción usuarios
	public final static String BEETER_API_STING = "application/vnd.beeter.api.sting+json"; //sting
	public final static String BEETER_API_STING_COLLECTION = "application/vnd.beeter.api.sting.collection+json"; //coleción stings
	public final static String BEETER_API_ERROR = "application/vnd.dsa.beeter.error+json";
}
