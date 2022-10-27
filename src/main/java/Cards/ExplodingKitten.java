package Cards;

import Game.GameManager;

public class ExplodingKitten extends Card {

    public ExplodingKitten() {
        setName("ExplodingKitten");
    }

    @Override
    public void drawEffect(GameManager gameManager) {

        Card drawCard = gameManager.getCurrentPlayer().getHand().getCardFromCardName("Defuse");

        if (drawCard == null) {
            cannotDefuse(gameManager);
            return;
        }

        gameManager.getCurrentPlayer().getHand().removeCard(drawCard);
        gameManager.getCurrentPlayer().sendMessage("You defused the kitten. Where in the deck do you wish to place the ExplodingKitten? [0.." + (gameManager.getPile().getSize()-1) + "]");
        //TODO: Add to the pile at position chosen by player.
        gameManager.getPile().addCard(this);
        gameManager.getPlayerManager().sendMessageToAllPlayers("Player " + gameManager.getCurrentPlayer().playerID + " successfully defused a kitten");
    }

    private void cannotDefuse(GameManager gameManager) {
        gameManager.getDiscard().addCard(this);
        gameManager.getDiscard().addDeck(gameManager.getCurrentPlayer().getHand());
        gameManager.getCurrentPlayer().exploded = true;
        gameManager.getPlayerManager().movePlayerToSpectators(gameManager.getCurrentPlayer());
        gameManager.getPlayerManager().sendMessageToAllPlayersAndSpectators("Player " + gameManager.getCurrentPlayer().playerID + " exploded");
    }
}
