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
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvEstudio", urlPatterns = {"/privado/SvEstudio","/privado/SvEstudio/crear_estudio","/privado/SvEstudio/mostrar_campos","/privado/SvEstudio/mostrar_gatos","/privado/SvEstudio/mostrar_estudios_gato","/privado/SvEstudio/eliminar_estudio"})
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
        } else if (uri.endsWith("/mostrar_estudios_gato")) {
            mostrarEstudiosGato(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/crear_estudio")) {
            crearEstudio(request, response);
        } else if (uri.endsWith("/eliminar_estudio")) {
            eliminarEstudio(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void crearEstudio(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EstudioJpaController dao = new EstudioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        HttpSession s = request.getSession(false);

        Map<String, String> errores = new HashMap<>();

        //obtencion de datos
        String gatoId = request.getParameter("gatoId");
        String titulo = request.getParameter("titulo");
        String descripcionEstudio = request.getParameter("descripcion");

        //reiyeccion
        request.setAttribute("gatoId", gatoId);
        request.setAttribute("titulo", titulo);
        request.setAttribute("descripcionEstudio", descripcionEstudio);

        //validacion
        if (titulo == null || titulo.isBlank()) {
            errores.put("titulo", "El titulo es obligatoria");
        }

        if (descripcionEstudio == null || descripcionEstudio.isBlank()) {
            errores.put("descripcion", "La descripción es obligatoria");
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/registrarEstudio.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }
        
        Estudio nuevoEstudio = new Estudio(Long.parseLong(gatoId), titulo, descripcionEstudio);
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

    private void mostrarEstudiosGato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EstudioJpaController dao
                = new EstudioJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        System.out.println("svest mostrarestu gatoId: "+request.getParameter("gato"));
        List<Estudio> listaEstudios = dao.findEstudiosByGatoId(Long.parseLong(request.getParameter("gato")));

        request.setAttribute("gato", request.getParameter("gato"));
        request.setAttribute("listaEstudios", listaEstudios);
        request.setAttribute("contenido", "/privado/verEstudiosGato.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void eliminarEstudio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EstudioJpaController dao
                = new EstudioJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        
        long idGato = dao.findEstudio(Long.parseLong(request.getParameter("estudioId"))).getIdGato();
        
        try {
            dao.destroy(Long.parseLong(request.getParameter("estudioId")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect(request.getContextPath() + "/privado/SvEstudio/mostrar_estudios_gato?gato=" + idGato);
        
    }
}
