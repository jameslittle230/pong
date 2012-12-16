// Threaded Applet Template
// Jamie Little
// December 2012

// TODO: Make more stuff variables
// TODO: goup() and godown() methods in the paddle

// Import section
// Use this section to add additional libaries for use in your program.
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// This begins the class definition.
public class Pong extends Applet implements Runnable, KeyListener {

  // variable declaration section
  public int p1score;
  public int p2score;

  public Paddle p1;
  public Paddle p2;
  public Ball ball;

  public int paddleheight;
  public int paddlewidth;
  public int balldim;

  public int artificial;

  public int windowwidth;
  public int windowheight;

  public Graphics offscreen;
  public Image temp;
  public boolean red;
  public Font title;
  public Font score;
  public Font text;

  // This variable changes what gets painted to the game.
  // 0: Pre-game titles
  // 1: Playing the Game
  // 2: Post game "You Win!"
  public int gamestate;

  // Sets up a Thread called thread
  public Thread thread;

  // ****************************************************************************
  // Method definition section
  // init() is the first method an Applet runs when started

  public void init()
  {
    // Initialize variables
    gamestate = 0;

    title = new Font("Emulogic", Font.PLAIN, 56);
    score = new Font("Emulogic", Font.PLAIN, 23);
    text =  new Font("Emulogic", Font.PLAIN, 16);

    p1score = 0;
    p2score = 0;

    paddleheight = 70;
    paddlewidth = 20;
    balldim = 20;

    windowheight = 500;
    windowwidth = 800; //                                                                           ////
//                                                                                                ////
    artificial = 0; // Change me to turn ai on or off.                                       <<<<<<<<<<<<<<<<<<<<<<
//                                                                                                \\\\
    // Construct objects                                                                            \\\\
    ball = new Ball();
    p1 = new Paddle(false, this);
    p2 = new Paddle(true, this);

    // Other Stuff
    setSize(windowwidth, windowheight);                     // Sets applet size
    setBackground(Color.black);                             // Sets applet background - gets changed temporarily by boolean red
    temp = createImage(bounds().width,bounds().height);     // Creates image that's drawn to virtual graphics
    offscreen = temp.getGraphics();                         // Links virtual graphics to temp image
    addKeyListener(this);                                   // Sets up keyboard listening

    // Set up the thread - This should be the LAST lines in your init() method.
    thread = new Thread(this);
    thread.start();
  } //init()

  // ****************************************************************************
  // paint() is used to display things on the screen

  public void paint(Graphics g)
  {

    // Clear Buffer
    if (red) {
      offscreen.setColor(Color.red);
    } else {
      offscreen.setColor(Color.black);
    }
    offscreen.fillRect(0,0,windowwidth,windowheight);

    if (gamestate == 0) {
      // offscreen.setColor(Color.red);
      // offscreen.fillRect(400, 0, 1, 500);
      // offscreen.fillRect(300, 0, 1, 500);
      // offscreen.fillRect(500, 0, 1, 500);
      // offscreen.fillRect(200, 0, 1, 500);
      // offscreen.fillRect(600, 0, 1, 500);
      offscreen.setColor(Color.green);
      offscreen.setFont(title);
      offscreen.drawString("Pong", 284, 200);
      offscreen.setFont(score);
      offscreen.drawString("By Jamie Little", 235, 275);
      offscreen.setFont(text);
      if (artificial == 0) {
        offscreen.drawString("Press 1 for Player v. Comp", 193, 420);
        offscreen.drawString("Press 2 for Player v. Player", 178, 450);
      }

      if (artificial == 1) {
        offscreen.drawString("1 Player Selected", 200, 420);
        offscreen.drawString("Controls: Up: W; Down: S", 200, 450);
      }

      if (artificial == 2) {
        offscreen.drawString("2 Players Selected", 200, 420);
        offscreen.drawString("P1: Up: W; Down: S", 200, 450);
        offscreen.drawString("P2: Up: I; Down: K", 200, 480);
      }
    }

    if (gamestate == 1) {
      offscreen.setColor(Color.green);
      offscreen.fillRect(p1.xpos, p1.pos, paddlewidth, paddleheight);
      offscreen.fillRect(p2.xpos, p2.pos, paddlewidth, paddleheight);
      offscreen.fillRect(ball.xpos, ball.ypos, balldim, balldim);
      offscreen.setFont(score);
      offscreen.drawString(""+p1score, 10, 450);
      offscreen.drawString(""+p2score, 770, 450);
    }

    // Draw Buffer to Screen
    g.drawImage(temp, 0, 0, this);


  } // paint()

  // ****************************************************************************
  // Check for Intersections between ball and rectangles

  public void intersectAll() {
    if(ball.rec.intersects(p1.rec) || ball.rec.intersects(p2.rec)) {
      ball.switchdir();
    }

    // if(ball.rec.intersects(p1.rechi) || ball.rec.intersects(p2.rechi) || ball.rec.intersects(p1.reclo) || ball.rec.intersects(p2.reclo)) {
    //   ball.speedup();
    // }
  }

  public void ai() {
    if (artificial == 1) {
      if (ball.dx<0) {
        if (p2.pos>215) {
          p2.goup();
        } else if (p2.pos<215) {
          p2.godown();
        } else {
          p2.freeze();
        }
      } else if (ball.dx>0) {
        if (ball.xpos<500) {
          if ((p2.pos+35)>(ball.ypos+10)) {
            p2.goup();
          } else if ((p2.pos+35)<(ball.ypos+10)) {
            p2.godown();
          } else {
            p2.freeze();
          }
        } else {
          if (ball.dy>0) {
            p2.godown();
          } else if (ball.dy<0) {
            p2.goup();
          }
        }
      }
    }
  }

  public void resetall() {
    try {
      thread.sleep(2500);
    }
    catch (Exception e){ }

    ball.reset();
    p1.reset();
    p2.reset();
    red = false;
    repaint();

    try {thread.sleep(2500);}
    catch (Exception e){ }
  }

  // ****************************************************************************
  // Every thread needs a run method

  public void run() {
    // This thread loop forever and runs the paint method and then sleeps.
    while(true)
    {
      // HERE IS WHERE YOU WRITE WHAT THE PROGRAM DOES

      if (gamestate == 1) {
        p1.move();
        ai();
        p2.move();
        ball.move();
        intersectAll();
      }

      repaint(); // Run the paint method.

      if (gamestate == 0) {
        if (artificial == 1 || artificial == 2) {
          try {thread.sleep(4000);}
          catch (Exception e){ }
          gamestate = 1;
          repaint();
          try {thread.sleep(1000);}
          catch (Exception e){ }
        }
      }

      if (ball.xpos<0 - balldim) {
        red = true;
        p2score++;
        resetall();
      }

      if (ball.xpos>windowwidth) {
        red = true;
        p1score++;
        resetall();
      }

      //sleep
      try {thread.sleep(20);}
      catch (Exception e){ }
    } // while
  } // run()

  // ****************************************************************************
  // Begin required keylistener methods

  // Common use cases: Changing variables on keypress/keyrelease. A change in the variables makes something happen.

  public void keyPressed(KeyEvent event) {
    String keyin;
    keyin = ""+event.getKeyText( event.getKeyCode());

    if (gamestate == 0) {
      if (keyin.equals("1")) {
        artificial = 1;
      }

      if (keyin.equals("2")) {
        artificial = 2;
      }
    }

    if (gamestate == 1) {
      if(keyin.equals("W")) {
        p1.goup();
      }

      if(keyin.equals("S")) {
        p1.godown();
      }

      if (artificial == 2) {
       if(keyin.equals("I")) {
          p2.goup();
        }

        if(keyin.equals("K")) {
          p2.godown();
        }
      }
    }
  }

  public void keyReleased(KeyEvent event) {
    String keyin;
    keyin = ""+event.getKeyText( event.getKeyCode());

    if(keyin.equals("W")) {
      p1.freeze();
    }

    if(keyin.equals("S")) {
      p1.freeze();
    }

    if (artificial == 2) {
      if(keyin.equals("I")) {
        p2.freeze();
      }

      if(keyin.equals("K")) {
        p2.freeze();
      }
    }

  }

  public void keyTyped( KeyEvent event ) {
    // Silence is Golden
  }
} // World
