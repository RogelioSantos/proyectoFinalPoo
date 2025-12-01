public abstract class Usuario {
    protected String idUsuario;
    protected String nombre;

    public Usuario(String idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", idUsuario, nombre);
    }
}