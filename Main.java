import javax.swing.JFrame;

class Main {
  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().add(new Clock());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

}
