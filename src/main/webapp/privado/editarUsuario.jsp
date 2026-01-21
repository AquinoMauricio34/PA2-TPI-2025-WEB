<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h1>Edición ${tipo}</h1>

<c:set var="usu" value="${usuarioEditar}" />

<form class="user" action="${pageContext.request.contextPath}/privado/SvUsuario/editar" method="POST">
    <input type="hidden" name="usuarioOriginal"
           value="${usu.nombreUsuario}">
    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="nombre" name="nombre"
                   placeholder="Nombre y Apellido" value="${usu.nombre}">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user" id="telefono" name="telefono"
                   placeholder="Teléfono" value="${usu.telefono}">
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control form-control-user"
                   id="usuario" name="usuario" placeholder="Usuario" value="${usu.nombreUsuario}" disabled>
            <!-- Campo oculto para enviar el usuario (ya que el campo visible está disabled) -->
            <input type="hidden" name="nombreUsuario" value="${usu.nombreUsuario}">
        </div>
        <!-- Opcional: Si necesitas mostrar o editar la contraseña
        <div class="col-sm-6 mb-3">
            <input type="password" class="form-control form-control-user"
                id="contrasenia" name="contrasenia" placeholder="Contraseña">
        </div>
        -->
    </div>
    
    <button class="btn btn-primary btn-user btn-block" type="submit">
        Actualizar
    </button>
</form>