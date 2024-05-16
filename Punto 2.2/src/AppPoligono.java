import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class AppPoligono {
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

        // Crear un panel para colocar el botón y el combo box en la parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(clearButton);
        buttonPanel.add(new JLabel("Grosor:"));
        buttonPanel.add(thicknessComboBox);

        // Agregar el panel de dibujo y el panel de botones al marco
        frame.add(drawingPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Ajustar el tamaño de la ventana y hacerla visible
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}

class DrawingPanel extends JPanel implements MouseListener {
    private List<Point> points = new ArrayList<>();
    private List<Point> edgePoints = new ArrayList<>();
    private int currentThickness = 1;

    public DrawingPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
    }

    // Método para dibujar el polígono irregular en el panel
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(currentThickness));
        
        // Dibujar puntos seleccionados
        g2d.setColor(Color.RED);
        for (Point p : points) {
            int pointSize = 6 + currentThickness; // Tamaño del punto ajustado por el grosor del trazo
            g2d.fillOval(p.x - pointSize/2, p.y - pointSize/2, pointSize, pointSize);
        }
        
        // Dibujar polígono
        g2d.setColor(Color.BLACK);
        if (points.size() > 2) {
            Polygon polygon = new Polygon();
            for (Point p : points) {
                polygon.addPoint(p.x, p.y);
            }
            g2d.drawPolygon(polygon);
        }
    }

    // Método para agregar un punto al polígono irregular
    public void addPoint(Point point) {
        points.add(point);
        calculateEdgePoints();
        repaint();
    }

    // Método para borrar todos los puntos y el polígono irregular del panel
    public void clearLines() {
        points.clear();
        edgePoints.clear();
        repaint();
    }

    // Método para establecer el grosor actual del trazo
    public void setCurrentThickness(int thickness) {
        this.currentThickness = thickness;
    }

    // Método para calcular los puntos en los bordes del polígono
    private void calculateEdgePoints() {
        edgePoints.clear();
        if (points.size() > 2) {
            Polygon polygon = new Polygon();
            for (Point p : points) {
                polygon.addPoint(p.x, p.y);
            }
            Rectangle bounds = polygon.getBounds();
            for (int x = bounds.x; x <= bounds.x + bounds.width; x++) {
                edgePoints.add(new Point(x, bounds.y));
                edgePoints.add(new Point(x, bounds.y + bounds.height));
            }
            for (int y = bounds.y; y <= bounds.y + bounds.height; y++) {
                edgePoints.add(new Point(bounds.x, y));
                edgePoints.add(new Point(bounds.x + bounds.width, y));
            }
        }
    }

    // Métodos de MouseListener para manejar el evento de clic del mouse
    @Override
    public void mousePressed(MouseEvent e) {
        Point clickedPoint = e.getPoint();
        addPoint(clickedPoint);
    }

    // Métodos de MouseListener no utilizados
    @Override
    public void mouseClicked(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
}
