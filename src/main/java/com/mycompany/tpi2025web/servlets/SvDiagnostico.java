/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.DiagnosticoJpaController;
import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.TratamientoJpaController;
import com.mycompany.tpi2025web.model.Diagnostico;
import com.mycompany.tpi2025web.model.Gato;
import com.mycompany.tpi2025web.model.Tratamiento;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvDiagnostico", urlPatterns = {"/privado/SvDiagnostico", "/privado/SvDiagnostico/eliminar_tratamiento", "/privado/SvDiagnostico/llamar_mostrarDiagnostico", "/privado/SvDiagnostico/crear", "/privado/SvDiagnostico/listar", "/privado/SvDiagnostico/editar", "/privado/SvDiagnostico/cargar_editar", "/privado/SvDiagnostico/eliminar", "/privado/SvDiagnostico/llamar_altaTratamiento"})
public class SvDiagnostico extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/listar")) {
            listar(request, response);
        } else if (uri.endsWith("/llamar_altaTratamiento")) {
            altaTratamiento(request, response);
        } else if (uri.endsWith("/llamar_mostrarDiagnostico")) {
            mostrarDiagnostico(request, response);
        } else if (uri.endsWith("/cargar_editar")) {
            cargarEditar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        

        if ("agregarTratamiento".equals(accion)) {
            altaTratamiento(request, response);
        }

        if ("crearDiagnostico".equals(accion)) {
            crear(request, response);
            
        }
        if ("editarDiagnostico".equals(accion)) {
            
            editar(request, response);
        }
        if ("eliminarTratamiento".equals(accion)) {
            
            eliminarTratamiento(request, response);
        }
        if ("cargarEditarTrat".equals(accion)) {
            
            cargarEditarTratamiento(request, response);
        }
        if ("eliminar".equals(accion)) {
            
            eliminar(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiagnosticoJpaController daoD = new DiagnosticoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        List<Diagnostico> listaDiagnosticos = daoD.obtenerPorHistorial(daoG.findGato(Long.parseLong(request.getParameter("gato"))).getHistorial().getId());
        request.setAttribute("listaDiagnosticos", listaDiagnosticos);
        request.setAttribute("gato", request.getParameter("gato"));
        request.setAttribute("contenido", "/privado/verDiagnosticos.jsp");
        
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void crear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        Map<String, String> errores = new HashMap<>();

        //obtener datos
        String gatoId = request.getParameter("gatoId");
        String titulo = request.getParameter("titulo");
        String descripcion = request.getParameter("descripcion");

        //reiyeccion
        request.setAttribute("gatoId", gatoId);
        request.setAttribute("titulo", titulo);
        request.setAttribute("descripcion", descripcion);
        request.setAttribute("descripcion", descripcion);
        HttpSession session = request.getSession(false);
        List<Tratamiento> lista = (List<Tratamiento>) session.getAttribute("tratamientosTemp");
        request.setAttribute("listaTratamientos", lista);

        //validacion
        if (titulo == null || titulo.isBlank()) {
            errores.put("titulo", "El título es obligatorio");
        }
        if (descripcion == null || descripcion.isBlank()) {
            errores.put("descripcion", "La descripcion es obligatoria");
        }

        //errores
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/altaDiagnostico.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        
        Diagnostico d = new Diagnostico(request.getParameter("descripcion"), request.getParameter("titulo"), LocalDate.now());
        

        for (Tratamiento t : lista) {
            t.setDiagnostico(d);  // si corresponde
            d.addTratamiento(t);
        }
        
        
        Gato g = daoG.findGato(Long.parseLong(request.getParameter("gatoId")));
        g.getHistorial().addDiagnostico(d);

        try {
            
            daoG.edit(g);
            
            session.setAttribute("mensajeExito", "El diagnostico se registró exitosamente");
        } catch (Exception ex) {
            Logger.getLogger(SvDiagnostico.class.getName()).log(Level.SEVERE, null, ex);
            session.setAttribute("mensajeFallo", "El diagnostico no se pudo registrar");

        }

        session.removeAttribute("tratamientosTemp");
        response.sendRedirect(request.getContextPath() + "/privado/SvHistorial/mostrar_historial?gato=" + g.getId());

    }

    private void altaTratamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("titulo", request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        request.setAttribute("vistaVolver", request.getParameter("vistaVolver"));
        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        
        
        request.setAttribute("contenido", "/privado/altaTratamiento.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void mostrarDiagnostico(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        if (request.getParameter("diagnostico") != null) {
            request.setAttribute("diagnostico", request.getParameter("diagnostico"));
        }
        request.setAttribute("gatoId", request.getParameter("gato"));
        HttpSession s = request.getSession(false);
        s.removeAttribute("tratamientosTemp");
        
        request.setAttribute("contenido", "/privado/altaDiagnostico.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void cargarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiagnosticoJpaController daoD = new DiagnosticoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        Diagnostico d = daoD.findDiagnostico(Long.valueOf(request.getParameter("diagnostico")));
        List<Tratamiento> listaTratamientos = daoT.obtenerPorDiagnostico(d.getId());
        
        request.setAttribute("titulo", d.getDiagnostico());
        request.setAttribute("descripcion", d.getDescripcion());
        request.setAttribute("fechaDiagnostico", d.getFecha_diagnostico());
        request.setAttribute("diagnosticoId", d.getId());
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        
        HttpSession session = request.getSession(false);
        //para la session
        session.setAttribute("tratamientosTemp", listaTratamientos);
        //para mostrar en la jsp
        request.setAttribute("listaTratamientos", listaTratamientos);
        request.setAttribute("contenido", "/privado/editarDiagnostico.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        EntityManagerFactory emf
                = (EntityManagerFactory) request.getServletContext().getAttribute("emf");

        Map<String, String> errores = new HashMap<>();

        DiagnosticoJpaController daoD = new DiagnosticoJpaController(emf);
        TratamientoJpaController daoT = new TratamientoJpaController(emf);

        HttpSession session = request.getSession();

        //obtencion de datos
        Long diagnosticoId = Long.valueOf(request.getParameter("diagnosticoId"));
        Long gatoId = Long.valueOf(request.getParameter("gatoId"));
        String tituloDiag = request.getParameter("titulo");
        String descripcionDiag = request.getParameter("descripcion");
        String fechaDiag = request.getParameter("fechaDiagnostico");

        //reinyeccion
        request.setAttribute("diagnosticoId", diagnosticoId);
        request.setAttribute("gatoId", gatoId);
        request.setAttribute("titulo", tituloDiag);
        request.setAttribute("descripcion", descripcionDiag);
        request.setAttribute("fechaDiagnostico", fechaDiag);

        //validaciones
        if (tituloDiag == null || tituloDiag.isBlank()) {
            errores.put("titulo", "El titulo es obligatorio");
        }
        if (descripcionDiag == null || descripcionDiag.isBlank()) {
            errores.put("descripcion", "El descripcion es obligatorio");
        }
        
        //se crea la lista aquí porque se la necesita por si ocurre un error para volver a mostrar en el jsp
        List<Tratamiento> tratamientosSesion
                = (List<Tratamiento>) session.getAttribute("tratamientosTemp");
        //errores
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
        request.setAttribute("listaTratamientos", tratamientosSesion);
            request.setAttribute("contenido", "/privado/editarDiagnostico.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        Diagnostico d = daoD.findDiagnostico(diagnosticoId);

        if (d == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Diagnóstico no encontrado");
            return;
        }

        List<Tratamiento> tratamientosActuales = new ArrayList<>(d.getTratamientos());


        /*
        aniadir al diagnostico los tratamientos que estan en la session pero no estan en getTratamientos,
        es decir, hacer la union de las dos listas, pero eso se toma la lista de tratamiento del objeto diagnostico (lista A)
        y se toma la lista de la session (lista B) y a la lista A se aniade los tratamientos de la lista B que NO estan en la
        lista A
         */
        if (tratamientosSesion != null) {

            for (Tratamiento t : tratamientosSesion) {

                if (t.getId() == null) {

                    t.setDiagnostico(d);   // relación bidireccional
                    daoT.create(t);        // se crea el tratamiento en la db

                    tratamientosActuales.add(t); // se aniade el tratamiento creado en la lista A
                }
            }
        }

        d.setTratamientos(tratamientosActuales);

        d.setDiagnostico(tituloDiag);
        d.setDescripcion(descripcionDiag);

        try {
            daoD.edit(d);
            session.setAttribute("mensajeExito", "El diagnóstico se modificó exitosamente");
        } catch (Exception e) {
            session.setAttribute("mensajeFallo", "El diagnóstico no se pudo registrar");
            e.printStackTrace();
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al editar diagnóstico"
            );
            return;
        }

        session.removeAttribute("tratamientosTemp");
        

        response.sendRedirect(
                request.getContextPath()
                + "/privado/SvHistorial/mostrar_historial?gato=" + gatoId
        );
    }

    private void eliminarTratamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        
        HttpSession session = request.getSession(false);
        List<Tratamiento> lista = (List<Tratamiento>) session.getAttribute("tratamientosTemp");

        //si el tratamiento tiene id null, significa que no esta en la db. La eliminacion depende de se esta o no en la db
        
        if (request.getParameter("tratamientoId") != null && !request.getParameter("tratamientoId").isBlank()) {
            Tratamiento t = daoT.findTratamiento(Long.valueOf(request.getParameter("tratamientoId")));
            Diagnostico d = t.getDiagnostico();
            d.getTratamientos().remove(t);
            t.setDiagnostico(null);
            lista.removeIf(v -> v.getId() == t.getId());
            try {
                daoT.destroy(t.getId());
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        } else {
            
            lista.removeIf(v -> v.getId() == null && v.getDescripcion().hashCode() == Integer.parseInt(request.getParameter("tratamientoDescripcion")));
            
        }

        

        request.setAttribute("listaTratamientos", lista);

        request.setAttribute("titulo", request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        
        if (request.getParameter("diagnosticoId") != null) {
            request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        }
        request.setAttribute("contenido", request.getParameter("vistaVolver"));
        
        request.getRequestDispatcher("/privado/layout.jsp")
                .forward(request, response);

    }

    private void cargarEditarTratamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        Tratamiento t = daoT.findTratamiento(Long.valueOf(request.getParameter("tratamientoId")));

        request.setAttribute("tratamientoId", request.getParameter("tratamientoId"));
        

        request.setAttribute("gatoId", request.getParameter("gatoId"));
        

        request.setAttribute("titulo", request.getParameter("titulo"));
        

        request.setAttribute("descripcion", request.getParameter("descripcion"));
        

        request.setAttribute("vistaVolver", request.getParameter("vistaVolver"));
        

        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        

        request.setAttribute("fechaInicio", t.getFecha_inicio());
        

        request.setAttribute("fechaFin", t.getFecha_fin());
        

        request.setAttribute("descripcionTratamiento", t.getDescripcion());
        

        request.setAttribute("abandonoTratamiento", t.getAbandono_tratamiento());
        
        request.setAttribute("contenido", "/privado/editarTratamiento.jsp");
        
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DiagnosticoJpaController daoD = new DiagnosticoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        HttpSession s = request.getSession(false);
        try {
            daoD.destroy(Long.valueOf(request.getParameter("diagnostico")));
            s.setAttribute("mensajeExito", "Diagnostico eliminado");
        } catch (Exception e) {
            s.setAttribute("mensajeFallo", "No se pudo eliminar el diagnóstico");
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvHistorial/mostrar_historial?gato=" + request.getParameter("gatoId"));

    }
}
