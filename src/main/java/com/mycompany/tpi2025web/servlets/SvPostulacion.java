/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025web.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025web.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025web.model.Familia;
import com.mycompany.tpi2025web.model.Gato;
import com.mycompany.tpi2025web.model.Hogar;
import com.mycompany.tpi2025web.model.Postulacion;
import com.mycompany.tpi2025web.model.Usuario;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvPostulacion", urlPatterns = {"/SvPostulacion/eliminar_postulacion", "/SvPostulacion/cargar_mis_postulaciones", "/SvPostulacion", "/SvPostulacion/cargar_gatos_postular", "/SvPostulacion/postularse"})
public class SvPostulacion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/cargar_gatos_postular")) {
            cargarGatosPostulacion(request, response);
        } else if (uri.endsWith("/cargar_mis_postulaciones")) {
            cargarMisPostulaciones(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/postularse")) {
            postularUsuarioAGato(request, response);
        } else if (uri.endsWith("/eliminar_postulacion")) {
            eliminarPostulacion(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void postularUsuarioAGato(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PostulacionJpaController dao
                = new PostulacionJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        HttpSession s = request.getSession(false);
        Postulacion post = new Postulacion(String.valueOf(s.getAttribute("usuario")), Long.valueOf(request.getParameter("gato")));
        try {
            dao.create(post);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/SvPostulacion/cargar_gatos_postular");

    }

    private void cargarGatosPostulacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController dao
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        PostulacionJpaController daoP
                = new PostulacionJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        HttpSession s = request.getSession(false);
        try {
            if (s.getAttribute("usuario") == null) {
                throw new Exception("No hay usuario en la sesión.");
            }
            List<Gato> listaGatos = dao.findGatoEntities();
            //System.out.println(listaGatos);
            //se quitan los gatos que ya tienen duenio
            listaGatos = listaGatos.stream()
                    .filter(g -> g.getUsuario() == null)
                    .collect(Collectors.toList());
            //System.out.println(listaGatos);
            List<Postulacion> listaPostulaciones = daoP.findPostulacionesByPostulante(String.valueOf(s.getAttribute("usuario")));
            //System.out.println(listaPostulaciones);
            //obtener los idGato de cada postulacion
            List<Long> idsPostulados = listaPostulaciones.stream()
                    .map(Postulacion::getIdGato)
                    .collect(Collectors.toList());
            //System.out.println(listaPostulaciones);
            //filtrar los gatos que NO tengan el mismo id que idsPostulados
            List<Gato> listaFiltrada = listaGatos.stream()
                    .filter(g -> !idsPostulados.contains(g.getId()))
                    .collect(Collectors.toList());

            request.setAttribute("listaGatos", listaFiltrada);

            UsuarioJpaController daoU
                    = new UsuarioJpaController(
                            (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                    );

            Usuario usuarioActual = daoU.findUsuario(String.valueOf(s.getAttribute("usuario")));

            boolean esNoApto = (usuarioActual instanceof Familia familia && !familia.isAptoAdopcion())
                    || (usuarioActual instanceof Hogar hogar && !hogar.isAptoAdopcion());

            if (esNoApto) {
                request.setAttribute("error", "El usuario no es apto para adoptar.");
            }
        } catch (Exception e) {
            request.setAttribute(
                    "error",
                    "No se inició sesión."
            );
            e.printStackTrace();
        }

        request.setAttribute("contenido", "/verGatosPostulacion.jsp");
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }

    private void cargarMisPostulaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostulacionJpaController daoP
                = new PostulacionJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        HttpSession s = request.getSession(false);
        List<Postulacion> listaPostulaciones = daoP.findPostulacionesByPostulante(String.valueOf(s.getAttribute("usuario")));
        request.setAttribute("listaPostulaciones", listaPostulaciones);
        request.setAttribute("contenido", "/verMisPostulaciones.jsp");
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }

    private void eliminarPostulacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PostulacionJpaController daoP
                = new PostulacionJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        try {
            daoP.destroy(Long.valueOf(request.getParameter("postulacionId")));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SvPostulacion.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/SvPostulacion/cargar_mis_postulaciones");

    }

}
