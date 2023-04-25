/**
 * The class to calculate time in the game
 * It can calculate the attack, cool down or invincible time
 */
public class TimeControl {
    private final static int REFRESH_RATE = 60;
    private final static double REFRESH_RATE_MSEC = (REFRESH_RATE / 1000.0);
    private int counter = 0;

    /**
     * The empty constructor
     */
    public TimeControl() {
    }

    /**
     * The constructor for TimeControls
     * Player can use it to make sure he can attack at the beginning of the game
     *
     * @param counter greater than 120
     */
    public TimeControl(int counter) {
        this.counter = counter;
    }

    /**
     * Method for calculate the time according to the past of flames
     *
     * @return time in milliseconds
     */
    public double timeCount() {
        this.counter++;
        return (this.counter / REFRESH_RATE_MSEC);
    }

    /**
     * Method for reset the counter
     */
    public void resetCounter() {
        this.counter = 0;
    }
}
