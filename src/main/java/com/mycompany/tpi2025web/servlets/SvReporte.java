/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.ZonaJpaController;
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

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvReporte", urlPatterns = {"/SvReporte"})
public class SvReporte extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ZonaJpaController daoZ
                = new ZonaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        GatoJpaController daoG
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        
        List<Zona> listaZonas = daoZ.findZonaEntities();
        List<List<String>> listaMostrar = new ArrayList<>();

        for (Zona z : listaZonas) {

            String[] partes = z.getLocalizacion().split("/");
            String localizacion = partes[0].trim();

            // Si la zona no tiene gatos, devuelve 0
            Long cantidadGatos = daoG.countByZonaId(z.getId());

            listaMostrar.add(
                    List.of(
                            String.valueOf(z.getId()),
                            localizacion,
                            String.valueOf(cantidadGatos)
                    )
            );
        }

        request.setAttribute("listaZonas", listaMostrar);
        request.setAttribute("gatosEsterilizados", daoG.contarGatosEsterilizados());
        request.setAttribute("gatosAdoptados", daoG.contarGatosAdoptados());
        request.setAttribute("contenido", "/reporte.jsp");
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
