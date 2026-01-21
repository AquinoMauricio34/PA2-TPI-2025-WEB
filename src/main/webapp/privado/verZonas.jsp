<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">
        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Zonas</h3>
        </div>
        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">

                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre Zona</th>
                            <th>Latitud y Longitud</th>
                            <th style="width: 210px">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaZonas}">
                            <tr>
                                <td>${u[0]}</td>
                                <td>${u[1]}</td>
                                <td>${u[2]}</td>

                                <td>

                                    <div class="d-flex gap-123">

                                        <!-- ELIMINAR -->
                                        <form action="${pageContext.request.contextPath}/privado/SvZona/eliminar"
                                              method="POST">
                                            <input type="hidden" name="idZona" value="${u[0]}">

                                            <button type="submit"
                                                    class="btn btn-danger btn-user">
                                                <i class="fas fa-trash-alt"></i> Eliminar
                                            </button>
                                        </form>

                                        <!-- VER EN MAPA -->
                                        <form action="${pageContext.request.contextPath}/privado/SvZona/cargar_aniadir"
                                              method="GET">
                                            <input type="hidden" name="zonaFocusId" value="${u[0]}">

                                            <button type="submit"
                                                    class="btn btn-secondary btn-user">
                                                <i class="fas fa-map-marker-alt"></i> Ver en Mapa
                                            </button>
                                        </form>

                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </div>
        </div>
    </div>
</div>

<c:if test="${not empty error}">
    <div class="modal fade show" id="modalError" tabindex="-1"
         style="display:block; background: rgba(0,0,0,0.5);">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">

                <div class="modal-header bg-danger text-white">
                    <h5 class="modal-title">Error</h5>

                </div>

                <div class="modal-body">
                    <p>${error}</p>
                </div>

                <div class="modal-footer">
                    <button class="btn btn-secondary"
                            onclick="document.getElementById('modalError').style.display = 'none'">
                        Cerrar
                    </button>
                </div>

            </div>
        </div>
    </div>
</c:if>