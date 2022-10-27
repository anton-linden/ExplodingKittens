package Events;

import Game.GameManager;

public class SeeTheFuture extends CardEvent {

    SeeTheFuture() {
        setName("SeeTheFuture");
    }

    @Override
    public void execute(GameManager gameManager) {
        validateCard(gameManager, getName());
        gameManager.activateCardAndMoveToDiscardPile(gameManager.getCurrentPlayer(), getName());
        gameManager.queueNextTurn(false);
    }

}
