/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.EstudioJpaController;
import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.model.Estudio;
import com.mycompany.tpi2025web.model.Gato;
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
@WebServlet(name = "SvEstudio", urlPatterns = {"/privado/SvEstudio","/privado/SvEstudio/crear_estudio","/privado/SvEstudio/mostrar_campos","/privado/SvEstudio/mostrar_gatos"})
public class SvEstudio extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/mostrar_campos")) {
            mostrarCampos(request, response);
        } else if (uri.endsWith("/mostrar_gatos")) {
            mostrarGatos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/crear_estudio")) {
            crearEstudio(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void crearEstudio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EstudioJpaController dao = new EstudioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        Estudio nuevoEstudio = new Estudio(Long.valueOf(request.getParameter("gatoId")), request.getParameter("titulo"), request.getParameter("descripcion"));
        try {
            dao.create(nuevoEstudio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //redireccion
        response.sendRedirect(request.getContextPath()+"/privado/SvEstudio/mostrar_gatos");
        
        
    }

    private void mostrarCampos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("gatoId", request.getParameter("gato"));
        request.setAttribute("contenido", "/privado/registrarEstudio.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void mostrarGatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController dao
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        List<Gato> listaGatos = dao.findGatoEntities();

        request.setAttribute("listaGatos", listaGatos);
        request.setAttribute("contenido", "/privado/seleccionarGatoEstudio.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }
}
