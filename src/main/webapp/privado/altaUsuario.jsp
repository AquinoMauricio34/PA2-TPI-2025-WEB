<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Alta ${param.tipo}</h1>
<form class="user" action="${pageContext.request.contextPath}/privado/SvUsuario/crear" method="POST">
    <input type="hidden" name="tipo" value="${param.tipo}">
    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control" id="nombre" name="nombre"
                   placeholder="Nombre y Apellido" required>
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control" id="telefono" name ="telefono"
                placeholder="Teléfono" required>
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control"
                   id="usuario" name="usuario" placeholder="Usuario" required>
        </div>
        <div class="col-sm-6 mb-3">
            <input type="text" class="form-control"
                id="contrasenia" name="contrasenia" placeholder="Contrasenia" required>
        </div>
        <c:if test="${param.tipo eq 'Hogar'}">
            <div class="col-sm-6 mb-3">
                <div class="custom-control custom-checkbox">
                    <input type="checkbox" class="custom-control-input" 
                           id="isTransitorio" name="isTransitorio" value="true">
                    <label class="custom-control-label" for="isTransitorio">
                        ¿Es Transitorio?
                    </label>
                    <small class="form-text text-muted">
                        Marque esta opción si el usuario es de carácter transitorio
                    </small>
                </div>
            </div>
        </c:if>
    </div>
    
    <button class="btn btn-primary btn-user btn-block" type="submit">
        Registrar
    </button>
</form>