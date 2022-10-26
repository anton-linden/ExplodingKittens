package Cards;

import Game.GameManager;

public class Shuffle extends Card {

    public Shuffle() {
        setCanBePlayed(true);
        setName("Shuffle");
    }

    @Override
    public void playCard(GameManager gamemanager) {
        gamemanager.getPile().shuffle();
    }
}
