import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class for level 1 control
 */
public class Level1 extends Level0 {
    private final static String BACKGROUND_IMAGE_STRING = "res/background1.png";
    private final static int TREE_ARRAY_SIZE = 15;
    private final static int ENEMY_ARRAY_SIZE = 6;
    private final static int S_HOLE_ARRAY_SIZE = 5;
    private final static Tree[] trees = new Tree[TREE_ARRAY_SIZE];
    private final static Enemy[] enemy = new Enemy[ENEMY_ARRAY_SIZE];
    private final static Sinkhole[] sinkholes = new Sinkhole[S_HOLE_ARRAY_SIZE];
    private final static int INVINCIBLE_TIME = 3000;
    private final static int TIME_SCALE_UP = 3;
    private final static int TIME_SCALE_DOWN = -3;
    private int currentTimeScale = 0;

    /**
     * The constructor for level 1
     */
    public Level1() {
        this.WORLD_FILE = "res/level1.csv";
        this.BACKGROUND_IMAGE = new Image(BACKGROUND_IMAGE_STRING);
        this.readCSV();
    }

    /**
     * Method to read the level 1 world file
     */
    @Override
    public void readCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(WORLD_FILE))) {

            String line;
            int currentTreeCount = 0;
            int currentDemonCount = 0;
            int currentSinkholeCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Fae":
                        Player.getInstance().setPosition(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        Player.getInstance().setHealthPoints(Player.getInstance().getMaxHealthPoints());
                        break;
                    case "Tree":
                        trees[currentTreeCount] = new Tree(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentTreeCount++;
                        break;
                    case "Demon":
                        enemy[currentDemonCount] = new Demon(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentDemonCount++;
                        break;
                    case "Sinkhole":
                        sinkholes[currentSinkholeCount] = new Sinkhole(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentSinkholeCount++;
                        break;
                    case "Navec":
                        enemy[ENEMY_ARRAY_SIZE - 1] = new Navec(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        break;
                    case "TopLeft":
                        this.topLeft = new Point(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        break;
                    case "BottomRight":
                        this.bottomRight = new Point(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Method to update state
     *
     * @param input from user input
     */
    @Override
    public void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        // When player press "L", the movement speed of enemies will be faster
        if (input.wasPressed(Keys.L)) {
            if (currentTimeScale < TIME_SCALE_UP) {
                currentTimeScale++;
                System.out.println("Sped up, Speed: " + currentTimeScale);
            }
        }
        // When player press "K", the movement speed of enemies will be slower
        if (input.wasPressed(Keys.K)) {
            if (currentTimeScale > TIME_SCALE_DOWN) {
                currentTimeScale--;
                System.out.println("Slowed down, Speed: " + currentTimeScale);
            }
        }

        for (Tree current : trees) {
            current.update();
        }
        for (Sinkhole current : sinkholes) {
            current.update();
        }
        for (Enemy current : enemy) {
            current.update(this);
        }
        Player.getInstance().update(input, this);
    }

    /**
     * Method that checks for collisions between Fae and the stationary entities
     * and performs corresponding actions.
     * set the logic when fae collides with the enemies
     * set the logic when enemies collide with the fae
     *
     * @param player Fae
     */
    @Override
    public void checkCollisions(Player player) {
        Rectangle faeBox = player.getBoundingBox();
        for (Tree current : trees) {
            Rectangle treeBox = current.getBoundingBox();
            if (faeBox.intersects(treeBox)) {
                player.moveBack();
            }
        }

        for (Sinkhole hole : sinkholes) {
            Rectangle holeBox = hole.getBoundingBox();
            if (hole.isActive() && faeBox.intersects(holeBox)) {
                player.setHealthPoints(Math.max(player.getHealthPoints() - hole.getDamagePoints(), 0));
                player.moveBack();
                hole.setActive(false);
                sinkholeDamageLog(hole, player);
            }
        }

        // The logic for both sides of player and enemies
        for (Enemy current : enemy) {
            if (player.isAttackState() && current.isNotInvincibleState() &&
                    faeBox.intersects(current.getBoundingBox())) {
                current.setHealthPoints(Math.max(current.getHealthPoints() - player.getDAMAGE_POINT(), 0));
                attackLog(player, current);
                current.setInvincible(true);
                current.isInvincibleCount = true;
            }
            if (current.isInvincibleCount) {
                current.invincibleCount = current.invincibleControl.timeCount();
            }
            if (current.invincibleCount > INVINCIBLE_TIME) {
                current.setInvincible(false);
                current.isInvincibleCount = false;
                current.invincibleCount = 0;
                current.invincibleControl.resetCounter();
            }
            if (player.isNotInvincibleState() &&
                    current.fireRectangle != null && faeBox.intersects(current.fireRectangle)) {
                player.setHealthPoints(Math.max(player.getHealthPoints() - current.getDAMAGE_POINT(), 0));
                attackLog(current, player);
                player.setInvincible(true);
                player.isInvincibleCount = true;
            }
            if (player.isInvincibleCount) {
                player.invincibleCount = player.invincibleControl.timeCount();
            }
            if (player.invincibleCount > ENEMY_ARRAY_SIZE * INVINCIBLE_TIME) {
                player.setInvincible(false);
                player.isInvincibleCount = false;
                player.invincibleCount = 0;
                player.invincibleControl.resetCounter();
            }
        }
    }

    /**
     * Method that checks for collisions between enemies and the stationary entities
     * and performs corresponding actions.
     * also check if the enemy move outside the bound
     *
     * @param enemy enemies
     */
    public void checkCollisions(Enemy enemy) {
        Rectangle enemyBox = enemy.getBoundingBox();
        Point currentPosition = enemy.getPosition();

        if (enemy.isForward) {
            for (Tree current : trees) {
                Rectangle treeBox = current.getBoundingBox();
                if (enemyBox.intersects(treeBox)) {
                    enemy.isForward = false;
                    enemy.isBackward = true;
                }
            }

            for (Sinkhole hole : sinkholes) {
                Rectangle holeBox = hole.getBoundingBox();
                if (hole.isActive() && enemyBox.intersects(holeBox)) {
                    enemy.isForward = false;
                    enemy.isBackward = true;
                }
            }

            if ((currentPosition.y > bottomRight.y) || (currentPosition.y < topLeft.y) ||
                    (currentPosition.x < topLeft.x)
                    || (currentPosition.x > bottomRight.x)) {
                enemy.isForward = false;
                enemy.isBackward = true;
            }
        } else {
            for (Tree current : trees) {
                Rectangle treeBox = current.getBoundingBox();
                if (enemyBox.intersects(treeBox)) {
                    enemy.isBackward = false;
                    enemy.isForward = true;
                }
            }

            for (Sinkhole hole : sinkholes) {
                Rectangle holeBox = hole.getBoundingBox();
                if (hole.isActive() && enemyBox.intersects(holeBox)) {
                    enemy.isBackward = false;
                    enemy.isForward = true;
                }
            }

            if ((currentPosition.y > bottomRight.y) || (currentPosition.y < topLeft.y) ||
                    (currentPosition.x < topLeft.x) || (currentPosition.x > bottomRight.x)) {
                enemy.isBackward = false;
                enemy.isForward = true;
            }
        }

        enemy.drawFire(Player.getInstance());
    }

    /**
     * Method to get whether the player win
     *
     * @return boolean the Navec's state
     */
    public boolean gameWin() {
        return enemy[ENEMY_ARRAY_SIZE - 1].isDead();
    }

    /**
     * Method to get the TimeScale value
     *
     * @return timescale value for level 1
     */
    public int getCurrentTimeScale() {
        return currentTimeScale;
    }
}
