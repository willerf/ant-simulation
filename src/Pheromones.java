
public class Pheromones extends Thread {

    private double[][] homePheromones;
    private double[][] foodPheromones;

    public Pheromones(double[][] homePheromonesIn, double[][] foodPheromonesIn) {
        homePheromones = homePheromonesIn;
        foodPheromones = foodPheromonesIn;
    }

    public void run() {
        while(true) {
            for (int i = 0; i < Properties.WIDTH; i++) {
                for (int j = 0; j < Properties.HEIGHT; j++) {
                    if (homePheromones[i][j] > 0)
                        homePheromones[i][j] -= Properties.PHEROMONE_DISSIPATION;
                    if (foodPheromones[i][j] > 0)
                        foodPheromones[i][j] -= Properties.PHEROMONE_DISSIPATION;
                }
            }
            try {
                Thread.sleep(Properties.PHEROMONES_DELAY);
            } catch(InterruptedException e) {
                System.err.println("Sleep Error");
            }
        }
    }
}
