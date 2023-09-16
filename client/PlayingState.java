package client;


import observer.TetrisPieceObserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class PlayingState implements SuperState{
    private Client client;
    public TetrisPieceObserver observer;
    public PlayingState(TetrisPieceObserver observer, Client client){
        this.observer = observer;
        this.client = client;
    }

    public void listening(String message){
        if (message.substring(0, 6).equals("update")){
            update(message.substring(7));
        }
    }
    public void update(String message) {
        observer.update(message.substring(0, message.lastIndexOf("|")).split(","));
        if (message.substring(message.lastIndexOf("|") + 1).equals("true")) {
            this.client.decrementIndex();

        }
    }
}
