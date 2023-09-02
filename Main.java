import javax.swing.JButton;
import javax.swing.JFrame;

class Main {
  public static void main(String args[]) {
    JFrame frame = new JFrame("HI!");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 300);
    JButton button = new JButton("Press");
    button.setBounds(130, 100, 100, 40);
    frame.add(button);
    frame.setLayout(null);
    frame.setVisible(true);
  }

}
