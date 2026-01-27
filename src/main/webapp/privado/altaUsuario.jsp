<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Alta ${param.tipo}</h1>

<!-- Error general -->
<c:if test="${errores.general != null}">
    <div class="alert alert-danger">
        ${errores.general}
    </div>
</c:if>

<form class="user"
      action="${pageContext.request.contextPath}/privado/SvUsuario/crear"
      method="POST">

    <input type="hidden" name="tipo" value="${param.tipo}">

    <div class="form-group row">

        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <input type="text"
                   class="form-control ${errores.nombre != null ? 'is-invalid' : ''}"
                   id="nombre" name="nombre"
                   placeholder="Nombre"
                   value="${nombre}">
            <c:if test="${errores.nombre != null}">
                <div class="invalid-feedback">
                    ${errores.nombre}
                </div>
            </c:if>
        </div>

        <!-- Teléfono -->
        <div class="col-sm-6 mb-3">
            <input type="text"
                   class="form-control ${errores.telefono != null ? 'is-invalid' : ''}"
                   id="telefono" name="telefono"
                   placeholder="Teléfono"
                   value="${telefono}">
            <c:if test="${errores.telefono != null}">
                <div class="invalid-feedback">
                    ${errores.telefono}
                </div>
            </c:if>
        </div>

        <!-- Usuario -->
        <div class="col-sm-6 mb-3">
            <input type="text"
                   class="form-control ${errores.usuario != null ? 'is-invalid' : ''}"
                   id="usuarioCrear" name="usuarioCrear"
                   placeholder="Usuario"
                   value="${usuarioCrear}">
            <c:if test="${errores.usuario != null}">
                <div class="invalid-feedback">
                    ${errores.usuario}
                </div>
            </c:if>
        </div>

        <!-- Contraseña -->
        <div class="col-sm-6 mb-3">
            <input type="password"
                   class="form-control ${errores.contrasenia != null ? 'is-invalid' : ''}"
                   id="contrasenia" name="contrasenia"
                   placeholder="Contraseña">
            <c:if test="${errores.contrasenia != null}">
                <div class="invalid-feedback">
                    ${errores.contrasenia}
                </div>
            </c:if>
        </div>

        <!-- Hogar -->
        <c:if test="${param.tipo eq 'Hogar'}">
            <div class="col-sm-6 mb-3">
                <div class="custom-control custom-checkbox">
                    <input type="checkbox"
                           class="custom-control-input"
                           id="isTransitorio"
                           name="isTransitorio"
                           ${param.isTransitorio != null ? 'checked' : ''}>
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
