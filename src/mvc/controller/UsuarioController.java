package mvc.controller;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mvc.model.*;

@Controller
public class UsuarioController {
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("register")
	public String register(Usuario usuario, @RequestParam("confirmarsenha") String confirmarsenha, HttpSession session) {
		DAO dao = new DAO();
		if (usuario.getSenha().contentEquals(confirmarsenha)) {
			try {
				dao.register(usuario);
				return "login";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "ErroCadastro";
	}
	
	@RequestMapping("main")
	public String main(Usuario usuario, HttpSession session) {
		DAO dao = new DAO();
		try {
			Usuario usuarioLogin = dao.login(usuario);
			System.out.println(usuarioLogin);
			
			if (usuarioLogin == null) {
				return"login";
			} else {
				List<Nota> notas = dao.getLista(usuarioLogin);
				System.out.println(usuarioLogin.getNome());
				System.out.println(notas);
				session.setAttribute("usuario", usuarioLogin);
				session.setAttribute("notas", notas);
				return "main";

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "login";
		}
	}

}
