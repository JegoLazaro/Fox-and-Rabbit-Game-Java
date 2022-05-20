import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{


    static final int SC_WIDTH = 800;
    static final int SC_HEIGHT = SC_WIDTH;
    static final int BOX_SIZE = 40;
    static final int UNITS = (SC_WIDTH * SC_HEIGHT)/BOX_SIZE;
    static final int DELAY = 120;

    static final int W_OVER_S = SC_WIDTH/BOX_SIZE;
    static final int H_OVER_S = SC_HEIGHT/BOX_SIZE;

    int foxMvCount = 0;
    int MovesCounter = 0;
    int TileCounterX = 0;
    int TileCounterY = 0;

    int direction = 0; //0 right, 1 left, 2 up, 3 down

    final int x[] = new int[UNITS];
    final int y[] = new int[UNITS];
    final int rabbitXCoor[] = new int [50];
    final int rabbitYCoor[] = new int [50];

    int numOfRabbits = 3;

    int rabbitsEaten = 0;

    boolean start = true;
    boolean chase = false;
    Timer t;
    Random r;

    GamePanel(){
        r = new Random();
        this.setPreferredSize(new Dimension(SC_WIDTH,SC_HEIGHT));
        this.setBackground(Color.GRAY);
        this.setFocusable(true);

        this.addKeyListener(new keyAdapter());

        startGame();
    }

    public void startGame() {
        addRabbit();
        chase = false;
        t = new Timer(DELAY,this);
        t.start();
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        ImageIcon ic = new ImageIcon("C:\\Users\\LENOVO\\OneDrive\\Desktop\\FoxAndRabbit\\src\\fox.png");//PIC FOR FOX
        Image img = ic.getImage();
        ImageIcon ic1 = new ImageIcon("C:\\Users\\LENOVO\\OneDrive\\Desktop\\FoxAndRabbit\\src\\rabbit.png");//PIC FOR RABBITS
        Image img1 = ic1.getImage();

        if (start == false) {

            if(numOfRabbits >= 50) {
                gameOver(g);
            }
            else {

                for(int i = 0; i < SC_HEIGHT/BOX_SIZE; i++) {
                    g.drawLine(i*BOX_SIZE, 0, i*BOX_SIZE, SC_HEIGHT);
                    g.drawLine(0, i*BOX_SIZE, SC_WIDTH, i*BOX_SIZE);
                    g.setColor(Color.MAGENTA);
                }
                //RABBIT
                for(int i = 0 ; i < numOfRabbits; i++) {
                    g2d.drawImage(img1, rabbitXCoor[i], rabbitYCoor[i], BOX_SIZE, BOX_SIZE, null);
                }

                for(int i = 0; i < 1; i++){
                    //FOX
                    g2d.drawImage(img, x[i], y[i], BOX_SIZE, BOX_SIZE, null);
                    TileCounterX = x[i];
                    TileCounterY = y[i];
                }

                g.setFont(new Font("Comic Sans MS", Font.ITALIC, 15));
                g.setColor(Color.BLACK);
                g.drawString("Score: "+ rabbitsEaten, 10, g.getFont().getSize());
                g.drawString("FOX MOVES: "+ MovesCounter, 670, g.getFont().getSize());
                g.drawString("X: "+ TileCounterX/40, 750, 30);
                g.drawString("Y: "+ TileCounterY/40, 750, 50);
                g.drawString("# of Rabbits: "+ numOfRabbits, 665, 70);


                addRabbit();
            }

        }
        else if (start == true || numOfRabbits >= 50) {
            gameOver(g);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    public void addRabbit() {

        if (start) {
            for(int i = 0; i < numOfRabbits; i++) {
                rabbitXCoor[i] = r.nextInt((int)(W_OVER_S))*BOX_SIZE;
                rabbitYCoor[i] = r.nextInt((int)(H_OVER_S))*BOX_SIZE;
            }

            start = false;
        }

        else{

            if(foxMvCount == 3) {
                numOfRabbits *=2;
                foxMvCount = 0;

                for(int i = numOfRabbits/2; i < numOfRabbits; i++) {
                    rabbitXCoor[i] = r.nextInt((int)(W_OVER_S))*BOX_SIZE;
                    rabbitYCoor[i] = r.nextInt((int)(H_OVER_S))*BOX_SIZE;
                }
            }

            else {
                for(int i = 0; i < numOfRabbits; i++) {
                    if((x[0] == rabbitXCoor[i]) && (y[0] == rabbitYCoor[i])) {
                        rabbitXCoor[i] = r.nextInt((int)(W_OVER_S))*BOX_SIZE;
                        rabbitYCoor[i] = r.nextInt((int)(H_OVER_S))*BOX_SIZE;
                    }
                }
            }
        }

    }

    public void RabbitEaten() {
        for(int i = 0; i < numOfRabbits; i++) {
            if((x[0] == rabbitXCoor[i]) && (y[0] == rabbitYCoor[i])) {
                rabbitXCoor[i] = r.nextInt((int)(W_OVER_S))*BOX_SIZE;
                rabbitYCoor[i] = r.nextInt((int)(H_OVER_S))*BOX_SIZE;
                rabbitsEaten++;
            }
        }
    }

    public void foxMove() {
        for(int i = 1; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if (direction == 0) {
            x[0] = x[0] + BOX_SIZE;
            foxMvCount++;
            MovesCounter++;
        } else if (direction == 1) {
            x[0] = x[0] - BOX_SIZE;
            foxMvCount++;
            MovesCounter++;
        } else if (direction == 2) {
            y[0] = y[0] - BOX_SIZE;
            foxMvCount++;
            MovesCounter++;
        } else if (direction == 3) {
            y[0] = y[0] + BOX_SIZE;
            foxMvCount++;
            MovesCounter++;
        }
        //System.out.println(foxMvCount);
    }


    public void rabbitMove() {

        for(int i = 0; i < numOfRabbits;i++) {
            if (direction == r.nextInt(4)) {
                rabbitXCoor[i] = rabbitXCoor[i] + BOX_SIZE;
                //foxMvCount++;
            } else if (direction == r.nextInt(4)) {
                rabbitXCoor[i] = rabbitXCoor[i] - BOX_SIZE;
                //foxMvCount++;
            } else if (direction == r.nextInt(4)) {
                rabbitYCoor[i] = rabbitYCoor[i] - BOX_SIZE;
                //foxMvCount++;
            } else if (direction == r.nextInt(4)) {
                rabbitYCoor[i] = rabbitYCoor[i] + BOX_SIZE;
                //foxMvCount++;
            }
        }

    }

    public void RabbitsNum() {//checks if rabbit(s) is eaten
        for(int i= 0; i < numOfRabbits; i++) {
            if((x[0] == rabbitXCoor[i]) && (y[0] == rabbitYCoor[i])) {
                RabbitEaten();
            }
        }
    }

    public void setBordersFox() {
        if (x[0] < 0) {
            x[0] = x[0] + BOX_SIZE;
        }
        else if (x[0] > SC_WIDTH) {
            x[0] = x[0] - BOX_SIZE;
        }
        else if (y[0] < 0) {
            y[0] = y[0] + BOX_SIZE;
        }
        else if (y[0] > SC_HEIGHT) {
            y[0] = y[0] - BOX_SIZE;
        }
    }

    public void setBordersRabbit() {

        if (rabbitXCoor[0] < 0) {
            rabbitXCoor[0] = rabbitXCoor[0] + BOX_SIZE;
        }
        else if (rabbitXCoor[0] > SC_WIDTH) {
            rabbitXCoor[0] = rabbitXCoor[0] - BOX_SIZE;
        }
        else if (rabbitYCoor[0] < 0) {
            rabbitYCoor[0] = rabbitYCoor[0] + BOX_SIZE;
        }
        else if (rabbitYCoor[0] > SC_HEIGHT) {
            rabbitYCoor[0] = rabbitYCoor[0] - BOX_SIZE;
        }
    }

    public void callBorders() {
        for(int i = 1; i > 0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                chase = false;
            }
        }

        if(x[0] < 0 || x[0] > SC_WIDTH || y[0] < 0 || y[0] > SC_HEIGHT) {
            chase = false;
        }

        if(!chase) {
            t.stop();
        }

    }
    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Helvetica", Font.BOLD, 75));
        g.drawString("Score: "+ rabbitsEaten, 225, 310);
        g.drawString("You Lose", 205, 180);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial Black", Font.BOLD, 15));

        g.drawString("FOX MOVES: "+ MovesCounter, 670, g.getFont().getSize());
        g.drawString("TILE X: "+ TileCounterX/40, 670, 30);
        g.drawString("Y: "+ TileCounterY/40, 760, 30);
        g.drawString("Number of Rabbits: "+ numOfRabbits, 615, 70);
        t.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(chase) {
            foxMove();
            rabbitMove();
            RabbitsNum();

            setBordersFox();
            setBordersRabbit();
        }
        repaint();

    }

    public class keyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT) {
                chase = true;
                direction = 1;
            }else if (key == KeyEvent.VK_RIGHT) {
                chase = true;
                direction = 0;
            }else if (key == KeyEvent.VK_UP) {
                chase = true;
                direction = 2;
            }else if (key == KeyEvent.VK_DOWN) {
                chase = true;
                direction = 3;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if(key == KeyEvent.VK_LEFT) {
                chase = false;
                direction = 1;
            }
            else if (key == KeyEvent.VK_RIGHT) {
                chase = false;
                direction = 0;
            }
            else if (key == KeyEvent.VK_UP) {
                chase = false;
                direction = 2;
            }
            else if (key == KeyEvent.VK_DOWN) {
                chase = false;
                direction = 3;
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {

        }
    }

}

