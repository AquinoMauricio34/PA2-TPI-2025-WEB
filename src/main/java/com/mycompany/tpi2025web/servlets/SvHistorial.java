/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.DiagnosticoJpaController;
import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.model.Gato;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvHistorial", urlPatterns = {"/privado/SvHistorial/mostrar_historial","/privado/SvHistorial/seleccionar_gato"})
public class SvHistorial extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        if(uri.endsWith("/seleccionar_gato")){
            seleccionarGato(request,response);
        }else if(uri.endsWith("/mostrar_historial")){
            mostrarHistorial(request,response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        
        if(uri.endsWith("/mostrar_historial")){
            mostrarHistorial(request,response);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

    private void mostrarHistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        
        DiagnosticoJpaController daoD = new DiagnosticoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
            
        Gato gato;
        if(request.getParameter("gato")==null){
            
            gato = daoG.findGato((Long) request.getAttribute("gato"));
            
        }else{
            
            
            gato = daoG.findGato(Long.parseLong(request.getParameter("gato")));
            
        }
        
        
        
        request.getRequestDispatcher("/privado/SvDiagnostico/listar").forward(request, response);
        
    }

    private void seleccionarGato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        request.setAttribute("listaGatos", daoG.findGatoEntities());
        request.setAttribute("contenido", "/privado/seleccionarGato.jsp");
        request.setAttribute("direccion", request.getParameter("direccion"));
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }
    
}
