<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid">

    <div class="row">

        <!-- COLUMNA IZQUIERDA: TABLA -->
        <div class="col-lg-8">
            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h3 class="m-0 font-weight-bold text-primary">
                        Gatos por zona
                    </h3>
                </div>

                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" width="100%" cellspacing="0">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre Zona</th>
                                    <th>Cantidad de gatos</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="u" items="${listaZonas}">
                                    <tr>
                                        <td>${u[0]}</td>
                                        <td>${u[1]}</td>
                                        <td>${u[2]}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <!-- COLUMNA DERECHA: TARJETAS -->
        <div class="col-lg-4">

            <!-- Esterilizados -->
            <div class="card shadow mb-4 border-left-success">
                <div class="card-body text-center">
                    <h5 class="font-weight-bold text-success">
                        Gatos esterilizados
                    </h5>
                    <h2>${gatosEsterilizados}</h2>
                </div>
            </div>

            <!-- Adoptados -->
            <div class="card shadow mb-4 border-left-info">
                <div class="card-body text-center">
                    <h5 class="font-weight-bold text-info">
                        Gatos adoptados
                    </h5>
                    <h2>${gatosAdoptados}</h2>
                </div>
            </div>

        </div>

    </div>
</div>
