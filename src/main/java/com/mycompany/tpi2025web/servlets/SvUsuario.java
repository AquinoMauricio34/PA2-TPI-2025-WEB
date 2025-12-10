/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.tpi2025web.servlets;

import com.mycompany.tpi2025web.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025web.DAOImpl.exceptions.NonexistentEntityException;
import com.mycompany.tpi2025web.model.Administrador;
import com.mycompany.tpi2025web.model.Familia;
import com.mycompany.tpi2025web.model.Usuario;
import com.mycompany.tpi2025web.model.Veterinario;
import com.mycompany.tpi2025web.model.Voluntario;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "SvUsuario", urlPatterns = {"/SvUsuario/crear","/SvUsuario/cargar_editar","/SvUsuario/editar","/SvUsuario/eliminar","/SvUsuario/listar"})
public class SvUsuario extends HttpServlet {

    private static final Map<String, Class<? extends Usuario>> TIPOS = Map.of(
        "Administrador", Administrador.class,
        "Veterinario", Veterinario.class,
        "Voluntario", Voluntario.class,
        "Familia", Familia.class
    );
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        if(uri.endsWith("/listar")) listar(request,response);
        else if(uri.endsWith("/cargar_editar")) carga_editar(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        if(uri.endsWith("/eliminar")) eliminar(request,response);
        else if(uri.endsWith("/editar")) editar(request, response);
        else if(uri.endsWith("/crear")) crear(request,response);
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

        
        UsuarioJpaController dao =
            new UsuarioJpaController(
                (EntityManagerFactory) request.getServletContext().getAttribute("emf")
            );

        List<? extends Usuario> lista = dao.findPorClase(clase);

        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        request.setAttribute("listaUsuarios", lista);
        request.setAttribute("titulo", tipo.toUpperCase());
        request.setAttribute("contenido", "/verUsuarios.jsp");

        request.getRequestDispatcher("/layout.jsp").forward(request, response);

    
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) {
        String usuario = request.getParameter("usuario");
        String tipo = request.getParameter("tipo");

        UsuarioJpaController dao =
            new UsuarioJpaController(
                (EntityManagerFactory) request.getServletContext().getAttribute("emf")
            );
        
        System.out.println(tipo+"--------------------------------------------------------------------------------------------------------");
        try {
            dao.destroy(usuario);
            response.sendRedirect(request.getContextPath()+"/SvUsuario/listar?tipo=" + tipo);
        } catch (IOException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editar(HttpServletRequest request, HttpServletResponse response) {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        
        
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        
        Usuario usu = dao.findUsuario(request.getParameter("usuarioOriginal"));
        usu.setNombre(nombre);
        usu.setTelefono(telefono);
        
        try {
            dao.edit(usu);
            response.sendRedirect(request.getContextPath() + "/SvUsuario/listar?tipo="+usu.getTipoUsuario());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void carga_editar(HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger("a").log(Level.SEVERE, "BAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaa");
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));
        Usuario usuarioEditar = dao.findUsuario(request.getParameter("usuario"));
        request.setAttribute("usuarioEditar", usuarioEditar);
        request.setAttribute("contenido", "/editarAdministrador.jsp");

        try {
            Logger.getLogger("a").log(Level.SEVERE, "AAAAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaa");
            request.getRequestDispatcher("/layout.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SvUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void crear(HttpServletRequest request, HttpServletResponse response) {
        UsuarioJpaController dao = new UsuarioJpaController((EntityManagerFactory) request.getServletContext().getAttribute("emf"));

        String tipo = request.getParameter("tipo");
        String nombre = request.getParameter("nombre");
        String telefono = request.getParameter("telefono");
        String usuario = request.getParameter("usuario");
        String contrasenia = request.getParameter("contrasenia");

        System.out.println("Creando usuario - Tipo: " + tipo + ", Nombre: " + nombre + 
                           ", Teléfono: " + telefono + ", Usuario: " + usuario);

        try {
            // Crear el usuario según el tipo usando reflexión
            Class<? extends Usuario> usuarioClass = TIPOS.get(tipo);
            if (usuarioClass == null) {
                throw new IllegalArgumentException("Tipo de usuario no válido: " + tipo);
            }

            // Obtener el constructor con los parámetros necesarios
            Constructor<? extends Usuario> constructor = usuarioClass.getConstructor(
                String.class, // nombre
                String.class, // contrasenia
                String.class, // telefono
                String.class  // nombreUsuario
            );

            // Crear la instancia
            Usuario nuevoUsuario = constructor.newInstance(nombre, contrasenia, telefono, usuario);

            // Persistir en la base de datos
            dao.create(nuevoUsuario);

            System.out.println("Usuario creado exitosamente: " + usuario);

        } catch (NoSuchMethodException e) {
            System.err.println("Error: La clase " + TIPOS.get(tipo) + " no tiene el constructor esperado");
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException | 
                 IllegalArgumentException | InvocationTargetException e) {
            System.err.println("Error al crear instancia del usuario");
            e.printStackTrace();
        } catch (Exception ex) {
            System.err.println("Error general al crear usuario");
            ex.printStackTrace();
        }

        try {
            response.sendRedirect(request.getContextPath() + "/SvPanel?vista=index.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
