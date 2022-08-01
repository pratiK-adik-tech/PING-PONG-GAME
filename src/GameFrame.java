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
