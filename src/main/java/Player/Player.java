package Player;

import Cards.Card;
import Deck.Deck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private final int SECONDS_TO_INTERRUPT_WITH_NOPE = 5;   //TODO: Dynamic variable?
    public int playerID;
    public boolean online;
    public boolean isBot;
    public Socket connection;
    public boolean exploded = false;
    public ObjectInputStream inFromClient;
    public ObjectOutputStream outToClient;
    Scanner in = null;

    private Deck hand = null;

    public Player(int playerID, boolean isBot, Socket connection, ObjectInputStream inFromClient, ObjectOutputStream outToClient) {
        this.playerID = playerID;
        this.connection = connection;
        this.inFromClient = inFromClient;
        this.outToClient = outToClient;
        this.isBot = isBot;

        hand = new Deck();

        if(connection == null)
            this.online = false;
        else
            this.online = true;
    }

    public void sendMessage(Object message) {
        if(online) {
            try {outToClient.writeObject(message);} catch (Exception e) {}
        } else if(!isBot){
            System.out.println(message);
        }
    }

    public String readMessage(boolean interruptable) {
        String word = " ";
        if(online)
            try{
                word = (String) inFromClient.readObject();
            } catch (Exception e){
                System.out.println("Reading from client failed: " + e.getMessage());
            }
        else
            try {
                if(interruptable) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    int millisecondsWaited = 0;
                    while(!br.ready() && millisecondsWaited<(SECONDS_TO_INTERRUPT_WITH_NOPE*1000)) {
                        Thread.sleep(200);
                        millisecondsWaited += 200;
                    }
                    if(br.ready())
                        return br.readLine();
                } else {
                    in = new Scanner(System.in);
                    word = in.nextLine();
                }
            } catch(Exception e){System.out.println(e.getMessage());}
        return word;
    }

    public Deck getHand() {
        hand.sortDeck();
        return hand;
    }

}
