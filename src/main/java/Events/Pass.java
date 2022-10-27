package Events;

import Cards.Card;
import Game.GameManager;

public class Pass extends CardEvent {

    Pass() {
        setName("Pass");
    }

    /**
     * Draws the top card from the game pile and activates its draw effects. Then tells the player which card was drawn and queues the next turn.
     * @param gameManager the GameManager of the current game.
     */
    @Override
    public void execute(GameManager gameManager) {
        Card drawCard = gameManager.getPile().drawTopCard();
        drawCard.drawEffect(gameManager);
        gameManager.getCurrentPlayer().getHand().addCard(drawCard);
        gameManager.getCurrentPlayer().sendMessage("You drew: " + drawCard.getName());
        gameManager.queueNextTurn(false, true);
    }
}
