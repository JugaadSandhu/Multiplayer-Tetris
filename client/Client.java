package client;
import javafx.application.Application;
import javafx.stage.Stage;
import observer.TetrisPieceObserver;
import tetris.TetrisModel;
import tetris.TetrisView;

import java.util.*;
import java.net.*;
import java.io.*;




public class Client extends Application {
    private static Socket socket;
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static String name;

    private static TetrisModel model;
    private TetrisView view;
    public static SuperState curr;

    public static TetrisPieceObserver observer = new TetrisPieceObserver();
    public Client() {
    }



    public void listener() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;


                while (socket.isConnected())  {
                    try {
                        message = reader.readLine();
                        if(message.equals("startGame|")){
                            model.startGame();
                        }
                        else {
                            curr.listening(message);
                        }
                    }
                    catch (IOException e) {closeEverything();}
                }
            }
        }).start();
    }

    public void closeEverything() {
        try {
            if (reader != null) {reader.close();}
            if (writer != null) {writer.close();}
            if (socket != null) {socket.close();}
        }
        catch (IOException e) {e.printStackTrace(); }
    }


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a username");
        name = scanner.nextLine();
        Client client = new Client();
        try {
            socket = new Socket("192.168.183.61", 4200); //The host IP should be chamged based on the user, currently the IPv4 address of Harsh's phone
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (IOException e) {client.closeEverything();}
        model = new TetrisModel(client); // create a model
        curr = new PlayingState(observer, client);
        scanner.close();
        client.listener();
        client.sendName();
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        this.view = new TetrisView(model, primaryStage, this); //tie the model to the view
    }

    public void sendName(){
        try {
            writer.write(name);
            writer.newLine();
            writer.flush();
        }
        catch (IOException e){closeEverything();}

    }


    public void update(String message){
        try {
            int temp = message.indexOf("|");
            writer.write(name + message.substring(temp + 1));
            writer.newLine();
            writer.flush();
        }
        catch (Exception e) {closeEverything();}
    }

    public void getsEliminated(){
        try {
            writer.write("eliminated|");
            writer.newLine();
            writer.flush();
            curr = new EliminatedState(observer, this);
        }
        catch (Exception e) {closeEverything();}
    }




    /*
     * Notifies the server that a piece was placed, and updates score accordingly
     * @param
     */
    public void placedPiece(int index, int score, int rowsCleared) {
        try {
            writer.write("placedPiece" + Integer.toString(index) + "|" + Integer.toString(score) + "|" + Integer.toString(rowsCleared));
            writer.newLine();
            writer.flush();
        }
        catch (Exception e) {closeEverything();}
    }

    public void decrementIndex() {
        model.index -= 1;
    }

    public void setStats(String stats) {
        model.stats = stats.replace("},","\n").replace("=", ":").replace("{","\n").replace("}","\n");
    }

    public String getStats() {
        return model.stats;
    }
}