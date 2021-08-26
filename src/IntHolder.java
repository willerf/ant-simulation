public class IntHolder {
    private int value;

    public IntHolder(int valueIn) {
        value = valueIn;
    }

    public int getInt() {
        return value;
    }

    public void setInt(int valueIn) {
        value = valueIn;
    }

    public void increment() {
        value++;
    }
}
