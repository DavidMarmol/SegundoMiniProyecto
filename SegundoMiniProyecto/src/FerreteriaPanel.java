import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class FerreteriaPanel extends JPanel {
    private Inventario inventario;
    private JTable tablaProductos;
    private JTextArea detalleArea;
    private JFrame detalleFrame; 

    public FerreteriaPanel(Inventario inventario) {
        this.inventario = inventario;
        crearInterfaz();
    }

    private void crearInterfaz() {
        setLayout(new BorderLayout());

        // Panel para los botones
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));

        
        JButton agregarButton = new JButton("Agregar Producto");
        agregarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
        panelSuperior.add(agregarButton);

        
        JButton eliminarButton = new JButton("Eliminar Producto");
        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        panelSuperior.add(eliminarButton);

        JButton actualizarButton = new JButton("Actualizar Inventario");
        actualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarInventario();
            }
        });
        panelSuperior.add(actualizarButton);

        JButton estadisticasButton = new JButton("Mostrar Estadísticas");
        estadisticasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarEstadisticas();
            }
        });
        panelSuperior.add(estadisticasButton);

        add(panelSuperior, BorderLayout.NORTH);

        // Inventario de productos
        tablaProductos = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos.getTableHeader().setReorderingAllowed(false); 
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tablaProductos.getSelectedRow();
                if (selectedRow >= 0) {
                    Producto producto = inventario.getProductos().get(selectedRow);
                    mostrarDetalleProducto(producto);
                }
            }
        });
        JScrollPane scrollPaneTabla = new JScrollPane(tablaProductos);
        add(scrollPaneTabla, BorderLayout.CENTER);

        // Informacion de uso
        detalleArea = new JTextArea();
        detalleArea.setEditable(false);
        detalleArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPaneDetalle = new JScrollPane(detalleArea);
        add(scrollPaneDetalle, BorderLayout.EAST);

        actualizarListaProductos();
    }

    private void actualizarListaProductos() {
        String[] columnNames = {"Nombre", "Descripción", "Precio", "Cantidad en stock"};
        Object[][] data = new Object[inventario.getProductos().size()][];
        for (int i = 0; i < inventario.getProductos().size(); i++) {
            Producto producto = inventario.getProductos().get(i);
            data[i] = new Object[]{producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getCantidad()};
        }
        tablaProductos.setModel(new DefaultTableModel(data, columnNames));
    }

    private void mostrarDetalleProducto(Producto producto) {
        if (producto != null) {
            // Crea una nueva ventana emergente
            if (detalleFrame == null) {
                detalleFrame = new JFrame("Detalles del Producto");
                detalleFrame.setSize(400, 300);
                detalleFrame.setLocationRelativeTo(null);
                detalleFrame.add(new JScrollPane(detalleArea));
            }

            // detalles del producto
            detalleArea.setText(
                    "Nombre: " + producto.getNombre() + "\n" +
                            "Descripción: " + producto.getDescripcion() + "\n" +
                            "Precio: " + producto.getPrecio() + "\n" +
                            "Cantidad en stock: " + producto.getCantidad() + "\n" +
                            "Material de elaboración: " + producto.getMaterialElaboracion() + "\n" +
                            "Ejemplos de uso: " + producto.getEjemplosUso() + "\n" +
                            "Herramientas necesarias: " + String.join(", ", producto.getHerramientasNecesarias())
            );
            detalleFrame.setVisible(true);
        } else {
            detalleArea.setText("");
            if (detalleFrame != null) {
                detalleFrame.setVisible(false);
            }
        }
    }

    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo producto:");
        String descripcion = JOptionPane.showInputDialog(null, "Ingrese la descripción del nuevo producto:");
        double precio = 0;
        try {
            precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio del nuevo producto:"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Precio inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad inicial del nuevo producto:"));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String materialElaboracion = JOptionPane.showInputDialog(null, "Ingrese el material de elaboración del nuevo producto:");
        String ejemplosUso = JOptionPane.showInputDialog(null, "Ingrese ejemplos de uso del nuevo producto:");
        String herramientasStr = JOptionPane.showInputDialog(null, "Ingrese las herramientas necesarias (separadas por coma) del nuevo producto:");
        List<String> herramientasNecesarias = List.of(herramientasStr.split(","));
        Producto nuevoProducto = new Producto(nombre, descripcion, precio, cantidad, materialElaboracion, ejemplosUso, herramientasNecesarias);
        inventario.agregarProducto(nuevoProducto);
        JOptionPane.showMessageDialog(null, "¡Producto agregado con exito!", "Información", JOptionPane.INFORMATION_MESSAGE);

        actualizarListaProductos();
    }

    private void eliminarProducto() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto a eliminar:");
        if (nombre != null && !nombre.isEmpty()) {
            boolean eliminado = inventario.eliminarProducto(nombre);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto", "Error", JOptionPane.ERROR_MESSAGE);
            }

            actualizarListaProductos();
        }
    }

    private void actualizarInventario() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto a actualizar:");
        if (nombre != null && !nombre.isEmpty()) {
            Producto producto = inventario.buscarProducto(nombre);
            if (producto != null) {
                String[] opciones = {"Aumentar", "Vender"}; 
                int seleccion = JOptionPane.showOptionDialog(null, "¿Desea aumentar o vender la cantidad del producto?", "Actualizar Inventario", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                int cantidad = 0;
                try {
                    cantidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la cantidad a " + (seleccion == 0 ? "aumentar" : "vender") + ":"));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (seleccion == 0) {
                    producto.setCantidad(producto.getCantidad() + cantidad);
                } else {
                    if (cantidad > producto.getCantidad()) {
                        JOptionPane.showMessageDialog(null, "No puedes vender más de lo que tienes en stock", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        producto.setCantidad(producto.getCantidad() - cantidad);
                        JOptionPane.showMessageDialog(null, "¡Venta realizada con exito!", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                actualizarListaProductos();
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarEstadisticas() {
        // Precio total de todos los productos del inventario
        double precioTotal = inventario.getPrecioTotal();

        // Productos con más y menos stock
        List<Producto> productos = inventario.getProductos();
        List<Producto> productosMasStock = productos.stream()
                .sorted((p1, p2) -> p2.getCantidad() - p1.getCantidad())
                .limit(2)
                .collect(Collectors.toList());
        List<Producto> productosMenosStock = productos.stream()
                .sorted((p1, p2) -> p1.getCantidad() - p2.getCantidad())
                .limit(2)
                .collect(Collectors.toList());

        Map<String, Integer> herramientasUtilizadas = inventario.herramientasMasUtilizadas();

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(herramientasUtilizadas.entrySet());
        sortedEntries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
        StringBuilder herramientasStr = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            herramientasStr.append(entry.getKey()).append("=").append(entry.getValue()).append(", ");
        }
        if (herramientasStr.length() > 0) {
            herramientasStr.setLength(herramientasStr.length() - 2);
        }

        StringBuilder estadisticas = new StringBuilder();
        estadisticas.append("Precio total de todos los productos en inventario: ").append(precioTotal).append("\n");
        if (!productos.isEmpty()) {
            estadisticas.append("Productos con más stock: ").append(productosMasStock.stream().map(Producto::getNombre).collect(Collectors.joining(", "))).append("\n");
            estadisticas.append("Productos con menos stock: ").append(productosMenosStock.stream().map(Producto::getNombre).collect(Collectors.joining(", "))).append("\n");
        } else {
            estadisticas.append("No hay productos en el inventario\n");
        }
        estadisticas.append("Herramientas más utilizadas: ").append(herramientasStr).append("\n");

        JOptionPane.showMessageDialog(null, estadisticas.toString(), "Estadísticas del Inventario", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ferretería");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new FerreteriaPanel(new Inventario()));
        frame.setVisible(true);
    }
}
