<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Alta Diagnóstico</h1>

<form class="user" action="${pageContext.request.contextPath}/privado/SvDiagnostico/crear?gatoId=${gatoId}" method="POST">

    <input type="hidden" name="gatoId" value="${gatoId}">
    <input type="hidden" name="diagnostico" value="${diagnostico}">
    <input type="hidden" name="vistaVolver" value="/privado/altaDiagnostico.jsp">
    <!-- <input type="hidden" name="accion" value="${accion}"> -->

    <!-- TÍTULO -->
    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <label for="titulo" class="form-label">Título</label>
            <input type="text" class="form-control ${errores.titulo != null ? 'is-invalid' : ''}" id="titulo" name="titulo"
                   placeholder="Título del diagnóstico"
                   value="${titulo}">
            <c:if test="${errores.titulo != null}">
                <div class="invalid-feedback">
                    ${errores.titulo}
                </div>
            </c:if>
        </div>
    </div>

    <!-- DESCRIPCIÓN -->
    <div class="form-group row">
        <div class="col-12 mb-3">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea class="form-control ${errores.descripcion != null ? 'is-invalid' : ''}"
                      id="descripcion" 
                      name="descripcion"
                      placeholder="Estado de salud, síntomas, cambios, etc."
                      rows="4">${descripcion}</textarea>
            <c:if test="${errores.descripcion != null}">
                <div class="invalid-feedback">
                    ${errores.descripcion}
                </div>
            </c:if>
        </div>
    </div>


    <!-- Tabla de tratamientos -->
    <div class="container-fluid mb-4">
        <div class="card shadow">

            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                <h4 class="m-0 font-weight-bold text-primary">Tratamientos</h4>

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

                <!-- Botón para agregar tratamiento -->
                <button href="${pageContext.request.contextPath}/privado/SvDiagnostico/llamar_altaTratamiento?gatoId=${gatoId}"
                        class="btn btn-primary" type="submit" name="accion" value="agregarTratamiento">
                    <i class="fas fa-plus"></i> Aniadir tratamiento
                </button>
            </div>

            <div class="card-body">
                <div class="table-responsive">

                    <table class="table table-bordered" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Descripcion</th>
                                <th>Fecha Inicio</th>
                                <th>Fecha Fin</th>
                                <th>¿Abandono tratamiento?</th>
                                <th style="width: 210px">Accion</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="t" items="${listaTratamientos}">
                                <tr>
                                    <td>${t.descripcion}</td>
                                    <td>${t.fecha_inicio}</td>
                                    <td>${t.fecha_fin}</td>
                                    <td>${t.abandono_tratamiento == false ? 'No' : 'Sí'}</td>
                                    <td style="display: flex; width: 230px;">

                                        <!-- ELIMINAR -->
                                        <form action="${pageContext.request.contextPath}/privado/SvDiagnostico/eliminar_tratamiento" method="POST">
                                            <input type="hidden" name="tratamientoId" value="${t.id}">
                                            <input type="hidden" name="tratamientoDescripcion" value="${t.getDescripcion().hashCode()}">
                                            <button type="submit"
                                                    class="btn btn-danger btn-block"
                                                    style="margin-right: 5px;" name="accion" value="eliminarTratamiento">
                                                <i class="fas fa-trash-alt"></i> Eliminar
                                            </button>
                                        </form>

                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>

                </div>
            </div>
        </div>
    </div>


    <!-- BOTÓN REGISTRAR -->
    <button class="btn btn-primary btn-user btn-block" type="submit" name="accion" value="crearDiagnostico">
        Registrar
    </button>
</form>
