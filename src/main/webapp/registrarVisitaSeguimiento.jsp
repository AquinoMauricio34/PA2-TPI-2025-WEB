<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Alta de Gato</h1>

<form class="user" action="${pageContext.request.contextPath}/SvVisita/crear_visita" method="POST">

    <input type="hidden" name="gatoId" value="${gatoId}">
    
    <div class="form-group row">
        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <label for="fecha" class="form-label">Fecha</label>
            <input type="text" class="form-control" 
                   id="fecha" name="fecha" 
                   placeholder="dd/mm/yyyy"
                   required>
        </div>


    </div>

    <div class="form-group row">
        <div class="col-12 mb-3">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea class="form-control" 
                      id="descripcion" name="descripcion"
                      placeholder="Descripción de la visita del seguimiento para el gato"
                      rows="4" required></textarea>
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