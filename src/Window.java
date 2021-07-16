
import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Properties.WIDTH + 56, Properties.HEIGHT + 79);
        setTitle("Ant Simulation");
        getRootPane().setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, Color.BLACK));
        setVisible(true);
    }

}
