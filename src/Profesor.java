public class Profesor extends Usuario {

    public Profesor(String id, String nombre) {
        super(id, nombre); 
    }

    @Override
    public String toString() {
        return "Profesor: " + super.toString();
    }
}
