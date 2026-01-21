/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.TratamientoJpaController;
import com.mycompany.tpi2025web.model.Tratamiento;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvTratamiento", urlPatterns = {"/privado/SvTratamiento/crear","/privado/SvTratamiento/eliminar","/privado/SvTratamiento/cargar_editar","/privado/SvTratamiento/editar"})
public class SvTratamiento extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        System.out.println("svtrat ACCION: "+accion);

        if("cargar_editar".equals(accion)) {
            cargarEditar(request, response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        System.out.println("svtrat uri: "+uri);
        if(uri.endsWith("/crear")){
            crear(request,response);
        }else if(uri.endsWith("/editar")){
            editar(request,response);
        }else if(uri.endsWith("/cargar_editar")){
            cargarEditar(request,response);
        }
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void crear(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {
        
        HttpSession session = request.getSession();
        System.out.println("SESSION ID = " + session.getId());
        List<Tratamiento> lista = (List<Tratamiento>) session.getAttribute("tratamientosTemp");

        // Si aún no existe, crearla
        if (lista == null) {
            System.out.println("-----------------Lista null se crea nueva");
            lista = new ArrayList<>();
            session.setAttribute("tratamientosTemp", lista);
        }
        
        String fechaInicio = request.getParameter("fechaInicio");
        String fechaFin = request.getParameter("fechaFin");
        String descripcion = request.getParameter("descripcionTratamiento");

        Tratamiento t = new Tratamiento(descripcion, fechaInicio, fechaFin);
        t.setAbandono_tratamiento(request.getParameter("abandono") != null);

        request.setAttribute("tratamiento", t);
        
        lista.add(t);
        request.setAttribute("listaTratamientos", lista);

        request.setAttribute("titulo", request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        request.setAttribute("contenido",request.getParameter("vistaVolver"));
        System.out.println("Vista volver svtrat/crear: "+request.getParameter("vistaVolver"));
        request.getRequestDispatcher("/privado/layout.jsp")
               .forward(request, response);
    }

    private void cargarEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, ServletException, IOException {
        System.out.println("AWEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        Tratamiento t = daoT.findTratamiento(Long.valueOf(request.getParameter("tratamientoId")));
        
        
        
        
        request.setAttribute("tratamientoId", request.getParameter("tratamientoId"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        System.out.println("svtrat cargEdit gatoId: "+request.getParameter("gatoId"));
        request.setAttribute("titulo", request.getParameter("titulo"));
        System.out.println("svtrat cargEdit titulo: "+request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("vistaVolver", request.getParameter("vistaVolver"));
        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        request.setAttribute("fechaInicio", t.getFecha_inicio());
        request.setAttribute("fechaFin", t.getFecha_fin());
        request.setAttribute("descripcionTratamiento", t.getDescripcion());
        request.setAttribute("abandonoTratamiento", t.getAbandono_tratamiento());
        request.setAttribute("contenido","/editarTratamiento.jsp");
        System.out.println("svtrat cargEdit Vista volver: "+request.getParameter("vistaVolver"));
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
        
            
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TratamientoJpaController daoT = new TratamientoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        Tratamiento t = daoT.findTratamiento(Long.valueOf(request.getParameter("tratamientoId")));
        t.setDescripcion(request.getParameter("descripcionTratamiento"));
        t.setFecha_inicio(request.getParameter("fechaInicio"));
        t.setFecha_fin(request.getParameter("fechaFin"));
        t.setAbandono_tratamiento(request.getParameter("abandono") != null);
        
        HttpSession session = request.getSession();
        System.out.println("SESSION ID = " + session.getId());
        List<Tratamiento> lista = (List<Tratamiento>) session.getAttribute("tratamientosTemp");
        
        lista.set(lista.indexOf(lista.stream().filter(v -> v.getId() == t.getId()).findFirst().orElse(null)), t);
        
        try {
            System.out.println("svtrat edit dao antes");
            daoT.edit(t);
            System.out.println("svtrat edit dao despues");
        } catch (Exception e) {
            System.out.println("error svtrar edit");
            e.printStackTrace();
        }
        
        request.setAttribute("listaTratamientos", lista);

        
        request.setAttribute("titulo", request.getParameter("titulo"));
        request.setAttribute("descripcion", request.getParameter("descripcion"));
        request.setAttribute("gatoId", request.getParameter("gatoId"));
        request.setAttribute("diagnosticoId", request.getParameter("diagnosticoId"));
        request.setAttribute("contenido",request.getParameter("vistaVolver"));
        System.out.println("Vistavolver svtrat editar: "+request.getParameter("vistaVolver"));
        request.getRequestDispatcher("/privado/layout.jsp")
               .forward(request, response);
        
        
    }

    
}
