package Events;

import Game.GameManager;

public abstract class CardEvent extends Event {

    public void validateCard(GameManager gameManager, String cardName) {
        if (!gameManager.getCurrentPlayer().getHand().hasCardWithCardName(cardName)) {
            gameManager.getCurrentPlayer().sendMessage("You don't have that card on your hand!");
            return;
        }
    }

}
