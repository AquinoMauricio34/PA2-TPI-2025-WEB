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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin/login", "/SvLogin/logout", "/SvLogin/registrar_familia"})
public class SvLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

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

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        UsuarioJpaController dao = new UsuarioJpaController(
                (EntityManagerFactory) request.getServletContext().getAttribute("emf")
        );

        String nombreUsuario = request.getParameter("nombreUsuario").trim();
        String contrasenia = request.getParameter("contrasenia").trim();

        // Mapa de errores
        Map<String, String> errores = new HashMap<>();

        // Validaciones básicas
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            errores.put("nombreUsuario", "El usuario es obligatorio");
        }

        if (contrasenia == null || contrasenia.isBlank()) {
            errores.put("contrasenia", "La contraseña es obligatoria");
        }

        Usuario usuario = null;

        if (errores.isEmpty()) {
            usuario = dao.findUsuario(nombreUsuario);

            if (usuario == null) {
                errores.put("nombreUsuario", "El usuario no existe");
            } else if (!usuario.getContrasena().equals(contrasenia)) {
                errores.put("contrasenia", "Contraseña incorrecta");
            }
        }

        // ❌ Si hay errores → volver al login
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("nombreUsuario", nombreUsuario);

            request.getRequestDispatcher("/login.jsp")
                    .forward(request, response);
            return;
        }

        // ✅ Login correcto
        HttpSession s = request.getSession(true);
        s.setAttribute("usuario", usuario.getNombreUsuario());
        s.setAttribute("tipoUsuarioSesion", usuario.getTipoUsuario());

        response.sendRedirect(
                request.getContextPath() + "/privado/SvUsuario/cargar_mis_datos"
        );
    }

//    private void registrarFamilia(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        FamiliaJpaController dao = new FamiliaJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
//        Familia nuevaFamilia = new Familia(request.getParameter("nombre"), request.getParameter("contrasenia"), request.getParameter("telefono"), request.getParameter("nombreUsuario"));
//        try {
//            dao.create(nuevaFamilia);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect(request.getContextPath() + "/login.jsp");
//    }
    private void registrarFamilia(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String nombre = request.getParameter("nombre");
        String contrasenia = request.getParameter("contrasenia");
        String telefono = request.getParameter("telefono");
        String nombreUsuario = request.getParameter("nombreUsuario");

        // 🔹 Reinyectamos valores para no perderlos
        request.setAttribute("nombre", nombre);
        request.setAttribute("telefono", telefono);
        request.setAttribute("nombreUsuario", nombreUsuario);

        boolean hayErrores = false;

        // 🔴 Validaciones
        if (nombre == null || nombre.trim().isEmpty()) {
            request.setAttribute("errorNombre", "El nombre de la familia es obligatorio");
            hayErrores = true;
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            request.setAttribute("errorTelefono", "El teléfono es obligatorio");
            hayErrores = true;
        }

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            request.setAttribute("errorUsuario", "El nombre de usuario es obligatorio");
            hayErrores = true;
        }

        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            request.setAttribute("errorContrasenia", "La contraseña es obligatoria");
            hayErrores = true;
        }

        if (hayErrores) {
            request.getRequestDispatcher("/registrarFamiliaLogin.jsp")
                    .forward(request, response);
            return;
        }

        FamiliaJpaController dao
                = new FamiliaJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        // 🔴 Usuario ya existe
        if (dao.findFamilia(nombreUsuario) != null) {
            request.setAttribute("errorUsuario", "El nombre de usuario ya existe");
            request.getRequestDispatcher("/registrarFamiliaLogin.jsp")
                    .forward(request, response);
            return;
        }

        // ✅ Crear familia
        try {
            Familia nuevaFamilia = new Familia(nombre, contrasenia, telefono, nombreUsuario);
            dao.create(nuevaFamilia);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorGeneral", "Error al registrar la familia");
            request.getRequestDispatcher("/registrarFamiliaLogin.jsp")
                    .forward(request, response);
            return;
        }

        // ✅ Todo OK → volver al login
        HttpSession session = request.getSession();
        session.setAttribute("mensajeExito", "Familia registrada correctamente. Ya podés iniciar sesión.");

        response.sendRedirect(request.getContextPath() + "/login.jsp");

    }

}
