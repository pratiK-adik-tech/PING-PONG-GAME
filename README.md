# JAVA-Projects
Java Projects using DSA basics
// code starts from here

// class containing main method





public class PongGame {
    public static void main(String[] args) {
GameFrame frame = new GameFrame();

    }
}
 /*************************/
 
 import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    GamePanel panel;
    GameFrame(){
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("PiNg-PoNg GAME");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}


/**************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLOutput;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH =1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH*0.5555);

    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER =20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);


    }

    public void newPaddles(){
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);



    }
    public  void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync();
    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision(){
        //bounce ball off top and bottom window edges
        if(ball.y <=0){
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER){
            ball.setYDirection((-ball.yVelocity));

        }
        //bounce ball off paddles
        if(ball.intersects(paddle1)){
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //optional for more difficulty.
            if(ball.yVelocity >0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        if(ball.intersects(paddle2)){

            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.xVelocity++; //optional for more difficulty.
            if(ball.yVelocity >0)
                ball.yVelocity++;
            else
                ball.yVelocity--;
            ball.setXDirection(-ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }
        //stops paddle at window edge
        if(paddle1.y<=0){
            paddle1.y=0;

        }
        if(paddle1.y >=(GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle1.y = GAME_HEIGHT- PADDLE_HEIGHT;
        }
        if(paddle2.y <=0)
            paddle2.y =0;
        if(paddle2.y >= (GAME_HEIGHT-PADDLE_HEIGHT))
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;

        //Give a player one point and creates a new paddle and ball
        if(ball.x <=0){
            score.player2++;
            newPaddles();
            newBall();
            System.out.println("Player 2: " +score.player2);
        }
        if(ball.x >= GAME_WIDTH-BALL_DIAMETER){
            score.player1++;
            newPaddles();
            newBall();
            System.out.println("Player 1: " +score.player1);
        }
    }
public void run(){
        //Game loop

    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0;
    double ns = 1000000000/amountOfTicks;
    double delta =0;
    while(true){

        long now = System.nanoTime();
        delta += (now-lastTime)/ns;
        lastTime = now;

        if(delta >=1){
            move();
            checkCollision();
            repaint();
            delta--;
        }
    }

}
public class AL extends KeyAdapter{  //AL = action listener class
        public void keyPressed(KeyEvent e){
            paddle1.keyPresses(e);
            paddle2.keyPresses(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }

}
}

/**********************************/

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle extends Rectangle {
    int id;
    int yVelocity;
    int speed =10;
    Paddle(int x,int y,int PADDLE_WIDTH,int PADDLE_HEIGHT,int id){
        super(x,y,PADDLE_WIDTH,PADDLE_HEIGHT);
        this.id =id;

    }
    public void keyPresses(KeyEvent e){
        switch (id){
            case 1:
                if(e.getKeyCode()== KeyEvent.VK_W){
                    setYDirection(-speed);

                }
                if(e.getKeyCode()== KeyEvent.VK_S){
                    setYDirection(speed);
                }
                break;

            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    setYDirection(-speed);

                }
                if(e.getKeyCode()== KeyEvent.VK_DOWN){

                    setYDirection(speed);

                }
                break;
        }
    }
    public void keyReleased(KeyEvent e){
        switch(id){
            case 1:
                if(e.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(0);
                }
                if(e.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(0);
                }
                break;

            case 2:
                if(e.getKeyCode()==KeyEvent.VK_UP){
                    setYDirection(0);

                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(0);
                }
                break;
        }
    }
    public void setYDirection(int yDirection){
        yVelocity = yDirection;

    }
    public void move(){
        y = y+ yVelocity;
    }
    public void  draw(Graphics g){
        if(id==1)
            g.setColor(Color.BLUE);
        else
            g.setColor(Color.RED);
        g.fillRect(x,y,width,height);
    }

}

/***********************************/


import java.awt.*;
import java.util.*;

public class Ball extends Rectangle {
    Random random;
    int xVelocity;
    int yVelocity;
    int initialSpeed =2;

    Ball(int x, int y,int width, int height){
        super(x,y,width,height);
        random = new Random();
        int randomXDirection = random.nextInt(2);

        if(randomXDirection==0){
            randomXDirection--;
        }
        setXDirection(randomXDirection*initialSpeed);
        int randomYDirection = random.nextInt(2);
        if(randomYDirection ==0)
            randomYDirection--;
        setYDirection(randomYDirection*initialSpeed);

    }
    public void setXDirection(int randomXDirection){
        xVelocity = randomXDirection;

    }
    public void setYDirection(int randomYDirection){
        yVelocity = randomYDirection;

    }
    public void move(){
        x += xVelocity;
        y += yVelocity;
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x,y,height,width);

    }
}


/********************************/


import java.awt.*;

public class Score extends Rectangle {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;

    Score(int GAME_WIDTH,int GAME_HEIGHT){
        Score.GAME_HEIGHT = GAME_HEIGHT;
        Score.GAME_WIDTH = GAME_WIDTH;

    }
    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.setFont(new Font("Consolas",Font.PLAIN,60));
        g.drawLine(GAME_WIDTH/2,0,GAME_WIDTH/2,GAME_HEIGHT);

        g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10),(GAME_WIDTH/2)-85,50);

        g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10),(GAME_WIDTH/2)+20,50);



    }
}


/********************************************/
 
 
