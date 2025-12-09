<%-- 
    Document   : editarAdministrador
    Created on : 5 dic 2025, 8:10:49 p. m.
    Author     : aquin
--%>

<%@page import="com.mycompany.tpi2025web.model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp" %>
<%@include file="componentes/body1.jsp" %>
<h1>Edición Administrador</h1>
<% Usuario usu = (Usuario) request.getSession().getAttribute("usuarioEditar"); %>
<form class="user" action="SvEditAdministradores" method="POST">
    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                   placeholder="Nombre y Apellido" value="<%=usu.getNombre() %>">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="telefono" name ="telefono"
                placeholder="Teléfono" value="<%=usu.getTelefono() %>">
        </div>
        <div class="col-sm-6 mb-3">
            <input  type="text" class="form-control form-control-user"
                   id="usuario" name="usuario" placeholder="Usuario" value="<%=usu.getNombreUsuario()%>" disabled>
        </div>
        <!--<div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user"
                id="contrasenia" name="contrasenia" placeholder="Contrasenia" value="<%=usu.getNombre() %>">
        </div>-->
    </div>
    
    <button class="btn btn-primary btn-user btn-block" type="submit">
        Registrar
    </button>
</form>
<%@include file="componentes/body2.jsp" %>