import bagel.Image;
import bagel.util.Point;

/**
 * The Wall class
 * Player cannot go through or overlap them
 */
public class Wall extends GameEntity {
    private final static String WALL_IMAGE = "res/wall.png";

    /**
     * The constructor of walls
     *
     * @param startX the x coordinate
     * @param startY the y coordinate
     */
    public Wall(int startX, int startY) {
        this.ENTITY_IMAGE = new Image(WALL_IMAGE);
        this.position = new Point(startX, startY);
    }

    /**
     * Method that performs state update
     */
    @Override
    public void update() {
        ENTITY_IMAGE.drawFromTopLeft(this.position.x, this.position.y);
    }
}