
public class Ant {

    private double x;
    private double y;
    private double angle;

    public Ant(int xStart, int yStart, double angleStart) {
        x = xStart;
        y = yStart;
        angle = angleStart;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAngle() {
        return angle;
    }

    public void setX(double newX) {
        x = newX;
    }

    public void setY(double newY) {
        y = newY;
    }

    public void setAngle(double newAngle) {
        angle = newAngle;
    }
}
