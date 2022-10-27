package Events;

import Cards.Card;
import Game.GameManager;
import Player.Player;

import java.util.ArrayList;

public class PlayerTurn extends Event {

    /**
     * Tell the players about the turn. Get a response and then queue a new event.
     * @param gameManager the GameManager of the current game.
     */
    public void execute(GameManager gameManager) {
        messageAllPlayers(gameManager);
        printTurn(gameManager);
        readResponse(gameManager);
    }

    /**
     * Messenges all players
     * @param gameManager the game's current GameManager.
     */
    private void messageAllPlayers(GameManager gameManager) {
        for(Player player: gameManager.getPlayerManager().getPlayers()) {
            if(player == gameManager.getCurrentPlayer())
                player.sendMessage("It is your turn");
            else
                player.sendMessage("It is now the turn of player " + gameManager.getCurrentPlayer().playerID);
        }
    }

    /**
     * Prints the turn for the player to read in the console. And it's options
     * @param gameManager the game's current GameManager.
     */
    private void printTurn(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("\nYou have " + gameManager.getNumberOfTurnsToTake() + ((gameManager.getNumberOfTurnsToTake()>1)?" turns":" turn") + " to take");
        gameManager.getPlayerManager().printHand(gameManager.getCurrentPlayer());
        printOptions(gameManager);
    }


    /**
     * Prints the available options for the current player depending on what cards the player holds.
     * @param gameManager the game's current GameManager.
     */
    private void printOptions(GameManager gameManager) {
        String yourOptions = "You have the following options:\n";
        ArrayList<String> stringArrayList = new ArrayList<>();

        for(Card card : gameManager.getCurrentPlayer().getHand().getDeck()) {
            if (card.canBePlayed() && !stringArrayList.contains(card.getName())) {
                stringArrayList.add(card.getName());
            }
        }

        for(String str: stringArrayList) {
            yourOptions += "\t" + str + "\n";
        }

        for (Card card : gameManager.getCurrentPlayer().getHand().getCardsWithFrequency(2, 2)) {
            yourOptions += "\tTwo " + card.getName() + " [target] (available targets: " + "otherPlayerIDs" + ") (Steal random card)\n";
        }

        for (Card card : gameManager.getCurrentPlayer().getHand().getCardsWithFrequency(3, 9999)) { //TODO: ADD IDS!!!
            yourOptions += "\tThree " + card.getName() + " [target] [Card Type] (available targets: " + "otherPlayerIDs" + ") (Name and pick a card)\n";
        }

        yourOptions += "\tPass\n";

        gameManager.getCurrentPlayer().sendMessage(yourOptions);
    }

    /**
     * Reads the response.
     * @param gameManager the game's current GameManager.
     */
    private void readResponse(GameManager gameManager) {
        String response = gameManager.getCurrentPlayer().readMessage(false);
        queueResponseIfValid(gameManager, response);
    }

    /**
     * Checks if a user response is a valid move and then queues the next event.
     * @param gameManager the game's current GameManager.
     * @param response the response to split and see what event to queue.
     */
    private void queueResponseIfValid(GameManager gameManager, String response) {
        String[] args = response.split(" ");
        if (args.length <= 0)
            invalidResponse(gameManager);

        Event e = gameManager.getEventFactory().makeEvent(args[0]);

        if (args.length > 2)
            e.setType(args[1]);

        if(args.length == 3)
            e.setTarget(gameManager.getPlayerManager().getPlayers().get(Integer.valueOf(args[2])));

        if (e != null) {
            gameManager.getEventQueue().add(e);
        } else {
            invalidResponse(gameManager);
        }
    }

    /**
     * Handles an invalid response.
     * @param gameManager the game's current GameManager.
     */
    private void invalidResponse(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("Invalid response");
        printTurn(gameManager);
    }
}
