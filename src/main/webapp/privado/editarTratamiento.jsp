<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Ver Tratamiento</h1>

<form class="user" action="${pageContext.request.contextPath}/privado/SvTratamiento/editar" method="POST">

    <input type="hidden" name="tratamientoId" value="${tratamientoId}">
    <input type="hidden" name="gatoId" value="${gatoId}">
    <input type="hidden" name="titulo" value="${titulo}">
    <input type="hidden" name="descripcion" value="${descripcion}">
    <input type="hidden" name="vistaVolver" value="${vistaVolver}">
    <input type="hidden" name="diagnosticoId" value="${diagnosticoId}">
    <input type="hidden" name="descripcionAux" value="${descripcionAux}">

    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <label for="fechaInicio" class="form-label">Fecha inicio</label>
            <input type="text" class="form-control ${errores.fechaInicio != null ? 'is-invalid' : ''}" id="fechaInicio" name="fechaInicio"
                   placeholder="Fecha de inicio del tratamiento" value="${fechaInicio}">
            <c:if test="${errores.fechaInicio != null}">
                <div class="invalid-feedback">
                    ${errores.fechaInicio}
                </div>
            </c:if>
        </div>
    </div>
    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <label for="fechaFin" class="form-label">Fecha Fin</label>
            <input type="text" class="form-control ${errores.fechaFin != null ? 'is-invalid' : ''}" id="fechaFin" name="fechaFin"
                   placeholder="Fecha de fin del tratamiento" value="${fechaFin}">
            <c:if test="${errores.fechaFin != null}">
                <div class="invalid-feedback">
                    ${errores.fechaFin}
                </div>
            </c:if>
        </div>
    </div>

    <!-- DESCRIPCIÓN -->
    <div class="form-group row">
        <div class="col-12 mb-3">
            <label for="descripcionTratamiento" class="form-label">Descripción del tratamiento</label>
            <textarea class="form-control ${errores.descripcion != null ? 'is-invalid' : ''}"
                      id="descripcionTratamiento" 
                      name="descripcionTratamiento"
                      placeholder="Estado de salud, síntomas, cambios, etc."
                      rows="4">${descripcionTratamiento}</textarea>
            <c:if test="${errores.descripcion != null}">
                <div class="invalid-feedback">
                    ${errores.descripcion}
                </div>
            </c:if>
        </div>
    </div>

    <div class="col-sm-6 mb-3">
        <div class="custom-control custom-checkbox">
            <input type="checkbox" class="custom-control-input" 
                   id="abandono" name="abandono" value="true" 
                   <c:if test="${abandonoTratamiento == true}">
                       checked
                   </c:if>>
            <label class="custom-control-label" for="abandono">
                ¿Abandonado?
            </label>
            <small class="form-text text-muted">
                Marque esta opción si el tratamiento ha sido abandonado
            </small>
        </div>
    </div>



    <!-- BOTÓN REGISTRAR -->
    <button class="btn btn-primary btn-user btn-block" type="submit">
        Guardar cambios
    </button>
</form>
