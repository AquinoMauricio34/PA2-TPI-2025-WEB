/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.FamiliaJpaController;
import com.mycompany.tpi2025web.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025web.model.Familia;
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

/**
 *
 * @author aquin
 */
@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin/login", "/SvLogin/logout", "/SvLogin/registrar_familia"})
public class SvLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // NO crea sesión nueva

        if (session != null) {
            session.invalidate(); // 🔥 mata la sesión
        }

        // Redirigir al login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();

        if (uri.endsWith("/login")) {
            login(request, response);
        } else if (uri.endsWith("/registrar_familia")) {
            registrarFamilia(request, response);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        List<Usuario> listaUsuario = dao.findUsuarioEntities();
        String nombreUsuario = request.getParameter("nombreUsuario");
        String contrasenia = request.getParameter("contrasenia");
        boolean validacion = listaUsuario.stream().anyMatch(u -> u.getNombreUsuario().equals(nombreUsuario) && u.getContrasena().equals(contrasenia));
        if (validacion) {
            HttpSession s = request.getSession(true);
            s.setAttribute("usuario", nombreUsuario);
            s.setAttribute("tipoUsuarioSesion", dao.findUsuario(nombreUsuario).getTipoUsuario());
            //response.sendRedirect("index.jsp");
            response.sendRedirect(
                    request.getContextPath() + "/privado/SvUsuario/cargar_mis_datos"
            );
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private void registrarFamilia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FamiliaJpaController dao = new FamiliaJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        Familia nuevaFamilia = new Familia(request.getParameter("nombre"), request.getParameter("contrasenia"), request.getParameter("telefono"), request.getParameter("nombreUsuario"));
        try {
            dao.create(nuevaFamilia);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }

}
