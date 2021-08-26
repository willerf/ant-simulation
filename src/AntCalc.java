
import java.util.ArrayList;

public class AntCalc extends Thread {

    private ArrayList<Ant> ants;
    private ArrayList<Square> structures;
    private double[][] homePheromones;
    private double[][] foodPheromones;
    private boolean[][] food;
    private IntHolder foodCaptured;

    public AntCalc(ArrayList<Ant> antsIn, ArrayList<Square> structuresIn, double[][] homePheromonesIn, double[][] foodPheromonesIn, boolean[][] foodIn, IntHolder foodCapturedIn) {
        ants = antsIn;
        structures = structuresIn;
        homePheromones = homePheromonesIn;
        foodPheromones = foodPheromonesIn;
        food = foodIn;
        foodCaptured = foodCapturedIn;
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
            boolean foodStatus = ant.getFoodStatus();

            double xAdj = x - Properties.WIDTH / 2;
            double yAdj = Properties.HEIGHT / 2 - y;

            if(Properties.OVAL) {
                if((xAdj/((Properties.WIDTH - 2*Properties.BORDER_WIDTH)/2))*(xAdj/((Properties.WIDTH - 2*Properties.BORDER_WIDTH)/2)) +
                        (yAdj/((Properties.HEIGHT - 2*Properties.BORDER_WIDTH)/2))*(yAdj/((Properties.HEIGHT - 2*Properties.BORDER_WIDTH)/2)) > 1) {
                    angle = getAngle(xAdj, yAdj);
                }
            }
            else {
                if(x < Properties.BORDER_WIDTH || x > (Properties.WIDTH - Properties.BORDER_WIDTH) ||
                        y < Properties.BORDER_WIDTH || y > (Properties.HEIGHT - Properties.BORDER_WIDTH)) {
                    angle = getAngle(xAdj, yAdj);
                }
            }

            if(food[(int)x][(int)y] && !foodStatus) {
                foodStatus = true;
                food[(int)x][(int)y] = false;
                angle += Math.PI;
                ant.resetLastInteraction();
            }

            if(xAdj*xAdj + yAdj*yAdj < Properties.HOME_RADIUS*Properties.HOME_RADIUS && ant.getLastFlip() > 10) {
                if(foodStatus) {
                    foodStatus = false;
                    foodCaptured.increment();
                }
                angle += Math.PI;
                ant.resetLastFlip();
                ant.resetLastInteraction();
            }

            for(int j = 0; j < structures.size(); j++) {
                Square square = structures.get(j);
                if(square.XYIntersect((int)x, (int)y)) {
                    angle += Math.PI;
                    ant.resetLastFlip();
                }
            }

            x += Properties.SPEED * Math.cos(angle);
            y -= Properties.SPEED * Math.sin(angle);
            angle += Properties.WANDER*Math.random() - Properties.WANDER/2;

            double maxAngle = 0;
            double maxPheromone = 0;
            double curPheromone = 0;

            for (int r = Properties.VISION_DIST; r > Properties.VISION_DIST-Properties.VISION_DEPTH; r--) {
                for (double theta = -Properties.VISION_ANGLE; theta < Properties.VISION_ANGLE; theta += Math.PI/24) {
                    int xIndex = (int) (x + r * Math.cos(angle + theta));
                    int yIndex = (int) (y - r * Math.sin(angle + theta));

                    if (xIndex >= 0 && xIndex < Properties.WIDTH && yIndex >= 0 && yIndex < Properties.HEIGHT) {
                        if(foodStatus) {
                            //curPheromone = homePheromones[xIndex][yIndex];

                            curPheromone = Math.pow((Properties.VISION_ANGLE - Math.abs(theta)), 3) * homePheromones[xIndex][yIndex];
                            if((xIndex - Properties.WIDTH/2)*(xIndex - Properties.WIDTH/2) + (Properties.HEIGHT/2 - yIndex)*(Properties.HEIGHT/2 - yIndex) < Properties.HOME_RADIUS*Properties.HOME_RADIUS)
                                curPheromone = 4*(Properties.VISION_ANGLE - Math.abs(theta));

                        }
                        else {
                            //curPheromone = foodPheromones[xIndex][yIndex];

                            curPheromone = Math.pow((Properties.VISION_ANGLE - Math.abs(theta)), 3)*foodPheromones[xIndex][yIndex];
                            if(food[xIndex][yIndex])
                                curPheromone = 4*(Properties.VISION_ANGLE - Math.abs(theta));
                        }

                    } else {
                        System.err.println("Vision Out Of Bounds Error");
                    }

                    if (curPheromone > maxPheromone) {
                        maxAngle = theta;
                        maxPheromone = curPheromone;
                    }
                }
            }

            angle += maxAngle;

            ant.setX(x);
            ant.setY(y);
            ant.setAngle(angle);
            ant.setFoodStatus(foodStatus);
            ant.incLastFlip();
            ant.incLastInteraction();

            if(x >= 0 && x < Properties.WIDTH && y >= 0 && y < Properties.HEIGHT) {
                int lastInteraction = (1000 - ant.getLastInteraction())/50;
                if(lastInteraction < 0)
                    lastInteraction = 0;
                if(foodStatus) {
                    if (foodPheromones[(int) x][(int) y] + lastInteraction*Properties.PHEROMONE_STRENGTH <= 1) {
                        foodPheromones[(int) x][(int) y] += lastInteraction*Properties.PHEROMONE_STRENGTH;
                    }
                }
                else {
                    if (homePheromones[(int) x][(int) y] + lastInteraction*Properties.PHEROMONE_STRENGTH <= 1) {
                        homePheromones[(int) x][(int) y] += lastInteraction*Properties.PHEROMONE_STRENGTH;
                    }
                }
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
