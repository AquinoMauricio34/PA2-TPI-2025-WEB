<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Registro de visita de seguimiento</h1>

<form class="user" action="${pageContext.request.contextPath}/privado/SvVisita/crear_visita" method="POST">

    <input type="hidden" name="gatoId" value="${gatoId}">

    <div class="form-group row">
        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <label for="fecha" class="form-label">Fecha</label>
            <input type="text" class="form-control ${errores.fecha != null ? 'is-invalid' : ''}" 
                   id="fecha" name="fecha" 
                   placeholder="dd/mm/yyyy" value="${fecha}">
            <c:if test="${errores.fecha != null}">
                <div class="invalid-feedback">
                    ${errores.fecha}
                </div>
            </c:if>
        </div>


    </div>

    <div class="form-group row">
        <div class="col-12 mb-3">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea class="form-control ${errores.descripcion != null ? 'is-invalid' : ''}" 
                      id="descripcion" name="descripcion"
                      placeholder="Descripción de la visita del seguimiento para el gato"
                      rows="4">${descripcionVisita}</textarea>
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
                Registrar Visita
            </button>
        </div>
    </div>

</form>