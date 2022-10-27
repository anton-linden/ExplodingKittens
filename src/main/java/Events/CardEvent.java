package Events;

import Cards.Card;
import Game.GameManager;

public abstract class CardEvent extends Event {

    private String name = "Event";

    public void execute(GameManager gameManager) {}

    public void validateCard(GameManager gameManager, String cardName) {
        if (!gameManager.getCurrentPlayer().getHand().hasCardWithCardName(cardName)) {
            gameManager.getCurrentPlayer().sendMessage("You don't have that card on your hand!");
            return;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
