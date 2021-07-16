
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
                        pheromones[i][j] -= 0.001;
                }
            }
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {

            }
        }
    }

}
