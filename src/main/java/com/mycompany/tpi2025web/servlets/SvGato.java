/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.google.zxing.WriterException;
import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025web.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025web.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025web.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025web.model.Familia;
import com.mycompany.tpi2025web.model.Gato;
import com.mycompany.tpi2025web.model.Hogar;
import com.mycompany.tpi2025web.model.Postulacion;
import com.mycompany.tpi2025web.model.Usuario;
import com.mycompany.tpi2025web.model.Zona;
import com.mycompany.tpi2025web.model.enums.EstadoSalud;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvGato", urlPatterns = {"/privado/SvGato/adoptar_gato", "/privado/SvGato/cargar_gatos_elegir", "/privado/SvGato/cargar_gatos_postular", "/privado/SvGato/postularse", "/privado/SvGato/ver_qr", "/privado/SvGato/cargar_alta", "/privado/SvGato/listar", "/privado/SvGato/crear", "/privado/SvGato/editar", "/privado/SvGato/cargar_editar", "/privado/SvGato/eliminar"})
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
        } else if (uri.endsWith("/cargar_gatos_elegir")) {
            cargarGatosEligir(request, response);
        }
        System.out.println("svgata dogetfin");

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
        } else if (uri.endsWith("/adoptar_gato")) {
            adoptarGato(request, response);
        }
        System.out.println("svgata dopostfin");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void cargarAlta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ZonaJpaController dao = new ZonaJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));

        List<Zona> zonas = dao.findZonaEntities();
        request.setAttribute("listaZonas", zonas);

        request.setAttribute("contenido", "/privado/altaGato.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void crear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ZonaJpaController daoZ = new ZonaJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));
        GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));

        Map<String, String> errores = new HashMap<>();

        //obtención de los datos
        String nombreG = request.getParameter("nombre").trim();
        String colorG = request.getParameter("color").trim();
        String caracteristicasG = request.getParameter("caracteristicas").trim();
        String estadoSaludG = request.getParameter("estadoSalud").trim();
        Long zonaIdG = 0L;
        //se utiliza try porque si el string obtenido no es un numero lanza una excepcion
        try {
            zonaIdG = Long.valueOf(request.getParameter("zonaId"));
        } catch (Exception e) {
            //esta asignacion sirve luego para la validacion
            zonaIdG = null;
        }

        //reiyeccion
        request.setAttribute("nombreG", nombreG);
        request.setAttribute("colorG", colorG);
        request.setAttribute("caracteristicasG", caracteristicasG);
        request.setAttribute("estadoSaludG", estadoSaludG);
        request.setAttribute("zonaIdG", zonaIdG);
        request.setAttribute("listaZonas", daoZ.findZonaEntities());

        //validacion de datos
        if (colorG == null || colorG.isBlank()) {
            errores.put("colorGato", "El color es obligatorio");
        }
        if (zonaIdG == null) {
            errores.put("zonaIdGato", "Debe seleccionar una zona");
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/altaGato.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        Gato g = new Gato();
        g.setNombre(nombreG);
        g.setColor(colorG);
        g.setCaracteristicas(caracteristicasG);
        g.setEstadoSalud(EstadoSalud.valueOf(estadoSaludG));
        g.setHistorial();

        Zona z = daoZ.findZona(zonaIdG);
        g.setZona(z);

        try {
            daoG.create(g);
            g.setQr("QR" + g.getId());

            daoG.edit(g);
            System.out.println("svgato crear try 4");
            HttpSession s = request.getSession(false);
            s.setAttribute("mensajeExito", "El gato se registró exitosamente");
        } catch (Exception ex) {
            errores.put("errorGeneral", "El gato no se ha podido registrar");
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/altaGato.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvGato/cargar_alta");

    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController dao = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        ZonaJpaController daoZ = new ZonaJpaController((EntityManagerFactory) getServletContext().getAttribute("emf"));

        //obtencion de datos
        String idGatoOriginal = request.getParameter("idGatoOriginal");
        String nombreG = request.getParameter("nombre");
        String colorG = request.getParameter("color");
        String caracteristicasG = request.getParameter("caracteristicas");
        String estadoSaludG = request.getParameter("estadoSalud");
        String zonaIdG = request.getParameter("zonaId");
        Zona zonaG = null;
        try {
            zonaG = daoZ.findZona(Long.valueOf(zonaIdG));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gato gInyeccion = new Gato("", nombreG, colorG, zonaG, caracteristicasG, EstadoSalud.valueOf(estadoSaludG));
        gInyeccion.setId(Long.valueOf(idGatoOriginal));

        //reinyeccion
        request.setAttribute("gatoEditar", gInyeccion);
        request.setAttribute("listaZonas", daoZ.findZonaEntities());

        //validacion
        if (colorG == null || colorG.isBlank()) {
            request.setAttribute("errorColorGato", "El color es obligatorio");
            request.setAttribute("contenido", "/privado/editarGato.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        Gato g = dao.findGato(Long.parseLong(idGatoOriginal));
        g.setNombre(nombreG);
        g.setColor(colorG);
        g.setCaracteristicas(caracteristicasG);
        g.setEstadoSalud(EstadoSalud.valueOf(estadoSaludG));

        Zona z = daoZ.findZona(Long.parseLong(zonaIdG));
        g.setZona(z);

        HttpSession s = request.getSession(false);

        try {
            dao.edit(g);
            response.sendRedirect(request.getContextPath() + "/privado/SvGato/listar");
            s.setAttribute("mensajeExito", "El gato se modificó exitosamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            s.setAttribute("mensajeFallo", "Error al modificar el gato");
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
        request.setAttribute("contenido", "/privado/editarGato.jsp");

        try {
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long idGato = Long.parseLong(request.getParameter("gato"));

        GatoJpaController dao
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        HttpSession s = request.getSession(false);
        try {
            dao.destroy(idGato);
            s.setAttribute("mensajeExito", "El gato ha sido eliminado");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
            s.setAttribute("mensajeFallo", "No se pudo eliminar al gato");
        }
        response.sendRedirect(request.getContextPath() + "/privado/SvGato/listar");
    }

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GatoJpaController dao
                = new GatoJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        List<Gato> listaGatos = dao.findGatoEntities();

        request.setAttribute("listaGatos", listaGatos);
        request.setAttribute("contenido", "/privado/verGatos.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void mostrarQR(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String textoQR = request.getParameter("gatoToString");

        String qrBase64 = null;
        try {
            qrBase64 = Utils.generarQRBase64(textoQR);
        } catch (WriterException ex) {
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SvGato.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("qrBase64", qrBase64);

        request.getRequestDispatcher("/privado/SvGato/listar").forward(request, response);

    }

    private void cargarGatosEligir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PostulacionJpaController daoP
                = new PostulacionJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        List<Postulacion> listaPostulaciones = daoP.findPostulacionesByPostulante(request.getParameter("usuario"));
        request.setAttribute("listaPostulaciones", listaPostulaciones);
        request.setAttribute("contenido", "/privado/verPostulacionAElegir.jsp");
        System.out.println("svgato carggatoelegi");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void adoptarGato(HttpServletRequest request, HttpServletResponse response) throws IOException {

        EntityManagerFactory emf
                = (EntityManagerFactory) request.getServletContext().getAttribute("emf");

        PostulacionJpaController daoP = new PostulacionJpaController(emf);
        GatoJpaController daoG = new GatoJpaController(emf);
        UsuarioJpaController daoU = new UsuarioJpaController(emf);

        Long postulacionId = Long.valueOf(request.getParameter("postulacionId"));

        Postulacion pSel = daoP.findPostulacion(postulacionId);
        if (pSel == null) {
            throw new IllegalStateException("Postulación inexistente");
        }

        Gato gSel = daoG.findGato(pSel.getIdGato());
        Usuario adoptante = daoU.findUsuario(pSel.getPostulante());

        if (gSel == null || adoptante == null) {
            throw new IllegalStateException("Datos inválidos");
        }

        if (gSel.getUsuario() != null) {
            throw new IllegalStateException("El gato ya fue adoptado");
        }

        // ==========================
        // ASIGNAR ADOPTANTE
        // ==========================
        gSel.setUsuario(adoptante);

        if (adoptante instanceof Familia f) {
            f.addGato(gSel);
            f.setAptoAdopcion(false);
        } else if (adoptante instanceof Hogar h) {
            h.addGato(gSel);
            h.setAptoAdopcion(false);
        }

        try {
            // Persistir owning side
            daoG.edit(gSel);
            daoU.edit(adoptante);

            // ==========================
            // ELIMINAR TODAS LAS POSTULACIONES DEL GATO
            // ==========================
            List<Postulacion> postulaciones
                    = daoP.findPostulacionesPorGato(gSel.getId());

            for (Postulacion p : postulaciones) {
                daoP.destroy(p.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al adoptar gato", e);
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvUsuario/cargar_usuarios_aptos");

    }

}
