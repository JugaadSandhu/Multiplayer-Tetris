package observer;
import tetris.*;
import java.util.ArrayList;

public class TetrisPieceObserver {
    private ArrayList<TetrisPiece> pieces = new ArrayList<>();

    /**
     * Updates the pieces on the observable
     *
     * @param pieces pieces to update with
     */
    public void update(String[] pieces) {
        this.pieces.clear();
        for(String piece : pieces) {
            this.pieces.add(new TetrisPiece(piece));
        }
    }

    /**
     * Getter for pieces currently stored by the array
     */
    public ArrayList<TetrisPiece> getPieces() {
        return this.pieces;
    }

    /**
     * Getter for specific piece at specific index
     * @param index index of piece
     */
    public TetrisPiece getPiece(int index) {
        return this.pieces.get(index);
    }
}