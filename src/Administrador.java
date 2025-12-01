public class Administrador extends Usuario {

    public Administrador(String id, String nombre) {
        super(id, nombre); 
    }

    @Override
    public String toString() {
        return "Administrador: " + super.toString();
    }
}
