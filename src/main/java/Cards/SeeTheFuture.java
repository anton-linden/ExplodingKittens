package Cards;

import Game.GameManager;
import Player.Player;

public class SeeTheFuture extends Card {

    public SeeTheFuture() {
        setCanBePlayed(true);
        setName("SeeTheFuture");
    }

    @Override
    public void playCard(GameManager gameManager) {
        gameManager.getCurrentPlayer().sendMessage(
                "The top 3 cards are: "
                        + gameManager.getPile().getDeck().get(0).getName() + " "
                        + gameManager.getPile().getDeck().get(1).getName() + " "
                        + gameManager.getPile().getDeck().get(2).getName()
        );
    }
}
