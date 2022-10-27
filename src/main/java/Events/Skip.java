package Events;

import Game.GameManager;

public class Skip extends CardEvent {

    Skip() {
        setName("Skip");
    }

    @Override
    public void execute(GameManager gameManager) {
        validateCard(gameManager, getName());
        gameManager.activateCardAndMoveToDiscardPile(gameManager.getCurrentPlayer(), getName());
        gameManager.queueNextTurn(false);
    }
}
