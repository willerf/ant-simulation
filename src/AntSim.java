
import java.util.ArrayList;

public class AntSim {

    private ArrayList<Ant> ants;
    private ArrayList<Square> structures;
    private double[][] homePheromones;
    private double[][] foodPheromones;
    private boolean[][] food;
    private IntHolder foodCaptured;

    public AntSim() {
        ants = new ArrayList<>();
        homePheromones = new double[Properties.WIDTH][Properties.HEIGHT];
        foodPheromones = new double[Properties.WIDTH][Properties.HEIGHT];
        food = new boolean[Properties.WIDTH][Properties.HEIGHT];
        foodCaptured = new IntHolder(0);
        structures = new ArrayList<>();
        structures.add(new Square(200, 250, 800, 300));

        for(int i = 0; i < 200; i++) {
            for(int j = 0; j < 50; j++) {
                food[i + 400][j + 100] = true;
            }
        }

        for(int i = 0; i < Properties.NUM_ANTS; i++) {
            double angle = 2 * Math.PI * Math.random();
            ants.add(new Ant((int)(Properties.WIDTH / 2 + (Properties.HOME_RADIUS+2)*Math.cos(angle)), (int)(Properties.HEIGHT / 2 - (Properties.HOME_RADIUS+2)*Math.sin(angle)), angle));
        }
    }

    public static void main(String[] args) {
        AntSim instance = new AntSim();
        instance.setUp();
    }

    public void setUp() {
        Window window = new Window();
        Environment environment = new Environment(ants, structures, homePheromones, foodPheromones, food, foodCaptured);
        window.add(environment);

        AntCalc antCalc = new AntCalc(ants, structures, homePheromones, foodPheromones, food, foodCaptured);
        antCalc.start();

        Pheromones pheromonesThread = new Pheromones(homePheromones, foodPheromones);
        pheromonesThread.start();

        while(true) {
            environment.repaint();
            try {
                Thread.sleep(Properties.GRAPHICS_DELAY);
            } catch(InterruptedException e) {
                System.err.println("Sleep Error");
            }
        }
    }
}
