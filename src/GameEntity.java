import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * This is the base abstract class for all Stationary Entities
 * including Wall, Tree and Sinkhole
 * (I do not extend movingEntity from this class because I want to make sure this class has single entity image)
 * (also the param for update method is different, I think separate to two classes is not a kind of code duplication)
 */
public abstract class GameEntity {
    protected Image ENTITY_IMAGE;
    protected Point position;

    /**
     * Method for building bounding box for entities for preparing collision detection
     *
     * @return Rectangle bounding box
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(this.position, this.ENTITY_IMAGE.getWidth(), this.ENTITY_IMAGE.getHeight());
    }

    /**
     * Abstract method for updating
     * draw entities in the game
     */
    public abstract void update();
}
