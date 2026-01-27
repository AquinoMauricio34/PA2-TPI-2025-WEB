/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.TareaJpaController;
import com.mycompany.tpi2025web.model.Tarea;
import com.mycompany.tpi2025web.utils.Utils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

        Map<String, String> errores = new HashMap<>();

        //datos
        String fecha = request.getParameter("fecha");
        String hora = request.getParameter("hora");
        String ubicacion = request.getParameter("ubicacion");
        String tarea = request.getParameter("tarea");

        //reinyeccion
        request.setAttribute("fechaTarea", fecha);
        request.setAttribute("horaTarea", hora);
        request.setAttribute("ubicacionTarea", ubicacion);
        request.setAttribute("tipoTarea", tarea);

        //validacion
        if (fecha == null || fecha.isBlank() || !Utils.isFechaValida(fecha)) {
            errores.put("fecha", "Fecha inválida (dd/MM/yyyy)");
        }

        if (hora == null || hora.isBlank() || !Utils.isHoraValida(hora)) {
            errores.put("hora", "Hora inválida (HH:mm)");
        }

        if (ubicacion == null || ubicacion.isBlank()) {
            errores.put("ubicacion", "La ubicación es obligatoria");
        }

        if (tarea == null || tarea.isBlank()) {
            errores.put("tipoTarea", "La tarea es obligatoria");
        }

        //errores
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/registrarTareaRealizada.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        //validacion
        HttpSession s = request.getSession(false);
        Tarea nuevaTarea = new Tarea(
                fecha,
                hora,
                String.valueOf(s.getAttribute("usuario")),
                ubicacion,
                tarea
        );
        try {
            dao.create(nuevaTarea);
            s.setAttribute("mensajeExito", "La tarea se registró exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            s.setAttribute("mensajeFallo", "Ocurrió un error al registrar la tarea");
        }
        
        response.sendRedirect(request.getContextPath() + "/privado/SvPanel?vista=registrarTareaRealizada.jsp");
    }

}
