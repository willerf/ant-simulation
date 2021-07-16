
import java.util.ArrayList;

public class AntCalc extends Thread {

    private ArrayList<Ant> ants;
    private double[][] pheromones;

    public AntCalc(ArrayList<Ant> antsIn, double[][] pheromonesIn) {
        ants = antsIn;
        pheromones = pheromonesIn;
    }

    public void run() {
        while(true) {
            nextFrame();
            try {
                Thread.sleep(Properties.ANT_STEP_DELAY);
            } catch(InterruptedException e) {
                System.err.println("Sleep Error");
            }
        }
    }

    public void nextFrame() {
        for(int i = 0; i < ants.size(); i++) {
            Ant ant = ants.get(i);
            double x = ant.getX();
            double y = ant.getY();
            double angle = ant.getAngle();

            double xAdj = x - Properties.WIDTH / 2;
            double yAdj = Properties.HEIGHT / 2 - y;

            int decWidth = Properties.WIDTH - Properties.BORDER;
            int decHeight = Properties.HEIGHT - Properties.BORDER;

            if(Properties.OVAL) {
                if ((xAdj/(decWidth/2))*(xAdj/(decWidth/2)) + (yAdj/(decHeight/2))*(yAdj/(decHeight/2)) > 1) {
                    angle = getAngle(xAdj, yAdj);
                }
            }
            else {
                if(x < Properties.BORDER || x > decWidth || y < Properties.BORDER || y > decHeight) {
                    angle = getAngle(xAdj, yAdj);
                }
            }

            x += Properties.SPEED * Math.cos(angle);
            y -= Properties.SPEED * Math.sin(angle);
            angle += Properties.WANDER*Math.random() - Properties.WANDER/2;

            double maxAngle = 0;
            boolean foundPheromone = false;

            for (int r = Properties.VISION_DIST; r > Properties.VISION_DIST-Properties.VISION_DEPTH; r--) {
                double maxPheromone = 0;
                for (double theta = -Properties.VISION_ANGLE; theta < Properties.VISION_ANGLE; theta += Math.PI/24) {
                    int xIndex = (int) (x + r * Math.cos(angle + theta));
                    int yIndex = (int) (y - r * Math.sin(angle + theta));

                    double curPheromone = 0;
                    if (xIndex >= 0 && xIndex < Properties.WIDTH && yIndex >= 0 && yIndex < Properties.HEIGHT) {
                        curPheromone = pheromones[xIndex][yIndex];
                    } else {
                        System.err.println("Vision Out Of Bounds Error");
                    }

                    if (curPheromone > maxPheromone) {
                        maxAngle = theta;
                        maxPheromone = curPheromone;
                        foundPheromone = true;
                    }
                }
                if (foundPheromone) {
                    break;
                }
            }

            angle += maxAngle;

            ant.setX(x);
            ant.setY(y);
            ant.setAngle(angle);

            if(x >= 0 && x < Properties.WIDTH && y >= 0 && y < Properties.HEIGHT) {
                pheromones[(int) x][(int) y] += Properties.PHEROMONE_STRENGTH;
                if (pheromones[(int) x][(int) y] > 1)
                    pheromones[(int) x][(int) y] = 1;
            }
            else {
                System.err.println("Pheromone Out Of Bounds Error");
            }
        }
    }

    private double getAngle(double xAdj, double yAdj) {
        double radius = Math.pow(xAdj*xAdj + yAdj*yAdj, 0.5);
        double angle = Math.asin(yAdj/radius);
        if (xAdj < 0 && yAdj > 0 || xAdj < 0 && yAdj < 0)
            angle = Math.PI - angle;
        angle += Math.PI;
        return angle;
    }
}
