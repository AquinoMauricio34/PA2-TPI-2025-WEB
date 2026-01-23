<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Datos Personales</h1>
<form class="user" action="${pageContext.request.contextPath}/privado/SvUsuario/habilitar_edicion" method="GET">
    <input type="hidden" name="tipo" value="${param.tipo}">

    <div class="col-sm-6 mb-3">
        <label for="nombre">Nombre</label>
        <input type="text" class="form-control" id="nombre" name="nombre"
               placeholder="Nombre y Apellido" value="${nombreUsuario}" disabled required>
    </div>
    <div class="col-sm-6 mb-3">
        <label for="telefono">Telefono</label>
        <input type="text" class="form-control" id="telefono" name ="telefono"
               placeholder="Teléfono" value="${telefonoUsuario}" disabled required>
    </div>
    <div class="col-sm-6 mb-3">
        <label for="usuario">Nombre de Usuario</label>
        <input type="text" class="form-control"
               id="usuario" name="usuario" placeholder="Usuario" value="${nombreUsuUsuario}" disabled required>
    </div>
    <div class="col-sm-6 mb-3">
        <label for="contrasenia">Contraseña</label>
        <input type="text" class="form-control"
               id="contrasenia" name="contrasenia" placeholder="Contrasenia" value="${contraseniaUsuario}" disabled required>
    </div>

    <button class="btn btn-primary btn-user col-sm-6 mb-3" type="submit">
        Editar
    </button>
</form>