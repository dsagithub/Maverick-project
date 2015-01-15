package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.codec.digest.DigestUtils;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.Users;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.UsersCollection;




@Path("/users")
public class UserResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private String GET_USER_BY_USERNAME_QUERY = "select * from users where username=?;";
	private final static String INSERT_USER_INTO_USERS = "insert into users values(?, MD5(?), ?, ?, ?)";
	private final static String INSERT_USER_INTO_USER_ROLES = "insert into user_roles values (?, ?)";
	private final static String GET_USER_BY_USERNAME ="select * from users where username=?";
	private final static String DELETE_USER_QUERY = "Delete from users where username=? ";
	private final static String UPDATE_USER_QUERY ="update users set name=ifnull(?, name), email=ifnull(?, email), description=ifnull(?, description)  where username=?;";
	
	//Metodo para loguearse
	@Path("/login")
	@POST
	@Produces(MediaType.MAVERICK_API_USER)
	@Consumes(MediaType.MAVERICK_API_USER)
	public Users login(Users user) {
		System.out.println("Entramos al metodo");
		if (user.getUsername() == null || user.getUserpass() == null)
			throw new BadRequestException(
					"username and password cannot be null.");

		String pwdDigest = DigestUtils.md5Hex(user.getUserpass()); //calculamos el md5 de la contraseña
		String storedPwd = getUserFromDatabase(user.getUsername(), true) //nos devuelve el pasword en md5 y en hexadecimal y se puede comparar con el de la base de datos y que sean iguales
				.getUserpass();
		
		user.setLoginSuccessful(pwdDigest.equals(storedPwd)); //ponemos el atributo de login si es true o false si coinciden
		 
		user.setUserpass(null); //cuando nos pasan el user, de manera que la contraseña no aparece
		System.out.println("Usuario logueado");
		return user;
	}
 
	private Users getUserFromDatabase(String username, boolean password) {
		Users user = new Users();
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(GET_USER_BY_USERNAME_QUERY);
			stmt.setString(1, username);
			
			System.out.println(stmt);
 
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				user.setUsername(rs.getString("username"));
				if (password)
					user.setUserpass(rs.getString("userpass"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));
				user.setDescription("description");
			} else
				throw new NotFoundException(username + " not found.");
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
 
		return user;
	}
	
	
	//Metodo para registrarse
	@POST
	@Consumes(MediaType.MAVERICK_API_USER)
	@Produces(MediaType.MAVERICK_API_USER)
	public Users createUser(Users user) {
		validateUser(user);
 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmtGetUsername = null;
		PreparedStatement stmtInsertUserIntoUsers = null;
		PreparedStatement stmtInsertUserIntoUserRoles = null;
		try {
			//Busca el nombre de usuario a la base de datos y mira si ya existe
			stmtGetUsername = conn.prepareStatement(GET_USER_BY_USERNAME_QUERY);
			stmtGetUsername.setString(1, user.getUsername());
			System.out.println(stmtGetUsername);
 
			ResultSet rs = stmtGetUsername.executeQuery();
			if (rs.next())
				throw new WebApplicationException(user.getUsername()
						+ " already exists.", Status.CONFLICT);
			rs.close();
 
			conn.setAutoCommit(false); //no se hace, hasta que confirmemos que queremos que se haga, ya que un insert significa insertar datas en dos tablas, y da un problema puede quedar un usuario sin rol
			stmtInsertUserIntoUsers = conn //creamos los stmt
					.prepareStatement(INSERT_USER_INTO_USERS);
			stmtInsertUserIntoUserRoles = conn
					.prepareStatement(INSERT_USER_INTO_USER_ROLES);
 //cuando verificas que el nombre es unico, insertas el usuario a la base de datos
			//damos valor
			stmtInsertUserIntoUsers.setString(1, user.getUsername());
			stmtInsertUserIntoUsers.setString(2, user.getUserpass());
			stmtInsertUserIntoUsers.setString(3, user.getName());
			stmtInsertUserIntoUsers.setString(4, user.getEmail());
			stmtInsertUserIntoUsers.setString(5, user.getDescription());
			stmtInsertUserIntoUsers.executeUpdate(); //ejecutamos
 //damos valor
			System.out.println(stmtInsertUserIntoUsers);
			stmtInsertUserIntoUserRoles.setString(1, user.getUsername());
			stmtInsertUserIntoUserRoles.setString(2, user.getRolename());
			System.out.println(stmtInsertUserIntoUserRoles);
			stmtInsertUserIntoUserRoles.executeUpdate(); //ejecutamos
 
			conn.commit(); // se inserta en la base de datos en las dos tablas
		} catch (SQLException e) {
			if (conn != null)
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			throw new ServerErrorException(e.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		} finally { //tenemos que poner el commit a true
			try {
				if (stmtGetUsername != null)
					stmtGetUsername.close();
				if (stmtInsertUserIntoUsers != null)
					stmtGetUsername.close();
				if (stmtInsertUserIntoUserRoles != null)
					stmtGetUsername.close();
				conn.setAutoCommit(true);
				conn.close();
			} catch (SQLException e) {
			}
		}
		user.setUserpass(null);
		return user;
	}
 
	private void validateUser(Users user) {
		if (user.getUsername() == null)
			throw new BadRequestException("username cannot be null.");
		if (user.getUserpass() == null)
			throw new BadRequestException("password cannot be null.");
		if (user.getName() == null)
			throw new BadRequestException("name cannot be null.");
		if (user.getEmail() == null)
			throw new BadRequestException("email cannot be null.");
		if (user.getDescription() == null)
			throw new BadRequestException("description cannot be null.");
	}
	
	
	//Metodo para modificar perfil
	@PUT
	@Path("/{username}")
	@Consumes(MediaType.MAVERICK_API_USER)
	@Produces(MediaType.MAVERICK_API_USER)
	public Users updateUser(@PathParam("username") String username, Users user) {
		
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			
			
			stmt = conn.prepareStatement(UPDATE_USER_QUERY);
			stmt.setString(4, username);
		
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail());
			
			stmt.setString(3, user.getDescription());
			System.out.println(stmt);
	 
			int rows = stmt.executeUpdate();
			if (rows == 1)
				user = getupdateUserFromDatabase(username);
	 
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
	 
		return user;
	}
	private Users getupdateUserFromDatabase(String username) {// Cogemos datos del
		// username
		Users user = new Users();

// Hacemos la conexión a la base de datos
	Connection conn = null;
	try {
		conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);	
			}

	PreparedStatement stmt = null;
	try {
		stmt = conn.prepareStatement(GET_USER_BY_USERNAME_QUERY);
		stmt.setString(1, username);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			user.setUsername(rs.getString("username"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setDescription(rs.getString("description"));

		} else {
			throw new NotFoundException("There's no user with username ="
					+ username);
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
	return user;

		}
	
		
	//Metodo para borrar perfil
	@DELETE 
	@Path("/{username}")
	public void deleteUser(@PathParam("username") String username) {
		//tenemos un void de manera que no devuelve nada ni consume ni produce, devuelve 204 ya que no hay contenido
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	 
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(DELETE_USER_QUERY);
			stmt.setString(1, username);
	 
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no user with username ="
						+ username);
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
	}
	
	
	
	//Metodo para buscar un usuario
	@GET
	@Path("/search")
	@Produces(MediaType.MAVERICK_API_USER_COLLECTION)
	public UsersCollection getUserbyUsername(@QueryParam("username") String username) {
		
		UsersCollection users = new UsersCollection();
		validateSearch(username);
		Connection conn = null;
		try{ conn =ds.getConnection();
		}catch (SQLException e)
		{
			throw new ServerErrorException("Could not connect to the databes", 
					Response.Status.SERVICE_UNAVAILABLE);
		}
		PreparedStatement stmt = null;
		System.out.println("datos: " + username);
		try{
			
			
				stmt = conn.prepareStatement(GET_USER_BY_USERNAME);
				stmt.setString (1, username);
				
				
			
	
			System.out.println("Query salida: " + stmt);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			Users user = new Users();
			user.setUsername(rs.getString("username"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setDescription(rs.getString("description"));
			
			
			users.addUser(user);
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
		return users;
		
	
	}
	
	private void validateSearch(String username){
		
		if (username == null)
			throw new BadRequestException(
					"No se han introducido datos en el campo de búsqueda");
		
	}
	
	
	

	
	
	
	
	
	
	
	
	//Metodo para ver el perfil de un usuario
	@Path("/{username}")
	@GET
	@Produces(MediaType.MAVERICK_API_USER)
	public Response getProfile(@PathParam("username") String username, @Context Request request){
		
		
		System.out.println("Implementamos el cache");
		
		// Create CacheControl
		CacheControl cc = new CacheControl();
		System.out.println(username);
		
		Users user = getprofileUserFromDatabase(username);
		//pillamos el correo y el username para ver si ha modificado
		String s = user.getEmail() + " " + user.getName() + " " + user.getDescription();
		
		//Calculate the ETag on last modified date of user resource
		EntityTag eTag = new EntityTag(Long.toString(s.hashCode()));
	 
		// Verify if it matched with etag available in http request
		Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);
		
		// If ETag matches the rb will be non-null;
		// Use the rb to return the response without any further processing
		if (rb != null) {
			return rb.cacheControl(cc).tag(eTag).build();
		}
	 
		// If rb is null then either it is first time request; or resource is
		// modified
		// Get the updated representation and return with Etag attached to it
		rb = Response.ok(user).cacheControl(cc).tag(eTag);
	 
		return rb.build();
		
	}
	
	private Users getprofileUserFromDatabase( String username) {
		System.out.println("Conectamos a la base de datos" +  username);
		
		Users user = new Users();
		 
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
		
		PreparedStatement stmt = null;
		try {
			System.out.println("Preparamos la query");
			stmt = conn.prepareStatement(GET_USER_BY_USERNAME);
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			System.out.println("Ejecutamos la query");
			if (rs.next()) {
				user.setUsername(rs.getString("username"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
				user.setDescription(rs.getString("description"));
			
			} else {
				throw new NotFoundException("There's no user with username="
						+ username);
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
	 
		return user;
	 
		
		
	}
//seguir usuario
	private String SeguirUser = "insert into follow (followingname, followername) values (?, ?);";
	@Path("/{username}/following")
	@POST
	@Consumes(MediaType.MAVERICK_API_USER_COLLECTION)
	public Users create

	(@PathParam("username") String username, Users user) {
		
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(SeguirUser);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, username);
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

		return user;

	}
	
	/*private String buildCreateFollower() {

		return "insert into follow (followingname, followername) values (?, ?);";
	}*/
	
	
	
	//Dejar de seguir usuario
	//private String DejarSeguirUser ="delete from Follow where followername = ? and followingname = ?";
	@Path("/{username}/following/{following}")
	@DELETE
	public void deleteFollowing(@PathParam("username") String username, @PathParam("following") String following) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServerErrorException("Could not connect to the database",
					Response.Status.SERVICE_UNAVAILABLE);
		}
	
		PreparedStatement stmt = null;
		try {
			String sql = buildDeleteFollow();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, following);
			int rows = stmt.executeUpdate();
			if (rows == 0)
				throw new NotFoundException("There's no User with username=" + username + " or " + following);
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
	}
	
	private String buildDeleteFollow() {
		return "delete from Follow where followername = ? and followingname = ?";
	}
	//Lista de usuarios follower
	
		private String ListarUsuariosFollowers = "select * from users, follow where follow.followername = users.username and follow.followingname = ?"; //"select u.* from users u, Follow f where f.followername = u.username and f.followingname = ?";
		@Path("/{username}/follower")
		@GET
		@Produces(MediaType.MAVERICK_API_USER_COLLECTION)
		public UsersCollection getFollower(@PathParam("username") String username) {
			UsersCollection follower= new UsersCollection();
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(ListarUsuariosFollowers);
				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Users user = new Users();
					user.setUsername(rs.getString("username"));
					user.setName(rs.getString("name"));
					user.setDescription(rs.getString("description"));
					follower.addUser(user);
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

			return follower;
		}

	
		
//Lista de usuarios seguidores

		private String ListarUsuariosSeguidores = "select u.* from users u, Follow f where f.followingname = u.username and f.followername = ?";
		@Path("/{username}/following")
		@GET
		@Produces(MediaType.MAVERICK_API_USER_COLLECTION)
		public UsersCollection getFollowing(@PathParam("username") String username) {
			UsersCollection following = new UsersCollection();
			Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				throw new ServerErrorException("Could not connect to the database",
						Response.Status.SERVICE_UNAVAILABLE);
			}

			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement(ListarUsuariosSeguidores);
				stmt.setString(1, username);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					Users user = new Users();
					user.setUsername(rs.getString("username"));
					user.setName(rs.getString("name"));
					user.setDescription(rs.getString("description"));
					following.addUser(user);
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

			return following;
		}
}

	
		
		
		

		
