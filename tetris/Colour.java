package tetris;


import javafx.scene.paint.Color;

public class Colour implements ColourChanger{
    /**
     *
     * @return background which is the colour of the background for the game
     */
    @Override
    public Color getColourBackground() {
        return Color.GREEN;
    }

    /**
     *
     * @return blocks which is the colour of the blocks for the game
     */
    @Override
    public Color getColourBlocks() {
        return Color.RED;
    }
}
