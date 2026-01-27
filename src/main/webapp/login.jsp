<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>TPI 2025 - Login</title>

        <!-- Fonts -->
        <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,600,700,800,900" rel="stylesheet">

        <!-- Styles -->
        <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body class="bg-gradient-primary">

        <div class="container">
            <div class="row justify-content-center">
                <div class="col-xl-10 col-lg-12 col-md-9">
                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">
                            <div class="p-5">

                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Ingreso</h1>
                                </div>

                                <c:if test="${not empty sessionScope.mensajeExito}">
                                    <div class="alert alert-success text-center">
                                        ${sessionScope.mensajeExito}
                                    </div>
                                    <c:remove var="mensajeExito" scope="session"/>
                                </c:if>


                                <form class="user" action="${pageContext.request.contextPath}/SvLogin/login" method="POST">

                                    <!-- USUARIO -->
                                    <div class="form-group">
                                        <input type="text"
                                               class="form-control form-control-user
                                               ${errores.nombreUsuario != null ? 'is-invalid' : ''}"
                                               id="nombreUsuario"
                                               name="nombreUsuario"
                                               placeholder="Nombre de usuario"
                                               value="${nombreUsuario}">

                                        <c:if test="${errores.nombreUsuario != null}">
                                            <div class="invalid-feedback">
                                                ${errores.nombreUsuario}
                                            </div>
                                        </c:if>
                                    </div>

                                    <!-- CONTRASEÑA -->
                                    <div class="form-group">
                                        <input type="password"
                                               class="form-control form-control-user
                                               ${errores.contrasenia != null ? 'is-invalid' : ''}"
                                               id="contrasenia"
                                               name="contrasenia"
                                               placeholder="Contraseña">

                                        <c:if test="${errores.contrasenia != null}">
                                            <div class="invalid-feedback">
                                                ${errores.contrasenia}
                                            </div>
                                        </c:if>
                                    </div>

                                    <button class="btn btn-primary btn-user btn-block" type="submit">
                                        Login
                                    </button>

                                    <hr>

                                    <a href="${pageContext.request.contextPath}/registrarFamiliaLogin.jsp"
                                       class="btn btn-secondary btn-block">
                                        Registrar Familia
                                    </a>

                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- JS -->
        <script src="${pageContext.request.contextPath}/vendor/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="${pageContext.request.contextPath}/vendor/jquery-easing/jquery.easing.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/sb-admin-2.min.js"></script>

    </body>
</html>
