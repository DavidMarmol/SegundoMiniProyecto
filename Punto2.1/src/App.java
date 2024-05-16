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
        JFrame frame = new JFrame("App de dibujo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear el panel de dibujo
        drawingPanel = new DrawingPanel();

        // Crear un botón para activar el modo de líneas
        JButton lineModeButton = new JButton("Tipo de linea");
        lineModeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.toggleLineMode();
            }
        });

        // Crear un botón para borrar el dibujo
        JButton clearButton = new JButton("Limpiar todo");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.clearLines();
            }
        });

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

        // Crear un botón para activar/desactivar el modo borrador
        JButton eraserButton = new JButton("Borrador");
        eraserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.toggleEraserMode();
            }
        });

        // Crear un panel para colocar los botones y el combo box en la parte superior
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(lineModeButton);
        buttonPanel.add(new JLabel("Grosor:"));
        buttonPanel.add(thicknessComboBox);
        buttonPanel.add(clearButton);
        buttonPanel.add(eraserButton);

        // Agregar el panel de dibujo y el panel de botones al marco
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.NORTH);

        // Ajustar el tamaño de la ventana y hacerla visible
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener {
    private List<Line> lines = new ArrayList<>();
    private int currentThickness = 1;
    private Point startPoint, endPoint;
    private boolean lineMode = false;
    private Color currentColor = Color.BLACK;
    private boolean eraserMode = false;
    private boolean isDrawing = true;

    public DrawingPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    // Método para dibujar las líneas en el panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (Line line : lines) {
            g2d.setStroke(new BasicStroke(line.getThickness()));
            g2d.setColor(line.getColor());
            g2d.drawLine(line.getStartPoint().x, line.getStartPoint().y, line.getEndPoint().x, line.getEndPoint().y);
        }
    }

    // Método para agregar una línea al panel
    public void addLine(Line line) {
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

    // Método para establecer el color actual
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    // Método para activar/desactivar el modo de líneas
    public void toggleLineMode() {
        lineMode = !lineMode;
        isDrawing = !lineMode; 
    }

    // Método para activar/desactivar el modo borrador
    public void toggleEraserMode() {
        eraserMode = !eraserMode;
        isDrawing = true; 
    }

    // Método para manejar el evento de presionar el mouse
    @Override
    public void mousePressed(MouseEvent e) {
        startPoint = e.getPoint();
        if (lineMode) {
            endPoint = startPoint;
            addLine(new Line(startPoint, endPoint, currentThickness, currentColor));
        }
    }

    // Método para manejar el evento de arrastrar el mouse
    @Override
    public void mouseDragged(MouseEvent e) {
        endPoint = e.getPoint();
        if (!eraserMode) {
            if (!lineMode || startPoint.equals(endPoint)) {
                addLine(new Line(startPoint, endPoint, currentThickness, currentColor));
                startPoint = endPoint;
            }
        } else {
            addLine(new Line(startPoint, endPoint, currentThickness, Color.WHITE)); 
            startPoint = endPoint;
        }
    }

    // Método para manejar el evento de liberar el mouse
    @Override
    public void mouseReleased(MouseEvent e) {
        if (lineMode) {
            endPoint = e.getPoint();
            addLine(new Line(startPoint, endPoint, currentThickness, currentColor));
        }
    }

    // Métodos de MouseListener no utilizados
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
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
