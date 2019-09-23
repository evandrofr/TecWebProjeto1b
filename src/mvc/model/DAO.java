package mvc.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DAO {
	private Connection connection = null;
	String url = System.getenv("mysql_url");
	String user = System.getenv("mysql_user");
	String password = System.getenv("mysql_password");
	
	public DAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(
					url,user,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void register(Usuario usuario) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("INSERT INTO usuario (user, password, name) VALUES (?,?,?)");
		ps.setString(1, usuario.getUsuario());
		ps.setString(2, usuario.getSenha());
		ps.setString(3, usuario.getNome());
		ps.execute();
		ps.close();
	}
	
	public Usuario login(Usuario usuario) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE user = ? AND password = ?");
		ps.setString(1, usuario.getUsuario());
		ps.setString(2, usuario.getSenha());
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			Usuario user = new Usuario();
			user.setUsuario(rs.getString("user"));
			user.setSenha(rs.getString("password"));
			user.setNome(rs.getString("name"));
			user.setId(rs.getInt("id"));
			return user;
		}
		return null;
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Nota> getLista(Usuario usuario) {
		List<Nota> notas = new ArrayList<Nota>();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("SELECT * FROM nota WHERE usuario_id = ?");
			stmt.setInt(1,usuario.getId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Nota nota = new Nota();
				nota.setId(rs.getInt("id"));
				nota.setUsuario_id(rs.getInt("usuario_id"));
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("date"));
				nota.setDate(data);
				Calendar deadline = Calendar.getInstance();
				deadline.setTime(rs.getDate("deadline"));;
				nota.setDeadline(deadline);
				nota.setTitle(rs.getString("Title"));
				nota.setMensage(rs.getString("Mensage"));
				notas.add(nota);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notas;
	}
	
	public Nota getNota(String nota_id) {
		PreparedStatement ps;
		Nota nota = new Nota();
		try {
			ps = connection.prepareStatement("SELECT * FROM nota WHERE id = ?");
			System.out.println(nota_id);
			ps.setInt(1, Integer.valueOf(nota_id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				nota.setId(rs.getInt("id"));
				nota.setUsuario_id(rs.getInt("usuario_id"));
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("date"));
				nota.setDate(data);
				Calendar deadline = Calendar.getInstance();
				deadline.setTime(rs.getDate("date"));;
				nota.setDeadline(deadline);
				nota.setTitle(rs.getString("Title"));
				nota.setMensage(rs.getString("Mensage"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nota;
	}
	
	
	public Usuario getUsuario(int usuario_id) {
		PreparedStatement ps;
		Usuario usuario = new Usuario();
		try {
			ps = connection.prepareStatement("SELECT * FROM usuario WHERE id = ?");
			ps.setInt(1, usuario_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("name"));
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuario;
	}
	
	public void sendNote(String title, String message, String deadlineString, String user_id) throws SQLException {
		
		PreparedStatement ps = connection.prepareStatement("INSERT INTO nota (usuario_id, mensage, date, deadline, title) VALUES (?,?,CURDATE(),?,?)");
		ps.setInt(1, Integer.valueOf(user_id));
		ps.setString(2, message);
		ps.setString(3, deadlineString);
		ps.setString(4, title);
		ps.execute();
		ps.close();
		
	}
	
	public void deleteNote(String note_id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("DELETE FROM nota WHERE id = ?");
		ps.setInt(1, Integer.valueOf(note_id));
		ps.execute();
		ps.close();
	}
	
	public void alterNote(String title, String message, String deadlineString, String user_id, String nota_id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement("UPDATE nota SET title = ?, usuario_id = ?, mensage = ?, date = CURDATE(), deadline = ? WHERE id = ?");
		ps.setString(1, title);
		ps.setInt(2, Integer.valueOf(user_id));
		ps.setString(3,message);
		ps.setString(4, deadlineString);
		ps.setInt(5, Integer.valueOf(nota_id));
		ps.execute();
		ps.close();
		
	}
	
	public List<Nota> orderListaByDeadline(int usuario_id) {
		List<Nota> notas = new ArrayList<Nota>();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("SELECT * FROM nota WHERE usuario_id = ? ORDER BY deadline ASC");
			stmt.setInt(1,usuario_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Nota nota = new Nota();
				nota.setId(rs.getInt("id"));
				nota.setUsuario_id(rs.getInt("usuario_id"));
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("date"));
				nota.setDate(data);
				Calendar deadline = Calendar.getInstance();
				deadline.setTime(rs.getDate("deadline"));;
				nota.setDeadline(deadline);
				nota.setTitle(rs.getString("Title"));
				nota.setMensage(rs.getString("Mensage"));
				notas.add(nota);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notas;
	}
	
	public List<Nota> orderListaByTitle(int usuario_id) {
		List<Nota> notas = new ArrayList<Nota>();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("SELECT * FROM nota WHERE usuario_id = ? ORDER BY title ASC");
			stmt.setInt(1,usuario_id);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Nota nota = new Nota();
				nota.setId(rs.getInt("id"));
				nota.setUsuario_id(rs.getInt("usuario_id"));
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("date"));
				nota.setDate(data);
				Calendar deadline = Calendar.getInstance();
				deadline.setTime(rs.getDate("deadline"));;
				nota.setDeadline(deadline);
				nota.setTitle(rs.getString("Title"));
				nota.setMensage(rs.getString("Mensage"));
				notas.add(nota);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notas;
	}
	
	public List<Nota> filter(int usuario_id, String word) {
		List<Nota> notas = new ArrayList<Nota>();
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement("SELECT * FROM nota WHERE usuario_id = ? and mensage REGEXP ?");
			stmt.setInt(1,usuario_id);
			stmt.setString(2, word);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Nota nota = new Nota();
				nota.setId(rs.getInt("id"));
				nota.setUsuario_id(rs.getInt("usuario_id"));
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("date"));
				nota.setDate(data);
				Calendar deadline = Calendar.getInstance();
				deadline.setTime(rs.getDate("deadline"));;
				nota.setDeadline(deadline);
				nota.setTitle(rs.getString("Title"));
				nota.setMensage(rs.getString("Mensage"));
				notas.add(nota);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notas;
	}
}

