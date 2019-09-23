package mvc.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mvc.model.*;


@Controller
public class NotaController {
	@RequestMapping("SendNote")
	public String SendNote(String titulo, String msg, String deadline, HttpSession session) {
		DAO dao = new DAO();
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		try {
			dao.sendNote(titulo, msg, deadline, String.valueOf(usuario.getId()));
			List<Nota> notas = dao.getLista(usuario);
			session.setAttribute("notas", notas);
			session.setAttribute("usuario", usuario);
			return "main";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "main";
		}
	}

	@RequestMapping("DeleteNote")
	public String DeleteNote(String note_id, HttpSession session) {
		System.out.println("note_id: " + note_id);
		DAO dao = new DAO();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		try {
			Nota nota = dao.getNota(note_id);
			dao.deleteNote(note_id);
			List<Nota> notas = dao.getLista(usuario);
			session.setAttribute("notas", notas);
			session.setAttribute("usuario", usuario);
			return "main";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "main";
		}
	}

	@RequestMapping("EditNote")
	public String EditNote(String note_id, HttpSession session) {
		DAO dao = new DAO();
		Nota nota = dao.getNota(note_id);
		session.setAttribute("nota", nota);
		Usuario usuario = dao.getUsuario(nota.getUsuario_id());
		session.setAttribute("usuario", usuario);
		return "editor";

	}
	
	@RequestMapping("AlterNote")
	public String AlterNote(String title, String msg, String deadline,String user_id, String nota_id,HttpSession session) {
		DAO dao = new DAO();
		try {
			dao.alterNote(title,msg,deadline,user_id,nota_id);
			Usuario usuario = dao.getUsuario(Integer.valueOf(user_id));
			List<Nota> notas = dao.getLista(usuario);
			session.setAttribute("notas", notas);
			session.setAttribute("usuario", usuario);
			return "main";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "editor";
		}
	}
	
	@RequestMapping("OrderByDeadline")
	public String OrderByDeadline(String user_id,HttpSession session) {
		DAO dao = new DAO();
		List<Nota> notas = dao.orderListaByDeadline(Integer.valueOf(user_id));
		Usuario usuario = dao.getUsuario(Integer.valueOf(user_id));
		session.setAttribute("notas", notas);
		session.setAttribute("usuario", usuario);
		return "main";
		
	}
	
	@RequestMapping("OrderByTitle")
	public String OrderByTitle(String user_id,HttpSession session) {
		DAO dao = new DAO();
		List<Nota> notas = dao.orderListaByTitle(Integer.valueOf(user_id));
		Usuario usuario = dao.getUsuario(Integer.valueOf(user_id));
		session.setAttribute("notas", notas);
		session.setAttribute("usuario", usuario);
		return "main";
		
	}
	
	@RequestMapping("Filter")
	public String Filter(String word,String user_id, HttpSession session) {
		DAO dao = new DAO();
		Usuario usuario = dao.getUsuario(Integer.valueOf(user_id));
		System.out.println("word" + word);
		if (word == "") {
			List<Nota> notas = dao.getLista(usuario);
			session.setAttribute("notas", notas);
		} else {
			List<Nota> notas = dao.filter(Integer.valueOf(user_id), word);
			session.setAttribute("notas", notas);
		}
		session.setAttribute("usuario", usuario);
		return "main";
		
	}
	
}
