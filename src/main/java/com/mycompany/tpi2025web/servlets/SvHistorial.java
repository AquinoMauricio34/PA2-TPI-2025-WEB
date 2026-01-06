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
@WebServlet(name = "SvHistorial", urlPatterns = {"/SvHistorial/mostrar_historial","/SvHistorial/seleccionar_gato"})
public class SvHistorial extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
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
        System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk34");
        if(uri.endsWith("/mostrar_historial")){
            mostrarHistorial(request,response);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

    private void mostrarHistorial(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        System.out.println("QWERTY");
        DiagnosticoJpaController daoD = new DiagnosticoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
            System.out.println("P".repeat(120));
        Gato gato;
        if(request.getParameter("gato")==null){
            System.out.println("Q".repeat(120));
            gato = daoG.findGato((Long) request.getAttribute("gato"));
            System.out.println("W".repeat(120));
        }else{
            System.out.println("e".repeat(120));
            System.out.println(request.getParameter("gato"));
            gato = daoG.findGato(Long.parseLong(request.getParameter("gato")));
            System.out.println("r".repeat(120));
        }
        
        System.out.println("H: "+gato.getHistorial().getId()+"-".repeat(20));
        //request.setAttribute("gatoId", gato.getId());
        
        request.getRequestDispatcher("/SvDiagnostico/listar").forward(request, response);
        
    }

    private void seleccionarGato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        request.setAttribute("listaGatos", daoG.findGatoEntities());
        request.setAttribute("contenido", "/seleccionarGato.jsp");
        request.setAttribute("direccion", request.getParameter("direccion"));
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }
    
}
