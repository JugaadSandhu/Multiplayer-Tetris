package client;

import observer.TetrisPieceObserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class EliminatedState implements SuperState{
    private Client client;
    private BufferedReader reader;
    private BufferedWriter writer;
    public TetrisPieceObserver observer;

    public EliminatedState(TetrisPieceObserver observer, Client client){
        this.observer = observer;
        this.client = client;
        getStats("Eliminated");
    }

    public void listening(String message){
        if (message.substring(0, 10).equals("getStats|{")){
            getStats(message.substring(10));
        }
    }

    public void getStats(String message){this.client.setStats(message);}
}
