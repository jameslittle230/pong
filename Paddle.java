// TODO: Get d.width() working.
// TODO: Get p1.draw() working.

import java.awt.*;

public class Paddle {
  public int pos;
  public int xpos;

  public int dy;

  public boolean isGoingUp;
  public boolean isGoingDown;

  public Rectangle rec;
  public Rectangle rechi;
  public Rectangle reclo;
  public Dimension d;
  public Graphics g;

  public Paddle(boolean p2, Pong app) {
    pos = 50;
    dy = 3;

    g = app.getGraphics(); // gets the graphics from the original applet

    isGoingUp = false;
    isGoingDown = false;

    if (p2) {
      xpos = 800 - 60;
    } else if (!p2) {
      xpos = 40;
    }

    rec = new Rectangle(xpos, pos, 20, 70);
    rechi = new Rectangle(xpos, pos, 20, 10);
    reclo = new Rectangle(xpos, pos+50, 20, 10);
  }

  public void draw(Graphics z) {
    z = g;
    g.setColor(Color.green);
    g.fillRect(xpos, pos, 20, 70);
  }

  public void goup() {
    isGoingDown = false;
    isGoingUp = true;
  }

  public void godown() {
    isGoingUp = false;
    isGoingDown = true;
  }

  public void freeze() {
    isGoingDown = false;
    isGoingUp = false;
  }

  public void move() {
    if (isGoingUp == true) {
      pos = pos - dy;
    }

    if (isGoingDown == true) {
      pos = pos + dy;
    }

    if (pos<0) {
      pos = 0;
    }

    if (pos>500-70) {
      pos = 500-70;
    }

    rec = new Rectangle(xpos, pos, 20, 70);
    rechi = new Rectangle(xpos, pos, 20, 10);
    reclo = new Rectangle(xpos, pos+50, 20, 10);
  }

  public void reset() {
    pos = 100;
  }
}
