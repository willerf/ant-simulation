
public class Pheromones extends Thread {

    private double[][] pheromones;

    public Pheromones(double[][] pheromonesIn) {
        pheromones = pheromonesIn;
    }

    public void run() {
        while(true) {
            for (int i = 0; i < pheromones.length; i++) {
                for (int j = 0; j < pheromones[i].length; j++) {
                    if (pheromones[i][j] > 0)
                        pheromones[i][j] -= Properties.PHEROMONE_DISSIPATION;
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
