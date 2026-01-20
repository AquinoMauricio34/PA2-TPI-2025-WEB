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

                                    <!-- ELIMINAR -->
                                    <form action="${pageContext.request.contextPath}/SvVisita/mostrar_campos" method="GET">
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

<c:if test="${not empty qrBase64}">
    <div class="modal fade show" id="modalQR" tabindex="-1"
         style="display:block; background: rgba(0,0,0,0.5);">
        
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">

                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">
                        <i class="fas fa-qrcode"></i> Código QR del gato
                    </h5>
                    
                </div>

                <div class="modal-body text-center">
                    <img src="data:image/png;base64,${qrBase64}"
                         alt="QR del gato"
                         style="width: 200px; height: 200px;">
                </div>

                <div class="modal-footer">
                    <button class="btn btn-secondary"
                            onclick="document.getElementById('modalQR').style.display='none'">
                        Cerrar
                    </button>
                </div>

            </div>
        </div>
    </div>
</c:if>
