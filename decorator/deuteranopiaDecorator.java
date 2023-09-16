package decorator;

import javafx.scene.paint.Color;
import tetris.Colour;

public class deuteranopiaDecorator extends gameDecorator {

    /**
     *
     * @param colour
     * Uses colour as the base and decorates the background and blocks
     */
    public deuteranopiaDecorator(Colour colour){
        background = Color.BLUE;
        blocks = Color.YELLOW;
    }

    /**
     *
     * @return background which is the colour of the background for the game
     */
    @Override
    public Color getColourBackground() {
        return background;
    }

    /**
     *
     * @return blocks which is the colour of the blocks for the game
     */
    @Override
    public Color getColourBlocks() {
        return blocks;
    }
}
