
public class Ant {

    private double x;
    private double y;
    private double angle;
    private boolean foodStatus;
    private int lastFlip;
    private int lastInteraction;

    public Ant(int xStart, int yStart, double angleStart) {
        x = xStart;
        y = yStart;
        angle = angleStart;
        foodStatus = false;
        lastFlip = 0;
        lastInteraction = 0;
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

    public boolean getFoodStatus() {
        return foodStatus;
    }

    public int getLastFlip() {
        return lastFlip;
    }

    public int getLastInteraction() {
        return lastInteraction;
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

    public void setFoodStatus(boolean newFoodStatus) {
        foodStatus = newFoodStatus;
    }

    public void resetLastFlip() {
        lastFlip = 0;
    }

    public void incLastFlip() {
        lastFlip++;
    }

    public void resetLastInteraction() {
        lastInteraction = 0;
    }

    public void incLastInteraction() {
        lastInteraction++;
    }
}
