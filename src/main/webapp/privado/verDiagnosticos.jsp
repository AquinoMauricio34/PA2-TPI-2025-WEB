<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <input type="hidden" name="gatoId" value="${gato}">
        
        <div class="card-header py-3 d-flex justify-content-between align-items-center">
            <h3 class="m-0 font-weight-bold text-primary">Diagnósticos</h3>
            <input type="hidden" name="gatoId" value="${param.gato}">
            <input type="hidden" name="diagnostico" value="${diagnostico}">
            <div>
                <a href="${pageContext.request.contextPath}/privado/SvEstudio/mostrar_estudios_gato?gato=${param.gato}"
                   class="btn btn-primary">
                    <i class="fas fa-book-medical"></i> Estudios
                </a>
                <!-- BOTÓN: Crear Diagnóstico -->
                <a href="${pageContext.request.contextPath}/privado/SvDiagnostico/llamar_mostrarDiagnostico?gato=${param.gato}"
                   class="btn btn-primary">
                    <i class="fas fa-plus"></i> Crear diagnóstico
                </a>
            </div>
        </div>


        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Diagnostico</th>
                            <th>Descripcion</th>
                            <th>Fecha Diagnostico</th>
                            <th style="width: 210px">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaDiagnosticos}">
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.diagnostico}</td>
                                <td>${u.descripcion}</td>
                                <td>${u.fecha_diagnostico}</td>
                                <td style="display: flex; width: 230px;">

                                    <!-- ELIMINAR -->
                                    <form action="${pageContext.request.contextPath}/privado/SvDiagnostico/eliminar" method="POST">
                                        <input type="hidden" name="gatoId" value="${gato}">
                                        <input type="hidden" name="diagnostico" value="${u.id}">
                                        <button type="submit"
                                            class="btn btn-danger btn-user btn-block"
                                            style="margin-right: 5px;">
                                            <i class="fas fa-trash-alt"></i> Eliminar
                                        </button>
                                    </form>

                                    <!-- EDITAR -->
                                    <form action="${pageContext.request.contextPath}/privado/SvDiagnostico/cargar_editar" method="GET"
                                        style="margin-left: 5px;">
                                        <input type="hidden" name="gatoId" value="${gato}">
                                        <input type="hidden" name="diagnostico" value="${u.id}">
                                        <button type="submit"
                                            class="btn btn-primary btn-user btn-block">
                                            <i class="fas fa-pencil-alt"></i> Ver
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