/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.TratamientoJpaController;
import com.mycompany.tpi2025web.model.Tratamiento;
import com.mycompany.tpi2025web.utils.Utils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvTratamiento", urlPatterns = {"/privado/SvTratamiento/crear", "/privado/SvTratamiento/eliminar", "/privado/SvTratamiento/cargar_editar", "/privado/SvTratamiento/editar"})
public class SvTratamiento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        System.out.println("svtrat ACCION: " + accion);

        if ("cargar_editar".equals(accion)) {
            cargarEditar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println("svtrat uri: " + uri);
        if (uri.endsWith("/crear")) {
            crear(request, response);
        } else if (uri.endsWith("/editar")) {
            editar(request, response);
        } else if (uri.endsWith("/cargar_editar")) {
            cargarEditar(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void crear(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Map<String, String> errores = new HashMap<>();

        //obtener datos
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");
        String descripcionTratamiento = request.getParameter("descripcionTratamiento");
        String abandono = request.getParameter("abandono");
        String tituloDiag = request.getParameter("titulo");
        String descripcionDiag = request.getParameter("descripcion");
        String gatoId = request.getParameter("gatoId");
        String diagnosticoId = request.getParameter("diagnosticoId");
        String vistaVolver = request.getParameter("vistaVolver");

        //reinyeccion
        request.setAttribute("fechaInicio", fechaInicio);
        request.setAttribute("fechaFin", fechaFin);
        request.setAttribute("descripcionTratamiento", descripcionTratamiento);
        request.setAttribute("abandono", abandono);
        request.setAttribute("titulo", tituloDiag);
        request.setAttribute("descripcion", descripcionDiag);
        request.setAttribute("gatoId", gatoId);
        request.setAttribute("diagnosticoId", diagnosticoId);
        request.setAttribute("vistaVolver", vistaVolver);

        //validacion
        if (fechaInicio == null || fechaInicio.isBlank()) {
            errores.put("fechaInicio", "La fecha de inicio es obligatoria");
        } else if (!Utils.isFechaValida(fechaInicio)) {
            errores.put("fechaInicio", "Fecha inválida (dd/MM/yyyy)");
        }

        if (fechaFin == null || fechaFin.isBlank()) {
            errores.put("fechaFin", "La fecha de fin es obligatoria");
        } else if (!Utils.isFechaValida(fechaFin)) {
            errores.put("fechaFin", "Fecha inválida (dd/MM/yyyy)");
        }

        if (descripcionTratamiento == null || descripcionTratamiento.isBlank()) {
            errores.put("descripcionTratamiento", "La descripción es obligatoria");
        }

        //errores
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/altaTratamiento.jsp");
            request.getRequestDispatcher("/privado/layout.jsp")
                    .forward(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        System.out.println("SESSION ID = " + session.getId());
        List<Tratamiento> lista = (List<Tratamiento>) session.getAttribute("tratamientosTemp");

        // Si aún no existe, crearla
        if (lista == null) {
            System.out.println("-----------------Lista null se crea nueva");
            lista = new ArrayList<>();
            session.setAttribute("tratamientosTemp", lista);
        }

        Tratamiento t = new Tratamiento(descripcionTratamiento, fechaInicio, fechaFin);
        t.setAbandono_tratamiento(abandono != null);

        request.setAttribute("tratamiento", t);

        lista.add(t);
        request.setAttribute("listaTratamientos", lista);

        request.setAttribute("titulo", tituloDiag);
        request.setAttribute("descripcion", descripcionDiag);
        request.setAttribute("gatoId", gatoId);
        request.setAttribute("diagnosticoId", diagnosticoId);
        request.setAttribute("contenido", vistaVolver);
        System.out.println("Vista volver svtrat/crear: " + request.getParameter("vistaVolver"));
        request.getRequestDispatcher("/privado/layout.jsp")
                .forward(request, response);
    }

    private void cargarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        System.out.println("AWEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        Tratamiento t = null;
        if (request.getParameter("tratamientoId") == null || request.getParameter("tratamientoId").isBlank()) {
            System.out.println("UNIONNNNNNNNNNNNNNNNNN: " + request.getParameter("tratamientoDescripcion"));
            int sumaHashCode = Integer.valueOf(request.getParameter("tratamientoDescripcion"));
            HttpSession s = request.getSession(false);
            List<Tratamiento> listaTratamientos = (List<Tratamiento>) s.getAttribute("tratamientosTemp");
            t = listaTratamientos.stream().filter(e -> e.getDescripcion().hashCode() == sumaHashCode).findFirst().orElse(null);
            request.setAttribute("descripcionAux", request.getParameter("tratamientoDescripcion"));
        } else {
            t = daoT.findTratamiento(Long.valueOf(request.getParameter("tratamientoId")));
        }

        request.setAttribute("fechaInicio", t.getFecha_inicio());
        request.setAttribute("fechaFin", t.getFecha_fin());
        request.setAttribute("descripcionTratamiento", t.getDescripcion());
        request.setAttribute("abandonoTratamiento", t.getAbandono_tratamiento());
        request.setAttribute("tratamientoId", request.getParameter("tratamientoId"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        System.out.println("svtrat cargEdit gatoId: " + request.getParameter("gatoId"));
        request.setAttribute("titulo", request.getParameter("titulo"));
        System.out.println("svtrat cargEdit titulo: " + request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("vistaVolver", request.getParameter("vistaVolver"));
        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        request.setAttribute("contenido", "/privado/editarTratamiento.jsp");
        System.out.println("svtrat cargEdit Vista volver: " + request.getParameter("vistaVolver"));
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);

    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TratamientoJpaController daoT
                = new TratamientoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        Map<String, String> errores = new HashMap<>();

        //obtencion de datos
        String tratamientoId = request.getParameter("tratamientoId");
        String descripcionTratamiento = request.getParameter("descripcionTratamiento");
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");
        String abandono = request.getParameter("abandono");

        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");
        String gatoId = request.getParameter("gatoId");
        String diagnosticoId = request.getParameter("diagnosticoId");
        String vistaVolver = request.getParameter("vistaVolver");
        String descripcionAux = request.getParameter("descripcionAux");

        //reinyeccion
        request.setAttribute("tratamientoId", tratamientoId);
        request.setAttribute("descripcionTratamiento", descripcionTratamiento);
        request.setAttribute("fechaInicio", fechaInicio);
        request.setAttribute("fechaFin", fechaFin);
        request.setAttribute("abandono", abandono);

        request.setAttribute("titulo", titulo);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("gatoId", gatoId);
        request.setAttribute("diagnosticoId", diagnosticoId);
        request.setAttribute("vistaVolver", vistaVolver);
        request.setAttribute("descripcionAux", descripcionAux);
        

        //validaciones
        if (descripcionTratamiento == null || descripcionTratamiento.isBlank()) {
            errores.put("descripcionTratamiento", "La descripción es obligatoria");
        }

        if (fechaInicio == null || fechaInicio.isBlank()) {
            errores.put("fechaInicio", "La fecha de inicio es obligatoria");
        }

        if (fechaFin == null || fechaFin.isBlank()) {
            errores.put("fechaFin", "La fecha de fin es obligatoria");
        }

        //errores
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/editarTratamiento.jsp");
            request.getRequestDispatcher("/privado/layout.jsp")
                    .forward(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        System.out.println("SESSION ID = " + session.getId());

        List<Tratamiento> lista
                = (List<Tratamiento>) session.getAttribute("tratamientosTemp");

        Tratamiento tFinal = null;
        if (request.getParameter("descripcionAux") != null && !request.getParameter("descripcionAux").isBlank()) {
            //lista.removeIf(e -> e.getDescripcion().equals(request.getParameter("descripcionAux")));
            Tratamiento t = new Tratamiento(descripcionTratamiento, fechaInicio, fechaFin);
            t.setAbandono_tratamiento(abandono != null);
            lista.set(lista.indexOf(lista.stream()
                    .filter(v -> v.getDescripcion().equals(t.getDescripcion()))
                    .findFirst()
                    .orElse(null)
            ), t);
            tFinal = t;
        } else {
            Tratamiento t = daoT.findTratamiento(Long.valueOf(tratamientoId));
            t.setDescripcion(descripcionTratamiento);
            t.setFecha_inicio(fechaInicio);
            t.setFecha_fin(fechaFin);
            t.setAbandono_tratamiento(abandono != null);
            lista.set(lista.indexOf(lista.stream()
                    .filter(v -> v.getId().equals(t.getId()))
                    .findFirst()
                    .orElse(null)
            ), t);
            tFinal = t;
        }

        try {
            daoT.edit(tFinal);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("listaTratamientos", lista);
        request.setAttribute("contenido", vistaVolver);

        request.getRequestDispatcher("/privado/layout.jsp")
                .forward(request, response);
    }

}
