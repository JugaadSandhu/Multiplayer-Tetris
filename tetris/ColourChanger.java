package tetris;

import javafx.scene.paint.Color;

public interface ColourChanger {
    /**
     *
     * @return background which is the colour of the background for the game
     */
    public Color getColourBackground();

    /**
     *
     * @return blocks which is the colour of the blocks for the game
     */
    public Color getColourBlocks();

}
