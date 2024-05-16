import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class App {
    private static DrawingPanel drawingPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        // Crear la ventana principal
        JFrame frame = new JFrame("Drawing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel de dibujo
        drawingPanel = new DrawingPanel();

        // Crear un combo box para seleccionar el grosor del trazo
        String[] thicknessOptions = {"1", "2", "3", "4", "5"};
        JComboBox<String> thicknessComboBox = new JComboBox<>(thicknessOptions);
        thicknessComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
                String selectedThickness = (String) comboBox.getSelectedItem();
                drawingPanel.setCurrentThickness(Integer.parseInt(selectedThickness));
            }
        });

        // Crear un botón para borrar el dibujo
        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clearLines();
            }
        });

        // Crear un botón para activar el modo borrador
        JButton eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setCurrentColor(Color.WHITE); // Configurar el color del borrador como blanco
                drawingPanel.setCurrentThickness(20); // Ajustar el grosor del borrador
            }
        });

        // Crear un botón para seleccionar un color aleatorio
        JButton randomColorButton = new JButton("Random Color");
        randomColorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setRandomColorMode(true);
            }
        });

        // Crear una paleta de colores
        JPanel colorPalette = new JPanel();
        colorPalette.setLayout(new GridLayout(1, 10)); // Ajustado para 10 colores
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.LIGHT_GRAY, Color.DARK_GRAY}; // Agregados más colores
        for (Color color : colors) {
            JButton colorButton = new JButton();
            colorButton.setBackground(color);
            colorButton.setPreferredSize(new Dimension(30, 30));
            colorButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawingPanel.setCurrentColor(color);
                    drawingPanel.setCurrentThickness(1); // Restaurar el grosor del trazo a 1
                    drawingPanel.setRandomColorMode(false); // Desactivar el modo de color aleatorio
                }
            });
            colorPalette.add(colorButton);
        }

        // Crear un panel para colocar los componentes en la parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Thickness:"));
        buttonPanel.add(thicknessComboBox);
        buttonPanel.add(clearButton);
        buttonPanel.add(eraserButton);
        buttonPanel.add(randomColorButton);
        buttonPanel.add(colorPalette);

        // Agregar el panel de dibujo y el panel de botones al marco
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Ajustar el tamaño de la ventana y hacerla visible
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
    private List<Line> lines = new ArrayList<>();
    private int currentThickness = 1;
    private boolean eraseMode = false;
    private boolean randomColorMode = false;
    private Color currentColor = Color.BLACK;
    private Point startPoint, endPoint;

    public DrawingPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true); // Permitir que el panel tenga el foco para detectar las teclas
    }

    // Método para dibujar las líneas en el panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Line line : lines) {
            g2d.setColor(line.getColor());
            // Establecer el grosor del trazo
            g2d.setStroke(new BasicStroke(line.getThickness()));
            // Dibujar la línea
            g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
        }
    }

    // Método para agregar una línea al panel
    public void addLine(Line line) {
        if (eraseMode) {
            line.setColor(Color.WHITE); // Configurar el color como blanco si está activado el modo borrador
        } else if (randomColorMode) {
            line.setColor(getRandomColor()); // Asignar un color aleatorio si está activado el modo de color aleatorio
        }
        lines.add(line);
        repaint();
    }

    // Método para borrar todas las líneas del panel
    public void clearLines() {
        lines.clear();
        repaint();
    }

    // Método para establecer el grosor actual del trazo
    public void setCurrentThickness(int thickness) {
        this.currentThickness = thickness;
    }

    // Método para activar/desactivar el modo de borrado
    public void toggleEraseMode() {
        eraseMode = !eraseMode;
        if (eraseMode) {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR)); // Cambiar el cursor para indicar el modo de borrado
        } else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Restaurar el cursor predeterminado
        }
    }

    // Método para activar/desactivar el modo de color aleatorio
    public void setRandomColorMode(boolean randomColorMode) {
        this.randomColorMode = randomColorMode;
    }

    // Método para establecer el color actual
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    // Método para generar un color aleatorio
    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

    // Método para manejar el evento de presionar el mouse
    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
    }

    // Método para manejar el evento de soltar el mouse
    @Override
    public void mouseReleased(MouseEvent e) {
        endPoint = e.getPoint();
        // Agregar una línea con el grosor actual entre el punto de inicio y el punto final
        addLine(new Line(startPoint, endPoint, currentThickness, currentColor));
    }

    // Métodos de MouseListener y MouseMotionListener no utilizados
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }


}

class Line {
    private Point startPoint;
    private Point endPoint;
    private int thickness;
    private Color color;

    public Line(Point startPoint, Point endPoint, int thickness, Color color) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.thickness = thickness;
        this.color = color;
    }

    // Métodos getter y setter para los puntos de inicio y fin de la línea
    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    // Métodos getter y setter para el grosor del trazo
    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    // Métodos getter y setter para el color
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
