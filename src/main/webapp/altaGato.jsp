<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Alta de Gato</h1>

<form class="user" action="${pageContext.request.contextPath}/SvGato/crear" method="POST">
    
    <!-- Primera fila: Nombre y Color -->
    <div class="form-group row">
        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <label for="nombre" class="form-label">Nombre (opcional)</label>
            <input type="text" class="form-control" 
                   id="nombre" name="nombre" 
                   placeholder="Nombre del gato">
        </div>
        
        <!-- Color -->
        <div class="col-sm-6 mb-3">
            <label for="color" class="form-label">Color</label>
            <input type="text" class="form-control" 
                   id="color" name="color" 
                   placeholder="Color del gato" 
                   required>
        </div>
    </div>
    
    <!-- Segunda fila: Zona y Estado de Salud -->
    <div class="form-group row">
        <!-- Zona -->
        <div class="col-sm-6 mb-3">
            <label for="zona" class="form-label">Zona</label>
            <select class="form-control" 
                    id="zona" name="zonaId" 
                    required>
                <option value="" selected disabled>Seleccione una zona</option>
                <c:forEach var="zona" items="${listaZonas}">
                    <option value="${zona.id}">${zona.localizacion}</option>
                </c:forEach>
            </select>
        </div>
        
        <!-- Estado de Salud -->
        <div class="col-sm-6 mb-3">
            <label for="estadoSalud" class="form-label">Estado de Salud</label>
            <select class="form-control" 
                    id="estadoSalud" name="estadoSalud" 
                    required>
                
                <option value="ENFERMO">ENFERMO</option>
                <option value="SANO" selected>SANO</option>
                <option value="EN_TRATAMIENTO">EN TRATAMIENTO</option>
                <option value="ESTERILIZADO">ESTERILIZADO</option>
            </select>
        </div>
    </div>
    
    <!-- Tercera fila: Características (toda la fila) -->
    <div class="form-group row">
        <div class="col-12 mb-3">
            <label for="caracteristicas" class="form-label">Características</label>
            <textarea class="form-control" 
                      id="caracteristicas" name="caracteristicas"
                      placeholder="Describa al gato (edad, tamaño, temperamento, condiciones especiales, etc.)"
                      rows="4"></textarea>
        </div>
    </div>
    
    <!-- Botón de envío -->
    <div class="form-group row">
        <div class="col-12">
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Registrar Gato
            </button>
        </div>
    </div>
    
</form>