import bagel.Image;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The class for level 0 control
 */
public class Level0 extends Level {
    private final static String BACKGROUND_IMAGE_STRING = "res/background0.png";
    private final static int WALL_ARRAY_SIZE = 52;
    private final static int S_HOLE_ARRAY_SIZE = 5;
    private final static Wall[] walls = new Wall[WALL_ARRAY_SIZE];
    private final static Sinkhole[] sinkholes = new Sinkhole[S_HOLE_ARRAY_SIZE];

    /**
     * The constructor for the level 0
     */
    public Level0() {
        this.WORLD_FILE = "res/level0.csv";
        this.BACKGROUND_IMAGE = new Image(BACKGROUND_IMAGE_STRING);
        this.readCSV();
    }

    /**
     * Method used to read file and create objects
     */
    @Override
    public void readCSV() {
        try (BufferedReader reader = new BufferedReader(new FileReader(WORLD_FILE))) {

            String line;
            int currentWallCount = 0;
            int currentSinkholeCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Fae":
                        Player.getInstance().setPosition(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        break;
                    case "Wall":
                        walls[currentWallCount] = new Wall(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentWallCount++;
                        break;
                    case "Sinkhole":
                        sinkholes[currentSinkholeCount] = new Sinkhole(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentSinkholeCount++;
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
        this.BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        for (Wall current : walls) {
            current.update();
        }
        for (Sinkhole current : sinkholes) {
            current.update();
        }
        Player.getInstance().update(input, this);
    }

    /**
     * Method that checks for collisions between Fae and the other stationary entities
     * and performs corresponding actions
     *
     * @param player Fae
     */
    @Override
    public void checkCollisions(Player player) {
        Rectangle faeBox = player.getBoundingBox();
        for (Wall current : walls) {
            Rectangle wallBox = current.getBoundingBox();
            if (faeBox.intersects(wallBox)) {
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
    }

    /**
     * Method that checks if Fae has gone out-of-bounds and performs corresponding action
     *
     * @param player Fae
     */
    @Override
    public void checkOutOfBounds(Player player) {
        Point currentPosition = player.getPosition();
        if ((currentPosition.y > bottomRight.y) || (currentPosition.y < topLeft.y) || (currentPosition.x < topLeft.x)
                || (currentPosition.x > bottomRight.x)) {
            player.moveBack();
        }
    }

    /**
     * Method to get whether Fae reach to the gate
     *
     * @return boolean
     */
    public boolean enterNextLever() {
        return Player.getInstance().reachedGate();
    }

    /**
     * Method to get if fae's die and the game lose
     *
     * @return boolean the fae's state¬¬
     */
    public boolean gameOver() {
        return Player.getInstance().isDead();
    }
}
