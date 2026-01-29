<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<h1>Edición Gato</h1>

<c:set var="g" value="${gatoEditar}" />

<form class="user" action="${pageContext.request.contextPath}/privado/SvGato/editar" method="POST">
    <input type="hidden" name="idGatoOriginal"
           value="${g.id}">
    
    <!-- Primera fila: Nombre y Color -->
    <div class="form-group row">
        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <label for="nombre" class="form-label">Nombre (opcional)</label>
            <input type="text" class="form-control" 
                   id="nombre" name="nombre" 
                   placeholder="Nombre del gato" value="${g.nombre}" ${sessionScope.tipoUsuarioSesion eq 'Administrador' ? '' : 'readonly'}>
        </div>
        
        <!-- Color -->
        <div class="col-sm-6 mb-3">
            <label for="color" class="form-label">Color</label>
            <input type="text" class="form-control ${errorColorGato != null ? 'is-invalid' : ''}" 
                   id="color" name="color" 
                   placeholder="Color del gato" value="${g.color}"
                   ${sessionScope.tipoUsuarioSesion eq 'Administrador' ? '' : 'readonly'}>
            <c:if test="${errorColorGato != null}">
                <div class="invalid-feedback">
                    ${errorColorGato}
                </div>
            </c:if>
        </div>
    </div>
    
    <!-- Segunda fila: Zona y Estado de Salud -->
    <div class="form-group row">
        <!-- Zona -->
        <c:if test="${sessionScope.tipoUsuarioSesion ne 'Administrador'}">
            <input type="hidden" name="zonaId" value="${g.zona.id}">
        </c:if>
        <div class="col-sm-6 mb-3">
            <label for="zona" class="form-label">Zona</label>
            <select class="form-control" 
                    id="zona" name="zonaId" 
                    required ${sessionScope.tipoUsuarioSesion eq 'Administrador' ? '' : 'disabled'}>
                <!--<option value="" selected disabled>Seleccione una zona</option>-->
                <c:forEach var="zona" items="${listaZonas}">
                    
                    <option value="${zona.id}" <c:if test="${g != null && g.zona != null && g.zona.id == zona.id}">selected</c:if>>${zona.localizacion}</option>
                </c:forEach>
            </select>
        </div>
        
        <!-- Estado de Salud -->
        <div class="col-sm-6 mb-3">
            <label for="estadoSalud" class="form-label">Estado de Salud</label>
            <select class="form-control" 
                    id="estadoSalud" name="estadoSalud" 
                    required>
                
                <option value="ENFERMO" 
                    ${g.estadoSalud eq 'ENFERMO' ? 'selected' : ''}>
                    ENFERMO
                </option>
                <option value="SANO" 
                    ${g.estadoSalud eq 'SANO' ? 'selected' : ''}>
                    SANO
                </option>
                <option value="EN_TRATAMIENTO" 
                    ${g.estadoSalud eq 'EN_TRATAMIENTO' ? 'selected' : ''}>
                    EN TRATAMIENTO
                </option>
                <option value="ESTERILIZADO" 
                    ${g.estadoSalud eq 'ESTERILIZADO' ? 'selected' : ''}>
                    ESTERILIZADO
                </option>
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
                      rows="4" ${sessionScope.tipoUsuarioSesion eq 'Administrador' ? '' : 'readonly'}>${g.caracteristicas}</textarea>
        </div>
    </div>
    
    <!-- Botón de envío -->
    <div class="form-group row">
        <div class="col-12">
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Actualizar
            </button>
        </div>
    </div>
</form>