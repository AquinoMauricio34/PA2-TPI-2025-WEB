<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Emision de certificado de aptitud para adopción</h3>
        </div>

        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">

                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Teléfono</th>
                            <th>Usuario</th>
                            <th>¿Apto para adopción?</th>
                            <th style="width: 210px">Acción</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaUsuarios}">
                            <tr>
                                <td>${u.nombre}</td>
                                <td>${u.telefono}</td>
                                <td>${u.nombreUsuario}</td>
                                <c:if test="${u.aptoAdopcion == true}">
                                    <td>Si</td>
                                </c:if>
                                <c:if test="${u.aptoAdopcion == false}">
                                    <td>No</td>
                                </c:if>

                                <td style="display: flex; width: 230px;">

                                    <!-- BOTON DE EMISION DE CERTIFICADO DE APTITUD PARA ADOPCION -->
                                    <form action="${pageContext.request.contextPath}/SvUsuario/cambiar_estado" method="POST">
                                        <input type="hidden" name="usuario" value="${u.nombreUsuario}">
                                        <button type="submit"
                                                class="btn btn-secondary btn-user btn-block"
                                                style="margin-right: 5px;">
                                            <i class="fas fa-trash-alt"></i> Cambiar Estado
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