package Events;

import Cards.Card;
import Game.GameManager;
import Player.Player;

import java.util.ArrayList;

public class PlayerTurn extends Event {

    public void execute(GameManager gameManager) {
        messageAllPlayers(gameManager);
        printTurn(gameManager);
        readResponse(gameManager);
    }

    private void messageAllPlayers(GameManager gameManager) {
        for(Player player: gameManager.getPlayerManager().getPlayers()) {
            if(player == gameManager.getCurrentPlayer())
                player.sendMessage("It is your turn");
            else
                player.sendMessage("It is now the turn of player " + gameManager.getCurrentPlayer().playerID);
        }
    }

    private void printTurn(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("\nYou have " + gameManager.getNumberOfTurnsToTake() + ((gameManager.getNumberOfTurnsToTake()>1)?" turns":" turn") + " to take");
        gameManager.getPlayerManager().printHand(gameManager.getCurrentPlayer());
        printOptions(gameManager);
    }

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

        for (Card card : gameManager.getCurrentPlayer().getHand().getCardsWithFrequency(3, 9999)) {
            yourOptions += "\tThree " + card.getName() + " [target] [Card Type] (available targets: " + "otherPlayerIDs" + ") (Name and pick a card)\n";
        }

        yourOptions += "\tPass\n";

        gameManager.getCurrentPlayer().sendMessage(yourOptions);
    }

    private void readResponse(GameManager gameManager) {
        String response = gameManager.getCurrentPlayer().readMessage(false);
        queueResponseIfValid(gameManager, response);
    }

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

    private void invalidResponse(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("Invalid response");
        printTurn(gameManager);
    }
}
