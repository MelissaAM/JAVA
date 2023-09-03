import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Line2D;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class Clock extends JPanel {
  private static final int PREF_W = 600;
  private static final int PREF_H = PREF_W;
  private static final Color color = Color.gray;
  private Path2D myPath = new Path2D.Double();
  private int pendulumLength = 200;
  private double pendulumAngle = 0;

  public Clock() {
    double x = (PREF_W / 2.0) * (1 - 1 / Math.sqrt(3));
    double y = 3.0 * PREF_H / 4.0;

    myPath.moveTo(x, y);
    myPath.lineTo(PREF_W - x, y);
    myPath.lineTo(PREF_W / 2.0, PREF_H / 4.0);
    myPath.closePath();

    // Hilo para actualizar el reloj
    Thread clockThread = new Thread(() -> {
      while (true) {
        pendulumAngle += 0.01; // Incrementa el ángulo del péndulo
        if (pendulumAngle >= Math.PI * 2) {
          pendulumAngle = 0;
        }
        repaint();
        try {
          TimeUnit.SECONDS.sleep(1); // espera 1 segundo
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    clockThread.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int seconds = calendar.get(Calendar.SECOND);
    int minutes = calendar.get(Calendar.MINUTE);
    int hours = calendar.get(Calendar.HOUR);

    // Cuadrado
    double squareSize = 200;
    double squareX = (PREF_W - squareSize) / 2.0;
    double squareY = PREF_H * 3.0 / 4.0 + 20; // Ajusta la posicion vertical
                                              // aca

    g2.setPaint(Color.gray);
    g2.fillRect((int) squareX, (int) squareY, (int) squareSize, (int) squareSize);

    // Triangulo
    g2.setPaint(color);
    g2.fill(myPath);

    // Circulo
    // double squareSize = squareSize * 0.8; // Tamaño del círculo (80% del
    // cuadrado)
    // double squareX = squareX + (squareSize - squareSize) / 2.0;
    // double squareY = squareY + (squareSize - squareSize) / 2.0;

    // g2.setPaint(Color.white); // Color del círculo
    // g2.fillOval((int) squareX, (int) squareY, (int) squareSize, (int)
    // squareSize);

    // g2.setPaint(Color.black);
    // Font font = new Font("Times New Roman", Font.PLAIN, 24);
    // g2.setFont(font);

    for (int i = 1; i <= 12; ++i) {
      double angle = Math.toRadians(360 - (i * 30));
      double x = squareSize + squareSize / 2 * Math.cos(angle);
      double y = squareY + squareSize / 2 * Math.sin(angle);

      String numeral = convertToRoman(i);
      int width = g2.getFontMetrics().stringWidth(numeral);
      int height = g2.getFontMetrics().getHeight();

      g2.drawString(numeral, (int) x - width / 2, (int) y + height / 4);
    }

    // Dibuja el péndulo
    double pendulumX = squareX + squareSize / 2;
    double pendulumY = squareY + squareSize / 2;

    double pendulumBobX = pendulumX + pendulumLength * Math.sin(pendulumAngle);
    double pendulumBobY = pendulumY + pendulumLength * Math.cos(pendulumAngle);

    g2.setPaint(Color.black);
    g2.drawLine((int) pendulumX, (int) pendulumY, (int) pendulumBobX, (int) pendulumBobY);

    // Dibuja la manecilla de segundos
    double secondsAngle = Math.toRadians(90 - seconds * 6);
    double secondsX = squareX + squareSize / 2 * Math.cos(secondsAngle);
    double secondsY = squareY - squareSize / 2 * Math.sin(secondsAngle);
    g2.setPaint(Color.red);
    g2.draw(new Line2D.Double(squareX, squareY, secondsX, secondsY));

    // Dibuja la manecilla de minutos
    double minutesAngle = Math.toRadians(90 - minutes * 6 - seconds * 0.1);
    double minutesX = squareX + squareSize / 2 * Math.cos(minutesAngle);
    double minutesY = squareY - squareSize / 2 * Math.sin(minutesAngle);
    g2.setPaint(Color.blue);
    g2.draw(new Line2D.Double(squareX, squareY, minutesX, minutesY));

    // Dibuja la manecilla de horas
    double hoursAngle = Math.toRadians(90 - (hours % 12) * 30 - minutes * 0.5);
    double hoursX = squareX + squareSize / 3 * Math.cos(hoursAngle);
    double hoursY = squareY - squareSize / 3 * Math.sin(hoursAngle);
    g2.setPaint(Color.green);
    g2.draw(new Line2D.Double(squareX, squareY, hoursX, hoursY));

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
