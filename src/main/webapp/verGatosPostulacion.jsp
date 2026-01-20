<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Gatos para postularse</h3>
            <c:if test="${not empty error}">
                <br>
                <h5 class="m-0 font-weight-bold text-danger">${error}</h5>
            </c:if>
        </div>

        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">

                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Color</th>
                            <th>Caracteristicas</th>
                            <th>Estado de Salud</th>
                            <th style="width: 210px">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaGatos}">
                            <tr>
                                <td>${u.nombre}</td>
                                <td>${u.color}</td>
                                <td>${u.caracteristicas}</td>
                                <td>${u.estadoSalud}</td>
                                <td style="display: flex; width: 230px;">

                                    <!-- POSTULARSE -->
                                    <form action="${pageContext.request.contextPath}/SvPostulacion/postularse" method="POST">
                                        <input type="hidden" name="gato" value="${u.id}">
                                        <button type="submit"
                                                class="btn btn-user btn-block
                                                ${error != null ? 'disabled' : 'btn-primary'}"
                                                style="margin-right: 5px;"
                                                ${error != null ? 'disabled' : ''}>
                                            <i class="fas fa-hand"></i> Postularse
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