<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1>Alta ${param.tipo}</h1>
<form class="user" action="${pageContext.request.contextPath}/SvUsuario/crear" method="POST">
    <input type="hidden" name="tipo" value="${param.tipo}">
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
    
    <button class="btn btn-primary btn-user btn-block" type="submit">
        Registrar
    </button>
</form>