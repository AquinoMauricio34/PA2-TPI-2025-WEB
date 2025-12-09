<%-- 
    Document   : verAdministrador
    Created on : 5 dic 2025, 12:19:11 p. m.
    Author     : aquin
--%>

<%@page import="com.mycompany.tpi2025web.model.Administrador"%>
<%@page import="com.mycompany.tpi2025web.model.Usuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="componentes/header.jsp" %>
<%@include file="componentes/body1.jsp" %>
<h1>Ver Administradores</h1>
<div class="container-fluid">
                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">DataTables Example</h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Telefono</th>
                                            <th>Usuario</th>
                                            <th style="width: 210px">Acción</th>
                                        </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
                                            <th>Nombre</th>
                                            <th>Telefono</th>
                                            <th>Usuario</th>
                                            <th style="width: 210px">Acción</th>
                                        </tr>
                                    </tfoot>
                                    
                                    <%
                                        List<Usuario> listaUsuario = (List) request.getSession().getAttribute("listaAdmin");
                                        %>
                                    <tbody>
                                        <%for(Usuario usu: listaUsuario){%>
                                        <tr>
                                            <td><%=usu.getNombre() %></td>
                                            <td><%=usu.getTelefono() %></td>
                                            <td><%=usu.getNombreUsuario() %></td>
                                            
                                            <td style="display: flex; width: 230px;">
                                                <form name="eliminar" action="SvElimAdministradores" method="POST"> <!-- esto es para mandar el codigo al servlet -->
                                                    <button type="submit" class="btn btn-primary btn-user btn-block" style="background-color:red; margin-right: 5px;">
                                                    <i class="fas fa-trash-alt"></i> Eliminar
                                                    </button>
                                                    <input type="hidden" name="nombreUsuario" value="<%=usu.getNombreUsuario() %>"><!-- esto es para mandar el codigo al servlet -->
                                                </form>


                                                <form name="editar" action="SvEditAdministradores" method="GET"> <!-- esto es para mandar el codigo al servlet -->
                                                    <button type="submit" class="btn btn-primary btn-user btn-block"; style="margin-left: 5px;">
                                                    <i class="fas fa-pencil-alt"></i> Editar
                                                    </button>
                                                    <input type="hidden" name="nombreUsuario" value="<%=usu.getNombreUsuario() %>"><!-- esto es para mandar el codigo al servlet -->
                                                </form>
                                            </td>

                                        </tr>
                                        <%}%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->
<%@include file="componentes/body2.jsp" %>