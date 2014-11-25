package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.Users;






@Path("/users")
public class UserResource {
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	private String GET_USER_BY_USERNAME_QUERY = "select * from users where username=?";
	private final static String INSERT_USER_INTO_USERS = "insert into users values(?, MD5(?), ?, ?, ?)";
	private final static String INSERT_USER_INTO_USER_ROLES = "insert into user_roles values (?, 'artist')";
	
	//Metodo para loguearse
	@Path("/login")
	@POST
	@Produces(MediaType.MAVERICK_API_USER)
	@Consumes(MediaType.MAVERICK_API_USER)
	public Users login(Users user) {
		if (user.getUsername() == null || user.getUserpass() == null)
			throw new BadRequestException(
					"username and password cannot be null.");
 
		String pwdDigest = DigestUtils.md5Hex(user.getUserpass()); //calculamos el md5 de la contraseña
		String storedPwd = getUserFromDatabase(user.getUsername(), true) //nos devuelve el pasword en md5 y en hexadecimal y se puede comparar con el de la base de datos y que sean iguales
				.getUserpass();
 
		user.setLoginSuccessful(pwdDigest.equals(storedPwd)); //ponemos el atributo de login si es true o false si coinciden
		user.setUserpass(null); //cuando nos pasan el user, de manera que la contraseña no aparece
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
	//Metodo para borrar perfil
	//Metodo para seguir un usuario
	//Metodo para dejar de seguir un usuario
	//Metodo para buscar usuarios
	//Metodo para ver el perfil de un usuario

}
