import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

/**
 * The base abstract class for enemy
 */
public abstract class Enemy extends MovingEntity {
    protected final static double MOVE_SPEED_UP = 0.7;
    protected final static double MOVE_SPEED_LOW = 0.2;
    protected Random random = new Random();
    protected int ATTACK_RANGE;
    protected int directionCode;
    protected boolean isForward = true;
    protected boolean isBackward = false;
    protected Image FIRE;
    protected final static DrawOptions ROTATED = new DrawOptions();
    protected final static double RADIAN = 1.57079633;
    protected Rectangle fireRectangle;
    protected double moveSpeed;
    protected final Rectangle EMPTY_RECTANGLE = new Rectangle(0, 0, 0, 0);
    protected double originalMoveSpeed;
    protected final static double CHANGE_OF_SPEED_UP = 1.5;
    protected final static double CHANGE_OF_SPEED_DOWN = 0.5;
    private final static int ROTATION_1 = -1;
    private final static int ROTATION_2 = -2;

    /**
     * Method to update state
     *
     * @param level1 the player's input
     */
    public abstract void update(Level1 level1);

    /**
     * Method to draw fire when Fae close to enemies
     *
     * @param player Fae
     */
    public void drawFire(Player player) {
        if (player.getCentre().distanceTo(this.getCentre()) <= this.ATTACK_RANGE) {
            if (player.getCentre().x <= this.getCentre().x && player.getCentre().y <= this.getCentre().y) {
                this.FIRE.drawFromTopLeft(this.position.x - this.FIRE.getWidth(),
                        this.position.y - this.FIRE.getHeight());
                fireRectangle = this.FIRE.getBoundingBoxAt(new Point(this.position.x -
                        (this.FIRE.getWidth() / 2), this.position.y - (this.FIRE.getHeight() / 2)));

            } else if (player.getCentre().x <= this.getCentre().x && player.getCentre().y > this.getCentre().y) {
                this.FIRE.drawFromTopLeft(this.position.x - this.FIRE.getWidth(),
                        this.position.y + this.currentImage.getHeight(),
                        ROTATED.setRotation((ROTATION_1) * RADIAN));
                fireRectangle = this.FIRE.getBoundingBoxAt(new Point(this.position.x -
                        (this.FIRE.getWidth() / 2), this.position.y + this.currentImage.getHeight() +
                        (this.FIRE.getHeight() / 2)));

            } else if (player.getCentre().x > this.getCentre().x && player.getCentre().y <= this.getCentre().y) {
                this.FIRE.drawFromTopLeft(this.position.x + this.currentImage.getWidth(),
                        this.position.y - this.FIRE.getHeight(), ROTATED.setRotation(RADIAN));
                fireRectangle = this.FIRE.getBoundingBoxAt(new Point(this.position.x +
                        this.currentImage.getWidth() + (this.FIRE.getWidth() / 2),
                        this.position.y - (this.FIRE.getHeight() / 2)));

            } else if (player.getCentre().x > this.getCentre().x && player.getCentre().y > this.getCentre().y) {
                this.FIRE.drawFromTopLeft(this.position.x + this.currentImage.getWidth(),
                        this.position.y + this.currentImage.getHeight(),
                        ROTATED.setRotation((ROTATION_2) * RADIAN));
                fireRectangle = this.FIRE.getBoundingBoxAt(new Point(this.position.x +
                        this.currentImage.getWidth() + (this.FIRE.getWidth() / 2),
                        this.position.y + this.currentImage.getHeight() + (this.FIRE.getHeight() / 2)));

            }
        }
    }
}
