/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.FamiliaJpaController;
import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.HogarJpaController;
import com.mycompany.tpi2025web.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025web.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025web.model.Administrador;
import com.mycompany.tpi2025web.model.Familia;
import com.mycompany.tpi2025web.model.Gato;
import com.mycompany.tpi2025web.model.Hogar;
import com.mycompany.tpi2025web.model.Usuario;
import com.mycompany.tpi2025web.model.Veterinario;
import com.mycompany.tpi2025web.model.Voluntario;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(name = "SvUsuario", urlPatterns = {"/privado/SvUsuario/editar_mi_usuario", "/privado/SvUsuario/habilitar_edicion", "/privado/SvUsuario/cargar_mis_datos", "/privado/SvUsuario/cargar_usuarios_aptos", "/privado/SvUsuario/cambiar_estado", "/privado/SvUsuario/cargar_usuarios_emision", "/privado/SvUsuario/crear", "/privado/SvUsuario/cargar_editar", "/privado/SvUsuario/editar", "/privado/SvUsuario/eliminar", "/privado/SvUsuario/listar"})
public class SvUsuario extends HttpServlet {

    private static final Map<String, Class<? extends Usuario>> TIPOS = Map.of(
            "Administrador", Administrador.class,
            "Veterinario", Veterinario.class,
            "Voluntario", Voluntario.class,
            "Familia", Familia.class,
            "Hogar", Hogar.class
    );

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        if (uri.endsWith("/listar")) {
            listar(request, response);
        } else if (uri.endsWith("/cargar_editar")) {
            carga_editar(request, response);
        } else if (uri.endsWith("/cargar_usuarios_emision")) {
            cargarEmision(request, response);
        } else if (uri.endsWith("/cargar_usuarios_aptos")) {
            cargarUsuariosAptos(request, response);
        } else if (uri.endsWith("/cargar_mis_datos")) {
            cargarMisDatos(request, response);
        } else if (uri.endsWith("/habilitar_edicion")) {
            cargarMisDatosEdicion(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        String uri = request.getRequestURI();
        if (uri.endsWith("/eliminar")) {
            eliminar(request, response);
        } else if (uri.endsWith("/editar")) {
            editar(request, response);
        } else if (uri.endsWith("/crear")) {
            crear(request, response);
        } else if (uri.endsWith("/cambiar_estado")) {
            cambiarEstadoEmision(request, response);
        } else if (uri.endsWith("/editar_mi_usuario")) {
            editarMiUsuario(request, response);
        } else if (uri.endsWith("/cargar_mis_datos")) {
            cargarMisDatos(request, response);
        }
        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tipo = request.getParameter("tipo");
        // admin | veterinario | voluntario | familia

        Class<? extends Usuario> clase = TIPOS.get(tipo);

        if (clase == null) {
            response.sendError(400, "Tipo de usuario inválido");
            return;
        }

        UsuarioJpaController dao
                = new UsuarioJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        if (!"Hogar".equals(tipo)) {
            List<? extends Usuario> lista = dao.findPorClase(clase);
            request.setAttribute("listaUsuarios", lista);
        } else {
            List<Hogar> lista = dao.findPorClase(Hogar.class);
            request.setAttribute("listaUsuarios", lista);
        }

        

        request.setAttribute("titulo", tipo);
        request.setAttribute("contenido", "/privado/verUsuarios.jsp");

        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);

    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usuario = request.getParameter("usuario");
        String tipo = request.getParameter("tipo");

        UsuarioJpaController dao
                = new UsuarioJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        
        HttpSession s = request.getSession(false);
        try {
            dao.destroy(usuario);
            s.setAttribute("mensajeExito", "El usuario se eliminó exitosamente");
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
            s.setAttribute("mensajeFallo", "No se pudo eliminar al usuario");
        }
        response.sendRedirect(request.getContextPath() + "/privado/SvUsuario/listar?tipo=" + tipo);
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        Map<String, String> errores = new HashMap<>();

        //obtener datos
        String nombre = request.getParameter("nombre").trim();
        String telefono = request.getParameter("telefono").trim();
        String nombreUsuario = request.getParameter("nombreUsuario").trim();
        String tipoUsuario = request.getParameter("tipo").trim();
        Usuario usuDatos = new Usuario(nombre, "", telefono, nombreUsuario);

        //reiyeccion
        request.setAttribute("usuarioEditar", usuDatos);
        request.setAttribute("tipo", tipoUsuario);
        //el tipo de usuario como viene como parametro

        //verificacion
        if (nombre == null || nombre.isBlank()) {
            errores.put("errorNombre", "El nombre es obligatorio");
        }
        if (telefono == null || telefono.isBlank()) {
            errores.put("errorTelefono", "El teléfono es obligatorio");
        }

        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/editarUsuario.jsp");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
            return;
        }

        Usuario usu = dao.findUsuario(request.getParameter("usuarioOriginal"));
        usu.setNombre(nombre);
        usu.setTelefono(telefono);

        HttpSession s = request.getSession(false);
        try {
            dao.edit(usu);
            s.setAttribute("mensajeExito", "El usuario se modificó exitosamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            s.setAttribute("mensajeFallo", "Ocurrió un fallo en la modificación del usuario");
        }
        response.sendRedirect(request.getContextPath() + "/privado/SvUsuario/listar?tipo=" + usu.getTipoUsuario());
    }

    private void carga_editar(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger("a").log(Level.SEVERE, "BAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaa");
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        Usuario usuarioEditar = dao.findUsuario(request.getParameter("usuario"));
        request.setAttribute("usuarioEditar", usuarioEditar);
        request.setAttribute("tipo", usuarioEditar.getTipoUsuario());
        request.setAttribute("contenido", "/privado/editarUsuario.jsp");

        

        try {
            Logger.getLogger("a").log(Level.SEVERE, "AAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaa");
            request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void crear(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UsuarioJpaController dao
                = new UsuarioJpaController(
                        (EntityManagerFactory) request.getServletContext().getAttribute("emf")
                );

        // Mapa único de errores
        Map<String, String> errores = new HashMap<>();

        String tipo = request.getParameter("tipo");
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String usuarioCrear = request.getParameter("usuarioCrear");
        String contrasenia = request.getParameter("contrasenia");
        String isTransitorio = request.getParameter("isTransitorio");

        // Reinyectar valores (para no perder datos en el form)
        request.setAttribute("nombre", nombre);
        request.setAttribute("telefono", telefono);
        request.setAttribute("usuarioCrear", usuarioCrear);
        request.setAttribute("tipo", tipo);

        // Validaciones
        if (usuarioCrear == null || usuarioCrear.trim().isEmpty()) {
            errores.put("usuario", "El usuario es obligatorio");
        } else if (dao.findUsuario(usuarioCrear) != null) {
            errores.put("usuario", "El nombre de usuario ya existe");
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre es obligatorio");
        }

        if (telefono == null || telefono.trim().isEmpty()) {
            errores.put("telefono", "El teléfono es obligatorio");
        }

        if (contrasenia == null || contrasenia.trim().isEmpty()) {
            errores.put("contrasenia", "La contraseña es obligatoria");
        }

        if (!TIPOS.containsKey(tipo)) {
            errores.put("tipo", "Tipo de usuario inválido");
        }

        // Si hay errores, volvemos al formulario
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/altaUsuario.jsp");
            request.getRequestDispatcher("/privado/layout.jsp")
                    .forward(request, response);
            return;
        }

        // Crear usuario
        try {
            Class<? extends Usuario> usuarioClass = TIPOS.get(tipo);

            Constructor<? extends Usuario> constructor
                    = !tipo.equals("Hogar")
                    ? usuarioClass.getConstructor(String.class, String.class, String.class, String.class)
                    : usuarioClass.getConstructor(String.class, String.class, String.class, String.class, boolean.class);

            Usuario nuevoUsuario
                    = !tipo.equals("Hogar")
                    ? constructor.newInstance(nombre, contrasenia, telefono, usuarioCrear)
                    : constructor.newInstance(nombre, contrasenia, telefono, usuarioCrear, isTransitorio != null);

            dao.create(nuevoUsuario);

        } catch (NoSuchMethodException e) {
            errores.put("general", "Error interno: constructor no encontrado");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            errores.put("general", "Error al crear el usuario");
        } catch (Exception e) {
            errores.put("general", "Error inesperado al guardar el usuario");
        }

        // Error al crear -> volver al form
        if (!errores.isEmpty()) {
            request.setAttribute("errores", errores);
            request.setAttribute("contenido", "/privado/altaUsuario.jsp");
            request.getRequestDispatcher("/privado/layout.jsp")
                    .forward(request, response);
            return;
        }
        
        response.sendRedirect(request.getContextPath() + "/privado/SvPanel?vista=index.jsp");
    }

    private void cargarEmision(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FamiliaJpaController daoF = new FamiliaJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        HogarJpaController daoH = new HogarJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.addAll(daoF.findFamiliaEntities());
        listaUsuarios.addAll(daoH.findHogarEntities());
        request.setAttribute("listaUsuarios", listaUsuarios);
        request.setAttribute("contenido", "/privado/aptitudUsuarios.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);

    }

    private void cambiarEstadoEmision(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        Usuario usuario = dao.findUsuario(request.getParameter("usuario"));

        if (usuario instanceof Familia) {
            Familia f = (Familia) usuario;
            f.setAptoAdopcion(!f.isAptoAdopcion());

        } else if (usuario instanceof Hogar) {
            Hogar h = (Hogar) usuario;
            h.setAptoAdopcion(!h.isAptoAdopcion());
        }

        try {
            dao.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvUsuario/cargar_usuarios_emision");
    }

    private void cargarUsuariosAptos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FamiliaJpaController daoF = new FamiliaJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        HogarJpaController daoH = new HogarJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.addAll(daoF.findFamiliaEntities().stream().filter(e -> e.isAptoAdopcion()).collect(Collectors.toList()));
        listaUsuarios.addAll(daoH.findHogarEntities().stream().filter(e -> e.isAptoAdopcion()).collect(Collectors.toList()));
        request.setAttribute("listaUsuarios", listaUsuarios);
        request.setAttribute("contenido", "/privado/verAptosAdopcion.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void cargarMisDatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        HttpSession s = request.getSession(false);
        Usuario usuario = dao.findUsuario((String.valueOf(s.getAttribute("usuario"))));
        request.setAttribute("nombreUsuario", usuario.getNombre());
        request.setAttribute("telefonoUsuario", usuario.getTelefono());
        request.setAttribute("nombreUsuUsuario", usuario.getNombreUsuario());
        request.setAttribute("contraseniaUsuario", usuario.getContrasena());
        String tipoSesion = String.valueOf(s.getAttribute("tipoUsuarioSesion"));
        if (tipoSesion.equals("Hogar") || tipoSesion.equals("Familia")) {
            GatoJpaController daoG = new GatoJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
            List<Gato> listaGatos = daoG.findGatosByNombreUsuario(String.valueOf(s.getAttribute("usuario")));
            request.setAttribute("listaGatos", listaGatos);
        }
        request.setAttribute("contenido", "/privado/index.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

    private void editarMiUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        Usuario usuario = dao.findUsuario(request.getParameter("usuario"));
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setContrasenia(request.getParameter("contrasenia"));
        usuario.setTelefono(request.getParameter("telefono"));

        try {
            dao.edit(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/privado/SvUsuario/cargar_mis_datos");
    }

    private void cargarMisDatosEdicion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        HttpSession s = request.getSession(false);
        Usuario usuario = dao.findUsuario((String.valueOf(s.getAttribute("usuario"))));
        request.setAttribute("nombreUsuario", usuario.getNombre());
        request.setAttribute("telefonoUsuario", usuario.getTelefono());
        request.setAttribute("nombreUsuUsuario", usuario.getNombreUsuario());
        request.setAttribute("contraseniaUsuario", usuario.getContrasena());
        request.setAttribute("contenido", "/privado/indexEditar.jsp");
        request.getRequestDispatcher("/privado/layout.jsp").forward(request, response);
    }

}
