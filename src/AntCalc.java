
import java.util.ArrayList;

public class AntCalc extends Thread {
    private ArrayList<Ant> ants;
    private double[][] pheromones;
    private static final int SPEED = 1;

    public AntCalc(ArrayList<Ant> antsIn, double[][] pheromonesIn) {
        ants = antsIn;
        pheromones = pheromonesIn;
    }

    public void run() {
        while(true) {
            nextFrame();
            try {
                Thread.sleep(10);
            } catch(InterruptedException e) {

            }
        }
    }

    public void nextFrame() {
        for(int i = 0; i < ants.size(); i++) {
            Ant ant = ants.get(i);
            double x = ant.getX();
            double y = ant.getY();
            double angle = ant.getAngle();

            double xAdj = x - Properties.DIAMETER / 2;
            double yAdj = Properties.DIAMETER / 2 - y;

            if(Properties.CIRCLE) {
                double radius = Properties.DIAMETER / 2 - 10;

                if (xAdj * xAdj + yAdj * yAdj > radius * radius) {
                    if (xAdj > radius)
                        xAdj = radius;
                    else if (xAdj < -radius)
                        xAdj = -radius;
                    if (yAdj > radius)
                        yAdj = radius;
                    else if (yAdj < -radius)
                        yAdj = -radius;
                    angle = Math.asin(yAdj/radius);
                    if (xAdj < 0 && yAdj > 0 || xAdj < 0 && yAdj < 0)
                        angle = Math.PI - angle;
                    angle += Math.PI;
                }
            }
            else {
                double radius = Math.pow(xAdj*xAdj + yAdj*yAdj, 0.5);

                if(x < 10 || x > Properties.WIDTH - 10 || y < 10 || y > Properties.HEIGHT - 10) {
                    angle = Math.asin(yAdj/radius);
                    if (xAdj < 0 && yAdj > 0 || xAdj < 0 && yAdj < 0)
                        angle = Math.PI - angle;
                    angle += Math.PI;
                }

                /*
                if(x < 10 || x > Main.WIDTH - 10) {
                    angle = Math.PI - angle;
                }
                else if(y < 10 || y > Main.HEIGHT - 10) {
                    angle = -angle;
                }
                */
            }


            x += SPEED * Math.cos(angle);
            y -= SPEED * Math.sin(angle);
            angle += 0.1*Math.random() - 0.05;

            double maxAngle = angle;
            boolean foundPheromone = false;

            try {
                for (int r = Properties.VISION_DIST; r > Properties.VISION_DIST-Properties.VISION_DEPTH; r--) {
                    double maxPheromone = 0;
                    for (double theta = -Properties.VISION_ANGLE; theta < Properties.VISION_ANGLE; theta += Math.PI / 24) {
                        double curPheromone = pheromones[(int) (x + r * Math.cos(angle + theta))][(int) (y - r * Math.sin(angle + theta))];
                        if (curPheromone > maxPheromone) {
                            maxAngle = angle + theta;
                            maxPheromone = curPheromone;
                            foundPheromone = true;
                        }
                    }
                    if(foundPheromone)
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            }

            angle = maxAngle;

            ant.setX(x);
            ant.setY(y);
            ant.setAngle(angle);

            try {
                pheromones[(int)x][(int)y] += 0.1;
                if(pheromones[(int)x][(int)y] > 1)
                    pheromones[(int)x][(int)y] = 1;
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Pheromones");
            }
        }
    }
}
