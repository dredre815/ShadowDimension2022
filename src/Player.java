import bagel.*;
import bagel.util.Point;

/**
 * The class for player: Fae
 * Only one player so it is the singleton
 */
public class Player extends MovingEntity {
    private final static String FAE_LEFT = "res/fae/faeLeft.png";
    private final static String FAE_RIGHT = "res/fae/faeRight.png";
    private final static String FAE_LEFT_ATTACK = "res/fae/faeAttackLeft.png";
    private final static String FAE_RIGHT_ATTACK = "res/fae/faeAttackRight.png";
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;
    private final static int ATTACK_TIME = 1000;
    private final static int ATTACK_COOL_DOWN = 2000;
    private final static int ORIGINAL_COOL_DOWN_COUNTER = 130;
    private final TimeControl timeControl = new TimeControl();
    private final TimeControl coolDownCount = new TimeControl(ORIGINAL_COOL_DOWN_COUNTER);
    private double coolDown = 2001;
    private double attackTimeCounter = 0;
    private boolean isInputAttack = false;
    private static final Player player = new Player();
    private final static double MOVE_SPEED = 2;

    /**
     * The constructor for Player
     */
    private Player() {
        this.RIGHT_IMAGE = new Image(FAE_RIGHT);
        this.LEFT_IMAGE = new Image(FAE_LEFT);
        this.SECOND_RIGHT_IMAGE = new Image(FAE_RIGHT_ATTACK);
        this.SECOND_LEFT_IMAGE = new Image(FAE_LEFT_ATTACK);
        this.MAX_HEALTH_POINT = 100;
        this.currentHealthPoint = this.MAX_HEALTH_POINT;
        this.DAMAGE_POINT = 20;
        this.HEALTH_X = 20;
        this.HEALTH_Y = 25;
        this.FONT_SIZE = 30;
        this.isFacingRight = true;
        this.currentImage = this.RIGHT_IMAGE;
        this.isAttack = false;
        this.isInvincible = false;
        this.name = "Fae";
        this.centre = this.position;
    }

    /**
     * Method to get the instance of player
     *
     * @return Fae
     */
    public static Player getInstance() {
        return player;
    }

    /**
     * Method that performs state update
     * set the movement logic
     *
     * @param input the player's input
     * @param level level
     */
    public void update(Input input, Level level) {
        this.setCentre();
        this.stateControl(input);

        if (input.isDown(Keys.UP)) {
            this.setPrevPosition();
            this.move(0, -MOVE_SPEED);
            if (this.isFacingRight) {
                if (isAttack) {
                    this.currentImage = SECOND_RIGHT_IMAGE;
                } else {
                    this.currentImage = RIGHT_IMAGE;
                }
            } else {
                if (this.isAttack) {
                    this.currentImage = SECOND_LEFT_IMAGE;
                } else {
                    this.currentImage = LEFT_IMAGE;
                }
            }
        } else if (input.isDown(Keys.DOWN)) {
            this.setPrevPosition();
            this.move(0, MOVE_SPEED);
            if (this.isFacingRight) {
                if (isAttack) {
                    this.currentImage = SECOND_RIGHT_IMAGE;
                } else {
                    this.currentImage = RIGHT_IMAGE;
                }
            } else {
                if (this.isAttack) {
                    this.currentImage = SECOND_LEFT_IMAGE;
                } else {
                    this.currentImage = LEFT_IMAGE;
                }
            }
        } else if (input.isDown(Keys.LEFT)) {
            if (this.isFacingRight) {
                this.isFacingRight = false;
            }
            if (this.isAttack) {
                this.currentImage = SECOND_LEFT_IMAGE;
            } else {
                this.currentImage = LEFT_IMAGE;
            }
            this.setPrevPosition();
            this.move(-MOVE_SPEED, 0);
        } else if (input.isDown(Keys.RIGHT)) {
            if (!this.isFacingRight) {
                this.isFacingRight = true;
            }
            if (this.isAttack) {
                this.currentImage = SECOND_RIGHT_IMAGE;
            } else {
                this.currentImage = RIGHT_IMAGE;
            }
            this.setPrevPosition();
            this.move(MOVE_SPEED, 0);
        }
        this.currentImage.drawFromTopLeft(this.position.x, this.position.y);
        level.checkCollisions(this);
        this.renderHealthPoints(this.HEALTH_X, this.HEALTH_Y);
        level.checkOutOfBounds(this);
    }

    /**
     * Method that checks if Fae has found the gate
     *
     * @return boolean
     */
    public boolean reachedGate() {
        return (this.position.x >= WIN_X) && (this.position.y >= WIN_Y);
    }

    /**
     * Method that stores Fae's previous position
     */
    private void setPrevPosition() {
        this.prevPosition = new Point(this.position.x, this.position.y);
    }

    /**
     * Method that moves Fae back to previous position
     */
    public void moveBack() {
        this.position = prevPosition;
    }

    /**
     * Method to set the fae's position
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
    }

    /**
     * Method to set the attack logic
     *
     * @param input the player's input
     */
    public void stateControl(Input input) {
        if (input.wasPressed(Keys.A) && coolDown > ATTACK_COOL_DOWN) {
            isInputAttack = true;
            coolDown = 0;
            this.isAttack = true;
            coolDownCount.resetCounter();
            if (this.isFacingRight) {
                this.currentImage = SECOND_RIGHT_IMAGE;
            } else {
                this.currentImage = SECOND_LEFT_IMAGE;
            }
        }
        if (isInputAttack) {
            attackTimeCounter = timeControl.timeCount();
        } else {
            coolDown = coolDownCount.timeCount();
        }
        if (attackTimeCounter > ATTACK_TIME) {
            isInputAttack = false;
            attackTimeCounter = 0;
            this.isAttack = false;
            timeControl.resetCounter();
            if (this.isFacingRight) {
                this.currentImage = RIGHT_IMAGE;
            } else {
                this.currentImage = LEFT_IMAGE;
            }
        }
    }
}