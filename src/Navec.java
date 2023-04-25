import bagel.Image;

/**
 * The class for Navec extends from the Demon
 */
public class Navec extends Demon {
    private final static String NAVEC_LEFT = "res/navec/navecLeft.png";
    private final static String NAVEC_RIGHT = "res/navec/navecRight.png";
    private final static String NAVEC_LEFT_INVINCIBLE = "res/navec/navecInvincibleLeft.png";
    private final static String NAVEC_RIGHT_INVINCIBLE = "res/navec/navecInvincibleRight.png";
    private final static String NAVEC_FIRE = "res/navec/navecFire.png";

    /**
     * The constructor for Navec
     *
     * @param startX the x coordinates
     * @param startY the y coordinates
     */
    public Navec(int startX, int startY) {
        super(startX, startY);
        this.RIGHT_IMAGE = new Image(NAVEC_RIGHT);
        this.LEFT_IMAGE = new Image(NAVEC_LEFT);
        this.SECOND_RIGHT_IMAGE = new Image(NAVEC_RIGHT_INVINCIBLE);
        this.SECOND_LEFT_IMAGE = new Image(NAVEC_LEFT_INVINCIBLE);
        this.FIRE = new Image(NAVEC_FIRE);
        this.MAX_HEALTH_POINT = 80;
        this.currentHealthPoint = this.MAX_HEALTH_POINT;
        this.DAMAGE_POINT = 20;
        this.ATTACK_RANGE = 200;
        this.isAggressive = true;
        this.currentImage = RIGHT_IMAGE;
        this.name = "Navec";
    }
}
