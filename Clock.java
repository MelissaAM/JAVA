import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
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
    double squareSize = 300;
    double squareX = (PREF_W - squareSize) / 2.0;
    double squareY = PREF_H * 3.0 / 4.0 + 20;

    g2.setPaint(Color.gray);
    g2.fillRect((int) squareX, (int) squareY, (int) squareSize, (int) squareSize);
    double circleSize = squareSize * 0.8; // Tamaño del círculo (80% del cuadrado)
    double circleX = squareX + (squareSize - circleSize) / 2.0;
    double circleY = squareY + (squareSize - circleSize) / 2.0;

    g2.setPaint(Color.red); // Color del círculo
    g2.fill(new Ellipse2D.Double(circleX, circleY, circleSize, circleSize));

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
    String[] romanSymbols = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C" };
    int[] arabicValues = { 1, 4, 5, 9, 10, 40, 50, 90, 100 };
    StringBuilder romanNumeral = new StringBuilder();

    for (int i = arabicValues.length - 1; i >= 0; i--) {
      while (num >= arabicValues[i]) {
        romanNumeral.append(romanSymbols[i]);
        num -= arabicValues[i];
      }
    }

    return romanNumeral.toString();
  }
}
