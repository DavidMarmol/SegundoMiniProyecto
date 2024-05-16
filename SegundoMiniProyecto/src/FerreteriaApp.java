import javax.swing.*;

public class FerreteriaApp {

    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        FerreteriaPanel panel = new FerreteriaPanel(inventario);

        JFrame frame = new JFrame("Ferretería App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
