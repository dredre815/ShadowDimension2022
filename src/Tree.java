import bagel.Image;

/**
 * The Tree class extends from wall
 * The player or enemy cannot overlap with or move through the trees
 */
public class Tree extends Wall {
    private final static String TREE_IMAGE = "res/tree.png";

    /**
     * The constructor of the trees
     *
     * @param startX the x coordinate
     * @param startY the y coordinate
     */
    public Tree(int startX, int startY) {
        super(startX, startY);
        this.ENTITY_IMAGE = new Image(TREE_IMAGE);
    }
}
