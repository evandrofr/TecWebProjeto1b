<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%@page import="mvc.model.*,java.util.*" %>
</head>
<body>
<% Usuario usuario = (Usuario) session.getAttribute("usuario"); 
Nota nota = (Nota) session.getAttribute("nota"); %>
<h3> Editar nota de <%= usuario.getNome() %> </h3>
<form action="./AlterNote" method="post">
Título: <input type="text" name="title" value="<%= nota.getTitle() %>">
</br>
Mensagem: <input type="text" name="msg" value="<%= nota.getMensage() %>">
</br>
Deadline: <input type="text" name="deadline" value="<%= nota.getDeadline().getTime().toString() %>">
</br>
<input type="hidden" name="user_id" value=<%=usuario.getId() %>>
<input type="hidden" name="nota_id" value=<%=nota.getId() %>>
<input type="submit" name="nota">

</form>
</body>
</html>