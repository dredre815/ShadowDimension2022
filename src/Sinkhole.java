import bagel.Image;
import bagel.util.Point;

/**
 * The Sinkhole class
 * they can put damage on player but not enemy
 */
public class Sinkhole extends GameEntity {
    private final static String SINKHOLE_IMAGE = "res/sinkhole.png";
    private final static int DAMAGE_POINTS = 30;
    private boolean isActive;

    /**
     * The constructor of the sinkholes
     *
     * @param startX the x coordinate
     * @param startY the y coordinate
     */
    public Sinkhole(int startX, int startY) {
        this.ENTITY_IMAGE = new Image(SINKHOLE_IMAGE);
        this.position = new Point(startX, startY);
        this.isActive = true;
    }

    /**
     * Method that performs state update
     * drawing the active sinkholes
     */
    @Override
    public void update() {
        if (isActive) {
            ENTITY_IMAGE.drawFromTopLeft(this.position.x, this.position.y);
        }
    }

    /**
     * Method to get the value of sinkhole's damage point
     *
     * @return damage point
     */
    public int getDamagePoints() {
        return DAMAGE_POINTS;
    }

    /**
     * Method to get the state of sinkholes
     *
     * @return boolean
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Method to set the state of sinkholes
     *
     * @param active state
     */
    public void setActive(boolean active) {
        isActive = active;
    }
}