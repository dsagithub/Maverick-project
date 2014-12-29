package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;

import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.DataSourceSPA;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.Songs;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.SongsCollection;



@Path("/songs")
public class SongResource {
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	@Context
	private Application app;
	
	//private Security Context security;
	
	//Listar todas las canciones
	private String ListarSongs = "select * from songs; ";
	@GET
	public SongsCollection getSongs(){
		SongsCollection songs = new SongsCollection();
		
		System.out.println("no conectados a la BD");
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("conectados a la BD");
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(ListarSongs);
			//stmt.executeQuery();
			ResultSet rs = stmt.executeQuery();
			System.out.println(stmt);
			
			while (rs.next()) {
				
				Songs song = new Songs();
				song.setSong_name(rs.getString("song_name"));
				song.setUsername(rs.getString("username"));
				song.setSongid(rs.getString("songid"));
				song.setAlbum(rs.getString("album_name"));
				song.setDescription(rs.getString("description"));
				song.setStyle(rs.getString("style"));
				song.setLast_modified(rs.getDate("last_modified"));
				song.setSongURL(app.getProperties().get("uploadFolder")
						+ song.getSongid()+ ".mp3");
				song.setLikes(rs.getString("likes"));
				songs.add(song);
				System.out.println("Query salida: " + stmt);
				
				
			
				
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		System.out.println(songs);
		return songs;
	}
	
	
	//Método para subir una cancion
	

	@POST
	@Consumes(javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA)
	public Songs uploadAudio(@FormDataParam("song_name") String song_name,
			@FormDataParam("username") String username, 
			@FormDataParam("album_name") String album_name,
			@FormDataParam("description") String description,
			@FormDataParam("style") String style,
			@FormDataParam("likes") String likes,
			@FormDataParam("songfile") InputStream file){ 
			//@FormDataParam("song") FormDataContentDisposition fileDisposition) {
		
		//Properties propiedades = new Properties(); //ver el archivo properties
		UUID uuid = write(file);
		Songs songs = new Songs();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("insert into songs (songid, song_name, username, album_name, description, style, likes) values (?,?,?,?,?,?,?) ");
			stmt.setString(1, uuid.toString());
			stmt.setString(3, username);
			stmt.setString(2, song_name);
			stmt.setString(4, album_name);
			stmt.setString(5, description);
			stmt.setString(6, style);
			stmt.setString(7, likes); //inicialmente 0
			
			System.out.println("Query salida: " + stmt);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}
		
		//songs.setFilename(uuid.toString() + ".mp3"); //unico formato
		//songs.setTitle(song_name);
		//songs.setSongURL(app.getProperties().get("songBaseURL") + songData.getFilename());
		//System.out.println(propiedades.getProperty("songBaseURL"));
		
		
		return songs;
	}

	private UUID write(InputStream file) {
		
		//File soundFile = new File("C:/DSA/Proyectos/Maverick-project/www/song");
		//Properties propiedades = new Properties(); //ver el archivo properties
		UUID uuid = UUID.randomUUID();
		String filename = uuid.toString() + ".mp3"; //nombre de la cancion
        //DataInputStream audioIn = new DataInputStream(new BufferedInputStream(file));
		DataInputStream audioIn = new DataInputStream(file);
		DataOutputStream audioOut = null;
		try {
			//leemos el fichero original y lo escribimos 
			audioOut = new DataOutputStream(new FileOutputStream(app.getProperties().get("uploadFolder") + filename));
			byte[] buf = new byte[1024];
			//int nbytesread = audioIn.read(buf);
			int nbytesread;
			while ((nbytesread=audioIn.read(buf)) > 0){
				audioOut.write(buf, 0, nbytesread);
				
			}
			System.out.println("salimos del bucle de escritura");
			System.out.println("directorio: " + app.getProperties().get("uploadFolder") + filename); //directorio + nombre de la cancion guardada
			
		} catch (IOException e) {
			throw new InternalServerErrorException("Problema al escribir la canción, revisa el proceso por favor");
		}
		
		finally{
			try {
				audioOut.close();
				audioIn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		System.out.println("id unico de la cancion: " +uuid); //identificador unico
		return uuid;
	}

	//Método para borrar una cancion
	
	private String DELETE_SONG = "DELETE FROM songs where song_name = ?;";

	@DELETE
	@Path("/{song_name}")
	public void DeleteSong(@PathParam("song_name") String song_name) {
        Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("datos: " + song_name);
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_SONG, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, song_name);
			System.out.println(stmt);

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay una cancion con este nombre " + song_name);
			} else {
				System.out.println("cancion eliminada");
			}
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
			}
		}

		// no retorna nada el delete, es un void!
	}

	//Método para actualizar una cancion
	
	
	//Método para buscar canciones
	
	private String GET_SEARCH_SONG = " SELECT * from songs where song_name LIKE ? ;";
	@Path("/search")
	@GET
    @Produces(MediaType.MAVERICK_API_SONG)
	public SongsCollection SearchSongs(@QueryParam("song_name") String song_name) {
		SongsCollection songs = new SongsCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("datos: " + song_name);
		

		PreparedStatement stmt = null;

		try {

			if (song_name != null) {
				stmt = conn.prepareStatement(GET_SEARCH_SONG);
				stmt.setString(1, song_name);
			}

	        
			ResultSet rs = stmt.executeQuery();
			System.out.println(stmt);
				while (rs.next()) {
					Songs song = new Songs();
					song.setSong_name(rs.getString("song_name"));
					song.setUsername(rs.getString("username"));
					song.setSongid(rs.getString("songid"));
					song.setAlbum(rs.getString("album_name"));
					song.setDescription(rs.getString("description"));
					song.setStyle(rs.getString("style"));
					song.setLast_modified(rs.getDate("last_modified"));
					song.setSongURL(app.getProperties().get("uploadFolder")
							+ song.getSongid()+ ".mp3");
					song.setLikes(rs.getString("likes"));
					
					System.out.println("Query salida: " + stmt);
				
					songs.add(song);
				}
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);

		} finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(songs);
		return songs;
	}
	
	//Metodo para listar las canciones propias
	
	private String GET_SONG_BY_USERNAME = " SELECT * from songs where username LIKE ? ;";

	@GET
	@Path("/search2")
	@Produces(MediaType.MAVERICK_API_SONG)
	public SongsCollection SearchCancionbyUsername(@QueryParam("username") String username) {
		SongsCollection songs = new SongsCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("datos: " + username);
		
		PreparedStatement stmt = null;
		try {

			if (username != null) {
				stmt = conn.prepareStatement(GET_SONG_BY_USERNAME);
				stmt.setString(1, username);
			}
			
			
			ResultSet rs = stmt.executeQuery();
			System.out.println(stmt);
				while (rs.next()) {
					Songs song = new Songs();
					song.setSong_name(rs.getString("song_name"));
					song.setUsername(rs.getString("username"));
					song.setSongid(rs.getString("songid"));
					song.setAlbum(rs.getString("album_name"));
					song.setDescription(rs.getString("description"));
					song.setStyle(rs.getString("style"));
					song.setLast_modified(rs.getDate("last_modified"));
					song.setSongURL(app.getProperties().get("uploadFolder")
							+ song.getSongid()+ ".mp3");
					song.setLikes(rs.getString("likes"));
					
					System.out.println("Query salida: " + stmt);
				
					songs.add(song);
				}
			
		} catch (SQLException e) {
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);

		} 
		finally {
			try {
				if (stmt != null)
					stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(songs);
		return songs;
	}
	
	//Método para listar canciones por likes
	
	private String listar_canciones_likes_todas = "select * from songs order by likes DESC;";
	private String listar_canciones_likes_mias = "select * from songs where username LIKE ? order by likes DESC;";
	private String listar_canciones_likes_seguidores = "select * from songs, follow where follow.followingname = songs.username and follow.followername = ? order by likes DESC;";
	
	@GET
	@Path("/likes")
	@Produces(MediaType.MAVERICK_API_SONG)
	public SongsCollection getLikes(@QueryParam("username") String username, 
			@QueryParam("followername") String followername){
		
		SongsCollection songs = new SongsCollection();
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		System.out.println("datos: " + username);
		PreparedStatement stmt = null;
		
		try{
			if (username != null  && followername == null){
				stmt = conn.prepareStatement(listar_canciones_likes_mias);
				stmt.setString (1, username);	
			}
			else if (username == null && followername == null)
			{
				stmt = conn.prepareStatement(listar_canciones_likes_todas);
			}
			else if(username == null && followername != null)
			{
				stmt = conn.prepareStatement(listar_canciones_likes_seguidores);
				stmt.setString (1, followername);	
			}
			
			
			ResultSet rs = stmt.executeQuery();
			System.out.println(stmt);
				while (rs.next()) {
					Songs song = new Songs();
					song.setSong_name(rs.getString("song_name"));
					song.setUsername(rs.getString("username"));
					song.setSongid(rs.getString("songid"));
					song.setAlbum(rs.getString("album_name"));
					song.setDescription(rs.getString("description"));
					song.setStyle(rs.getString("style"));
					song.setLast_modified(rs.getDate("last_modified"));
					song.setSongURL(app.getProperties().get("uploadFolder")
							+ song.getSongid()+ ".mp3");
					song.setLikes(rs.getString("likes"));
					
					System.out.println("Query salida: " + stmt);
				
					songs.add(song);
				}
		}
				catch (SQLException e) {
					throw new ServerErrorException(e.getMessage(),
							Response.Status.INTERNAL_SERVER_ERROR);
				}
				finally {
					try {
						if (stmt != null)
							stmt.close();
						conn.close();
					} catch (SQLException e) {
					}
				}
		
		return songs;
	}
	
	
	
	//Metodo para listar las canciones de los usuarios a los que sigo

}
