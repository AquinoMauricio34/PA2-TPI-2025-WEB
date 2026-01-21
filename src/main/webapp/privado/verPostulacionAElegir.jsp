<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%System.out.println("en el jsppppppppppppp");%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Postulaciones</h3>
        </div>

        <div class="card-body">
            <div class="table-responsive">

                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">

                    <thead>
                        <tr>
                            <th>ID Postulación</th>
                            <th>ID Gato</th>
                            <th style="width: 210px">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="u" items="${listaPostulaciones}">
                            <tr>
                                <td>${u.id}</td>
                                <td>${u.idGato}</td>
                                <td style="display: flex; width: 230px;">

                                    <!-- Adoptar -->
                                    <form action="${pageContext.request.contextPath}/privado/SvGato/adoptar_gato" method="POST">
                                        <input type="hidden" name="postulacionId" value="${u.id}">
                                        <button type="submit"
                                                class="btn btn-info btn-user btn-block"
                                                style="margin-right: 5px;">
                                            <i class="fas fa-trash-alt"></i> Adoptar
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