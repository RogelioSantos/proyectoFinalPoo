public class Tecnico extends Usuario {
    private String especialidad;
    
    public Tecnico(String id, String nombre) {
        super(id, nombre);
        this.especialidad = "General";
    }

    public Tecnico(String id, String nombre, String especialidad) {
        super(id, nombre);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    @Override
    public String toString() {
        return "Técnico: " + super.toString() + " - Especialidad: " + especialidad;
    }
}