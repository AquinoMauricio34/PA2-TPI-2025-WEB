<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid">
    <div class="card shadow mb-4">

        <div class="card-header py-3">
            <h3 class="m-0 font-weight-bold text-primary">Gatos</h3>
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
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Color</th>
                            <th>Caracteristicas</th>
                            <th>Estado de Salud</th>
                            <th>¿Adoptado?</th>
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
                                <td>${u.usuario == null ? 'No' : 'Sí'}</td>
                                <td style="width: 230px;">
                                    <div style="display: flex; gap: 5px;">
                                        <c:if test="${sessionScope.tipoUsuarioSesion eq 'Administrador'}">
                                            <!-- ELIMINAR -->
                                            <form action="${pageContext.request.contextPath}/privado/SvGato/eliminar" method="POST">
                                                <input type="hidden" name="gato" value="${u.id}">
                                                <button type="submit"
                                                        class="btn btn-danger btn-user btn-block"
                                                        style="margin-right: 5px;">
                                                    <i class="fas fa-trash-alt"></i> Eliminar
                                                </button>
                                            </form>
                                        </c:if>

                                        <!-- EDITAR -->
                                        <form action="${pageContext.request.contextPath}/privado/SvGato/cargar_editar" method="GET"
                                              style="margin-left: 5px;">
                                            <input type="hidden" name="gato" value="${u.id}">
                                            <button type="submit"
                                                    class="btn btn-primary btn-user btn-block">
                                                <i class="fas fa-pencil-alt"></i> Editar
                                            </button>
                                        </form>
                                        <!-- VER QR -->
                                        <form action="${pageContext.request.contextPath}/privado/SvGato/ver_qr" method="GET"
                                              style="margin-left: 5px;">
                                            <input type="hidden" name="gatoToString" value="${u.toString()}">
                                            <button type="submit"
                                                    class="btn btn-secondary btn-user btn-block">
                                                <i class="fas fa-qrcode"></i> QR
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
                            onclick="document.getElementById('modalQR').style.display = 'none'">
                        Cerrar
                    </button>
                </div>

            </div>
        </div>
    </div>
</c:if>
