package Server;

import Player.PlayerManager;
import Player.Player;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket aSocket;


    /*TODO: Split up
        Add users
        Create server
        More?
     */

    public Server(PlayerManager playerManager, int numberPlayers, int numberOfBots) {

        System.out.println("Constructor Server()");

        //Add first player
        playerManager.addPlayer(new Player(0, false, null, null, null)); //add this instance as a player

        //Add bots
        addBots(playerManager, numberOfBots);


        if(numberPlayers > 1)   //TODO: Not logical???
            try {
                //TODO: Replace with some socket class?
                aSocket = new ServerSocket(2048);
            } catch (Exception e) {
                //TODO: Fix exception handling
            }

        for(int i = numberOfBots + 1; i < numberPlayers + numberOfBots; i++) {
            try {
                Socket connectionSocket = aSocket.accept();

                ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());

                playerManager.addPlayer(new Player(i, false, connectionSocket, inFromClient, outToClient)); //add an online client

                System.out.println("Connected to player " + i);
                outToClient.writeObject("You connected to the server as player " + i + "\n");

            } catch (Exception e) {

            }
        }

    }

    /**
     * Adds bots to the current game.
     * @param amount
     */
    private void addBots(PlayerManager playerManager, int amount) {
        for(int i=0; i < amount; i++) {
            playerManager.addPlayer(new Player(i+1, true, null, null, null)); //add a bot
        }
    }
}
