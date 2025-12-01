import java.util.*;
import java.text.SimpleDateFormat;

// --- ENUMS ---
enum TipoIncidencia { HARDWARE, SOFTWARE, RED }
enum EstadoIncidencia { ABIERTA, EN_PROCESO, RESUELTA, CERRADA }
enum Prioridad { BAJA, MEDIA, ALTA }

public class Incidencia {
    private static int contadorIds = 1;
    private int id;
    private String idEquipo;
    private String descripcion;
    private TipoIncidencia tipo;
    private EstadoIncidencia estado;
    private Prioridad prioridad;
    private Usuario reportadoPor;
    private Tecnico tecnicoAsignado;
    private List<String> historialCambios;
    private Date fechaCreacion;
    private Date fechaResolucion;
    private String notaSolucion;

    public Incidencia(String idEquipo, String descripcion, TipoIncidencia tipo, Usuario usuario) {
        this.id = contadorIds++;
        this.idEquipo = idEquipo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.reportadoPor = usuario;
        this.estado = EstadoIncidencia.ABIERTA;
        this.prioridad = Prioridad.MEDIA; // Default
        this.historialCambios = new ArrayList<>();
        this.fechaCreacion = new Date();
        this.fechaResolucion = null;
        this.notaSolucion = "";
        registrarCambio("Incidencia creada.");
    }

    public void registrarCambio(String nota) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        historialCambios.add(sdf.format(new Date()) + ": " + nota);
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
        registrarCambio("Estado cambiado a " + estado);
        if (estado == EstadoIncidencia.RESUELTA || estado == EstadoIncidencia.CERRADA) {
            this.fechaResolucion = new Date();
        }
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
        registrarCambio("Prioridad cambiada a " + prioridad);
    }

    public void asignarTecnico(Tecnico tecnico) {
        this.tecnicoAsignado = tecnico;
        registrarCambio("Técnico asignado: " + tecnico.getNombre());
    }

    public void agregarNotaSolucion(String nota) {
        this.notaSolucion = nota;
        registrarCambio("Nota de solución agregada: " + nota);
    }

    public void resolverConNota(String nota) {
        this.notaSolucion = nota;
        this.estado = EstadoIncidencia.RESUELTA;
        this.fechaResolucion = new Date();
        registrarCambio("Incidencia RESUELTA. Solución: " + nota);
    }

    public void mostrarHistorial() {
        System.out.println("\n--- HISTORIAL DE INCIDENCIA #" + id + " ---");
        for (String cambio : historialCambios) {
            System.out.println("  " + cambio);
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String tecnico = (tecnicoAsignado != null) ? tecnicoAsignado.getNombre() : "Sin asignar";
        return String.format("ID: %d | Equipo: %s | Tipo: %s | Estado: %s | Prioridad: %s | Reportado por: %s | Técnico: %s | Fecha: %s",
                id, idEquipo, tipo, estado, prioridad, reportadoPor.getNombre(), tecnico, sdf.format(fechaCreacion));
    }

    public String toStringDetallado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("\n╔══════════════════════════════════════════════════════════════╗\n");
        sb.append(String.format("║ INCIDENCIA #%-48d ║\n", id));
        sb.append("╠══════════════════════════════════════════════════════════════╣\n");
        sb.append(String.format("║ Equipo: %-52s ║\n", idEquipo));
        sb.append(String.format("║ Descripción: %-47s ║\n", descripcion.length() > 47 ? descripcion.substring(0, 44) + "..." : descripcion));
        sb.append(String.format("║ Tipo: %-54s ║\n", tipo));
        sb.append(String.format("║ Estado: %-52s ║\n", estado));
        sb.append(String.format("║ Prioridad: %-49s ║\n", prioridad));
        sb.append(String.format("║ Reportado por: %-45s ║\n", reportadoPor.getNombre()));
        sb.append(String.format("║ Técnico: %-51s ║\n", (tecnicoAsignado != null) ? tecnicoAsignado.getNombre() : "Sin asignar"));
        sb.append(String.format("║ Fecha creación: %-44s ║\n", sdf.format(fechaCreacion)));
        if (fechaResolucion != null) {
            sb.append(String.format("║ Fecha resolución: %-42s ║\n", sdf.format(fechaResolucion)));
        }
        if (notaSolucion != null && !notaSolucion.isEmpty()) {
            sb.append(String.format("║ Solución: %-50s ║\n", notaSolucion.length() > 50 ? notaSolucion.substring(0, 47) + "..." : notaSolucion));
        }
        sb.append("╚══════════════════════════════════════════════════════════════╝");
        return sb.toString();
    }
    
    // Getters necesarios para filtros
    public int getId() { return id; }
    public String getIdEquipo() { return idEquipo; }
    public String getDescripcion() { return descripcion; }
    public TipoIncidencia getTipo() { return tipo; }
    public EstadoIncidencia getEstado() { return estado; }
    public Prioridad getPrioridad() { return prioridad; }
    public Usuario getReportadoPor() { return reportadoPor; }
    public Tecnico getTecnicoAsignado() { return tecnicoAsignado; }
    public Date getFechaCreacion() { return fechaCreacion; }
    public Date getFechaResolucion() { return fechaResolucion; }
    public List<String> getHistorialCambios() { return historialCambios; }
    public String getNotaSolucion() { return notaSolucion; }
}