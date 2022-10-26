package Cards;

import Game.GameManager;

public class Attack extends Card {

    public Attack() {
        setCanBePlayed(true);
        setName("Attack");
    }

    @Override
    public void playCard(GameManager gamemanager) {

    }
}