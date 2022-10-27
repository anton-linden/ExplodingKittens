package Events;

import Game.GameManager;

public class Shuffle extends CardEvent {

    Shuffle() {
        setName("Shuffle");
    }

    @Override
    public void execute(GameManager gameManager) {
        validateCard(gameManager, getName());
        gameManager.activateCardAndMoveToDiscardPile(gameManager.getCurrentPlayer(), getName());
        gameManager.queueNextTurn(false);
    }

}
