import java.awt.*;

public class Properties {

    public static final int NUM_ANTS = 10000;

    public static final boolean OVAL = false;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    public static final int BORDER_WIDTH = 50;

    public static final boolean DRAW_ANT = false;
    public static final boolean DRAW_PHEROMONES = true;
    public static final boolean DRAW_VISION = false;

    public static final double VISION_ANGLE = Math.PI/6;
    public static final int VISION_DIST = 10;
    public static final int VISION_DEPTH = 1;

    public static final int SPEED = 1;
    public static final double WANDER = 0.2;

    public static final double PHEROMONE_STRENGTH = 0.1;
    public static final double PHEROMONE_DISSIPATION = 0.005;

    public static final int ANT_STEP_DELAY = 10;
    public static final int PHEROMONES_DELAY = 10;
    public static final int GRAPHICS_DELAY = 10;

    public static final Color BORDER_COLOR = Color.BLACK;
    public static final Color BACKGROUND_COLOR = Color.BLACK;

    public static final Color ANT_COLOR = Color.BLUE;
    public static final Color PHEROMONE_COLOR = Color.CYAN;
    public static final Color VISION_COLOR = Color.BLUE;

}
