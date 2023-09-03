import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

class Main {
  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setLayout(new GridBagLayout());
    Clock clockPanel = new Clock();

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.CENTER;

    // Agregar el JPanel al centro
    frame.add(clockPanel, gbc);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null); // Centrar el JFrame en la pantalla
    frame.setVisible(true);
  }

}
