<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Registrar Estudio</h1>

<form class="user" action="${pageContext.request.contextPath}/privado/SvEstudio/crear_estudio" method="POST">

    <input type="hidden" name="gatoId" value="${gatoId}">

    <div class="form-group row">
        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <label for="titulo" class="form-label">Titulo</label>
            <input type="text" class="form-control ${errores.titulo != null ? 'is-invalid' : ''}" 
                   id="titulo" name="titulo" 
                   placeholder="Titulo del estudio" value = "${titulo}">
            <c:if test="${errores.titulo != null}">
                <div class="invalid-feedback">
                    ${errores.titulo}
                </div>
            </c:if>
        </div>


    </div>

    <div class="form-group row">
        <div class="col-12 mb-3">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea class="form-control ${errores.descripcion != null ? 'is-invalid' : ''}" 
                      id="descripcion" name="descripcion"
                      placeholder="Descripción del estudio"
                      rows="4">${descripcionEstudio}</textarea>
            <c:if test="${errores.descripcion != null}">
                <div class="invalid-feedback">
                    ${errores.descripcion}
                </div>
            </c:if>
        </div>
    </div>


    <!-- Botón de envío -->
    <div class="form-group row">
        <div class="col-12">
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Registrar Estudio
            </button>
        </div>
    </div>

</form>