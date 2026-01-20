<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Alta de Gato</h1>

<form class="user" action="${pageContext.request.contextPath}/SvTarea/crear_tarea" method="POST">

    <!-- Primera fila: Nombre y Color -->
    <div class="form-group row">
        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <label for="fecha" class="form-label">Fecha</label>
            <input type="text" class="form-control" 
                   id="fecha" name="fecha" 
                   placeholder="dd/mm/yyyy"
                   required>
        </div>

        <!-- Color -->
        <div class="col-sm-6 mb-3">
            <label for="hora" class="form-label">Hora</label>
            <input type="text" class="form-control" 
                   id="hora" name="hora" 
                   placeholder="hh:mm" 
                   required>
        </div>
    </div>

    <!-- Segunda fila: Zona y Estado de Salud -->
    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <label for="ubicacion" class="form-label">Ubicacion</label>
            <input type="text" class="form-control" 
                   id="ubicacion" name="ubicacion" 
                   placeholder="Ubicacion" 
                   required>
        </div>
        <!-- Estado de Salud -->
        <div class="col-sm-6 mb-3">
            <label for="tarea" class="form-label">Tipo de tarea</label>
            <select class="form-control" 
                    id="tarea" name="tarea" 
                    required>
                <option value="" selected disabled>Seleccione una tarea</option>
                <option value="ALIMENTACION">Alimentación</option>
                <option value="CAPTURA_CASTRACION">Captura para castración</option>
                <option value="CONTROL_VETERINARIO">Control veterinario</option>
                <option value="TRANSPORTE_HOGAR_TRANSITORIO">Transporte a hogar transitorio</option>
            </select>
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