import bagel.Image;
import bagel.Input;
import bagel.util.Point;

/**
 * The base abstract class for level control
 */
public abstract class Level {
    protected String WORLD_FILE;
    protected Image BACKGROUND_IMAGE;
    protected Point topLeft;
    protected Point bottomRight;

    /**
     * Abstract method to read CSV file
     */
    public abstract void readCSV();

    /**
     * Abstract method to update
     *
     * @param input from user input
     */
    public abstract void update(Input input);

    /**
     * Abstract method to check collisions for player
     *
     * @param player Fae
     */
    public abstract void checkCollisions(Player player);

    /**
     * Abstract method to check if player move outside the bound
     *
     * @param player Fae
     */
    public abstract void checkOutOfBounds(Player player);

    /**
     * Method to draw log information when sinkhole damage the player
     *
     * @param sinkhole sinkholes
     * @param player   Fae
     */
    protected void sinkholeDamageLog(Sinkhole sinkhole, Player player) {
        System.out.println("Sinkhole inflicts " + sinkhole.getDamagePoints() + " damage points on Fae. " +
                "Fae's current health: " + player.getHealthPoints() + "/" + player.getMaxHealthPoints());
    }

    /**
     * Method to draw log information between player and enemies
     *
     * @param attacker attacker
     * @param target   target
     */
    protected void attackLog(MovingEntity attacker, MovingEntity target) {
        System.out.println(attacker.getName() + " inflicts " + attacker.getDAMAGE_POINT() + " damage points " +
                "on " + target.getName() + ". " + target.getName() + "'s" + " current health: " +
                target.getHealthPoints() + "/" + target.getMaxHealthPoints());
    }
}
