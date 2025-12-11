<%-- 
    Document   : body1
    Created on : 2 dic 2025, 1:19:39 p. m.
    Author     : aquin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<body id="page-top">
    
    
    <%
        /*
        HttpSession s = request.getSession(false);
        if (s == null || s.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        */
    %>
    
    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
                <!-- <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div> -->
                <div class="sidebar-brand-text mx-3">TPI 2025</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <!-- Nav Item - Dashboard -->
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp">
                    <i class="fas fa-fw fa-tachometer-alt"></i>
                    <span>Inicio</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider">

            <!-- Heading -->
            <div class="sidebar-heading">
                Pestanias
            </div>

            
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseAdministradores"
                    aria-expanded="true" aria-controls="collapseAdministradores">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Administradores</span>
                </a>
                <div id="collapseAdministradores" class="collapse" aria-labelledby="headingAdministradores"
                    data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <!-- <h6 class="collapse-header">Custom Utilities:</h6> -->
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvPanel?vista=altaUsuario.jsp&tipo=Administrador">Crear</a>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvUsuario/listar?tipo=Administrador">Ver</a>
                        <a class="collapse-item" href="#">Reporte</a>
                    </div>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseVoluntarios"
                    aria-expanded="true" aria-controls="collapseVoluntarios">
                    <i class="fas fa-fw fa-wrench"></i>
                    <span>Voluntarios</span>
                </a>
                <div id="collapseVoluntarios" class="collapse" aria-labelledby="headingVoluntarios"
                    data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <!-- <h6 class="collapse-header">Custom Utilities:</h6> -->
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvPanel?vista=altaUsuario.jsp&tipo=Voluntario">Crear</a>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvUsuario/listar?tipo=Voluntario">Ver</a>
                    </div>
                </div>
            </li>
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
                        <!-- <h6 class="collapse-header">Custom Utilities:</h6> -->
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvPanel?vista=altaUsuario.jsp&tipo=Veterinario">Crear</a>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvUsuario/listar?tipo=Veterinario">Ver</a>
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
                        <!-- <h6 class="collapse-header">Custom Utilities:</h6> -->
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvPanel?vista=altaUsuario.jsp&tipo=Familia">Crear</a>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvUsuario/listar?tipo=Familia">Ver</a>
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
                        <!-- <h6 class="collapse-header">Custom Utilities:</h6> -->
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvPanel?vista=altaUsuario.jsp&tipo=Hogar">Crear</a>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvUsuario/listar?tipo=Hogar">Ver</a>
                    </div>
                </div>
            </li>
            
            <!-- Sección Gato -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseGatos"
                    aria-expanded="true" aria-controls="collapseGatos">
                    <i class="fas fa-fw fa-hotel"></i> <!-- Icono para hogar -->
                    <span>Gatos</span>
                </a>
                <div id="collapseGatos" class="collapse" aria-labelledby="headingGatos"
                    data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <!-- <h6 class="collapse-header">Custom Utilities:</h6> -->
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvGato/cargar_alta">Crear</a>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvGato/listar">Ver</a>
                    </div>
                </div>
            </li>
            <!-- Sección Zona -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseZonas"
                    aria-expanded="true" aria-controls="collapseZonas">
                    <i class="fas fa-fw fa-hotel"></i> <!-- Icono para hogar -->
                    <span>Zonas</span>
                </a>
                <div id="collapseZonas" class="collapse" aria-labelledby="headingZonas"
                    data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <!-- <h6 class="collapse-header">Custom Utilities:</h6> -->
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvPanel?vista=mapa.jsp">Aniadir</a>
                        <a class="collapse-item" href="${pageContext.request.contextPath}/SvZona/listar">Ver</a>
                    </div>
                </div>
            </li>

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
                                <span class="mr-2 d-none d-lg-inline text-gray-600 small"><%=request.getSession().getAttribute("usuario") %></span>
                                <img class="img-profile rounded-circle"
                                    src="img/undraw_profile.svg">
                            </a>
                            <!-- Dropdown - User Information -->
                            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                                aria-labelledby="userDropdown">
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Profile
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Settings
                                </a>
                                <a class="dropdown-item" href="#">
                                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                                    Activity Log
                                </a>
                                <div class="dropdown-divider"></div>
                                <button class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
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
