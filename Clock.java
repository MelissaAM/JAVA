import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JPanel;

public class Clock extends JPanel {
  private static final int PREF_W = 600;
  private static final int PREF_H = PREF_W;
  private static final Color color = Color.gray;
  private Path2D myPath = new Path2D.Double();

  public Clock() {
    double x = (PREF_W / 2.0) * (1 - 1 / Math.sqrt(3));
    double y = 3.0 * PREF_H / 4.0;

    myPath.moveTo(x, y);
    myPath.lineTo(PREF_W - x, y);
    myPath.lineTo(PREF_W / 2.0, PREF_H / 4.0);
    myPath.closePath();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Cuadrado
    double squareSize = 200;
    double squareX = (PREF_W - squareSize) / 2.0;
    double squareY = PREF_H * 3.0 / 4.0 + 20; // Ajusta la posicion vertical
                                              // aca

    g2.setPaint(Color.gray);
    g2.fillRect((int) squareX, (int) squareY, (int) squareSize, (int) squareSize);

    // Circulo
    double circleSize = squareSize * 0.8; // Tamaño del círculo (80% del cuadrado)
    double circleX = squareX + (squareSize - circleSize) / 2.0;
    double circleY = squareY + (squareSize - circleSize) / 2.0;

    g2.setPaint(Color.white); // Color del círculo
    g2.fillOval((int) circleX, (int) circleY, (int) circleSize, (int) circleSize);

    g2.setPaint(Color.black);
    Font font = new Font("Times New Roman", Font.PLAIN, 24);
    g2.setFont(font);

    for (int i = 1; i <= 12; ++i) {
      double angle = Math.toRadians(360 - (i * 30));
      double x = circleX + circleSize / 2 * Math.cos(angle);
      double y = circleY + circleSize / 2 * Math.sin(angle);

      String numeral = convertToRoman(i);
      int width = g2.getFontMetrics().stringWidth(numeral); 
      int height = g2.getFontMetrics().getHeight(); 

      g2.drawString(numeral, (int) x - width / 2, (int) y + height / 4);
    }

    // Triangulo
    g2.setPaint(color);
    g2.fill(myPath);

  }

  @Override
  public Dimension getPreferredSize() {
    if (isPreferredSizeSet())
      return super.getPreferredSize();
    return new Dimension(PREF_W, PREF_H);
  }

  private String convertToRoman(int num) {
    String[] romanSymbols = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII" };
    return romanSymbols[num - 1];
  }
}
