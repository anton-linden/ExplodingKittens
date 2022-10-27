package Events;

import Game.GameManager;

public class Favor extends CardEvent {

    Favor() {
        setName("Favor");
    }

    @Override
    public void execute(GameManager gameManager) {
        validateCard(gameManager, getName());
        gameManager.activateCardAndMoveToDiscardPile(gameManager.getCurrentPlayer(), getName());
        gameManager.queueNextTurn(true, false);
    }
}
