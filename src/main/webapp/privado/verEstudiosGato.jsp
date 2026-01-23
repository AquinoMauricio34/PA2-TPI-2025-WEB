<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Estudios del Gato</h3>
        </div>

        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">

                    <thead>
                        <tr>
                            <th>ID Estudio</th>
                            <th>Titulo</th>
                            <th>Descripción</th>
                            <th style="width: 210px">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaEstudios}">
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.titulo}</td>
                                <td>${u.descripcion}</td>
                                <td style="display: flex; width: 230px;">

                                    <!-- ELIMINAR -->
                                    <form action="${pageContext.request.contextPath}/privado/SvEstudio/eliminar_estudio" method="POST">
                                        <input type="hidden" name="estudioId" value="${u.id}">
                                        <button type="submit"
                                                class="btn btn-danger btn-user btn-block"
                                                style="margin-right: 5px;">
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