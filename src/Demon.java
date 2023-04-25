import bagel.*;
import bagel.util.Point;

/**
 * The class for all demons
 */
public class Demon extends Enemy {
    private final static String DEMON_LEFT = "res/demon/demonLeft.png";
    private final static String DEMON_RIGHT = "res/demon/demonRight.png";
    private final static String DEMON_LEFT_INVINCIBLE = "res/demon/demonInvincibleLeft.png";
    private final static String DEMON_RIGHT_INVINCIBLE = "res/demon/demonInvincibleRight.png";
    private final static String DEMON_FIRE = "res/demon/demonFire.png";
    protected boolean isAggressive;
    protected final static int UP = 0;
    protected final static int DOWN = 1;
    protected final static int LEFT = 2;
    protected final static int RIGHT = 3;
    protected final static int HEALTH_BAR_Y_RANGE = 6;
    protected final static int SLOWER_MAX = -3;
    protected final static int SLOWER_MED = -2;
    protected final static int SLOWER_MIN = -1;
    protected final static int ORIGINAL = 0;
    protected final static int FASTER_MAX = 3;
    protected final static int FASTER_MED = 2;
    protected final static int FASTER_MIN = 1;

    /**
     * The constructor of demons
     *
     * @param startX the x coordinates
     * @param startY the y coordinates
     */
    public Demon(int startX, int startY) {
        this.RIGHT_IMAGE = new Image(DEMON_RIGHT);
        this.position = new Point(startX, startY);
        this.LEFT_IMAGE = new Image(DEMON_LEFT);
        this.SECOND_RIGHT_IMAGE = new Image(DEMON_RIGHT_INVINCIBLE);
        this.SECOND_LEFT_IMAGE = new Image(DEMON_LEFT_INVINCIBLE);
        this.FIRE = new Image(DEMON_FIRE);
        this.MAX_HEALTH_POINT = 40;
        this.currentHealthPoint = this.MAX_HEALTH_POINT;
        this.moveSpeed = random.nextDouble() * (MOVE_SPEED_UP - MOVE_SPEED_LOW) + MOVE_SPEED_LOW;
        this.isAggressive = random.nextBoolean();
        this.directionCode = random.nextInt(4);
        this.DAMAGE_POINT = 10;
        this.ATTACK_RANGE = 150;
        this.FONT_SIZE = 15;
        this.currentImage = RIGHT_IMAGE;
        this.isFacingRight = true;
        this.isAttack = true;
        this.isInvincible = false;
        this.name = "Demon";
        this.centre = this.position;
        this.originalMoveSpeed = this.moveSpeed;

    }

    /**
     * Method that performs state update
     * set the movement logic
     *
     * @param level1 level1
     */
    @Override
    public void update(Level1 level1) {
        if (this.currentHealthPoint > 0) {
            this.moveSpeedCheck(level1.getCurrentTimeScale());
            this.setCentre();

            if (!this.isAggressive) {
                level1.checkCollisions(this);
                if (this.isInvincible) {
                    this.currentImage = SECOND_RIGHT_IMAGE;
                } else {
                    this.currentImage = RIGHT_IMAGE;
                }
                if (this.directionCode == LEFT) {
                    if (this.isInvincible) {
                        this.currentImage = SECOND_LEFT_IMAGE;
                    } else {
                        this.currentImage = LEFT_IMAGE;
                    }
                }
            } else {
                switch (this.directionCode) {
                    case UP:
                        level1.checkCollisions(this);
                        if (this.isInvincible) {
                            this.currentImage = SECOND_RIGHT_IMAGE;
                        } else {
                            this.currentImage = RIGHT_IMAGE;
                        }
                        if (this.isForward) {
                            this.move(0, -this.moveSpeed);
                        } else {
                            this.move(0, this.moveSpeed);
                        }
                        break;
                    case DOWN:
                        level1.checkCollisions(this);
                        if (this.isInvincible) {
                            this.currentImage = SECOND_RIGHT_IMAGE;
                        } else {
                            this.currentImage = RIGHT_IMAGE;
                        }
                        if (this.isForward) {
                            this.move(0, this.moveSpeed);
                        } else {
                            this.move(0, -this.moveSpeed);
                        }
                        break;
                    case RIGHT:
                        level1.checkCollisions(this);
                        if (this.isForward) {
                            if (!this.isFacingRight) {
                                this.isFacingRight = true;
                            }
                            if (this.isInvincible) {
                                this.currentImage = SECOND_RIGHT_IMAGE;
                            } else {
                                this.currentImage = RIGHT_IMAGE;
                            }
                            this.move(this.moveSpeed, 0);
                        } else {
                            if (this.isFacingRight) {
                                this.isFacingRight = false;
                            }
                            if (this.isInvincible) {
                                this.currentImage = SECOND_LEFT_IMAGE;
                            } else {
                                this.currentImage = LEFT_IMAGE;
                            }
                            this.move(-this.moveSpeed, 0);
                        }
                        break;
                    case LEFT:
                        level1.checkCollisions(this);
                        if (this.isForward) {
                            if (this.isFacingRight) {
                                if (this.isInvincible) {
                                    this.currentImage = SECOND_LEFT_IMAGE;
                                } else {
                                    this.currentImage = LEFT_IMAGE;
                                }
                                this.isFacingRight = false;
                            }
                            this.move(-this.moveSpeed, 0);
                        } else {
                            if (!this.isFacingRight) {
                                if (this.isInvincible) {
                                    this.currentImage = SECOND_RIGHT_IMAGE;
                                } else {
                                    this.currentImage = RIGHT_IMAGE;
                                }
                                this.isFacingRight = true;
                            }
                            this.move(this.moveSpeed, 0);
                        }
                        break;
                }
            }
            this.currentImage.drawFromTopLeft(this.position.x, this.position.y);
            this.HEALTH_X = this.position.x;
            this.HEALTH_Y = this.position.y - HEALTH_BAR_Y_RANGE;
            this.renderHealthPoints(this.HEALTH_X, this.HEALTH_Y);
        } else {
            this.fireRectangle = EMPTY_RECTANGLE;
        }
    }

    /**
     * Method to set the logic for timescale control enemies' movement speed
     *
     * @param number timescale value
     */
    public void moveSpeedCheck(int number) {
        switch (number) {
            case ORIGINAL:
                this.moveSpeed = this.originalMoveSpeed;
                break;
            case FASTER_MIN:
                this.moveSpeed = CHANGE_OF_SPEED_UP * this.originalMoveSpeed;
                break;
            case FASTER_MED:
                this.moveSpeed = CHANGE_OF_SPEED_UP * CHANGE_OF_SPEED_UP * this.originalMoveSpeed;
                break;
            case FASTER_MAX:
                this.moveSpeed = CHANGE_OF_SPEED_UP * CHANGE_OF_SPEED_UP * CHANGE_OF_SPEED_UP * this.originalMoveSpeed;
                break;
            case SLOWER_MIN:
                this.moveSpeed = CHANGE_OF_SPEED_DOWN * this.originalMoveSpeed;
                break;
            case SLOWER_MED:
                this.moveSpeed = CHANGE_OF_SPEED_DOWN * CHANGE_OF_SPEED_DOWN * this.originalMoveSpeed;
                break;
            case SLOWER_MAX:
                this.moveSpeed =
                        CHANGE_OF_SPEED_DOWN * CHANGE_OF_SPEED_DOWN * CHANGE_OF_SPEED_DOWN * this.originalMoveSpeed;
                break;
        }
    }
}
