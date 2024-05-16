import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventario {
    private List<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public boolean eliminarProducto(String nombre) {
        Producto producto = buscarProducto(nombre);
        if (producto != null) {
            productos.remove(producto);
            return true;
        }
        return false;
    }

    public Producto buscarProducto(String nombre) {
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public double getPrecioTotal() {
        double precioTotal = 0;
        for (Producto producto : productos) {
            precioTotal += producto.getPrecio() * producto.getCantidad();
        }
        return precioTotal;
    }

    public Map<String, Integer> herramientasMasUtilizadas() {
        Map<String, Integer> herramientasContador = new HashMap<>();
        for (Producto producto : productos) {
            List<String> herramientas = producto.getHerramientasNecesarias();
            for (String herramienta : herramientas) {
                herramientasContador.put(herramienta, herramientasContador.getOrDefault(herramienta, 0) + 1);
            }
        }
        return herramientasContador;
    }
}
