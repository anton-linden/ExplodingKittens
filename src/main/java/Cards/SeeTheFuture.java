package Cards;

import Game.GameManager;
import Player.Player;

public class SeeTheFuture extends Card {

    public SeeTheFuture() {
        setCanBePlayed(true);
        setName("SeeTheFuture");
    }

    @Override
    public void playCard(GameManager gamemanager) {
        gamemanager.getCurrentPlayer().sendMessage(
                "The top 3 cards are: "
                        + gamemanager.getPile().getDeck().get(0).getName() + " "
                        + gamemanager.getPile().getDeck().get(1).getName() + " "
                        + gamemanager.getPile().getDeck().get(2).getName()
        );
    }
}
