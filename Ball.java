import java.awt.*;

public class Ball {

  public int xpos;
  public int ypos;

  public int dx;          //the speed of the hero in the y direction
  public int dy;          //the speed of the hero in the y direction

  public Rectangle rec;

  public Ball() {
    xpos = 200;
    ypos = 200;
    dx = 2;
    dy = 4;
    rec = new Rectangle(xpos, ypos, 20, 20);
  }

  public void move() {
    xpos = xpos + dx;
    ypos = ypos + dy;

    rec = new Rectangle(xpos, ypos, 20, 20);

    if (ypos<0 || ypos>500-40) {
      dy = dy * -1;
    }

    if (dy>10) {
      dy = 10;
    }

    if (dy<-10) {
      dy = -10;
    }
  }

  public void switchdir() {
    dx = dx * -1;
  }

  public void speedup() {
    if (dy<0) {
      dy++;
    }

    if (dy>0) {
      dy--;
    }
  }

  public void slowdown() {
    if (dy<0) {
      dy++;
    }

    if (dy>0) {
      dy--;
    }
  }

  public void reset() {
    xpos = 200;
    ypos = 80;
    dx = 2;
    dy = 4;
  }
}
