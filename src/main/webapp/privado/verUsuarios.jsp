<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">${titulo}</h3>
        </div>

        <!-- MENSAJE ÉXITO -->
        <c:if test="${not empty sessionScope.mensajeExito}">
            <div class="alert alert-success text-center">
                ${sessionScope.mensajeExito}
            </div>
            <c:remove var="mensajeExito" scope="session"/>
        </c:if>

        <!-- ERROR GENERAL -->
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
                            <th>Nombre</th>
                            <th>Teléfono</th>
                            <th>Usuario</th>
                                <c:if test="${titulo eq 'Hogar'}">
                                <th>¿Es Transitorio?</th>
                                </c:if>
                            <th style="width: 210px">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaUsuarios}">
                            <tr>
                                <td>${u.nombre}</td>
                                <td>${u.telefono}</td>
                                <td>${u.nombreUsuario}</td>
                                <c:if test="${titulo eq 'Hogar'}">
                                    <c:if test="${u.transitorio == true}">
                                        <td>Si</td>
                                    </c:if>
                                    <c:if test="${u.transitorio == false}">
                                        <td>No</td>
                                    </c:if>
                                </c:if>

                                <td style="width: 230px;">
                                    <div style="display: flex; gap: 5px;">
                                        <!-- ELIMINAR -->
                                        <form action="${pageContext.request.contextPath}/privado/SvUsuario/eliminar" method="POST">
                                            <input type="hidden" name="usuario" value="${u.nombreUsuario}">
                                            <input type="hidden" name="tipo" value="${String.valueOf(u.getClass().getSimpleName())}">
                                            <button type="submit"
                                                    class="btn btn-danger btn-user btn-block"
                                                    style="margin-right: 5px;">
                                                <i class="fas fa-trash-alt"></i> Eliminar
                                            </button>
                                        </form>

                                        <!-- EDITAR -->
                                        <form action="${pageContext.request.contextPath}/privado/SvUsuario/cargar_editar" method="GET"
                                              style="margin-left: 5px;">
                                            <input type="hidden" name="usuario" value="${u.nombreUsuario}">
                                            <button type="submit"
                                                    class="btn btn-primary btn-user btn-block">
                                                <i class="fas fa-pencil-alt"></i> Editar
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