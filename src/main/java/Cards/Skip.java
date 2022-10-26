package Cards;

import Game.GameManager;
import Player.Player;

public class Skip extends Card {

    public Skip() {
        setCanBePlayed(true);
        setName("Skip");
    }

    @Override
    public void playCard(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage("You played " + getName());
    }
}
