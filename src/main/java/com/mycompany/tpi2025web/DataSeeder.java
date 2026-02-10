/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025web;

import com.mycompany.tpi2025web.DAOImpl.DiagnosticoJpaController;
import com.mycompany.tpi2025web.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025web.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025web.DAOImpl.TratamientoJpaController;
import com.mycompany.tpi2025web.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025web.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025web.model.Administrador;
import com.mycompany.tpi2025web.model.Diagnostico;
import com.mycompany.tpi2025web.model.Familia;
import com.mycompany.tpi2025web.model.Gato;
import com.mycompany.tpi2025web.model.Hogar;
import com.mycompany.tpi2025web.model.Postulacion;
import com.mycompany.tpi2025web.model.Usuario;
import com.mycompany.tpi2025web.model.Veterinario;
import com.mycompany.tpi2025web.model.Voluntario;
import com.mycompany.tpi2025web.model.Zona;
import com.mycompany.tpi2025web.model.enums.EstadoSalud;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DataSeeder {

    public static void seed(EntityManagerFactory emf) {

        UsuarioJpaController dao = new UsuarioJpaController(emf);
        GatoJpaController daoG = new GatoJpaController(emf);
        ZonaJpaController daoZ = new ZonaJpaController(emf);
        TratamientoJpaController daoT = new TratamientoJpaController(emf);
        PostulacionJpaController daoP = new PostulacionJpaController(emf);
        DiagnosticoJpaController daoD = new DiagnosticoJpaController(emf);

        List<Zona> zonas = Arrays.asList(
                new Zona("Zona Norte - Barrio 1"),
                new Zona("Zona Norte - Barrio 2"),
                new Zona("Zona Sur - Barrio 3"),
                new Zona("Zona Este - Barrio 4"),
                new Zona("Zona Oeste - Barrio 5"),
                new Zona("Zona Centro 1"),
                new Zona("Zona Centro 2"),
                new Zona("Zona Costanera"),
                new Zona("Zona Rural 1"),
                new Zona("Zona Rural 2"),
                new Zona("Zona Industrial")
        );

        for (Zona z : zonas) {
            try {
                daoZ.create(z);
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }

        List<Usuario> admins = Arrays.asList(
                new Administrador("Mauricio Aquino", "a", "3764632667", "a"),
                new Administrador("Carlos Ruiz", "securepass", "3764876543", "carlosr"),
                new Administrador("Ana Martinez", "anaadmin", "3764765432", "anamtz"),
                new Administrador("Pedro Lopez", "pedro2024", "3764654321", "pedrolpz"),
                new Administrador("Lucia Romero", "romero123", "3764555111", "lromero"),
                new Administrador("Santiago Benitez", "adminbenitez", "3764222333", "sbenitez")
        );

        List<Usuario> vets = Arrays.asList(
                new Veterinario("Dra. Sofia Ramirez", "v", "3764123456", "v"),
                new Veterinario("Dr. Jorge Silva", "jorgevet", "3764234567", "jorgesilva"),
                new Veterinario("Dra. Maria Torres", "mvetcare", "3764345678", "mariatorres"),
                new Veterinario("Dr. Luis Fernandez", "luisvet", "3764456789", "luisfern"),
                new Veterinario("Dra. Valeria Nuñez", "vnunez", "3764556677", "vnuñez"),
                new Veterinario("Dr. Pablo Rios", "priosvet", "3764667788", "prios")
        );

        List<Usuario> vols = Arrays.asList(
                new Voluntario("Juan Perez", "vo", "3764567890", "vo"),
                new Voluntario("Maria Garcia", "mvolunteer", "3764678901", "mariagarc"),
                new Voluntario("Roberto Diaz", "robvol", "3764789012", "robertodz"),
                new Voluntario("Elena Castro", "elenavol", "3764890123", "elenacast"),
                new Voluntario("Sofia Rossi", "srossi", "3764991122", "srossiv"),
                new Voluntario("Miguel Suarez", "msuarez", "3764112233", "msuarezv"),
                new Voluntario("Laura Gimenez", "lgvol", "3764223344", "lgvol")
        );

        List<Usuario> familias = Arrays.asList(
                new Familia("Familia Rodriguez", "f", "3764901234", "f"),
                new Familia("Familia Herrera", "famherrera", "3764012345", "famherrera"),
                new Familia("Familia Morales", "fammorales", "3764123450", "fammorales"),
                new Familia("Familia Rios", "famrios", "3764234501", "famrios"),
                new Familia("Familia Benitez", "fambenitez", "3764345602", "fambenitez"),
                new Familia("Familia Cabrera", "famcabrera", "3764456703", "famcabrera")
        );

        List<Usuario> hogares = Arrays.asList(
                new Hogar("Hogar San José", "h", "3764123456", "h", false),
                new Hogar("Hogar Santa María", "hogstmaria", "3764234567", "hogstmaria", false),
                new Hogar("Hogar Esperanza", "hogesperanza", "3764345678", "hogesperanza", false),
                new Hogar("Hogar Nuevo Amanecer", "hogamanecer", "3764456789", "hogamanecer", true),
                new Hogar("Hogar Los Patitas", "hogpatitas", "3764567899", "hogpatitas", false),
                new Hogar("Hogar Refugio Gato Feliz", "gatofeliz", "3764678909", "gatofeliz", true)
        );

        for (Usuario u : admins) try {
            dao.create(u);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        for (Usuario u : vets) try {
            dao.create(u);
        } catch (Exception ignored) {
        }
        for (Usuario u : vols) try {
            dao.create(u);
        } catch (Exception ignored) {
        }
        for (Usuario u : familias) try {
            dao.create(u);
        } catch (Exception ignored) {
        }
        for (Usuario u : hogares) try {
            dao.create(u);
        } catch (Exception ignored) {
        }

        List<Diagnostico> diagnosticosBase = Arrays.asList(
                new Diagnostico("Control general", "Salud óptima, parámetros normales", LocalDate.now()),
                new Diagnostico("Herida leve en pata", "Requiere limpieza y curación diaria", LocalDate.now()),
                new Diagnostico("Fiebre", "Sospecha de infección viral", LocalDate.now()),
                new Diagnostico("Dificultad al respirar", "Posible afección respiratoria", LocalDate.now()),
                new Diagnostico("Sangrado nasal", "Debe monitorearse, posible alergia", LocalDate.now()),
                new Diagnostico("Conjuntivitis", "Ojos irritados, requiere gotas oftálmicas", LocalDate.now()),
                new Diagnostico("Otitis", "Inflamación del oído, requiere medicación", LocalDate.now()),
                new Diagnostico("Lesión en cola", "Golpe leve, necesita control", LocalDate.now()),
                new Diagnostico("Diarrea", "Probable desorden gastrointestinal", LocalDate.now()),
                new Diagnostico("Vómitos", "Posible intoxicación leve", LocalDate.now()),
                new Diagnostico("Dermatitis", "Piel irritada, requiere tratamiento tópico", LocalDate.now()),
                new Diagnostico("Pulgas", "Requiere antiparasitario", LocalDate.now()),
                new Diagnostico("Garrapatas", "Retiro manual y medicación", LocalDate.now()),
                new Diagnostico("Infección urinaria", "Necesita antibióticos", LocalDate.now()),
                new Diagnostico("Anemia leve", "Seguimiento y buena alimentación", LocalDate.now()),
                new Diagnostico("Deshidratación", "Administrar suero", LocalDate.now()),
                new Diagnostico("Golpe", "Moretón leve, requiere reposo", LocalDate.now()),
                new Diagnostico("Fractura leve", "Requiere férula y control", LocalDate.now()),
                new Diagnostico("Obstrucción intestinal", "Necesita tratamiento urgente", LocalDate.now()),
                new Diagnostico("Estrés", "Cambios de conducta, necesita ambiente tranquilo", LocalDate.now())
        );

        List<Gato> gatosExtra = Arrays.asList(
                new Gato("QR21", "mango", "naranja", null, "naranja brillante", EstadoSalud.SANO),
                new Gato("QR22", "azul", "gris", null, "gris plomo", EstadoSalud.ENFERMO),
                new Gato("QR23", "candy", "tricolor", null, "tricolor vibrante", EstadoSalud.ESTERILIZADO),
                new Gato("QR24", "ruby", "rojo", null, "rojizo suave", EstadoSalud.EN_TRATAMIENTO),
                new Gato("QR25", "storm", "negro", null, "negro humo", EstadoSalud.ENFERMO),
                new Gato("QR26", "pearl", "blanco", null, "blanco brillante", EstadoSalud.SANO),
                new Gato("QR27", "sky", "celeste", null, "celeste claro", EstadoSalud.ESTERILIZADO),
                new Gato("QR28", "dusty", "gris", null, "gris marrón", EstadoSalud.EN_TRATAMIENTO),
                new Gato("QR29", "lemon", "amarillo", null, "amarillo claro", EstadoSalud.SANO),
                new Gato("QR30", "coal", "negro", null, "negro mate", EstadoSalud.ENFERMO),
                new Gato("QR31", "peanut", "marrón", null, "marrón tostado", EstadoSalud.SANO),
                new Gato("QR32", "mimi", "blanco", null, "blanco puro", EstadoSalud.ESTERILIZADO),
                new Gato("QR33", "kira", "gris", null, "gris plateado", EstadoSalud.EN_TRATAMIENTO),
                new Gato("QR34", "rocky", "naranja", null, "naranja fuerte", EstadoSalud.ENFERMO),
                new Gato("QR35", "bella", "tricolor", null, "mosaico perfecto", EstadoSalud.SANO),
                new Gato("QR36", "choco", "marrón", null, "marrón chocolate", EstadoSalud.EN_TRATAMIENTO),
                new Gato("QR37", "neko", "negro", null, "negro brillante", EstadoSalud.SANO),
                new Gato("QR38", "iris", "gris", null, "gris con manchas blancas", EstadoSalud.ESTERILIZADO),
                new Gato("QR39", "oliver", "naranja", null, "pelaje suave", EstadoSalud.ENFERMO),
                new Gato("QR40", "loki", "negro", null, "ojos verdes intensos", EstadoSalud.SANO)
        );

        for (int i = 0; i < gatosExtra.size(); i++) {
            Gato g = gatosExtra.get(i);

            g.setZona(zonas.get(i % zonas.size())); // para que tengan zona
            g.setHistorial();                       // crear historial

            try {
                daoG.create(g);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Diagnostico d = diagnosticosBase.get(i); // diagnóstico correspondiente

            g.getHistorial().addDiagnostico(d);

            try {
                daoD.create(d);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        List<Postulacion> postulaciones = Arrays.asList(
                new Postulacion("famrodriguez", 1),
                new Postulacion("a", 3),
                new Postulacion("carlosr", 5),
                new Postulacion("sofiveter", 7),
                new Postulacion("juanperezv", 11),
                new Postulacion("famherrera", 9),
                new Postulacion("famrios", 10),
                new Postulacion("hogsanjose", 11),
                new Postulacion("gatofeliz", 12),
                new Postulacion("hogpatitas", 13),
                new Postulacion("famcabrera", 14),
                new Postulacion("famrodriguez", 5),
                new Postulacion("msuarezv", 19),
                new Postulacion("lgvol", 20),
                new Postulacion("fambenitez", 5),
                new Postulacion("famherrera", 1),
                new Postulacion("robertodz", 2),
                new Postulacion("elenacast", 3),
                new Postulacion("famherrera", 4),
                new Postulacion("gatofeliz", 5),
                new Postulacion("hogpatitas", 11),
                new Postulacion("famrios", 6),
                new Postulacion("anamtz", 5)
        );

        for (Postulacion px : postulaciones) {
            try {
                daoP.create(px);
            } catch (Exception ignored) {
            }
        }

        System.out.println("Datos base cargados correctamente");
    }
}
