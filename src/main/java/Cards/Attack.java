package Cards;

import Game.GameManager;

public class Attack extends Card {

    public Attack() {
        setCanBePlayed(true);
        setName("Attack");
    }

    @Override
    public void playCard(GameManager gamemanager) {
        gamemanager.getCurrentPlayer().sendMessage(getName() + " was played");
        increaseTurns(gamemanager);
    }

    private void increaseTurns(GameManager gameManager) {
        int turns = gameManager.getNumberOfTurnsToTake();

        if ((gameManager.getNumberOfTurnsToTake() > 1))
            turns += 1;

        turns += gameManager.getAMOUNT_OF_TURNS_EACH_ROUND();
        gameManager.setNumberOfTurnsToTake(turns);
    }
}