<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Datos Personales</h1>
<form class="user" action="${pageContext.request.contextPath}/privado/SvUsuario/habilitar_edicion" method="GET">
    <div class="container-fluid">
        <input type="hidden" name="tipo" value="${param.tipo}">
        <div class="row">
            <div class="col-lg-4">
                <div class="mb-3">
                    <label for="nombre">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre"
                           placeholder="Nombre y Apellido" value="${nombreUsuario}" disabled required>
                </div>
                <div class="mb-3">
                    <label for="telefono">Telefono</label>
                    <input type="text" class="form-control" id="telefono" name ="telefono"
                           placeholder="Teléfono" value="${telefonoUsuario}" disabled required>
                </div>
                <div class="mb-3">
                    <label for="usuario">Nombre de Usuario</label>
                    <input type="text" class="form-control"
                           id="usuario" name="usuario" placeholder="Usuario" value="${nombreUsuUsuario}" disabled required>
                </div>
                <div class="mb-3">
                    <label for="contrasenia">Contraseña</label>
                    <input type="text" class="form-control"
                           id="contrasenia" name="contrasenia" placeholder="Contrasenia" value="${contraseniaUsuario}" disabled required>
                </div>
                <button class="btn btn-primary btn-user col-sm-12 mb-3" type="submit">
                    Editar
                </button>
            </div>
            <c:if test="${sessionScope.tipoUsuarioSesion eq 'Hogar' or sessionScope.tipoUsuarioSesion eq 'Familia'}">
                <!-- COLUMNA IZQUIERDA: TABLA -->
                <div class="col-lg-8">
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h3 class="m-0 font-weight-bold text-primary">
                                Mis Gatos
                            </h3>
                        </div>

                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Nombre</th>
                                            <th>Caracteristicas</th>
                                            <th>Estado de Salud</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="u" items="${listaGatos}">
                                            <tr>
                                                <td>${u.id}</td>
                                                <td>${u.nombre}</td>
                                                <td>${u.caracteristicas}</td>
                                                <td>${u.estadoSalud}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

            </c:if>
        </div>


    </div>
</form>