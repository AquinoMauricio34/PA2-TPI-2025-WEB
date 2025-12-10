/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.AdministradorJpaController;
import com.mycompany.tpi2025web.model.Administrador;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvAdministrador", urlPatterns = {"/SvAdministrador"})
public class SvAdministrador extends HttpServlet {

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String uri = request.getRequestURI();
        
        if(uri.endsWith("lista")) obtenerlista(request,response);
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        AdministradorJpaController dao = new AdministradorJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");
        
        System.out.println(nombre+";"+telefono+";"+usuario+";"+contrasenia);
        
        try {
            dao.create(new Administrador(nombre,contrasenia, telefono, usuario));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath()+"/SvPanel?vista=index.jsp");
        
        
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void obtenerlista(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AdministradorJpaController dao = new AdministradorJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        List<Administrador> listaAdmin = dao.findAdministradorEntities();
        
        request.setAttribute("listaAdmin", listaAdmin);
        request.getRequestDispatcher("/SvPanel?vista=verAdministrador.jsp").forward(request,response);
    }

}
