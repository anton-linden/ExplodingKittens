package Cards;

import Game.GameManager;
import Player.Player;

public class Favor extends Card {

    public Favor() {
        setCanBePlayed(true);
        setName("Favor");
    }

    @Override
    public void playCard(GameManager gameManager) {
        selectPlayer(gameManager);
    }

    private void selectPlayer(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("Pick a player from [0.." + (gameManager.getPlayerManager().getPlayers().size() - 1) + "]");
        String response = gameManager.getCurrentPlayer().readMessage(false);
        requestAndMoveCardFromPlayer(gameManager, gameManager.getPlayerManager().getPlayers().get((Integer.valueOf(response))));
    }

    private void requestAndMoveCardFromPlayer(GameManager gameManager, Player player) {
        gameManager.getPlayerManager().printHand(player);
        player.sendMessage("Give a card to player: " + gameManager.getCurrentPlayer().playerID);

        String response = player.readMessage(false);
        Card card = player.getHand().getCardFromCardName(response);

        gameManager.getCurrentPlayer().getHand().addCard(card);
        player.getHand().removeCard(card);
        gameManager.getCurrentPlayer().sendMessage("Player " + player.playerID + " gave you a " + card.getName() + " card.");
    }

}
