<%-- 
    Document   : altas.jsp
    Created on : 5 dic 2025, 7:15:21 a. m.
    Author     : aquin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp" %>
<%@include file="componentes/body1.jsp" %>
<h1>Alta Administrador</h1>
<form class="user" action="SvAdministrador" method="POST">
    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                placeholder="Nombre y Apellido">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="telefono" name ="telefono"
                placeholder="Teléfono">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user"
                   id="usuario" name="usuario" placeholder="Usuario">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user"
                id="contrasenia" name="contrasenia" placeholder="Contrasenia">
        </div>
    </div>
    
    <button href="SvAdministrador" class="btn btn-primary btn-user btn-block" type="submit">
        Registrar
    </button>
</form>
<%@include file="componentes/body2.jsp" %>