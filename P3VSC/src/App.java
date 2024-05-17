import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class App extends JFrame {

    public App() {
        // Configuración de la ventana
        setTitle("Interfaz Gráfica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK)); // Borde negro para el panel principal

        // Panel con las barras horizontales
        JPanel horizontalBarsPanel = new JPanel(new BorderLayout());
        horizontalBarsPanel.setBackground(new Color(35,35,35));
        horizontalBarsPanel.setPreferredSize(new Dimension(getWidth(), 200)); // Altura de 200 píxeles
        mainPanel.add(horizontalBarsPanel, BorderLayout.SOUTH);

        // Barra horizontal superior
        JPanel topHorizontalBar = new JPanel();
        topHorizontalBar.setBackground(new Color(50, 50, 50)); // Mismo color que la barra de menú
        topHorizontalBar.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK)); // Borde negro arriba
        topHorizontalBar.setPreferredSize(new Dimension(200, 3)); // Ancho para tocar solo la segunda barra vertical
        horizontalBarsPanel.add(topHorizontalBar, BorderLayout.NORTH);

        // Barra horizontal inferior (segunda barra)
        JPanel secondBottomHorizontalBar = new JPanel();
        secondBottomHorizontalBar.setBackground(new Color(20, 20, 20)); // Mismo color que la barra de menú
        secondBottomHorizontalBar.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK)); // Borde negro arriba
        secondBottomHorizontalBar.setPreferredSize(new Dimension(getWidth(), 15)); // 15 de altura
        horizontalBarsPanel.add(secondBottomHorizontalBar, BorderLayout.SOUTH);

        // Panel con las barras verticales a la izquierda
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS)); // Alineación horizontal
        leftPanel.setBackground(new Color(50, 50, 50)); // Mismo color que la barra de menú

        // Primera barra vertical
        JPanel firstVerticalBar = new JPanel();
        firstVerticalBar.setBackground(new Color(50, 50, 50)); // Mismo color que la barra de menú
        firstVerticalBar.setPreferredSize(new Dimension(45, getHeight())); // Tamaño preferido
        firstVerticalBar.setBorder(new MatteBorder(0, 0, 0, 1, Color.BLACK)); // Borde derecho
        leftPanel.add(firstVerticalBar);

        // Segunda barra vertical (doble de ancho)
        JPanel secondVerticalBar = new JPanel();
        secondVerticalBar.setBackground(new Color(30, 30, 30)); // Mismo color que la barra de menú
        secondVerticalBar.setPreferredSize(new Dimension(200, getHeight())); // Doble de ancho
        secondVerticalBar.setBorder(new MatteBorder(0, 1, 0, 0, Color.BLACK)); // Borde izquierdo
        leftPanel.add(secondVerticalBar);

        // Panel con las barras verticales y contenido principal
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout());

        // Agregar componentes al panel principal
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Crear el menú
        JMenuBar menuBar = new JMenuBar();

        // Cambiar el color de fondo de la barra de menú a un gris más oscuro
        Color darkerGray = new Color(50, 50, 50);
        menuBar.setBackground(darkerGray);
        menuBar.setOpaque(true);
        menuBar.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK)); // Borde negro arriba

        // Crear las opciones del menú con letras blancas
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.setBackground(darkerGray);
        JMenu editMenu = new JMenu("Edit");
        editMenu.setForeground(Color.WHITE);
        editMenu.setBackground(darkerGray);
        JMenu selectionMenu = new JMenu("Selection");
        selectionMenu.setForeground(Color.WHITE);
        selectionMenu.setBackground(darkerGray);
        JMenu viewMenu = new JMenu("View");
        viewMenu.setForeground(Color.WHITE);
        viewMenu.setBackground(darkerGray);
        JMenu goMenu = new JMenu("Go");
        goMenu.setForeground(Color.WHITE);
        goMenu.setBackground(darkerGray);
        JMenu runMenu = new JMenu("Run");
        runMenu.setForeground(Color.WHITE);
        runMenu.setBackground(darkerGray);
        JMenu dotsMenu = new JMenu("...");
        dotsMenu.setForeground(Color.WHITE);
        dotsMenu.setBackground(darkerGray);

        // Agregar las opciones al menú
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(selectionMenu);
        menuBar.add(viewMenu);
        menuBar.add(goMenu);
        menuBar.add(runMenu);
        menuBar.add(dotsMenu);

        // Agregar subopciones a cada menú
        addEmptyOptionsToMenu(fileMenu);
        addEmptyOptionsToMenu(editMenu);
        addEmptyOptionsToMenu(selectionMenu);
        addEmptyOptionsToMenu(viewMenu);
        addEmptyOptionsToMenu(goMenu);
        addEmptyOptionsToMenu(runMenu);
        addEmptyOptionsToMenu(dotsMenu);

        // Agregar el componente flexible para que la barra de menú se extienda
        menuBar.add(Box.createHorizontalGlue());

        // Crear el panel para la barra de título personalizada
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setBorder(new EmptyBorder(1, 1, 1, 1)); // Agregar un espacio en la barra de título

        // Agregar la barra de menú a la barra de título personalizada
        titlePanel.add(menuBar);

        // Agregar la barra de título personalizada a la ventana
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // Agregar el panel principal a la ventana
        add(mainPanel);

        // Hacer visible la ventana
        setVisible(true);
    }

    // Método para agregar opciones vacías a un menú
    private void addEmptyOptionsToMenu(JMenu menu) {
        for (int i = 1; i <= 3; i++) {
            JMenuItem emptyOption = new JMenuItem("Empty Option " + i);
            emptyOption.setForeground(Color.WHITE);
            emptyOption.setBackground(menu.getBackground());
            menu.add(emptyOption);
        }
    }

    public static void main(String[] args) {
        // Crear la instancia de la interfaz gráfica en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> new App());
    }
}
