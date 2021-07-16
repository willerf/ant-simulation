
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Environment extends JPanel {

    private static final int ANT_SIZE = 3;
    private ArrayList<Ant> ants;
    private double[][] pheromones;

    private int redPhero;
    private int greenPhero;
    private int bluePhero;

    public Environment(ArrayList<Ant> antsIn, double[][] pheromonesIn) {
        ants = antsIn;
        pheromones = pheromonesIn;

        redPhero = Properties.PHEROMONE_COLOR.getRed();
        greenPhero = Properties.PHEROMONE_COLOR.getGreen();
        bluePhero = Properties.PHEROMONE_COLOR.getBlue();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Properties.BORDER_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        if(Properties.OVAL) {
            g.setColor(Properties.BACKGROUND_COLOR);
            g.fillOval(Properties.BORDER_WIDTH, Properties.BORDER_WIDTH, Properties.WIDTH - 2*Properties.BORDER_WIDTH, Properties.HEIGHT - 2*Properties.BORDER_WIDTH);
        }
        else {
            g.setColor(Properties.BACKGROUND_COLOR);
            g.fillRect(Properties.BORDER_WIDTH, Properties.BORDER_WIDTH, Properties.WIDTH - 2*Properties.BORDER_WIDTH, Properties.HEIGHT - 2*Properties.BORDER_WIDTH);        }

        if(Properties.DRAW_PHEROMONES) {
            for (int i = 0; i < pheromones.length; i++) {
                for (int j = 0; j < pheromones[i].length; j++) {
                    if (pheromones[i][j] > 0) {
                        g.setColor(new Color(redPhero, bluePhero, greenPhero, (int) (255 * pheromones[i][j])));
                        drawPixel(g, i, j);
                    }
                }
            }
        }

        if(Properties.DRAW_ANT || Properties.DRAW_VISION) {
            for (int i = 0; i < ants.size(); i++) {
                Ant ant = ants.get(i);

                if(Properties.DRAW_ANT) {
                    g.setColor(Properties.ANT_COLOR);
                    drawAnt(g, (int) ant.getX(), (int) ant.getY(), ant.getAngle());
                }
                if(Properties.DRAW_VISION) {
                    g.setColor(Properties.VISION_COLOR);
                    drawVision(g, (int) ant.getX(), (int) ant.getY(), ant.getAngle());
                }
            }
        }
    }

    public void drawAnt(Graphics g, int x, int y, double angle) {
        angle += Math.PI;
        int xLength = (int)(ANT_SIZE*Math.cos(angle));
        int yLength = (int)(ANT_SIZE*Math.sin(angle));

        g.drawLine(x, y, x + xLength, y - yLength);
    }

    public void drawPixel(Graphics g, int x, int y) {
        g.drawLine(x, y, x, y);
    }

    public void drawVision(Graphics g, int x, int y, double angle) {
        drawAnt(g, x, y, angle);
        for(int r = Properties.VISION_DIST; r > Properties.VISION_DIST-Properties.VISION_DEPTH; r--) {
            for(double theta = -Properties.VISION_ANGLE; theta < Properties.VISION_ANGLE; theta+= Math.PI/24) {
                drawPixel(g, (int)(x + r*Math.cos(angle + theta)), (int)(y - r*Math.sin(angle + theta)));
            }
        }
    }
}
