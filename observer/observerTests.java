package observer;
import org.junit.jupiter.api.Test;
import tetris.TetrisPiece;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class observerTests {

    @Test
    void AddPiecesTest() {
        //Create model observer and observable
        TetrisPieceObservable observable = new TetrisPieceObservable();
        TetrisPieceObserver observer = new TetrisPieceObserver();

        //Add some pieces to observable (This will happen when game is started)
        observable.addPiece();
        observable.addPiece();
        observable.addPiece();

        //Simplification of process of sending pieces from observable to observer (This would be done by the server
        //and the pieces are sent to the observer as a string array
        observer.update(observable.getPieces().split(","));

        assertEquals(observable.pieces.size(), 3);
        assertEquals(observer.getPieces().size(), 3);
    }

    @Test
    void RemovePiece() {
        //Adding pieces in same way add pieces was tested
        TetrisPieceObservable observable = new TetrisPieceObservable();
        TetrisPieceObserver observer = new TetrisPieceObserver();
        observable.addPiece();
        observable.addPiece();
        observable.addPiece();
        observer.update(observable.getPieces().split(","));
        assertEquals(observable.pieces.size(), 3);
        assertEquals(observer.getPieces().size(), 3);

        //Save the piece at the 0th index
        TetrisPiece piece = new TetrisPiece(observable.getPieces().split(",")[1]);

        //Remove the piece at the 0th index from the observable
        observable.removePiece();
        assertEquals(observable.pieces.size(), 2);

        //Update the observer to the change
        observer.update(observable.getPieces().split(","));
        assertEquals(observer.getPieces().size(), 2);

        //Ensure the first piece was the one that was removed
        assertEquals(observer.getPieces().get(0), piece);
    }

    @Test
    void checkSizeTest() {
        //Create observable with three pieces
        TetrisPieceObservable observable = new TetrisPieceObservable();
        observable.addPiece();
        observable.addPiece();
        observable.addPiece();

        //Tell the observable that a player is currently on the second piece
        observable.checkSize(2);

        assertEquals(observable.pieces.size(), 3);

        //Tell the observable that a player is on the first piece
        observable.checkSize(0);

        assertEquals(observable.pieces.size(), 4);
    }
}
