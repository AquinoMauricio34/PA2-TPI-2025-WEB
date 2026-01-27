<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <title>Registro de Familia</title>

        <!-- Custom fonts -->
        <link href="${pageContext.request.contextPath}/vendor/fontawesome-free/css/all.min.css"
              rel="stylesheet" type="text/css">

        <link href="https://fonts.googleapis.com/css?family=Nunito:200,300,400,600,700,800,900"
              rel="stylesheet">

        <!-- Custom styles -->
        <link href="${pageContext.request.contextPath}/css/sb-admin-2.min.css"
              rel="stylesheet">
    </head>

    <body class="bg-gradient-primary">

        <div class="container">

            <div class="row justify-content-center">

                <div class="col-xl-10 col-lg-12 col-md-9">

                    <div class="card o-hidden border-0 shadow-lg my-5">
                        <div class="card-body p-0">

                            <div class="p-5">

                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">
                                        Registro de Familia
                                    </h1>
                                </div>

                                <!-- ERROR GENERAL -->
                                <c:if test="${not empty errorGeneral}">
                                    <div class="alert alert-danger text-center">
                                        ${errorGeneral}
                                    </div>
                                </c:if>

                                <form class="user"
                                      action="${pageContext.request.contextPath}/SvLogin/registrar_familia"
                                      method="POST">

                                    <!-- NOMBRE -->
                                    <div class="form-group">
                                        <input type="text"
                                               class="form-control form-control-user
                                                      ${not empty errorNombre ? 'is-invalid' : ''}"
                                               name="nombre"
                                               placeholder="Nombre de Familia"
                                               value="${nombre}">
                                        <c:if test="${not empty errorNombre}">
                                            <small class="text-danger">${errorNombre}</small>
                                        </c:if>
                                    </div>

                                    <!-- TELEFONO -->
                                    <div class="form-group">
                                        <input type="text"
                                               class="form-control form-control-user
                                                      ${not empty errorTelefono ? 'is-invalid' : ''}"
                                               name="telefono"
                                               placeholder="Teléfono"
                                               value="${telefono}">
                                        <c:if test="${not empty errorTelefono}">
                                            <small class="text-danger">${errorTelefono}</small>
                                        </c:if>
                                    </div>

                                    <!-- USUARIO -->
                                    <div class="form-group">
                                        <input type="text"
                                               class="form-control form-control-user
                                                      ${not empty errorUsuario ? 'is-invalid' : ''}"
                                               name="nombreUsuario"
                                               placeholder="Nombre de usuario"
                                               value="${nombreUsuario}">
                                        <c:if test="${not empty errorUsuario}">
                                            <small class="text-danger">${errorUsuario}</small>
                                        </c:if>
                                    </div>

                                    <!-- CONTRASEÑA -->
                                    <div class="form-group">
                                        <input type="password"
                                               class="form-control form-control-user
                                                      ${not empty errorContrasenia ? 'is-invalid' : ''}"
                                               name="contrasenia"
                                               placeholder="Contraseña">
                                        <c:if test="${not empty errorContrasenia}">
                                            <small class="text-danger">${errorContrasenia}</small>
                                        </c:if>
                                    </div>

                                    <!-- BOTONES -->
                                    <div class="form-group row">
                                        <div class="col-sm-6 mb-3">
                                            <a class="btn btn-secondary btn-user w-100"
                                               href="${pageContext.request.contextPath}/login.jsp">
                                                Cancelar
                                            </a>
                                        </div>

                                        <div class="col-sm-6 mb-3">
                                            <button type="submit"
                                                    class="btn btn-primary btn-user w-100">
                                                Crear Usuario
                                            </button>
                                        </div>
                                    </div>

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
