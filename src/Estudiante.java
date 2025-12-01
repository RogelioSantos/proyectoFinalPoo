public class Estudiante extends Usuario {

    public Estudiante(String id, String nombre) {
        super(id, nombre); 
    }

    @Override
    public String toString() {
        return "Estudiante: " + super.toString();
    }
}
