package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
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









import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.DataSourceSPA;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.Songs;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.SongsCollection;




@Path("/songs")
public class SongResource {
	@Context
	private Application app;
	
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	//private SecurityContext security;
	
	//Metodo para listar las canciones propias
	//Metodo para subir una cancion
	//Metodo para borra una cancion
	
	private String DELETE_SONG = "DELETE FROM songs Where song_name = ?;";

	@DELETE
	@Path("/{song_name}")
	public void DeleteSong(@PathParam("song_name") String song_name) {
		// solo puede el registrado
		//if (!security.isUserInRole("registered"))
		//	throw new ForbiddenException("You are not allowed to delete a book");

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_SONG, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, song_name);
			System.out.println(stmt);

			int rows = stmt.executeUpdate();
			if (rows == 0) {
				throw new NotFoundException("No hay una cancion con este nombre"
						+ song_name);
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
	/*@PUT
	@Path("/{songid}")
	@Consumes(MediaType.MAVERICK_API_SONG)
	@Produces(MediaType.MAVERICK_API_SONG)
	public Songs updateBook(@PathParam("songid") String songid, Songs song) {

		
		
		//Falta por añadir que el usuario que edite la cncion sea el creador
		// Alicia solo edita lo de Alicia
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			// llamamos a la función para la query y la hacemos la database
			String sql = buildUpdateSong();
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, song.getSong_name());
			stmt.setString(2, songid);

			stmt.executeUpdate(); // para añadir con los datos de la BBDD
									
			// si todo va bien...
			
//Listar!					song = getSongFromDatabase(songid);

			

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
	
	// PUT para hacer un update de canciones
	
	private String buildUpdateSong() {
		return "update songs set song_name = ? where songid = ? ";
	}*/
	// PUT de una cancion, donde solo puede modificar el usuario que sube la canción.
			private String PUT_UPDATE_SONG = " UPDATE songs set song_name= ? where songid = ?  ";	
			@PUT
			@Path("/{song_name}/{songid}")
			@Consumes(MediaType.MAVERICK_API_SONG)
			@Produces(MediaType.MAVERICK_API_SONG)
			public Songs updateSong(@PathParam("song_name") String song_name,@PathParam("songid") String songid, Songs song) {	
				//
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
					
						if (song_name != null) {
							stmt = conn.prepareStatement(PUT_UPDATE_SONG);
							stmt.setString(1, song_name);
							stmt.setInt(2,Integer.valueOf(songid));
							
							
							stmt.executeUpdate(); // para añadir con los datos de la BBDD
							System.out.println("Query salida: " + stmt);
							
						}
						
											
					// si todo va bien...

//Hace el put pero no saca la lista, falta por acabar					
					
					song = getSongFromDatabase(song_name);

					

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
						song.setDate(rs.getTimestamp("last_modified").getTime());
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
//private String GET_SONG_BY_SONG_NAME = "select s.*, u.name from stings s, users u where u.username=s.username ";

 /*  @GET
	@Path("/{song_name}")
	@Produces(MediaType.MAVERICK_API_SONG)
		public Response getSting(@PathParam("song_name") String song,
				@Context Request request) {
		return song;
	}
*/
			 

		
		// MEtodo para listar canciones por género	
		
		private String GET_STYLE_SONG = " SELECT * from songs where style LIKE ? ";
		@Path("/{style}")
		@GET
	    @Produces(MediaType.MAVERICK_API_SONG)
		public SongsCollection SearchStyles(@QueryParam("style") String style) {
			SongsCollection songs = new SongsCollection();

			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the databes",
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
						song.setDate(rs.getTimestamp("last_modified").getTime());
						song.setSongURL(rs.getString("songURL"));
						song.setLikes(rs.getString("likes"));
						
						System.out.println("Query salida: " + stmt);
					
						songs.add(song);
					}
				
			} catch (SQLException e) {
				e.printStackTrace();
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
		
		
		
		//Metodo para buscar canciones
	
	private String GET_SEARCH_SONG = " SELECT * from songs where song_name LIKE ? ";
	@Path("/search")
	@GET
    @Produces(MediaType.MAVERICK_API_SONG)
	public SongsCollection SearchSongs(@QueryParam("song_name") String song_name) {
		SongsCollection songs = new SongsCollection();

		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the databes",
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
					song.setDate(rs.getTimestamp("last_modified").getTime());
					song.setSongURL(rs.getString("songURL"));
					song.setLikes(rs.getString("likes"));
					
					System.out.println("Query salida: " + stmt);
				
					songs.add(song);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
					song.setDate(rs.getTimestamp("last_modified").getTime());
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
		
		
		
		
		
		


}