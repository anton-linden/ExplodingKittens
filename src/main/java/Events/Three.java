package Events;

import Cards.Card;
import Game.GameManager;

public class Three extends CardEvent {

    Three() {
        setName("Three");
    }

    /**
     * If the current user has the correct amount of cards the cards gets removed from the player's hand. Then we attempt to take a card from the chosen player.
     * @param gameManager the GameManager of the current game.
     */
    @Override
    public void execute(GameManager gameManager) {
        if (!isValidateCardFrequency(gameManager))
            return;

        removeCards(gameManager);

        attemptToGetCardFromTarget(gameManager);
        gameManager.queueNextTurn(false, false);
    }

    /**
     * Attempts to get a card from the target. The player picks a card from the target.
     * @param gameManager the GameManager of the current game.
     */
    private void attemptToGetCardFromTarget(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("Type a card to attempt to steal from player " + getTarget().playerID);
        Card card = null;

        String cardName = gameManager.getCurrentPlayer().readMessage(false);
        card = getTarget().getHand().getCardFromCardName(cardName);

        if (card == null) {
            gameManager.getCurrentPlayer().sendMessage("Player " + getTarget().playerID + " didn't have any " + cardName + " in it's hands.");
            return;
        }

        getTarget().sendMessage("Player " + gameManager.getCurrentPlayer().playerID + " played " + getName()  + " and took a " + card.getName() + " card from your hand!");
        gameManager.getCurrentPlayer().getHand().addCard(card);
        gameManager.getCurrentPlayer().sendMessage("You received a " + card.getName() + " card from player " + getTarget().playerID);
    }

    /**
     * Validate that the player has the correct amount of cards.
     * @param gameManager the GameManager of the current game.
     * @return true/false
     */
    private boolean isValidateCardFrequency(GameManager gameManager) {
        return (gameManager.getCurrentPlayer().getHand().getCardCountFromCardName(getType()) >= 3);
    }

    /**
     * Remove the cards on hand if the correct amount existed.
     * @param gameManager the GameManager of the current game.
     */
    private void removeCards(GameManager gameManager) {
        for (int i = 0; i < 3; i++) {
            gameManager.getCurrentPlayer().getHand().removeCard(gameManager.getCurrentPlayer().getHand().getCardFromCardName(getType()));
        }
    }
}
