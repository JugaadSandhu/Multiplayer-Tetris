package server;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import observer.TetrisPieceObservable;
import server.ClientHandler;
import server.StatsStorage;
import tetris.TetrisPiece;


public class Server {
    //Networking Variables
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private TetrisPieceObservable observable = new TetrisPieceObservable();

    private HashMap<String, Integer> playerIndicies = new HashMap<>();

    StatsStorage stats = new StatsStorage();

    /**
     * Updates each observer with the new tetris points
     */
    public void updateObservers(boolean decrementIndex) {
        for (ClientHandler client : clients) {
            client.sendMessageToClient("update|" + this.observable.getPieces() + "|" + decrementIndex);
        }
    }

    /**
     * Starts the game and lets each client member know that the game has begun.
     */
    public void startGame() {
        this.observable.addPiece();
        this.observable.addPiece();
        this.observable.addPiece();
        updateObservers(false);
        for (ClientHandler client: clients) {
            client.sendMessageToClient("startGame|");
        }
        sendStats();
    }

    /**
     * Check if any new pieces need to be added or removed from the observable
     */
    public void placedPiece(int index, String name){
        if (this.observable.checkSize(index)) {
            updateObservers(false);
            playerIndicies.put(name, index);
            /*
=======
>>>>>>> c77ca23b8cf9988b28d47b9c4bbdd41152dd4bff
            for(int value: playerIndicies.values()){
                if(value < index){
                    return;
                }
            }
            observable.removePiece();
            updateObservers(true);
<<<<<<< HEAD
             */

        }

    }

    /**
     * Eliminates client when they lose, takes <username>:eliminated|
     */
    public void eliminateClient(){

    }

    /**
     * Sends the stats of the client to the client, sends <username>:sendStats|
     */

    //Server Management
    public Server (ServerSocket socket) {serverSocket = socket;}


    /**
     * When starting a new server we add the new client connection to an arraylist of clients and start a thread for each
     * client.
     */
    public void startServer() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.println("How many players will there be for this round?");
            int num = Integer.parseInt(scan.nextLine());
            for(int i = 0; i < num; i++){
                Socket socket = serverSocket.accept();
                System.out.println("New connection");
                this.clients.add(new ClientHandler(socket, this));
                this.stats.addPlayer(this.clients.get(clients.size()-1).getName());
                playerIndicies.put(this.clients.get(clients.size()-1).getName(), 0);
                Thread thread = new Thread(this.clients.get(clients.size()-1));
                thread.start();
            }
            startGame();
        }
        //Error handling next steps: Should server close if there is an error when someone is trying to join?
        catch (IOException e) {}
    }

    /**
     * Closes the server if it is open.
     */
    public void closeServer() {
        //Close server socket if it isn't null
        try {if (serverSocket != null) {serverSocket.close();}}
        catch (IOException e){e.printStackTrace();}
    }

    public static void main (String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4200); //<-- Whatever this is needs to match the port number on the client
        Server server = new Server(serverSocket);
        server.startServer();
    }

    public void sendStats() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (ClientHandler client : clients) {
                        client.sendMessageToClient("getStats|" + stats.getAllStats());
                    }
                    try {Thread.sleep(5000);}
                    catch (InterruptedException e) {}
                }
            }
        }).start();
    }
}