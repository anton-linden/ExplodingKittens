package Events;

import Cards.Card;
import Game.GameManager;

public class Two extends CardEvent {

    Two() {
        setName("Two");
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
        drawRandomAndMoveCard(gameManager);
        gameManager.queueNextTurn(false, false);
    }

    /**
     * Draw a random card and move the card to the current player.
     * @param gameManager the GameManager of the current game.
     */
    private void drawRandomAndMoveCard(GameManager gameManager) {
        Card card = getTarget().getHand().drawRandomCard();
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
        return (gameManager.getCurrentPlayer().getHand().getCardCountFromCardName(getType()) >= 2);
    }

    /**
     * Remove the cards on hand if the correct amount existed.
     * @param gameManager the GameManager of the current game.
     */
    private void removeCards(GameManager gameManager) {
        for (int i = 0; i < 2; i++) {
            String type = getType();
            Card c = gameManager.getCurrentPlayer().getHand().getCardFromCardName(type);
            gameManager.getCurrentPlayer().getHand().removeCard(c);
        }
    }
}
