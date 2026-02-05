<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Registro de tarea realizada</h1>

<form class="user" action="${pageContext.request.contextPath}/privado/SvTarea/crear_tarea" method="POST">

    <div class="form-group row">
        <!-- Fecha -->
        <div class="col-sm-6 mb-3">
            <label for="fecha" class="form-label ${errores.fecha != null ? 'is-invalid' : ''}">Fecha</label>
            <input type="text" class="form-control" 
                   id="fecha" name="fecha" 
                   placeholder="dd/mm/yyyy" value="${fechaTarea}">
            <c:if test="${errores.fecha != null}">
                <div class="invalid-feedback">
                    ${errores.fecha}
                </div>
            </c:if>
        </div>

        <!-- Hora -->
        <div class="col-sm-6 mb-3">
            <label for="hora" class="form-label ${errores.hora != null ? 'is-invalid' : ''}">Hora</label>
            <input type="text" class="form-control" 
                   id="hora" name="hora" 
                   placeholder="hh:mm" value="${horaTarea}">
            <c:if test="${errores.hora != null}">
                <div class="invalid-feedback">
                    ${errores.hora}
                </div>
            </c:if>
        </div>
    </div>

    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <label for="ubicacion" class="form-label ${errores.ubicacion != null ? 'is-invalid' : ''}">Ubicacion</label>
            <input type="text" class="form-control" 
                   id="ubicacion" name="ubicacion" 
                   placeholder="Ubicacion" value="${ubicacionTarea}">
            <c:if test="${errores.ubicacion != null}">
                <div class="invalid-feedback">
                    ${errores.ubicacion}
                </div>
            </c:if>
        </div>
        <!-- Tipo de tarea -->
        <div class="col-sm-6 mb-3">
            <label for="tarea" class="form-label">Tipo de tarea</label>
            <select class="form-control  ${errores.tipoTarea != null ? 'is-invalid' : ''}" 
                    id="tarea" name="tarea">
                <option value="" selected>Seleccione una tarea</option>
                <option value="ALIMENTACION"
                        ${tipoTarea == 'ALIMENTACION' ? 'selected' : ''}>Alimentación</option>
                <option value="CAPTURA_CASTRACION"
                        ${tipoTarea == 'CAPTURA_CASTRACION' ? 'selected' : ''}>Captura para castración</option>
                <option value="CONTROL_VETERINARIO"
                        ${tipoTarea == 'CONTROL_VETERINARIO' ? 'selected' : ''}>Control veterinario</option>
                <option value="TRANSPORTE_HOGAR_TRANSITORIO"
                        ${tipoTarea == 'TRANSPORTE_HOGAR_TRANSITORIO' ? 'selected' : ''}>Transporte a hogar transitorio</option>
            </select>
            <c:if test="${errores.tipoTarea != null}">
                <div class="invalid-feedback">
                    ${errores.tipoTarea}
                </div>
            </c:if>
        </div>
    </div>



    <!-- Botón de envío -->
    <div class="form-group row">
        <div class="col-12">
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Registrar Tarea
            </button>
        </div>
    </div>

</form>