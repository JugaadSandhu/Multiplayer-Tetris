package tetris;

import client.Client;
import decorator.*;
import tetris.TetrisModel.MoveType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Tetris View
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class TetrisView {

    TetrisModel model; //reference to model
    Stage stage;
    Colour colour = new Colour();
    gameDecorator decorator = new classicDecorator(colour);
    Button classicButton, deuteranopiaButton, protanomalyButton, deuteranomalyButton; //buttons for functions
    Label scoreLabel = new Label("");
    Label gameModeLabel = new Label("");
    
    Client client;

    BorderPane borderPane;
    Canvas canvas;
    GraphicsContext gc; //the graphics context will be linked to the canvas

    Boolean paused;
    Timeline timeline;

    int pieceWidth = 20; //width of block on display
    private double width; //height and width of canvas
    private double height;

    /**
     * Constructor
     *
     * @param model reference to tetris model
     * @param stage application stage
     */

    public TetrisView(TetrisModel model, Stage stage, Client client) {
        this.model = model;
        this.stage = stage;
        this.client = client;
        initUI();
    }

    public void updateStats(String stats){
        scoreLabel.setText(stats);
    }

    /**
     * Initialize interface
     */
    private void initUI() {
        this.paused = false;
        this.stage.setTitle("CSC207 Tetris");
        this.width = this.model.getWidth()*pieceWidth + 2;
        this.height = this.model.getHeight()*pieceWidth + 2;

        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #121212;");

        //add canvas
        canvas = new Canvas(this.width, this.height);
        canvas.setId("Canvas");
        gc = canvas.getGraphicsContext2D();

        //labels
        gameModeLabel.setId("GameModeLabel");
        scoreLabel.setId("ScoreLabel");

        gameModeLabel.setText("Player is: Human");
        gameModeLabel.setMinWidth(250);
        gameModeLabel.setFont(new Font(20));
        gameModeLabel.setStyle("-fx-text-fill: #e8e6e3");

        final ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton pilotButtonHuman = new RadioButton("Human");
        pilotButtonHuman.setToggleGroup(toggleGroup);
        pilotButtonHuman.setSelected(true);
        pilotButtonHuman.setUserData(Color.SALMON);
        pilotButtonHuman.setFont(new Font(16));
        pilotButtonHuman.setStyle("-fx-text-fill: #e8e6e3");

        RadioButton pilotButtonComputer = new RadioButton("Computer (Default)");
        pilotButtonComputer.setToggleGroup(toggleGroup);
        pilotButtonComputer.setUserData(Color.SALMON);
        pilotButtonComputer.setFont(new Font(16));
        pilotButtonComputer.setStyle("-fx-text-fill: #e8e6e3");

        scoreLabel.setText("Score is: 0");
        scoreLabel.setFont(new Font(20));
        scoreLabel.setStyle("-fx-text-fill: #e8e6e3");

        //added buttons for each choice in colour decorator
        classicButton = new Button("Classic");
        classicButton.setId("classic");
        classicButton.setPrefSize(150, 50);
        classicButton.setFont(new Font(12));
        classicButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        deuteranomalyButton = new Button("Deuteranomaly");
        deuteranomalyButton.setId("Start");
        deuteranomalyButton.setPrefSize(150, 50);
        deuteranomalyButton.setFont(new Font(12));
        deuteranomalyButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        deuteranopiaButton = new Button("Deuteranopia");
        deuteranopiaButton.setId("Save");
        deuteranopiaButton.setPrefSize(150, 50);
        deuteranopiaButton.setFont(new Font(12));
        deuteranopiaButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        protanomalyButton = new Button("Protanomaly");
        protanomalyButton.setId("Load");
        protanomalyButton.setPrefSize(150, 50);
        protanomalyButton.setFont(new Font(12));
        protanomalyButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");

        HBox controls = new HBox(20, protanomalyButton, classicButton, deuteranomalyButton, deuteranopiaButton);
        controls.setPadding(new Insets(20, 20, 20, 20));
        controls.setAlignment(Pos.CENTER);


        VBox scoreBox = new VBox(20, scoreLabel);
        scoreBox.setPadding(new Insets(20, 20, 20, 20));


        //timeline structures the animation, and speed between application "ticks"
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        //Uses the classicDecorator on the board and blocks
        classicButton.setOnAction(e -> {
            decorator = new classicDecorator(colour);
            paintBoard(decorator);
        });
        //Uses the deuteranopiaDecorator on the board and blocks
        deuteranopiaButton.setOnAction(e -> {
            decorator = new deuteranopiaDecorator(colour);
            paintBoard(decorator);
        });
        //Uses the deuteranomalyDecorator on the board and blocks
        deuteranomalyButton.setOnAction(e -> {
            decorator = new deuteranomalyDecorator(colour);
            paintBoard(decorator);
        });
        //Uses the protanomalyDecorator on the board and blocks
        protanomalyButton.setOnAction(e -> {
            decorator = new protanomalyDecorator(colour);
            paintBoard(decorator);
        });




        //configure this such that you can use controls to rotate and place pieces as you like!!
        //You'll want to respond to tie key presses to these moves:
        //TetrisModel.MoveType.DROP, TetrisModel.MoveType.ROTATE, TetrisModel.MoveType.LEFT
        //and TetrisModel.MoveType.RIGHT
        //make sure that you don't let the human control the board
        //if the autopilot is on, however.
        borderPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent k) {
                if (model.getAutoPilotMode()) {}
                else if (k.getCode() == KeyCode.A) {model.modelTick(MoveType.LEFT);}
                else if (k.getCode() == KeyCode.D) {model.modelTick(MoveType.RIGHT);}
                else if (k.getCode() == KeyCode.W) {model.modelTick(MoveType.ROTATE);}
                else if (k.getCode() == KeyCode.S) {model.modelTick(MoveType.DROP);}
            }
        });

        borderPane.setTop(controls);
        borderPane.setLeft(scoreBox);
        borderPane.setCenter(canvas);

        var scene = new Scene(borderPane, 800, 800);
        this.stage.setScene(scene);
        this.stage.show();
    }



    /**
     * Update board (paint pieces and score info)
     */
    private void updateBoard() {
        if (this.paused != true) {
            paintBoard(decorator);
            this.model.modelTick(TetrisModel.MoveType.DOWN);
            updateScore();
        }
    }

    /**
     * Update score on UI
     */
    private void updateScore() {
        if (model.gameOn) {
            scoreLabel.setText("Score is: " + model.getScore() + "\nPieces placed:" + model.getCount());
        }
        
        else {
            scoreLabel.setText(this.client.getStats());
        }
    }

    /**
     * Methods to calibrate sizes of pixels relative to board size
     */
    private final int yPixel(int y) {
        return (int) Math.round(this.height -1 - (y+1)*dY());
    }
    private final int xPixel(int x) {
        return (int) Math.round((x)*dX());
    }
    private final float dX() {
        return( ((float)(this.width-2)) / this.model.getBoard().getWidth() );
    }
    private final float dY() {
        return( ((float)(this.height-2)) / this.model.getBoard().getHeight() );
    }

    /**
     * Draw the board
     */
    public void paintBoard(gameDecorator decorator) {
        // Draw a rectangle around the whole screen

        gc.setStroke(decorator.getColourBackground());
        gc.setFill(decorator.getColourBackground());
        gc.fillRect(0, 0, this.width-1, this.height-1);

        // Draw the line separating the top area on the screen
        gc.setStroke(Color.BLACK);
        int spacerY = yPixel(this.model.getBoard().getHeight() - this.model.BUFFERZONE - 1);
        gc.strokeLine(0, spacerY, this.width-1, spacerY);

        // Factor a few things out to help the optimizer
        final int dx = Math.round(dX()-2);
        final int dy = Math.round(dY()-2);
        final int bWidth = this.model.getBoard().getWidth();

        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<bWidth; x++) {
            int left = xPixel(x);	// the left pixel
            // draw from 0 up to the col height
            final int yHeight = this.model.getBoard().getColumnHeight(x);
            for (y=0; y<yHeight; y++) {
                if (this.model.getBoard().getGrid(x, y)) {
                    gc.setFill(decorator.getColourBlocks());
                    gc.fillRect(left+1, yPixel(y)+1, dx, dy);
                    gc.setFill(decorator.getColourBlocks());
                }
            }
        }

    }

    /**
     * Create the view to save a board to a file
     */
    private void createSaveView(){
        SaveView saveView = new SaveView(this);
    }

    /**
     * Create the view to select a board to load
     */
    private void createLoadView(){
        LoadView loadView = new LoadView(this);
    }


}