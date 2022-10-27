package Cards;

import Game.GameManager;

public class ExplodingKitten extends Card {

    public ExplodingKitten() {
        setName("ExplodingKitten");
    }

    /**
     * When drawn, the player needs to defuse the card. Otherwise, the user will explode and be added to spectators.
     * @param gameManager the current game's GameManger.
     */
    @Override
    public void drawEffect(GameManager gameManager) {
        Card drawCard = gameManager.getCurrentPlayer().getHand().getCardFromCardName("Defuse");

        if (drawCard == null) {
            cannotDefuse(gameManager);
            return;
        }

        gameManager.getCurrentPlayer().getHand().removeCard(drawCard);
        gameManager.getCurrentPlayer().sendMessage("You defused the kitten. Where in the deck do you wish to place the ExplodingKitten? [0.." + (gameManager.getPile().getSize()-1) + "]");
        gameManager.getPile().addCardAtIndex(this, Integer.valueOf(gameManager.getCurrentPlayer().readMessage(false)));
        gameManager.getPlayerManager().sendMessageToAllPlayers("Player " + gameManager.getCurrentPlayer().playerID + " successfully defused a kitten");
    }

    /**
     * Deals with when a player can't defuse the exploding kitten. (User missing defuse cards)
     * @param gameManager the current game's GameManger.
     */
    private void cannotDefuse(GameManager gameManager) {
        gameManager.getDiscard().addCard(this);
        gameManager.getDiscard().addDeck(gameManager.getCurrentPlayer().getHand());
        gameManager.getCurrentPlayer().exploded = true;
        gameManager.getPlayerManager().movePlayerToSpectators(gameManager.getCurrentPlayer());
        gameManager.getPlayerManager().sendMessageToAllPlayersAndSpectators("Player " + gameManager.getCurrentPlayer().playerID + " exploded");

        if (isThereAWinner(gameManager))
            gameManager.getEventQueue().add(gameManager.getEventFactory().makeEvent("AnnounceWinner"));
    }

    /**
     * When a user explodes, check if any winner can be crowned.
     * @param gameManager the current game's GameManger.
     * @return true/false depending on if anyone has won or not.
     */
    private boolean isThereAWinner(GameManager gameManager) {
        return (gameManager.getPlayerManager().getPlayers().size() == 1);
    }
}
