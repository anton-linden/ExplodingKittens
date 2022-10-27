package Events;

import Game.GameManager;

public class Attack extends CardEvent {

    Attack() {
        setName("Attack");
    }

    @Override
    public void execute(GameManager gameManager) {
        validateCard(gameManager, getName());
        gameManager.activateCardAndMoveToDiscardPile(gameManager.getCurrentPlayer(), getName());
        gameManager.queueNextTurn(true, false);
    }
}
