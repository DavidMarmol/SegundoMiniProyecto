import java.util.List;

public class Producto {
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    private String materialElaboracion;
    private String ejemplosUso;
    private List<String> herramientasNecesarias;

    public Producto(String nombre, String descripcion, double precio, int cantidad,
                    String materialElaboracion, String ejemplosUso, List<String> herramientasNecesarias) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.materialElaboracion = materialElaboracion;
        this.ejemplosUso = ejemplosUso;
        this.herramientasNecesarias = herramientasNecesarias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMaterialElaboracion() {
        return materialElaboracion;
    }

    public void setMaterialElaboracion(String materialElaboracion) {
        this.materialElaboracion = materialElaboracion;
    }

    public String getEjemplosUso() {
        return ejemplosUso;
    }

    public void setEjemplosUso(String ejemplosUso) {
        this.ejemplosUso = ejemplosUso;
    }

    public List<String> getHerramientasNecesarias() {
        return herramientasNecesarias;
    }

    public void setHerramientasNecesarias(List<String> herramientasNecesarias) {
        this.herramientasNecesarias = herramientasNecesarias;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
