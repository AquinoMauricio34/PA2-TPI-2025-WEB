/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.VisitaSeguimientoJpaController;
import com.mycompany.tpi2025web.model.Gato;
import com.mycompany.tpi2025web.model.VisitaSeguimiento;
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
import java.util.List;
import java.util.Map;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvVisita", urlPatterns = {"/privado/SvVisita/mostrar_campos", "/privado/SvVisita/crear_visita","/privado/SvVisita/eliminar_visita","/privado/SvVisita/listar", "/privado/SvVisita/mostrar_gatos"})
public class SvVisita extends HttpServlet {

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
        } else if (uri.endsWith("/listar")) {
            listar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/crear_visita")) {
            crearVisita(request, response);
        } else if (uri.endsWith("/eliminar_visita")) {
            eliminarVisita(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void crearVisita(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        VisitaSeguimientoJpaController dao
                = new VisitaSeguimientoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        HttpSession s = request.getSession(false);

        Map<String, String> errores = new HashMap<>();

        //obtencion de datos
        String gatoId = request.getParameter("gatoId");
        String fecha = request.getParameter("fecha");
        String descripcionVisita = request.getParameter("descripcion");

        //reiyeccion
        request.setAttribute("gatoId", gatoId);
        request.setAttribute("fecha", fecha);
        request.setAttribute("descripcionVisita", descripcionVisita);

        //validacion
        if (fecha == null || fecha.isBlank()) {
            errores.put("fecha", "La fecha es obligatoria");
        } else if (!Utils.isFechaValida(fecha)) {
            errores.put("fecha", "Fecha inválida (dd/MM/yyyy)");
        }

        if (descripcionVisita == null || descripcionVisita.isBlank()) {
            errores.put("descripcion", "La descripción es obligatoria");
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/registrarVisitaSeguimiento.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        VisitaSeguimiento visita = new VisitaSeguimiento(String.valueOf(s.getAttribute("usuario")), Long.parseLong(request.getParameter("gatoId")), request.getParameter("fecha"), request.getParameter("descripcion"));
        try {
            dao.create(visita);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/privado/SvVisita/mostrar_gatos");
    }

    private void mostrarCampos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("gatoId", request.getParameter("gato"));
        request.setAttribute("contenido", "/privado/registrarVisitaSeguimiento.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void mostrarGatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController dao
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        List<Gato> listaGatos = dao.findGatosByAdoptado(true);

        request.setAttribute("listaGatos", listaGatos);
        request.setAttribute("contenido", "/privado/seleccionarGatoVisita.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        VisitaSeguimientoJpaController dao
                = new VisitaSeguimientoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        request.setAttribute("listaVisitas", dao.findVisitaSeguimientoEntities());
        request.setAttribute("contenido", "/privado/verVisitas.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void eliminarVisita(HttpServletRequest request, HttpServletResponse response) throws IOException {
        VisitaSeguimientoJpaController dao
                = new VisitaSeguimientoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        HttpSession s = request.getSession(false);
        try {
            dao.destroy(Long.valueOf(request.getParameter("visitaId")));
            s.setAttribute("mensajeExito", "La visita se eliminó exitosamente");
        } catch (Exception e) {
            e.printStackTrace();
            s.setAttribute("mensajeFallo", "No se pudo eliminar la visita");
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvVisita/listar");

    }
    
}
