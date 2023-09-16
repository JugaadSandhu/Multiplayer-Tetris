package observer;
import java.util.ArrayList;
import tetris.*;
import java.util.Random;

public class TetrisPieceObservable {
    ArrayList<String> pieces = new ArrayList<>();
    String[] possiblePieces = {TetrisPiece.STICK_STR, TetrisPiece.L1_STR, TetrisPiece.L2_STR, TetrisPiece.PYRAMID_STR, TetrisPiece.S1_STR, TetrisPiece.S2_STR, TetrisPiece.SQUARE_STR};

    /**
     * Create a new tetris piece and add to the pieces array
     */
    public void addPiece() {
        this.pieces.add(possiblePieces[(new Random().nextInt(7))]);
    }

    /**
     * Remove the first piece from the pieces array (Save memory)
    */
    public void removePiece() {pieces.remove(0);}

    /*
     * Return all of the pieces for updating the observer
     */
    public String getPieces() {
        return String.join(",", this.pieces);
    }

    /*
     * Checks if a new piece needs to be added to the array
     */
    public boolean checkSize(int index) {
        if (pieces.size() - 2  > index) {
            addPiece();
            return true;
        }
        return false;
    }
}