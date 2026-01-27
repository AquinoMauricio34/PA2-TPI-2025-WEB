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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvDiagnostico", urlPatterns = {"/privado/SvDiagnostico","/privado/SvDiagnostico/eliminar_tratamiento","/privado/SvDiagnostico/llamar_mostrarDiagnostico","/privado/SvDiagnostico/crear","/privado/SvDiagnostico/listar","/privado/SvDiagnostico/editar","/privado/SvDiagnostico/cargar_editar","/privado/SvDiagnostico/eliminar","/privado/SvDiagnostico/llamar_altaTratamiento"})
public class SvDiagnostico extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        
        if(uri.endsWith("/listar")){
            listar(request,response);
        }else if(uri.endsWith("/llamar_altaTratamiento")){
            altaTratamiento(request,response);
        }else if(uri.endsWith("/llamar_mostrarDiagnostico")){
            mostrarDiagnostico(request,response);
        }else if(uri.endsWith("/cargar_editar")){
            cargarEditar(request,response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        System.out.println("ACCION: "+accion);

        if ("agregarTratamiento".equals(accion)) {
            altaTratamiento(request, response);
        }

        if ("crearDiagnostico".equals(accion)) {
            crear(request, response);
            System.out.println("S".repeat(120));
        }
        if ("editarDiagnostico".equals(accion)) {
            System.out.println("SM".repeat(120));
            editar(request, response);
        }
        if ("eliminarTratamiento".equals(accion)) {
            System.out.println("SdddM".repeat(120));
            eliminarTratamiento(request, response);
        }
        if ("cargarEditarTrat".equals(accion)) {
            System.out.println("LKJU".repeat(120));
            cargarEditarTratamiento(request, response);
        }
        
        
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiagnosticoJpaController daoD = new DiagnosticoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
//        List<Diagnostico> listaDiagnosticos = daoD.obtenerPorHistorial(daoG.findGato((Long) request.getAttribute("gatoId")).getId());
        System.out.println("Parametro gatoID: "+request.getParameter("gato"));
        List<Diagnostico> listaDiagnosticos = daoD.obtenerPorHistorial(daoG.findGato(Long.parseLong(request.getParameter("gato"))).getHistorial().getId());
        request.setAttribute("listaDiagnosticos", listaDiagnosticos);
        request.setAttribute("gato", request.getParameter("gato"));
        request.setAttribute("contenido", "/privado/verDiagnosticos.jsp");
        System.out.println("YAAAAAAAAAAAAAAAaa");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void crear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        System.out.println("D".repeat(120));
        Diagnostico d = new Diagnostico(request.getParameter("descripcion"), request.getParameter("titulo"), LocalDate.now());
        HttpSession session = request.getSession();
        System.out.println("SESSION ID = " + session.getId());
        List<Tratamiento> lista = (List<Tratamiento>) session.getAttribute("tratamientosTemp");
        
        for (Tratamiento t : lista) {
            t.setDiagnostico(d);  // si corresponde
            d.addTratamiento(t);
        }
        System.out.println("F".repeat(120));
        System.out.println(request.getParameter("gatoId"));
        Gato g = daoG.findGato(Long.parseLong(request.getParameter("gatoId")));
        g.getHistorial().addDiagnostico(d);
        
        try {
            System.out.println("G".repeat(120));
            daoG.edit(g);
            System.out.println("H".repeat(120));
        } catch (Exception ex) {
            Logger.getLogger(SvDiagnostico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        session.removeAttribute("tratamientosTemp");
        //request.setAttribute("gato", g.getId());
        //request.getRequestDispatcher("/privado/SvHistorial/mostrar_historial").forward(request, response);
        response.sendRedirect(request.getContextPath()+"/privado/SvHistorial/mostrar_historial?gato="+g.getId());
        
    }

    private void altaTratamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("titulo", request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        request.setAttribute("vistaVolver", request.getParameter("vistaVolver"));
        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        System.out.println(request.getParameter("diagnosticoId")+"LN".repeat(120));
        System.out.println(request.getParameter("vistaVolver")+"LN".repeat(120));
        request.setAttribute("contenido", "/privado/altaTratamiento.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void mostrarDiagnostico(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        if(request.getParameter("diagnostico")!=null){
            request.setAttribute("diagnostico", request.getParameter("diagnostico"));
        }
        request.setAttribute("gatoId", request.getParameter("gato"));
        //request.setAttribute("accion", "crearDiagnosticoasf");
        //System.out.println(request.getAttribute("gatoId")+"L".repeat(120));
        request.setAttribute("contenido", "/privado/altaDiagnostico.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void cargarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiagnosticoJpaController daoD = new DiagnosticoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        //GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        //Gato g = daoG.findGato(Long.parseLong(request.getParameter("gatoId")));
        Diagnostico d = daoD.findDiagnostico(Long.valueOf(request.getParameter("diagnostico")));
        List<Tratamiento> listaTratamientos = daoT.obtenerPorDiagnostico(d.getId());
        System.out.println(listaTratamientos);
        request.setAttribute("titulo",d.getDiagnostico());
        request.setAttribute("descripcion",d.getDescripcion());
        request.setAttribute("fechaDiagnostico",d.getFecha_diagnostico());
        request.setAttribute("diagnosticoId",d.getId());
        request.setAttribute("gatoId",request.getParameter("gatoId"));
        System.out.println("SvDiag/cargEdit gatoId: "+request.getParameter("gatoId"));
        HttpSession session = request.getSession();
        //para la session
        session.setAttribute("tratamientosTemp", listaTratamientos);
        //para mostrar en la jsp
        request.setAttribute("listaTratamientos",listaTratamientos);
        request.setAttribute("contenido", "/privado/editarDiagnostico.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }
    
    private void editar(HttpServletRequest request, HttpServletResponse response)
        throws IOException {

        EntityManagerFactory emf =
            (EntityManagerFactory) request.getServletContext().getAttribute("emf");

        DiagnosticoJpaController daoD = new DiagnosticoJpaController(emf);
        TratamientoJpaController daoT = new TratamientoJpaController(emf);

        HttpSession session = request.getSession();

        Long diagnosticoId = Long.valueOf(request.getParameter("diagnosticoId"));
        Long gatoId = Long.valueOf(request.getParameter("gatoId"));

        Diagnostico d = daoD.findDiagnostico(diagnosticoId);

        if (d == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Diagnóstico no encontrado");
            return;
        }

        List<Tratamiento> tratamientosActuales = new ArrayList<>(d.getTratamientos());

        List<Tratamiento> tratamientosSesion =
            (List<Tratamiento>) session.getAttribute("tratamientosTemp");

        if (tratamientosSesion != null) {

            for (Tratamiento t : tratamientosSesion) {

                if (t.getId() == null) {

                    t.setDiagnostico(d);   // relación bidireccional
                    daoT.create(t);        // genera PK

                    tratamientosActuales.add(t);
                }
            }
        }

        d.setTratamientos(tratamientosActuales);

        d.setDiagnostico(request.getParameter("titulo"));
        d.setDescripcion(request.getParameter("descripcion"));

        try {
            daoD.edit(d);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error al editar diagnóstico"
            );
            return;
        }

        session.removeAttribute("tratamientosTemp");

        response.sendRedirect(
            request.getContextPath() +
            "/privado/SvHistorial/mostrar_historial?gato=" + gatoId
        );
    }


    
    private void eliminarTratamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        System.out.println("svdiag elimtrat");
        System.out.println("svdiag eliminartrat vista volver: "+request.getParameter("vistaVolver"));
        HttpSession session = request.getSession();
        List<Tratamiento> lista = (List<Tratamiento>) session.getAttribute("tratamientosTemp");
        
        //si el tratamiento tiene id null, significa que no esta en la db. La eliminacion depende de se esta o no en la db
        System.out.println("svdia elimtrat tratamientoId: "+request.getParameter("tratamientoId"));
        if(request.getParameter("tratamientoId") != null && !request.getParameter("tratamientoId").equals("")){
            Tratamiento t = daoT.findTratamiento(Long.valueOf(request.getParameter("tratamientoId")));
            Diagnostico d = t.getDiagnostico();
            d.getTratamientos().remove(t);
            t.setDiagnostico(null);
            lista.removeIf(v -> v.getId()==t.getId());
            try {
                daoT.destroy(t.getId());
            } catch (Exception e) {
                System.out.println("svdiag elimtrat error");
                e.printStackTrace();
            }
        }else{
            lista.removeIf(v -> v.getId() == null && v.getDescripcion().hashCode() == Integer.parseInt(request.getParameter("tratamientoDescripcion")));            
        }
        
        System.out.println("svdiag elimtrat 1221");
        
        
        request.setAttribute("listaTratamientos", lista);
        
        request.setAttribute("titulo", request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        System.out.println("sv diag elim diagnosticoId: "+request.getParameter("diagnosticoId"));
        if(request.getParameter("diagnosticoId")!=null) request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        request.setAttribute("contenido",request.getParameter("vistaVolver"));
        System.out.println("svdiag elimtrat final");
        request.getRequestDispatcher("/privado/layout.jsp")
               .forward(request, response);
        
    }
    
    
    private void cargarEditarTratamiento(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        System.out.println("AWEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        Tratamiento t = daoT.findTratamiento(Long.valueOf(request.getParameter("tratamientoId")));
        
        
        
        
        request.setAttribute("tratamientoId", request.getParameter("tratamientoId"));
        System.out.println("svtrat cargEdit tratamientoId: " + request.getParameter("tratamientoId"));

        request.setAttribute("gatoId", request.getParameter("gatoId"));
        System.out.println("svtrat cargEdit gatoId: " + request.getParameter("gatoId"));

        request.setAttribute("titulo", request.getParameter("titulo"));
        System.out.println("svtrat cargEdit titulo: " + request.getParameter("titulo"));

        request.setAttribute("descripcion", request.getParameter("descripcion"));
        System.out.println("svtrat cargEdit descripcion: " + request.getParameter("descripcion"));

        request.setAttribute("vistaVolver", request.getParameter("vistaVolver"));
        System.out.println("svtrat cargEdit vistaVolver: " + request.getParameter("vistaVolver"));

        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        System.out.println("svtrat cargEdit diagnosticoId: " + request.getParameter("diagnosticoId"));

        request.setAttribute("fechaInicio", t.getFecha_inicio());
        System.out.println("svtrat cargEdit fechaInicio (t.getFecha_inicio()): " + t.getFecha_inicio());

        request.setAttribute("fechaFin", t.getFecha_fin());
        System.out.println("svtrat cargEdit fechaFin (t.getFecha_fin()): " + t.getFecha_fin());

        request.setAttribute("descripcionTratamiento", t.getDescripcion());
        System.out.println("svtrat cargEdit descripcionTratamiento (t.getDescripcion()): " + t.getDescripcion());

        request.setAttribute("abandonoTratamiento", t.getAbandono_tratamiento());
        System.out.println("svtrat cargEdit abandonoTratamiento (t.getAbandono_tratamiento()): " + t.getAbandono_tratamiento());
        request.setAttribute("contenido","/privado/editarTratamiento.jsp");
        System.out.println("svtrat cargEdit Vista volver: "+request.getParameter("vistaVolver"));
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
        
            
    }
}
