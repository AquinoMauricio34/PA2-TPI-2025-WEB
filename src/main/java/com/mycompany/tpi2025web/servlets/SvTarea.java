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
import java.util.stream.Collectors;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvTarea", urlPatterns = {"/privado/SvTarea/crear_tarea", "/privado/SvTarea/listar", "/privado/SvTarea/listar_mis_tareas", "/privado/SvTarea/eliminar_tarea"})
public class SvTarea extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/listar")) {
            listar(request, response);
        } else if (uri.endsWith("/listar_mis_tareas")) {
            listarMisTareas(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/crear_tarea")) {
            crearTarea(request, response);
        } else if (uri.endsWith("/eliminar_tarea")) {
            eliminarTarea(request, response);
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvPanel?vista=registrarTareaRealizada.jsp");
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TareaJpaController dao
                = new TareaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        request.setAttribute("listaTareasRealizadas", dao.findTareaEntities());
        request.setAttribute("tituloJSP", "Tareas Realizadas");
        request.setAttribute("contenido", "/privado/verTareasRealizadas.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void eliminarTarea(HttpServletRequest request, HttpServletResponse response) throws IOException {
        TareaJpaController dao
                = new TareaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        HttpSession s = request.getSession(false);
        try {
            dao.destroy(Long.valueOf(request.getParameter("tareaId")));
            s.setAttribute("mensajeExito", "La tarea realizada se eliminó exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            s.setAttribute("mensajeFallo", "No se pudo eliminar la tarea realizada");
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvTarea/listar");

    }

    private void listarMisTareas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TareaJpaController dao
                = new TareaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        HttpSession s = request.getSession(false);
        request.setAttribute("listaTareasRealizadas", dao.findTareaEntities().stream().filter(e -> e.getNombreUsuarioVoluntario().equals(String.valueOf(s.getAttribute("usuario")))).collect(Collectors.toList()));
        request.setAttribute("tituloJSP", "Mis Tareas Realizadas");
        request.setAttribute("contenido", "/privado/verTareasRealizadas.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

}
