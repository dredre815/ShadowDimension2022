import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

/**
 * The base abstract class for all moving entities
 * include player, demon and Navec
 */
public abstract class MovingEntity {
    protected Image RIGHT_IMAGE;
    protected Point position;
    protected Image LEFT_IMAGE;
    protected Image SECOND_RIGHT_IMAGE;
    protected Image SECOND_LEFT_IMAGE;
    protected int currentHealthPoint;
    protected int DAMAGE_POINT;
    protected double HEALTH_X;
    protected double HEALTH_Y;
    protected final static int ORANGE_BOUNDARY = 65;
    protected final static int RED_BOUNDARY = 35;
    protected int FONT_SIZE;
    protected final static String FONT_TYPE = "res/frostbite.ttf";
    protected final static Colour GREEN = new Colour(0, 0.8, 0.2);
    protected final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    protected final static Colour RED = new Colour(1, 0, 0);
    protected final static DrawOptions COLOUR = new DrawOptions();
    protected Point prevPosition;
    protected Image currentImage;
    protected boolean isAttack;
    protected boolean isInvincible;
    protected String name;
    protected Point centre;
    protected final TimeControl invincibleControl = new TimeControl();
    protected double invincibleCount = 0;
    protected boolean isInvincibleCount = false;
    protected boolean isFacingRight;
    protected int MAX_HEALTH_POINT;

    /**
     * Method to build the bounding box, prepare for collision detection
     *
     * @return Rectangle bounding box
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(this.position, this.currentImage.getWidth(), this.currentImage.getHeight());
    }

    /**
     * Method to set movement logic
     *
     * @param xMove the x coordinate
     * @param yMove the y coordinate
     */
    public void move(double xMove, double yMove) {
        double newX = this.position.x + xMove;
        double newY = this.position.y + yMove;
        this.position = new Point(newX, newY);
    }

    /**
     * Method to render the healthy bar
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void renderHealthPoints(double x, double y) {
        Font FONT = new Font(FONT_TYPE, this.FONT_SIZE);
        double percentageHP = ((double) this.currentHealthPoint / this.MAX_HEALTH_POINT) * 100;
        if (percentageHP <= RED_BOUNDARY) {
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(ORANGE);
        } else {
            COLOUR.setBlendColour(GREEN);
        }
        FONT.drawString(Math.round(percentageHP) + "%", x, y, COLOUR);
    }

    /**
     * Method to get is the moving entities die
     *
     * @return boolean state
     */
    public boolean isDead() {
        return this.currentHealthPoint <= 0;
    }

    /**
     * Method to get the position
     *
     * @return position
     */
    public Point getPosition() {
        return this.position;
    }

    /**
     * Method to get the healthy point
     *
     * @return healthy point
     */
    public int getHealthPoints() {
        return this.currentHealthPoint;
    }

    /**
     * Method to set the current healthy point
     *
     * @param healthPoints current healthy point
     */
    public void setHealthPoints(int healthPoints) {
        this.currentHealthPoint = healthPoints;
    }

    /**
     * Method to get the maximum healthy point
     *
     * @return Max healthy point
     */
    public int getMaxHealthPoints() {
        return this.MAX_HEALTH_POINT;
    }

    /**
     * Method to get the damage point
     *
     * @return damage point
     */
    public int getDAMAGE_POINT() {
        return this.DAMAGE_POINT;
    }

    /**
     * Method to get the attack state
     *
     * @return boolean state
     */
    public boolean isAttackState() {
        return this.isAttack;
    }

    /**
     * Method to get the invincible state
     *
     * @return boolean state
     */
    public boolean isNotInvincibleState() {
        return !this.isInvincible;
    }

    /**
     * Method to set the state of invincible
     *
     * @param invincible boolean state
     */
    public void setInvincible(boolean invincible) {
        this.isInvincible = invincible;
    }

    /**
     * Method to get the name
     *
     * @return String name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to set the centre point
     */
    public void setCentre() {
        double x = this.position.x + (this.currentImage.getWidth() / 2);
        double y = this.position.y + (this.currentImage.getHeight() / 2);
        this.centre = new Point(x, y);
    }

    /**
     * Method to get the centre point
     *
     * @return centre
     */
    public Point getCentre() {
        return this.centre;
    }
}
