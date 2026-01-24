<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rol" value="${sessionScope.tipoUsuarioSesion}"/>

<body id="page-top">



    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center">
                <div class="sidebar-brand-text mx-3">TPI 2025</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="${pageContext.request.contextPath}/privado/SvUsuario/cargar_mis_datos">
                    <i class="fas fa-user"></i>
                    <span>Inicio</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Pestanias
            </div>

            <c:if test="${rol eq 'Administrador'}">
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseAdministradores"
                       aria-expanded="true" aria-controls="collapseAdministradores">
                        <i class="fas fa-fw fa-wrench"></i>
                        <span>Administradores</span>
                    </a>
                    <div id="collapseAdministradores" class="collapse" aria-labelledby="headingAdministradores"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPanel?vista=altaUsuario.jsp&tipo=Administrador">Crear</a>
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvUsuario/listar?tipo=Administrador">Ver</a>
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvReporte">Reporte</a>
                        </div>
                    </div>
                </li>

            </c:if>
            <c:if test="${rol eq 'Administrador' or rol eq 'Voluntario'}">
                <!-- Sección Voluntario -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseVoluntarios"
                       aria-expanded="true" aria-controls="collapseVoluntarios">
                        <i class="fas fa-hands-helping"></i>
                        <span>Voluntarios</span>
                    </a>
                    <div id="collapseVoluntarios" class="collapse" aria-labelledby="headingVoluntarios"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <c:if test="${rol eq 'Administrador'}">
                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPanel?vista=altaUsuario.jsp&tipo=Voluntario">Crear</a>
                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvUsuario/listar?tipo=Voluntario">Ver</a>
                            </c:if>
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPanel?vista=registrarTareaRealizada.jsp">Tarea Realizada</a>
                        </div>
                    </div>
                </li>

            </c:if>

            <c:if test="${rol eq 'Administrador'}">
                <!-- Sección Veterinario -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseVeterinarios"
                       aria-expanded="true" aria-controls="collapseVeterinarios">
                        <i class="fas fa-fw fa-stethoscope"></i> <!-- Icono para veterinario -->
                        <span>Veterinarios</span>
                    </a>
                    <div id="collapseVeterinarios" class="collapse" aria-labelledby="headingVeterinarios"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPanel?vista=altaUsuario.jsp&tipo=Veterinario">Crear</a>
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvUsuario/listar?tipo=Veterinario">Ver</a>
                        </div>
                    </div>
                </li>

                <!-- Sección Familia -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseFamilias"
                       aria-expanded="true" aria-controls="collapseFamilias">
                        <i class="fas fa-fw fa-home"></i> <!-- Icono para familia -->
                        <span>Familias</span>
                    </a>
                    <div id="collapseFamilias" class="collapse" aria-labelledby="headingFamilias"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPanel?vista=altaUsuario.jsp&tipo=Familia">Crear</a>
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvUsuario/listar?tipo=Familia">Ver</a>
                        </div>
                    </div>
                </li>
                <!-- Sección Hogar -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseHogares"
                       aria-expanded="true" aria-controls="collapseHogares">
                        <i class="fas fa-fw fa-hotel"></i> <!-- Icono para hogar -->
                        <span>Hogares</span>
                    </a>
                    <div id="collapseHogares" class="collapse" aria-labelledby="headingHogares"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPanel?vista=altaUsuario.jsp&tipo=Hogar">Crear</a>
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvUsuario/listar?tipo=Hogar">Ver</a>
                        </div>
                    </div>
                </li>

            </c:if>

            <c:if test="${rol eq 'Administrador' or rol eq 'Voluntario' or rol eq 'Veterinario'}">
                <!-- Sección Gato -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseGatos"
                       aria-expanded="true" aria-controls="collapseGatos">
                        <i class="fas fa-cat"></i>
                        <span>Gatos</span>
                    </a>
                    <div id="collapseGatos" class="collapse" aria-labelledby="headingGatos"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <c:if test="${rol eq 'Administrador' or rol eq 'Voluntario'}">

                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvGato/cargar_alta">Crear</a>
                            </c:if>
                            <c:if test="${rol eq 'Administrador' or rol eq 'Voluntario' or rol eq 'Veterinario'}">

                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvGato/listar">Ver</a>
                            </c:if>
                            <c:if test="${rol eq 'Administrador' or rol eq 'Veterinario'}">

                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvHistorial/seleccionar_gato?direccion=/privado/SvHistorial/mostrar_historial">Historial</a>
                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvEstudio/mostrar_gatos">Registrar Estudio</a>
                            </c:if>
                            <c:if test="${rol eq 'Administrador' or rol eq 'Voluntario'}">

                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvVisita/mostrar_gatos">Visita de Seguimiento</a>
                            </c:if>
                        </div>
                    </div>
                </li>

            </c:if>
            <c:if test="${rol eq 'Administrador' or rol eq 'Voluntario' or rol eq 'Veterinario' or rol eq 'Familia' or rol eq 'Hogar'}">
                <!-- Sección Postulacion y Aptitud -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePostulaciones"
                       aria-expanded="true" aria-controls="collapsePostulaciones">
                        <i class="fas fa-heart"></i>
                        <span>Adopción</span>
                    </a>
                    <div id="collapsePostulaciones" class="collapse" aria-labelledby="headingPostulaciones"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <c:if test="${rol eq 'Administrador' or rol eq 'Veterinario'}">

                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvUsuario/cargar_usuarios_emision">Emision Aptitud</a>
                            </c:if>
                            <c:if test="${rol eq 'Administrador' or rol eq 'Familia' or rol eq 'Hogar'}">

                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPostulacion/cargar_gatos_postular">Postularse</a>
                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvPostulacion/cargar_mis_postulaciones">Mis Postulaciones</a>
                            </c:if>
                            <c:if test="${rol eq 'Administrador' or rol eq 'Voluntario'}">

                                <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvUsuario/cargar_usuarios_aptos">Asignar gato</a>
                            </c:if>
                        </div>
                    </div>
                </li>

            </c:if>
            <c:if test="${rol eq 'Administrador'}">
                <!-- Sección Zona -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseZonas"
                       aria-expanded="true" aria-controls="collapseZonas">
                        <i class="fas fa-map-marker-alt"></i>
                        <span>Zonas</span>
                    </a>
                    <div id="collapseZonas" class="collapse" aria-labelledby="headingZonas"
                         data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvZona/cargar_aniadir">Aniadir</a>
                            <a class="collapse-item" href="${pageContext.request.contextPath}/privado/SvZona/listar">Ver</a>
                        </div>
                    </div>
                </li>

            </c:if>


        </ul>
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                    <!-- Sidebar Toggle (Topbar) -->
                    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                        <i class="fa fa-bars"></i>
                    </button>



                    <!-- Topbar Navbar -->
                    <ul class="navbar-nav ml-auto">




                        <!-- Nav Item - User Information -->
                        <li class="nav-item dropdown no-arrow">
                            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small">Usuario: <%=request.getSession().getAttribute("usuario")%></span>
                                <i class="fas fa-bars"></i>
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                 aria-labelledby="userDropdown">
                                <button class="dropdown-item" data-toggle="modal" data-target="#logoutModal">
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Logout
                                </button>
                            </div>
                        </li>

                    </ul>

                </nav>
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">
