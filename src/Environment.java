
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Environment extends JPanel {

    private static final int ANT_SIZE = 3;
    private ArrayList<Ant> ants;
    private ArrayList<Square> structures;
    private double[][] homePheromones;
    private double[][] foodPheromones;
    private boolean[][] food;
    private IntHolder foodCaptured;

    private int[] homeRGBPhero;
    private int[] foodRGBPhero;

    public Environment(ArrayList<Ant> antsIn, ArrayList<Square> structuresIn, double[][] homePheromonesIn, double[][] foodPheromonesIn, boolean[][] foodIn, IntHolder foodCapturedIn) {
        ants = antsIn;
        structures = structuresIn;
        homePheromones = homePheromonesIn;
        foodPheromones = foodPheromonesIn;
        food = foodIn;
        foodCaptured = foodCapturedIn;

        homeRGBPhero = new int[3];
        homeRGBPhero[0] = Properties.HOME_PHEROMONE_COLOR.getRed();
        homeRGBPhero[1] = Properties.HOME_PHEROMONE_COLOR.getGreen();
        homeRGBPhero[2] = Properties.HOME_PHEROMONE_COLOR.getBlue();

        foodRGBPhero = new int[3];
        foodRGBPhero[0] = Properties.FOOD_PHEROMONE_COLOR.getRed();
        foodRGBPhero[1] = Properties.FOOD_PHEROMONE_COLOR.getGreen();
        foodRGBPhero[2] = Properties.FOOD_PHEROMONE_COLOR.getBlue();
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
            g.fillRect(Properties.BORDER_WIDTH, Properties.BORDER_WIDTH, Properties.WIDTH - 2*Properties.BORDER_WIDTH, Properties.HEIGHT - 2*Properties.BORDER_WIDTH);
        }

        g.setColor(Color.PINK);
        g.fillOval(Properties.WIDTH/2 - Properties.HOME_RADIUS, Properties.HEIGHT/2 - Properties.HOME_RADIUS, 2*Properties.HOME_RADIUS, 2*Properties.HOME_RADIUS);

        g.setColor(Color.YELLOW);
        for(int i = 0; i < structures.size(); i++) {
            Square square = structures.get(i);
            g.fillRect(square.getX1(), square.getY1(), square.getX2() - square.getX1(), square.getY2() - square.getY1());
        }

        g.setColor(Color.BLACK);



        Font font = new Font("TimesRoman", Font.PLAIN, 30);
        drawStringCenter(g, foodCaptured.getInt()+ "", font, Properties.WIDTH/2, Properties.HEIGHT/2);

        for (int i = 0; i < Properties.WIDTH; i++) {
            for (int j = 0; j < Properties.HEIGHT; j++) {
                if(Properties.DRAW_PHEROMONES) {
                    if (homePheromones[i][j] > 0) {
                        g.setColor(new Color(homeRGBPhero[0], homeRGBPhero[1], homeRGBPhero[2], (int) (255 * homePheromones[i][j])));
                        drawPixel(g, i, j);
                    }
                }
                if(Properties.DRAW_PHEROMONES) {
                    if (foodPheromones[i][j] > 0) {
                        g.setColor(new Color(foodRGBPhero[0], foodRGBPhero[1], foodRGBPhero[2], (int) (255 * foodPheromones[i][j])));
                        drawPixel(g, i, j);
                    }
                }
                if(food[i][j]) {
                    g.setColor(Properties.FOOD_COLOR);
                    drawPixel(g, i, j);
                }
            }
        }

        if(Properties.DRAW_ANT || Properties.DRAW_VISION) {
            for (int i = 0; i < ants.size(); i++) {
                Ant ant = ants.get(i);

                if(Properties.DRAW_ANT) {
                    if(ant.getFoodStatus())
                        g.setColor(Properties.ANT_COLOR);
                    else
                        g.setColor(Color.CYAN);
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

    public void drawStringCenter(Graphics g, String text, Font font, int x, int y) {
        FontMetrics metrics = g.getFontMetrics(font);
        int newX = x - metrics.stringWidth(text)/2;
        int newY = y - metrics.getHeight()/2 + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, newX, newY);
    }
}
