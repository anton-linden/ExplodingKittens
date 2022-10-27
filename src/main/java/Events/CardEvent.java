package Events;

import Game.GameManager;

public abstract class CardEvent extends Event {

    /**
     * Validates that the given player has the card that is attempted to being played.
     * @param gameManager The current game's GameManager.
     * @param cardName Card name that was attempted to be played.
     */
    public void validateCard(GameManager gameManager, String cardName) {
        if (!gameManager.getCurrentPlayer().getHand().hasCardWithCardName(cardName)) {
            gameManager.getCurrentPlayer().sendMessage("You don't have that card on your hand!");
            return;
        }
    }

}
