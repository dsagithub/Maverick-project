package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import javax.sql.DataSource;
import javax.ws.rs.Path;



@Path("/songs")
public class SongResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	//Metodo para subir una cancion
	//Metodo para borra una cancion
	//Metodo para actualizar una cancion
	//Metodo para buscar canciones
	//Metodo para listar las canciones propias
	//Metodo para listar las canciones de los usuarios a los que sigo

}
