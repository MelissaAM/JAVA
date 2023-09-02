import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;

import javax.swing.JComponent;
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
    g2.setPaint(color);
    g2.fill(myPath);
  }

  @Override
  public Dimension getPreferredSize() {
    if (isPreferredSizeSet())
      return super.getPreferredSize();
    return new Dimension(PREF_W, PREF_H);
  }
}

