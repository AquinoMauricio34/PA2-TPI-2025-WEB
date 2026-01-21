/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.TareaJpaController;
import com.mycompany.tpi2025web.model.Tarea;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvTarea", urlPatterns = {"/privado/SvTarea/crear_tarea"})
public class SvTarea extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/crear_tarea")) {
            crearTarea(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void crearTarea(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TareaJpaController dao
                = new TareaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        
        HttpSession s = request.getSession(false);
        Tarea nuevaTarea = new Tarea(
                request.getParameter("fecha"),
                request.getParameter("hora"),
                String.valueOf(s.getAttribute("usuario")),
                request.getParameter("ubicacion"),
                request.getParameter("tarea")
        );
        try {
            dao.create(nuevaTarea);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("contenido", "/privado/registrarTareaRealizada.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

}
