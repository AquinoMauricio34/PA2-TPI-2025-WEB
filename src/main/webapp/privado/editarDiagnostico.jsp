<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Ver Diagnóstico</h1>

<form class="user"
      method="POST"
      action="${pageContext.request.contextPath}/privado/SvDiagnostico">


    <input type="hidden" name="gatoId" value="${gatoId}">
    <input type="hidden" name="diagnosticoId" value="${diagnosticoId}">
    <input type="hidden" name="fechaDiagnostico" value="${fechaDiagnostico}">
    <input type="hidden" name="vistaVolver" value="/editarDiagnostico.jsp">

    <div class="form-group row">
        <div class="col-sm-6 mb-3">
            <label class="form-label">
                Título
            </label>
            <input type="text"
                   class="form-control"
                   name="titulo"
                   value="${titulo}"
                   placeholder="Título del diagnóstico"
                   required>
        </div>
    </div>

    <div class="form-group row">
        <div class="col-12 mb-3">
            <label class="form-label">Descripción</label>
            <textarea class="form-control"
                      name="descripcion"
                      rows="4"
                      placeholder="Estado de salud, síntomas, cambios, etc.">${descripcion}</textarea>
        </div>
    </div>


    <div class="container-fluid mb-4">
        <div class="card shadow">

            <div class="card-header py-3 d-flex justify-content-between align-items-center">
                <h4 class="m-0 font-weight-bold text-primary">Tratamientos</h4>

                <button type="submit"
                        class="btn btn-primary"
                        name="accion"
                        value="agregarTratamiento"
                        formaction="${pageContext.request.contextPath}/privado/SvDiagnostico/llamar_altaTratamiento">
                    <i class="fas fa-plus"></i> Añadir tratamiento
                </button>
            </div>

            <div class="card-body">
                <div class="table-responsive">

                    <table class="table table-bordered" width="100%">
                        <thead>
                            <tr>
                                <th>Descripción</th>
                                <th>Fecha Inicio</th>
                                <th>Fecha Fin</th>
                                <th>Abandonado</th>
                                <th style="width: 220px">Acciones</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="t" items="${listaTratamientos}">
                                <tr>
                                    <td>${t.descripcion}</td>
                                    <td>${t.fecha_inicio}</td>
                                    <td>${t.fecha_fin}</td>
                                    <td>${t.abandono_tratamiento ? "Sí" : "No"}</td>

                                    <td style="display:flex; gap:5px">
                                        <!-- ELIMINAR TRATAMIENTO -->
                                        <button type="submit"
                                                class="btn btn-danger btn-user"
                                                name="accion"
                                                value="eliminarTratamiento"
                                                formaction="${pageContext.request.contextPath}/privado/SvDiagnostico/eliminar_tratamiento?tratamientoId=${t.id}&tratamientoDescripcion=${t.getDescripcion().hashCode()}">
                                            <i class="fas fa-trash-alt"></i> Eliminar
                                        </button>

                                        <!-- VER/EDITAR TRATAMIENTO -->
                                        <button type="submit"
                                                class="btn btn-primary btn-user"
                                                name="accion"
                                                value="cargarEditarTrat"
                                                formaction="${pageContext.request.contextPath}/privado/SvTratamiento/cargar_editar?tratamientoId=${t.id}">
                                            <i class="fas fa-pencil-alt"></i> Ver
                                        </button>

                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>

                </div>
            </div>
        </div>
    </div>


    <button type="submit"
            class="btn btn-primary btn-user btn-block"
            name="accion"
            value="editarDiagnostico">
        Guardar cambios
    </button>

</form>
