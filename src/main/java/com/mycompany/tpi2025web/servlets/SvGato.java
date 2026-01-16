/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.google.zxing.WriterException;
import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025web.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025web.model.Gato;
import com.mycompany.tpi2025web.model.Zona;
import com.mycompany.tpi2025web.model.enums.EstadoSalud;
import com.mycompany.tpi2025web.utils.QRUtils;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvGato", urlPatterns = {"/SvGato/cargar_gatos_postular","/SvGato/postularse","/SvGato/ver_qr", "/SvGato/cargar_alta", "/SvGato/listar", "/SvGato/crear", "/SvGato/editar", "/SvGato/cargar_editar", "/SvGato/eliminar"})
public class SvGato extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/cargar_editar")) {
            cargarEditar(request, response);
        } else if (uri.endsWith("/cargar_alta")) {
            cargarAlta(request, response);
        } else if (uri.endsWith("/listar")) {
            listar(request, response);
        } else if (uri.endsWith("/ver_qr")) {
            mostrarQR(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/crear")) {
            crear(request, response);
        } else if (uri.endsWith("/editar")) {
            editar(request, response);
        } else if (uri.endsWith("/eliminar")) {
            eliminar(request, response);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void cargarAlta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZonaJpaController dao = new ZonaJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));

        List<Zona> zonas = dao.findZonaEntities();
        request.setAttribute("listaZonas", zonas);

        request.setAttribute("contenido", "/altaGato.jsp");
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }

    private void crear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ZonaJpaController daoZ = new ZonaJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));

        Gato g = new Gato();
        g.setNombre(request.getParameter("nombre"));
        g.setColor(request.getParameter("color"));
        g.setCaracteristicas(request.getParameter("caracteristicas"));
        g.setEstadoSalud(EstadoSalud.valueOf(request.getParameter("estadoSalud")));
        g.setHistorial();

        Zona z = daoZ.findZona(Long.parseLong(request.getParameter("zonaId")));
        g.setZona(z);

        try {
            daoG.create(g);
            g.setQr("QR" + g.getId());
            //g.setQr(QRUtils.generarQRBase64(g.toString()));

            daoG.edit(g);
            System.out.println("svgato crear try 4");

        } catch (Exception ex) {
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/SvGato/cargar_alta");

    }

    private void editar(HttpServletRequest request, HttpServletResponse response) {
        GatoJpaController dao = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        ZonaJpaController daoZ = new ZonaJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));

        Gato g = dao.findGato(Long.parseLong(request.getParameter("idGatoOriginal")));
        g.setNombre(request.getParameter("nombre"));
        g.setColor(request.getParameter("color"));
        g.setCaracteristicas(request.getParameter("caracteristicas"));
        g.setEstadoSalud(EstadoSalud.valueOf(request.getParameter("estadoSalud")));

        Zona z = daoZ.findZona(Long.parseLong(request.getParameter("zonaId")));
        g.setZona(z);

        try {
            dao.edit(g);
            response.sendRedirect(request.getContextPath() + "/SvGato/listar");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cargarEditar(HttpServletRequest request, HttpServletResponse response) {

        GatoJpaController dao = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        ZonaJpaController daoZ = new ZonaJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));
        Gato gatoEditar = dao.findGato(Long.parseLong(request.getParameter("gato")));
        request.setAttribute("gatoEditar", gatoEditar);

        // para tener la lista de zonas
        List<Zona> zonas = daoZ.findZonaEntities();
        request.setAttribute("listaZonas", zonas);
        request.setAttribute("contenido", "/editarGato.jsp");

        try {
            request.getRequestDispatcher("/layout.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) {
        long idGato = Long.parseLong(request.getParameter("gato"));

        GatoJpaController dao
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        try {
            dao.destroy(idGato);
            response.sendRedirect(request.getContextPath() + "/SvGato/listar");
        } catch (IOException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController dao
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        List<Gato> listaGatos = dao.findGatoEntities();

        request.setAttribute("listaGatos", listaGatos);
        request.setAttribute("contenido", "/verGatos.jsp");
        request.getRequestDispatcher("/layout.jsp").forward(request, response);
    }

    private void mostrarQR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String textoQR = request.getParameter("gatoToString");

        

        String qrBase64=null;
        try {
            qrBase64 = QRUtils.generarQRBase64(textoQR);
        } catch (WriterException ex) {
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("qrBase64", qrBase64);

        request.getRequestDispatcher("/SvGato/listar").forward(request, response);

    }
}
