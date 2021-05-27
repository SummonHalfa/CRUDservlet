package ih.webapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ih.webapp.model.PasswordUtils;
import ih.webapp.model.User;

public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/user_sql";
	private String jdbcUsername = "root";
	private String jdbcPW = "";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + " (Nome, Cognome, email, Residenza) VALUES"
													+ " (?, ?, ?, ?);";
	
	private static final String SELECT_USER_BY_ID = "select id, Nome, Cognome, email, Residenza from users where id = ?";
	private static final String	SELECT_ALL_USERS = "select * from users";
	private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	private static final String UPDATE_USERS_SQL = "update users set Nome = ?, Cognome = ?, email = ?, residenza = ? where id = ?;";
	private static final String LOGIN_USER_VALIDATION = "select * from logintable where user= ? and email= ?;";
	private static final String REGISTER_USER_VALIDATION = "INSERT INTO logintable (email,user,password,salt) VALUES(?,?,?,?)";
	
	
	protected Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPW);
		} catch (SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public boolean validateUser(String name,String pass,String email) {
		Connection connection = getConnection();
		boolean status=false;
		boolean passwordMatch=false;
		try{  
			PreparedStatement ps=connection.prepareStatement(LOGIN_USER_VALIDATION); 
			ps.setString(1,name);   
			ps.setString(2,email); 
			
			ResultSet rs=ps.executeQuery(); 
			status=rs.next();
			
			if(status) {
				String securePassword = rs.getString("password");
				String salt = rs.getString("salt");
				  
				passwordMatch = PasswordUtils.verifyUserPassword(pass, securePassword, salt);
			}

			if(!passwordMatch) {
				status=false;
			}
			          
		}catch(Exception e){
			System.out.println(e);
		}  
			return status;  
	} 
	
	public boolean registerUser(String name,String pass,String email, String salt) {
		Connection connection = getConnection();
		boolean status=false;  
		try{  
			PreparedStatement ps=connection.prepareStatement(REGISTER_USER_VALIDATION);  
			ps.setString(1,email);  
			ps.setString(2,name);  
			ps.setString(3,pass); 
			ps.setString(4,salt);
			      
			ps.executeUpdate();
			status=true;
		}catch(Exception e){
			System.out.println(e);
		}  
			return status;  
	}  
	
	
	public void insertUser(User user) throws SQLException {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
			preparedStatement.setString(1, user.getNome());
			preparedStatement.setString(2, user.getCognome());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getResidenza());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateUser(User user)  throws SQLException {
		boolean rowUptated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL)){
			statement.setString(1, user.getNome());
			statement.setString(2, user.getCognome());
			statement.setString(3, user.getEmail());
			statement.setString(4, user.getResidenza());
			statement.setInt(5, user.getId());
			
			rowUptated = statement.executeUpdate() > 0;
		}
		return rowUptated;
	}
	
	public User selectUser(int id) {
		User user = null;
		
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String residenza = rs.getString("residenza");
				user = new User(id, nome, cognome, email, residenza);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> selectAllUser() {
		List<User> users = new ArrayList<>();
		
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String email = rs.getString("email");
				String residenza = rs.getString("residenza");
				users.add(new User(id, nome, cognome, email, residenza));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL)){
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
}
