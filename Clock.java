import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Line2D;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

public class Clock extends JPanel {
  private static final int PREF_W = 350; // Tama;o triangulo
  private static final int PREF_H = PREF_W;
  private static final Color color = Color.gray;
  private Path2D myPath = new Path2D.Double();
  private int pendulumLength = 100;
  private double pendulumAngle = 0;
  private boolean pendulumDirection = true; // True para derecha, False para izquierda

  public Clock() {
    double x = (PREF_W / 2.0) * (1 - 1 / Math.sqrt(3));
    double y = 2.0 * PREF_H / 4.0;

    myPath.moveTo(x, y);
    myPath.lineTo(PREF_W - x, y);
    myPath.lineTo(PREF_W / 2.0, PREF_H / 4.0);
    myPath.closePath();

    // Hilo para actualizar el reloj
    Thread clockThread = new Thread(() -> {
      while (true) {
        updatePendumlum();
        repaint();
        try {
          TimeUnit.MILLISECONDS.sleep(16); // espera 1 segundo
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
    double squareY = PREF_H * 2.0 / 4.0 + 20; // Ajusta la posicion vertical
                                              // aca

    g2.setPaint(Color.white);
    g2.fillRect((int) squareX, (int) squareY, (int) squareSize, (int) squareSize);

    // Dibuja el segundo cuadrado (abajo)
    double secondSquareY = PREF_H * 4.5 / 4.0 + 20; // Nueva posición vertical para el segundo cuadrado

    g2.setPaint(Color.gray); // Cambia el color si lo deseas
    g2.fillRect((int) squareX, (int) secondSquareY, (int) squareSize, (int) squareSize);

    // Triangulo
    g2.setPaint(color);
    g2.fill(myPath);

    Font font = new Font("Times New Roman", Font.PLAIN, 24);
    g2.setFont(font);
    double radius = squareSize / 2;

    for (int i = 1; i <= 12; ++i) {
      double angle = Math.toRadians(360 - (i * 30));
      double x = squareX + squareSize / 2 + radius * Math.cos(angle);
      double y = squareY + squareSize / 2 + radius * Math.sin(angle);

      String numeral = convertToRoman(i);
      int width = g2.getFontMetrics().stringWidth(numeral);
      int height = g2.getFontMetrics().getHeight();

      g2.drawString(numeral, (int) x - width / 2, (int) y + height / 4);
    }

    // Dibuja el péndulo
     double pendulumX = squareX + squareSize / 2;
     double pendulumY = secondSquareY + squareSize / 2;

     double pendulumBobX = pendulumX + pendulumLength * Math.sin(pendulumAngle);
     double pendulumBobY = pendulumY + pendulumLength * Math.cos(pendulumAngle);

     g2.setPaint(Color.black);
     g2.drawLine((int) pendulumX, (int) pendulumY, (int) pendulumBobX, (int)
     pendulumBobY);

    // Dibuja el círculo (peso) en la punta del péndulo
     int circleSize = 20; // Tamaño del círculo
     g2.fillOval((int) pendulumBobX - circleSize / 2, (int) pendulumBobY - circleSize / 2, circleSize, circleSize);

    // Calcular el centro del círculo
    double circleCenterX = squareX + squareSize / 2;
    double circleCenterY = squareY + squareSize / 2;

    // Dibuja la manecilla de segundos
    double secondsAngle = Math.toRadians(90 - seconds * 6);
    double secondsLength = squareSize / 2;
    double secondsX = circleCenterX + secondsLength * Math.cos(secondsAngle);
    double secondsY = circleCenterY - secondsLength * Math.sin(secondsAngle);
    g2.setPaint(Color.red);
    g2.draw(new Line2D.Double(circleCenterX, circleCenterY, secondsX, secondsY));

    // Dibuja la manecilla de minutos
    double minutesAngle = Math.toRadians(90 - minutes * 6 - seconds * 0.1);
    double minutesLength = squareSize / 2;
    double minutesX = circleCenterX + minutesLength * Math.cos(minutesAngle);
    double minutesY = circleCenterY - minutesLength * Math.sin(minutesAngle);
    g2.setPaint(Color.blue);
    g2.draw(new Line2D.Double(circleCenterX, circleCenterY, minutesX, minutesY));

    // Dibuja la manecilla de horas
    double hoursAngle = Math.toRadians(90 - (hours % 12) * 30 - minutes * 0.5);
    double hoursLength = squareSize / 3;
    double hoursX = circleCenterX + hoursLength * Math.cos(hoursAngle);
    double hoursY = circleCenterY - hoursLength * Math.sin(hoursAngle);
    g2.setPaint(Color.green);
    g2.draw(new Line2D.Double(circleCenterX, circleCenterY, hoursX, hoursY));
  }

  @Override
  public Dimension getPreferredSize() {
    if (isPreferredSizeSet())
      return super.getPreferredSize();
    return new Dimension(700, 700);
  }

  private String convertToRoman(int num) {
    String[] romanSymbols = { "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII" };
    return romanSymbols[num - 1];
  }

  private void updatePendumlum() {
    if (pendulumDirection) {
      pendulumAngle += 0.01;
      if (pendulumAngle >= Math.PI / 4) {
        pendulumDirection = false;
      }
    } else {
      pendulumAngle -= 0.01;
      if (pendulumAngle <= -Math.PI / 4) {
        pendulumDirection = true;
      }
    }
  }
}
