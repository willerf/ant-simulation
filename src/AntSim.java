
import java.util.ArrayList;

public class AntSim {

    private ArrayList<Ant> ants;
    private double[][] pheromones;

    public AntSim() {
        ants = new ArrayList<>();
        pheromones = new double[Properties.WIDTH][Properties.HEIGHT];

        for(int i = 0; i < Properties.NUM_ANTS; i++) {
            ants.add(new Ant(Properties.WIDTH/2, Properties.HEIGHT/2, 2*Math.PI*Math.random()));
        }
    }

    public static void main(String[] args) {
        AntSim instance = new AntSim();
        instance.setUp();
    }

    public void setUp() {
        Window window = new Window();
        Environment environment = new Environment(ants, pheromones);
        window.add(environment);

        AntCalc antCalc = new AntCalc(ants, pheromones);
        antCalc.start();

        Pheromones pheromonesThread = new Pheromones(pheromones);
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
