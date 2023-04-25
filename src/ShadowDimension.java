import bagel.*;

/**
 * Code for SWEN20003 Project 2, Semester 2, 2022
 * Shadow Dimension
 *
 * @author Marshall Zhang 1160040
 * @version 1.0
 */
public class ShadowDimension extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";

    private final static int TITLE_FONT_SIZE = 75;
    private final static int INSTRUCTION_FONT_SIZE = 40;
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 90;
    private final static int INS_Y_OFFSET = 190;
    private final Font TITLE_FONT = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
    private final Font INSTRUCTION_FONT = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
    private final static String INSTRUCTION_MESSAGE = "PRESS SPACE TO START\nUSE ARROW KEYS TO FIND GATE";
    private final static String END_MESSAGE = "GAME OVER!";
    private final static String LEVEL_COMPLETE = "LEVEL COMPLETE!";
    private boolean hasStarted;
    private boolean gameOver;
    private boolean playerEnterNextLevel;
    private final Level0 level0 = new Level0();
    private int level1StartCounter = 0;
    private final static int LEVEL_COMPLETE_TIME = 180;
    private final static int LEVEL1_START = 350;
    private final static int INS_Y_OFFSET_1 = 40;
    private final static String LEVEL_1_MESSAGE_1 = "PRESS SPACE TO START";
    private final static String LEVEL_1_MESSAGE_2 = "PRESS A TO ATTACK\nDEFEAT NAVEC TO WIN\n";
    private boolean level1Started;
    private boolean playerWin;
    private Level1 level1;
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private boolean isCreateLevel1;

    /**
     * The constructor of the ShadowDimension
     * Initialize some flags
     */
    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        hasStarted = false;
        gameOver = false;
        playerEnterNextLevel = false;
        level1Started = false;
        playerWin = false;
        isCreateLevel1 = false;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     * draw all start, game over and player win's screen.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        // Draw the start screen
        if (!hasStarted) {
            drawStartScreen();
            if (input.wasPressed(Keys.SPACE)) {
                hasStarted = true;
            }
        }

        if (gameOver) {
            drawMessage(END_MESSAGE);
        } else if (playerEnterNextLevel && !level1Started) {
            if (level1StartCounter < LEVEL_COMPLETE_TIME) {
                drawMessage(LEVEL_COMPLETE);
                level1StartCounter++;
            } else {
                drawLevel1Start();
                if (input.wasPressed(Keys.SPACE)) {
                    level1Started = true;
                }
            }
        } else if (playerWin) {
            drawMessage(WIN_MESSAGE);
        }

        // game is running
        // drawing level 0
        if (hasStarted && !gameOver && !playerEnterNextLevel) {
            level0.update(input);

            if (level0.gameOver()) {
                gameOver = true;
            }

            if (level0.enterNextLever()) {
                playerEnterNextLevel = true;
            }
        }

        // drawing level 1
        if ((level1Started && !gameOver && !playerWin)) {
            if (!isCreateLevel1) {
                level1 = new Level1();
                isCreateLevel1 = true;
            }
            level1.update(input);

            if (level1.gameOver()) {
                gameOver = true;
            }

            if (level1.gameWin()) {
                playerWin = true;
            }
        }
    }

    /**
     * Method used to draw the start screen title and instructions
     * from the sample solution in project 1
     */
    private void drawStartScreen() {
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE, TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
    }

    /**
     * Method used to draw end screen messages
     * from the sample solution in project 1
     */
    private void drawMessage(String message) {
        TITLE_FONT.drawString(message, (Window.getWidth() / 2.0 - (TITLE_FONT.getWidth(message) / 2.0)),
                (Window.getHeight() / 2.0 + (TITLE_FONT_SIZE / 2.0)));
    }

    /**
     * Method used to draw the level 1 start screen and instructions
     */
    private void drawLevel1Start() {
        INSTRUCTION_FONT.drawString(LEVEL_1_MESSAGE_1, LEVEL1_START, LEVEL1_START);
        INSTRUCTION_FONT.drawString(LEVEL_1_MESSAGE_2, LEVEL1_START, LEVEL1_START + INS_Y_OFFSET_1);
    }
}