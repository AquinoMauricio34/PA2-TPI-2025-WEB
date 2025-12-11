/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025web.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025web.model.Zona;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvZona", urlPatterns = {"/SvZona/crear", "/SvZona/listar","/SvZona/eliminar"})
public class SvZona extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.endsWith("/listar")) {
            listar(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.endsWith("/crear")) {
            crear(request, response);
        } else if(uri.endsWith("/eliminar")){
            eliminar(request,response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void crear(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ZonaJpaController dao
                = new ZonaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        String nombreZona = request.getParameter("nombreZona");
        String lat = request.getParameter("lat");
        String lng = request.getParameter("lng");
        String nombreFinal = nombreZona + " / " + lat + "," + lng;

        try {
            dao.create(new Zona(nombreFinal));
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/SvPanel?vista=mapa.jsp");
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZonaJpaController dao
                = new ZonaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        List<Zona> listaZonas = dao.findZonaEntities();
        List<List<String>> listaMostrar = new ArrayList<>();
        for (Zona z : listaZonas) {
            String[] partes = z.getLocalizacion().split("\\/");
            listaMostrar.add(new ArrayList<>(List.of(String.valueOf(z.getId()), String.valueOf(partes[0].trim()), String.valueOf(partes[1].trim()))));
        }

        request.setAttribute("listaZonas", listaMostrar);
        request.setAttribute("contenido", "/verZonas.jsp");
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) {
        ZonaJpaController dao
                = new ZonaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );
        long idZona = Long.parseLong(request.getParameter("idZona"));
        System.out.println("ID: "+idZona);
        try {
            dao.destroy(idZona);
            response.sendRedirect(request.getContextPath()+"/SvZona/listar");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SvZona.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SvZona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
