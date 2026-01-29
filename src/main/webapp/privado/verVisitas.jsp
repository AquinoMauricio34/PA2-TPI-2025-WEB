<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Visitas de seguimiento</h3>
        </div>

        <!-- MENSAJE ÉXITO -->
        <c:if test="${not empty sessionScope.mensajeExito}">
            <div class="alert alert-success text-center">
                ${sessionScope.mensajeExito}
            </div>
            <c:remove var="mensajeExito" scope="session"/>
        </c:if>

        <!-- MENSAJE FALLO -->
        <c:if test="${not empty sessionScope.mensajeFallo}">
            <div class="alert alert-danger text-center">
                ${sessionScope.mensajeFallo}
            </div>
            <c:remove var="mensajeFallo" scope="session"/>
        </c:if>

        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">

                    <thead>
                        <tr>
                            <th>ID Visita</th>
                            <th>Voluntario</th>
                            <th>ID Gato</th>
                            <th>Fecha</th>
                            <th>Descripcion</th>
                                <c:if test="${sessionScope.tipoUsuarioSesion eq 'Administrador'}">
                                <th style="width: 210px">Acciones</th>
                                </c:if>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaVisitas}">
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.getIdVoluntario()}</td>
                                <td>${u.idGato}</td>
                                <td>${u.fecha}</td>
                                <td>${u.descripcion}</td>
                                <c:if test="${sessionScope.tipoUsuarioSesion eq 'Administrador'}">
                                    <td style="width: 230px;">
                                        <div style="display: flex; gap: 5px;">
                                            <!-- ELIMINAR -->
                                            <form action="${pageContext.request.contextPath}/privado/SvVisita/eliminar_visita" method="POST">
                                                <input type="hidden" name="visitaId" value="${u.id}">
                                                <button type="submit"
                                                        class="btn btn-danger btn-user btn-block"
                                                        style="margin-right: 5px;">
                                                    <i class="fas fa-trash-alt"></i> Eliminar
                                                </button>
                                            </form>

                                        </div>


                                    </td>

                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>

            </div>
        </div>
    </div>
</div>