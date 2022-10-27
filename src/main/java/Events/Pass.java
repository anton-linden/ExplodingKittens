package Events;

import Cards.Card;
import Game.GameManager;

public class Pass extends CardEvent {

    Pass() {
        setName("Pass");
    }

    @Override
    public void execute(GameManager gameManager) {
        Card drawCard = gameManager.getPile().drawTopCard();
        drawCard.drawEffect(gameManager);
        gameManager.getCurrentPlayer().getHand().addCard(drawCard);
        gameManager.getCurrentPlayer().sendMessage("You drew: " + drawCard.getName());
        gameManager.queueNextTurn(false, true);
    }
}
