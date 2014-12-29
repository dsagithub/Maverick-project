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
import java.sql.Timestamp;
import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.core.Application;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import javax.ws.rs.core.SecurityContext;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.DataSourceSPA;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.Songs;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.SongsCollection;



@Path("/songs")
public class SongResource {

	@Context
	private SecurityContext security;
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
	@Context
	private Application app;
	
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

	
	//Metodo para actualizar una cancion
	// PUT de una cancion, donde solo puede modificar el usuario que sube la canción.
			//private String PUT_UPDATE_SONG = " UPDATE songs set song_name= ? where songid = ?  ";	
	private String PUT_UPDATE_SONG = "update songs set songid=ifnull(?, songid), style=ifnull(?, style) where song_name = ?;";
	 		
	        @PUT
			@Path("/{song_name}")
			@Consumes(MediaType.MAVERICK_API_SONG)
			@Produces(MediaType.MAVERICK_API_SONG)
			public Songs updateSong(@PathParam("song_name") String song_name, Songs song) {	
				
				// solo puede el registrado
				//if (!security.isUserInRole("registered"))
				//	throw new ForbiddenException("You are not allowed to delete a book");
				
				//Falta por añadir que el usuario que edite la cancion sea el creador
				// Felipe solo edita lo de Felipe
				
				Connection conn = null;
				try {
					conn = ds.getConnection();
				} catch (SQLException e) {
					throw new ServerErrorException("Could not connect to the database",
							Response.Status.SERVICE_UNAVAILABLE);
				}

				PreparedStatement stmt = null;
				try {
					
						//if (song_name != null) {
							stmt = conn.prepareStatement(PUT_UPDATE_SONG);
							stmt.setString(1, song.getSongid());
							stmt.setString(2, song.getStyle());
							stmt.setString(3, song_name);
							//stmt.setString(1, song_name);
							//stmt.setInt(2,Integer.valueOf(songid));
							
							
							int rows = stmt.executeUpdate(); // para añadir con los datos de la BBDD
							System.out.println("Query salida: " + stmt);
							
							if (rows == 0){
								throw new NotFoundException("No hay una cancion llamada " + song_name);
								}
							else {
								System.out.println("canción actualizada");
								
							}
				
						//}
						
											
					// si todo va bien...

//Hace el put pero no saca la lista, falta por acabar					
					
					//song = getSongFromDatabase(song_name);

					

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (stmt != null)
							stmt.close();
						conn.close();
					} catch (SQLException e) {
						throw new ServerErrorException(e.getMessage(),
								Response.Status.INTERNAL_SERVER_ERROR);
					}
				}
				return song;

			}
			
			//Metodo para obtener cancion con song_name
			
			private String get_songdatos = "select * from songs ;";		
//@GET
//@Path("/{song_name}")
//@Produces(MediaType.MAVERICK_API_SONG)
		private Songs getSongFromDatabase(String song_name) {
				
				Songs song = new Songs();
			 
				Connection conn = null;
				try {
					conn = ds.getConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			 
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement(get_songdatos);
					stmt.setString(1,song_name);
					//stmt.setInt(2,Integer.valueOf(songid));
					System.out.println("Query salida: " + stmt);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						song.setSong_name(rs.getString("song_name"));
						song.setUsername(rs.getString("username"));
						song.setSongid(rs.getString("songid"));
						//song.setAlbum(rs.getString("album_name"));
						song.setDescription(rs.getString("description"));
						song.setStyle(rs.getString("style"));
						//song.setDate(rs.getTimestamp("last_modified").getTime());
						//song.setSongURL(rs.getString("songURL"));
						song.setLikes(rs.getString("likes"));
						
						System.out.println("Query salida: " + stmt);
					}
					 else {
						 throw new NotFoundException("There's no sting with song_name="
						 + song_name);
						 }
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (stmt != null)
							stmt.close();
						conn.close();
					} catch (SQLException e) {
						throw new ServerErrorException("Could not connect to the database",
								Response.Status.SERVICE_UNAVAILABLE);
						
					}
				}
			 
				return song;
			}

	
	/*		private Songs getSongFromDatabase(String song_name) {
				Connection conn = null;
				// VideosCollection videos = new VideosCollection();
			
				Songs song = new Songs();
				try {
					conn = ds.getConnection();
				} catch (SQLException e) {
					throw new ServerErrorException("Could not connect to the database",
							Response.Status.SERVICE_UNAVAILABLE);
				}

				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement(get_songdatos);
					stmt.setString(1,song_name);

					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {

						song.setSong_name(rs.getString("song_name"));
						song.setUsername(rs.getString("username"));
						song.setSongid(rs.getString("songid"));
						song.setAlbum(rs.getString("album_name"));
						song.setDescription(rs.getString("description"));
						song.setStyle(rs.getString("style"));
						song.setDate(rs.getTimestamp("last_modified").getTime());
						//song.setSongURL(rs.getString("songURL"));
						song.setLikes(rs.getString("likes"));
						
						System.out.println("Query salida: " + stmt);;
						
					} else {

					}

				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						// Haya ido bien o haya ido mal cierra las conexiones
						if (stmt != null)
							stmt.close();
						conn.close();
					} catch (SQLException e) {
						throw new ServerErrorException(e.getMessage(),
								Response.Status.INTERNAL_SERVER_ERROR);
					}
				}
				return song;
			}
			*/
		 

		
		// MEtodo para listar canciones por género	
		
		private String GET_STYLE_SONG = " SELECT * from songs where style LIKE ?;";
		@Path("/style")
		@GET
	    @Produces(MediaType.MAVERICK_API_SONG)
		public SongsCollection SearchStyles(@QueryParam("style") String style) {
			SongsCollection songs = new SongsCollection();

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}
			System.out.println("datos: " + style);
			

			PreparedStatement stmt = null;

			try {

				if (style != null) {
					stmt = conn.prepareStatement(GET_STYLE_SONG);
					stmt.setString(1, style);
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
						//song.setDate(rs.getTimestamp("last_modified").getTime());
						//song.setSongURL(rs.getString("songURL"));

						//song.setStyle(rs.getString("style"));
						song.setLast_modified(rs.getDate("last_modified"));
						song.setSongURL(app.getProperties().get("uploadFolder")
								+ song.getSongid()+ ".mp3");
						//song.setDate(rs.getTimestamp("last_modified").getTime());
						//song.setSongURL(rs.getString("songURL"));
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
					//song.setDate(rs.getTimestamp("last_modified").getTime());
					song.setSongURL(rs.getString("songURL"));

					song.setStyle(rs.getString("style"));
					song.setLast_modified(rs.getDate("last_modified"));
					song.setSongURL(app.getProperties().get("uploadFolder")
							+ song.getSongid()+ ".mp3");
					//song.setDate(rs.getTimestamp("last_modified").getTime());
					//song.setSongURL(rs.getString("songURL"));
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
	//Metodo para listar las canciones de los usuarios a los que sigo
	
	@Path("/following")
	@GET
	@Produces(MediaType.MAVERICK_API_SONG_COLLECTION)
	public SongsCollection getSongsFollowing(@QueryParam("length") int length,
			@QueryParam("before") long before, @QueryParam("after") long after) {
		SongsCollection songs = new SongsCollection();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			boolean updateFromLast = after > 0;
			stmt = conn.prepareStatement(buildGetSongsFollowingQuery(updateFromLast));
			//stmt.setString(1, security.getUserPrincipal().getName());
			if (updateFromLast) {
				stmt.setTimestamp(2, new Timestamp(after));
			} else {
				if (before > 0)
					stmt.setTimestamp(2, new Timestamp(before));
				else
					stmt.setTimestamp(2, null);
				length = (length <= 0) ? 20 : length;
				stmt.setInt(3, length);
			}
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				
				Songs song = new Songs();
				song.setSong_name(rs.getString("song_name"));
				song.setUsername(rs.getString("username"));
				//song.setSongid(rs.getString("songid"));
				song.setAlbum(rs.getString("album_name"));
				song.setDescription(rs.getString("description"));
				song.setStyle(rs.getString("style"));
				//song.setDate(rs.getTimestamp("last_modified").getTime());
				//song.setSongURL(rs.getString("songURL"));
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
			}
		}

		return songs;
	}

	private String buildGetSongsFollowingQuery(boolean updateFromLast) {
		if (updateFromLast)
			return "select s.* from Songs s, Follow f where f.followername = ? and s.username = f.followingname and last_modified > ? order by last_modified desc";
		else
			return "select s.* from Songs s, Follow f where f.followername = ? and s.username = f.followingname and last_modified < ifnull(?, now())  order by last_modified desc limit ?";

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
	

	
	private String GET_SONGS_QUERY_FROM_LAST = "select s.* from songs s, follow f where f.followername = ? and s.username = f.followingname and last_modified > ? order by last_modified desc";
	private String GET_SONGS_QUERY = "select s.* from Songs s, Follow f where f.followername = ? and s.username = f.followingname and last_modified < ifnull(?, now())  order by last_modified desc limit ?";
	//Metodo para listar las canciones de los usuarios a los que sigo
	@GET 
	@Path("/{username}")
	@Produces(MediaType.MAVERICK_API_SONG_COLLECTION)
	public SongsCollection getStings(@PathParam("username") String username ,@QueryParam("length") int length,
			@QueryParam("before") long before, @QueryParam("after") long after) {
		
		SongsCollection songs = new SongsCollection();
	 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			boolean updateFromLast = after > 0;
			stmt = updateFromLast ? conn
					.prepareStatement(GET_SONGS_QUERY_FROM_LAST) : conn
					.prepareStatement(GET_SONGS_QUERY);
					
					stmt.setString(1, username);
			if (updateFromLast) {
				stmt.setTimestamp(2, new Timestamp(after));
			} else {
				if (before > 0)
					stmt.setTimestamp(2, new Timestamp(before));
				else
					stmt.setTimestamp(2, null);
				length = (length <= 0) ? 30 : length;
				stmt.setInt(3, length);
			}
			System.out.println(stmt);
			ResultSet rs = stmt.executeQuery();
			boolean first = true;
			long oldestTimestamp = 0;
			while (rs.next()) {
				Songs song = new Songs();
				song.setSong_name(rs.getString("song_name"));
				song.setUsername(rs.getString("username"));
				song.setAlbum(rs.getString("album_name"));
				song.setDescription(rs.getString("description"));
				song.setStyle(rs.getString("style"));
				//song.setSongURL(rs.getString("songurl"));
				song.setLikes(rs.getString("likes"));
				//song.setDate(rs.getLong("date"));
				
				oldestTimestamp = rs.getTimestamp("last_modified").getTime();
				song.setLastModified(oldestTimestamp);
				if (first) {
					first = false;
					songs.setNewestTimestamp(song.getLastModified());
				}
				songs.addSong(song);
			}
			songs.setOldestTimestamp(oldestTimestamp);
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
	 
		return songs;
	}

	private String INSERT_COMMENT_QUERY = "insert into comments (song_name,username, text) values (?,?,?); ";
	//Metodo para comentar una canción
	@POST 
	@Path("/{song_name}/comment")
	@Consumes(MediaType.MAVERICK_API_COMMENT) 
	@Produces(MediaType.MAVERICK_API_COMMENT)
	public Songs createComment(@PathParam("song_name") String song_name, Songs song) {
		//validateSong(song);
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(INSERT_COMMENT_QUERY,
					Statement.RETURN_GENERATED_KEYS); 
			
			stmt.setString(2, song.getUsername()); 
			System.out.println(song.getUsername());
			stmt.setString(1, song_name);
			System.out.println(song_name);
			stmt.setString(3, song.getText());
			System.out.println(song.getText());
			
			System.out.println(stmt);
		
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys(); 
			if (rs.next()) {
				int commentid = rs.getInt(1);

				song = getCommentFromDatabase(Integer.toString(commentid));
			} else {
				// Something has failed...
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
	 
		return song;
	}
	
	
	private void validateSong(Songs song) {

		if (song.getText().length() > 500)
			throw new BadRequestException("Text can't be greater than 500 characters.");
	}
	private String GET_COMMENTS_QUERY = "select * from comments where commentid = ?;";
	
	private Songs getCommentFromDatabase(String songid) {
		Songs song = new Songs();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_COMMENTS_QUERY);
			stmt.setInt(1, Integer.valueOf(songid));
			ResultSet rs = stmt.executeQuery();
			long oldestTimestamp = 0;
			if (rs.next()) {
				song.setSongid(songid);
				song.setUsername(rs.getString("username"));
				song.setText(rs.getString("text"));
				song.setLastModified(rs.getTimestamp("time")
						.getTime());
			

			} else {
				// Something has failed...
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
		return song;
	}
	
	
	//Metodo para borrar el comentario de una canción
	private String DELETE_COMMENT_QUERY = "delete from comments where commentid = ?;";
	@DELETE
	@Path("/{song_name}/comment/{commentid}")
	public void DeleteComment(@PathParam("song_name") String song_name, @PathParam("commentid") String commentid) {
		

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_COMMENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, commentid);
			System.out.println(stmt);

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay una comentario para esta cancion "
						+ song_name);
			} else {
				System.out.println("comentario eliminado");
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
	
	

}
