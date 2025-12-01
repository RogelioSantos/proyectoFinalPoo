import java.util.*;
import java.util.stream.Collectors;

public class SistemaTicketing {
    private static List<Incidencia> incidencias = new ArrayList<>();
    private static List<Tecnico> tecnicos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    // Usuario actual para el ejemplo
    private static Usuario usuarioActual = new Estudiante("S001", "Juan Perez");

    public static void main(String[] args) {
        // Inicializar algunos técnicos de ejemplo
        tecnicos.add(new Tecnico("T001", "Ing. López", "Hardware"));
        tecnicos.add(new Tecnico("T002", "Ing. García", "Software"));
        tecnicos.add(new Tecnico("T003", "Ing. Martínez", "Redes"));

        int opcion = 0;
        do {
            mostrarMenuPrincipal();
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1: registrarIncidencia(); break;
                case 2: menuConsultarIncidencias(); break;
                case 3: atenderIncidencia(); break;
                case 4: verHistorialIncidencia(); break;
                case 5: generarReporte(); break;
                case 6: gestionarTecnicos(); break;
                case 0: System.out.println("\n¡Hasta luego! Saliendo del sistema..."); break;
                default: System.out.println("\n Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA TICKETING LAB-LIS                    ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║  1. Registrar Nueva Incidencia                   ║");
        System.out.println("║  2. Consultar/Filtrar Incidencias                ║");
        System.out.println("║  3. Atender Incidencia (Estado/Prioridad/Técnico)║");
        System.out.println("║  4. Ver Historial de Incidencia                  ║");
        System.out.println("║  5. Generar Reporte                              ║");
        System.out.println("║  6. Gestionar Técnicos                           ║");
        System.out.println("║  0. Salir                                        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarIncidencia() {
        System.out.println("\n--- REGISTRAR NUEVA INCIDENCIA ---");
        
        System.out.print("ID del Equipo: ");
        String equipo = scanner.nextLine().trim();
        if (equipo.isEmpty()) {
            System.out.println(" El ID del equipo no puede estar vacío.");
            return;
        }

        System.out.print("Descripción del fallo: ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) {
            System.out.println(" La descripción no puede estar vacía.");
            return;
        }
        
        System.out.println("Seleccione el tipo de incidencia:");
        System.out.println("  1. HARDWARE");
        System.out.println("  2. SOFTWARE");
        System.out.println("  3. RED");
        System.out.print("Opción: ");
        
        int tipoOp;
        try {
            tipoOp = Integer.parseInt(scanner.nextLine().trim());
            if (tipoOp < 1 || tipoOp > 3) {
                System.out.println(" Tipo no válido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(" Entrada no válida.");
            return;
        }
        
        TipoIncidencia tipo = TipoIncidencia.values()[tipoOp - 1];

        System.out.println("Seleccione la prioridad:");
        System.out.println("  1. BAJA");
        System.out.println("  2. MEDIA");
        System.out.println("  3. ALTA");
        System.out.print("Opción (Enter para MEDIA por defecto): ");
        String prioridadInput = scanner.nextLine().trim();
        
        Prioridad prioridad = Prioridad.MEDIA;
        if (!prioridadInput.isEmpty()) {
            try {
                int priOp = Integer.parseInt(prioridadInput);
                if (priOp >= 1 && priOp <= 3) {
                    prioridad = Prioridad.values()[priOp - 1];
                }
            } catch (NumberFormatException e) {
                // Se mantiene MEDIA
            }
        }

        Incidencia nueva = new Incidencia(equipo, desc, tipo, usuarioActual);
        nueva.setPrioridad(prioridad);
        incidencias.add(nueva);
        
        System.out.println("\n✓ Incidencia registrada con éxito.");
        System.out.println("  ID asignado: " + nueva.getId());
    }

    private static void menuConsultarIncidencias() {
        System.out.println("\n--- CONSULTAR INCIDENCIAS ---");
        System.out.println("1. Ver todas las incidencias");
        System.out.println("2. Filtrar por Estado");
        System.out.println("3. Filtrar por Tipo");
        System.out.println("4. Filtrar por Prioridad");
        System.out.println("5. Filtrar por Técnico asignado");
        System.out.println("6. Buscar por ID de equipo");
        System.out.println("7. Buscar mis incidencias (por ID usuario)");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        
        int op;
        try {
            op = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            op = -1;
        }

        List<Incidencia> resultado = new ArrayList<>();

        switch (op) {
            case 1:
                resultado = incidencias.stream()
                        .sorted((a, b) -> b.getPrioridad().compareTo(a.getPrioridad()))
                        .collect(Collectors.toList());
                break;
            case 2:
                System.out.println("Estados: 1.ABIERTA 2.EN_PROCESO 3.RESUELTA 4.CERRADA");
                System.out.print("Seleccione: ");
                try {
                    int estOp = Integer.parseInt(scanner.nextLine().trim());
                    EstadoIncidencia estado = EstadoIncidencia.values()[estOp - 1];
                    resultado = incidencias.stream()
                            .filter(i -> i.getEstado() == estado)
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    System.out.println(" Selección no válida.");
                    return;
                }
                break;
            case 3:
                System.out.println("Tipos: 1.HARDWARE 2.SOFTWARE 3.RED");
                System.out.print("Seleccione: ");
                try {
                    int tipoOp = Integer.parseInt(scanner.nextLine().trim());
                    TipoIncidencia tipo = TipoIncidencia.values()[tipoOp - 1];
                    resultado = incidencias.stream()
                            .filter(i -> i.getTipo() == tipo)
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    System.out.println(" Selección no válida.");
                    return;
                }
                break;
            case 4:
                System.out.println("Prioridad: 1.BAJA 2.MEDIA 3.ALTA");
                System.out.print("Seleccione: ");
                try {
                    int priOp = Integer.parseInt(scanner.nextLine().trim());
                    Prioridad prioridad = Prioridad.values()[priOp - 1];
                    resultado = incidencias.stream()
                            .filter(i -> i.getPrioridad() == prioridad)
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    System.out.println(" Selección no válida.");
                    return;
                }
                break;
            case 5:
                if (tecnicos.isEmpty()) {
                    System.out.println("No hay técnicos registrados.");
                    return;
                }
                System.out.println("Técnicos disponibles:");
                for (int i = 0; i < tecnicos.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + tecnicos.get(i).getNombre());
                }
                System.out.print("Seleccione: ");
                try {
                    int tecOp = Integer.parseInt(scanner.nextLine().trim());
                    Tecnico tecnico = tecnicos.get(tecOp - 1);
                    resultado = incidencias.stream()
                            .filter(i -> i.getTecnicoAsignado() != null && 
                                    i.getTecnicoAsignado().getIdUsuario().equals(tecnico.getIdUsuario()))
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    System.out.println(" Selección no válida.");
                    return;
                }
                break;
            case 6:
                System.out.print("Ingrese ID del equipo: ");
                String idEquipo = scanner.nextLine().trim();
                resultado = incidencias.stream()
                        .filter(i -> i.getIdEquipo().toLowerCase().contains(idEquipo.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case 7:
                System.out.print("Ingrese su ID de usuario: ");
                String idUsuario = scanner.nextLine().trim();
                resultado = incidencias.stream()
                        .filter(i -> i.getReportadoPor().getIdUsuario().equalsIgnoreCase(idUsuario))
                        .collect(Collectors.toList());
                if (!resultado.isEmpty()) {
                    mostrarListaIncidenciasConNotas(resultado);
                    return;
                }
                break;
            case 0:
                return;
            default:
                System.out.println(" Opción no válida.");
                return;
        }

        mostrarListaIncidencias(resultado);
    }

    private static void mostrarListaIncidencias(List<Incidencia> lista) {
        if (lista.isEmpty()) {
            System.out.println("\n No se encontraron incidencias.");
            return;
        }
        System.out.println("\n--- LISTADO DE INCIDENCIAS (" + lista.size() + " encontradas) ---");
        for (Incidencia inc : lista) {
            System.out.println(inc);
        }
    }

    private static void mostrarListaIncidenciasConNotas(List<Incidencia> lista) {
        if (lista.isEmpty()) {
            System.out.println("\n No se encontraron incidencias.");
            return;
        }
        System.out.println("\n--- MIS INCIDENCIAS (" + lista.size() + " encontradas) ---");
        for (Incidencia inc : lista) {
            System.out.println(inc);
            String tecnico = (inc.getTecnicoAsignado() != null) ? inc.getTecnicoAsignado().getNombre() : "Sin asignar";
            String nota = (inc.getNotaSolucion() != null && !inc.getNotaSolucion().isEmpty()) 
                    ? inc.getNotaSolucion() : "Sin notas aún";
            System.out.println("    └── Técnico: " + tecnico + " | Nota: " + nota);
        }
    }

    private static void atenderIncidencia() {
        if (incidencias.isEmpty()) {
            System.out.println("\n No hay incidencias registradas.");
            return;
        }

        System.out.println("\n--- ATENDER INCIDENCIA ---");
        System.out.print("Ingrese ID de la incidencia a gestionar: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(" ID no válido.");
            return;
        }
        
        Incidencia encontrada = buscarIncidenciaPorId(id);

        if (encontrada == null) {
            System.out.println("Incidencia no encontrada.");
            return;
        }

        System.out.println(encontrada.toStringDetallado());
        System.out.println("\n¿Qué desea hacer?");
        System.out.println("1. Asignar Técnico");
        System.out.println("2. Cambiar Estado");
        System.out.println("3. Cambiar Prioridad");
        System.out.println("4. Resolver con Nota de Solución");
        System.out.println("0. Cancelar");
        System.out.print("Opción: ");
        
        int subOp;
        try {
            subOp = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(" Opción no válida.");
            return;
        }
        
        switch (subOp) {
            case 1:
                asignarTecnicoAIncidencia(encontrada);
                break;
            case 2:
                System.out.println("Estados disponibles:");
                System.out.println("  1. ABIERTA");
                System.out.println("  2. EN_PROCESO");
                System.out.println("  3. RESUELTA");
                System.out.println("  4. CERRADA");
                System.out.print("Seleccione: ");
                try {
                    int estOp = Integer.parseInt(scanner.nextLine().trim());
                    if (estOp >= 1 && estOp <= 4) {
                        encontrada.setEstado(EstadoIncidencia.values()[estOp - 1]);
                        System.out.println(" Estado actualizado.");
                    } else {
                        System.out.println(" Opción no válida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Entrada no válida.");
                }
                break;
            case 3:
                System.out.println("Prioridades disponibles:");
                System.out.println("  1. BAJA");
                System.out.println("  2. MEDIA");
                System.out.println("  3. ALTA");
                System.out.print("Seleccione: ");
                try {
                    int priOp = Integer.parseInt(scanner.nextLine().trim());
                    if (priOp >= 1 && priOp <= 3) {
                        encontrada.setPrioridad(Prioridad.values()[priOp - 1]);
                        System.out.println(" Prioridad actualizada.");
                    } else {
                        System.out.println(" Opción no válida.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Entrada no válida.");
                }
                break;
            case 4:
                System.out.println("\n--- RESOLVER INCIDENCIA ---");
                System.out.print("Ingrese la descripción de la solución: ");
                String notaSolucion = scanner.nextLine().trim();
                if (!notaSolucion.isEmpty()) {
                    encontrada.resolverConNota(notaSolucion);
                    System.out.println(" Incidencia resuelta con nota de solución.");
                } else {
                    System.out.println(" La nota de solución no puede estar vacía.");
                }
                break;
            case 0:
                System.out.println("Operación cancelada.");
                break;
            default:
                System.out.println(" Opción no válida.");
        }
    }

    private static void asignarTecnicoAIncidencia(Incidencia incidencia) {
        if (tecnicos.isEmpty()) {
            System.out.println(" No hay técnicos registrados en el sistema.");
            return;
        }

        System.out.println("\nTécnicos disponibles:");
        for (int i = 0; i < tecnicos.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + tecnicos.get(i));
        }
        System.out.print("Seleccione técnico: ");
        
        try {
            int tecOp = Integer.parseInt(scanner.nextLine().trim());
            if (tecOp >= 1 && tecOp <= tecnicos.size()) {
                incidencia.asignarTecnico(tecnicos.get(tecOp - 1));
                System.out.println("Técnico asignado correctamente.");
            } else {
                System.out.println(" Selección no válida.");
            }
        } catch (NumberFormatException e) {
            System.out.println(" Entrada no válida.");
        }
    }

    private static void verHistorialIncidencia() {
        if (incidencias.isEmpty()) {
            System.out.println("\n No hay incidencias registradas.");
            return;
        }

        System.out.println("\n--- VER HISTORIAL DE INCIDENCIA ---");
        System.out.print("Ingrese ID de la incidencia: ");
        
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(" ID no válido.");
            return;
        }
        
        Incidencia encontrada = buscarIncidenciaPorId(id);

        if (encontrada != null) {
            System.out.println(encontrada.toStringDetallado());
            encontrada.mostrarHistorial();
        } else {
            System.out.println(" Incidencia no encontrada.");
        }
    }
    
    private static void generarReporte() {
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              REPORTE DEL SISTEMA LAB-LIS                     ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        if (incidencias.isEmpty()) {
            System.out.println("\nNo hay incidencias registradas en el sistema.");
            return;
        }

        System.out.println("\n--- RESUMEN GENERAL ---");
        System.out.println("Total de Incidencias: " + incidencias.size());
        
        // Por estado
        System.out.println("\n--- POR ESTADO ---");
        for (EstadoIncidencia estado : EstadoIncidencia.values()) {
            long count = incidencias.stream().filter(i -> i.getEstado() == estado).count();
            System.out.printf("  %-12s: %d%n", estado, count);
        }

        // Por tipo
        System.out.println("\n--- POR TIPO ---");
        for (TipoIncidencia tipo : TipoIncidencia.values()) {
            long count = incidencias.stream().filter(i -> i.getTipo() == tipo).count();
            System.out.printf("  %-12s: %d%n", tipo, count);
        }

        // Por prioridad
        System.out.println("\n--- POR PRIORIDAD ---");
        for (Prioridad prioridad : Prioridad.values()) {
            long count = incidencias.stream().filter(i -> i.getPrioridad() == prioridad).count();
            System.out.printf("  %-12s: %d%n", prioridad, count);
        }

        // Incidencias sin técnico asignado
        long sinTecnico = incidencias.stream().filter(i -> i.getTecnicoAsignado() == null).count();
        System.out.println("\n--- ASIGNACIÓN ---");
        System.out.println("  Incidencias sin técnico asignado: " + sinTecnico);

        // Resumen por técnico
        if (!tecnicos.isEmpty()) {
            System.out.println("\n--- INCIDENCIAS POR TÉCNICO ---");
            for (Tecnico tecnico : tecnicos) {
                long count = incidencias.stream()
                        .filter(i -> i.getTecnicoAsignado() != null && 
                                i.getTecnicoAsignado().getIdUsuario().equals(tecnico.getIdUsuario()))
                        .count();
                System.out.printf("  %-20s: %d%n", tecnico.getNombre(), count);
            }
        }

        // Incidencias de alta prioridad pendientes
        long altaPrioridadPendiente = incidencias.stream()
                .filter(i -> i.getPrioridad() == Prioridad.ALTA && 
                        (i.getEstado() == EstadoIncidencia.ABIERTA || i.getEstado() == EstadoIncidencia.EN_PROCESO))
                .count();
        System.out.println("\n Incidencias ALTA prioridad pendientes: " + altaPrioridadPendiente);
    }

    private static void gestionarTecnicos() {
        System.out.println("\n--- GESTIÓN DE TÉCNICOS ---");
        System.out.println("1. Ver técnicos registrados");
        System.out.println("2. Agregar nuevo técnico");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
        
        int op;
        try {
            op = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            op = -1;
        }

        switch (op) {
            case 1:
                if (tecnicos.isEmpty()) {
                    System.out.println("No hay técnicos registrados.");
                } else {
                    System.out.println("\n--- TÉCNICOS REGISTRADOS ---");
                    for (Tecnico tec : tecnicos) {
                        System.out.println("  " + tec);
                    }
                }
                break;
            case 2:
                System.out.print("ID del técnico: ");
                String idTec = scanner.nextLine().trim();
                System.out.print("Nombre del técnico: ");
                String nombreTec = scanner.nextLine().trim();
                System.out.print("Especialidad (Hardware/Software/Redes/General): ");
                String especialidad = scanner.nextLine().trim();
                
                if (!idTec.isEmpty() && !nombreTec.isEmpty()) {
                    tecnicos.add(new Tecnico(idTec, nombreTec, especialidad.isEmpty() ? "General" : especialidad));
                    System.out.println("Técnico agregado correctamente.");
                } else {
                    System.out.println("Datos incompletos.");
                }
                break;
            case 0:
                break;
            default:
                System.out.println("⚠ Opción no válida.");
        }
    }

    private static Incidencia buscarIncidenciaPorId(int id) {
        for (Incidencia inc : incidencias) {
            if (inc.getId() == id) {
                return inc;
            }
        }
        return null;
    }
}