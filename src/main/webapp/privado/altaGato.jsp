<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Alta de Gato</h1>

<!-- MENSAJE ÉXITO -->
<c:if test="${not empty sessionScope.mensajeExito}">
    <div class="alert alert-success text-center">
        ${sessionScope.mensajeExito}
    </div>
    <c:remove var="mensajeExito" scope="session"/>
</c:if>

<!-- ERROR GENERAL -->
<c:if test="${not empty errores.errorGeneral}">
    <div class="alert alert-danger text-center">
        ${errores.errorGeneral}
    </div>
</c:if>

<form class="user" action="${pageContext.request.contextPath}/privado/SvGato/crear" method="POST">

    <input type="hidden" name="listaZonas" value="${listaZonas}">
    <div class="form-group row">

        <!-- Nombre -->
        <div class="col-sm-6 mb-3">
            <label for="nombre">Nombre (opcional)</label>
            <input type="text"
                   class="form-control"
                   id="nombre"
                   name="nombre"
                   placeholder="Nombre del gato"
                   value="${nombreG}">
        </div>

        <!-- Color -->
        <div class="col-sm-6 mb-3">
            <label for="color">Color</label>
            <input type="text"
                   class="form-control ${errores.colorGato != null ? 'is-invalid' : ''}"
                   id="color"
                   name="color"
                   placeholder="Color del gato"
                   value="${colorG}">
            <c:if test="${errores.colorGato != null}">
                <div class="invalid-feedback">
                    ${errores.colorGato}
                </div>
            </c:if>
        </div>

    </div>

    <div class="form-group row">

        <!-- Zona -->
        <div class="col-sm-6 mb-3">
            <label for="zona">Zona</label>
            <select class="form-control ${errores.zonaIdGato != null ? 'is-invalid' : ''}"
                    id="zona"
                    name="zonaId">
                <option value="">Seleccione una zona</option>

                <c:forEach var="zona" items="${listaZonas}">
                    <option value="${zona.id}"
                            <c:if test="${zona.id == zonaIdG}">
                                selected
                            </c:if>>
                        ${zona.localizacion}
                    </option>
                </c:forEach>
            </select>

            <c:if test="${errores.zonaIdGato != null}">
                <div class="invalid-feedback">
                    ${errores.zonaIdGato}
                </div>
            </c:if>
        </div>

        <!-- Estado de Salud -->
        <div class="col-sm-6 mb-3">
            <label for="estadoSalud">Estado de Salud</label>
            <select class="form-control"
                    id="estadoSalud"
                    name="estadoSalud">

                <option value="SANO"
                        ${estadoSaludG == 'SANO' || empty estadoSaludG ? 'selected' : ''}>
                    SANO
                </option>
                <option value="ENFERMO"
                        ${estadoSaludG == 'ENFERMO' ? 'selected' : ''}>
                    ENFERMO
                </option>
                <option value="EN_TRATAMIENTO"
                        ${estadoSaludG == 'EN_TRATAMIENTO' ? 'selected' : ''}>
                    EN TRATAMIENTO
                </option>
                <option value="ESTERILIZADO"
                        ${estadoSaludG == 'ESTERILIZADO' ? 'selected' : ''}>
                    ESTERILIZADO
                </option>
            </select>
        </div>

    </div>

    <div class="form-group row">
        <div class="col-12 mb-3">
            <label for="caracteristicas">Características</label>
            <textarea class="form-control"
                      id="caracteristicas"
                      name="caracteristicas"
                      rows="4"
                      placeholder="Describa al gato">${caracteristicasG}</textarea>
        </div>
    </div>

    <!-- BOTÓN -->
    <div class="form-group row">
        <div class="col-12">
            <button class="btn btn-primary btn-user btn-block" type="submit">
                Registrar Gato
            </button>
        </div>
    </div>

</form>
