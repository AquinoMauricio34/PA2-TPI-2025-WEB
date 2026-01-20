<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Gato</h3>
        </div>

        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                    
                    <thead>
                        <tr>
                            <th>ID</th>
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
                                <td>${u.id}</td>
                                <td>${u.nombre}</td>
                                <td>${u.color}</td>
                                <td>${u.caracteristicas}</td>
                                <td>${u.estadoSalud}</td>
                                <td style="display: flex; width: 230px;">

                                    <!-- SELECCIONAR -->
                                    <form action="${pageContext.request.contextPath}/SvEstudio/mostrar_campos" method="GET">
                                        <input type="hidden" name="gato" value="${u.id}">
                                        <button type="submit"
                                            class="btn btn-primary btn-user btn-block"
                                            style="margin-right: 5px;">
                                            <i class="fas fa-trash-alt"></i> Seleccionar
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