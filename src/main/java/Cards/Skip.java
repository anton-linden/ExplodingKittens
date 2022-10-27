package Cards;

import Game.GameManager;
import Player.Player;

public class Skip extends Card {

    public Skip() {
        setCanBePlayed(true);
        setName("Skip");
    }

    /**
     * When played, skip picking up card and move to next turn.
     * @param gameManager the current game's GameManger.
     */
    @Override
    public void playCard(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("You played " + getName());
    }
}
