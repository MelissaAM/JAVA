import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.FlowLayout;

class Main {
  public static void main(String args[]) {
    JFrame frame = new JFrame("Reloj");
    frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    Clock clockPanel = new Clock();
    frame.add(clockPanel);

    // Agregar el JPanel al centro
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(700, 700));
    frame.setLocationRelativeTo(null); // Centrar el JFrame en la pantalla
    frame.setVisible(true);
  }

}
