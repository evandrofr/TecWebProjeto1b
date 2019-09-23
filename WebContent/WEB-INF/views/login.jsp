<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form"
prefix="form" %>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
		<h3>Cadastrar</h3>
		<form action="register" method="post">
			NOME: <input type="text" name="nome"> 
			<br>
			USUARIO: <input type="text" name="usuario"> 
			<br>
			SENHA: <input type="password" name="senha"> 
			<br>
			CONFIRMAR SENHA: <input type="password" name="confirmarsenha"> 
			<br>
			<input type="submit" name="register" value="register	">
		</form>
		<h3>Login</h3>
		<form action="main" method="post">
			USUARIO: <input type="text" name="usuario"> 
			<br>
			SENHA: <input type="password" name="senha"> 
			<br>
			<input type="submit" name="login" value="login">
		</form>
	</body>
</html>