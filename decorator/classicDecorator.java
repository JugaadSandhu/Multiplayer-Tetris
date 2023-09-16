package decorator;

import javafx.scene.paint.Color;
import tetris.Colour;

public class classicDecorator extends gameDecorator {

    public classicDecorator(Colour colour){
        background = Color.GREEN;
        blocks = Color.RED;
    }

    @Override
    public Color getColourBackground() {
        return background;
    }

    @Override
    public Color getColourBlocks() {
        return blocks;
    }
}
