package Cards;

import Game.GameManager;
import Player.Player;

public abstract class Card {

    private String name = "Name missing";   //Initialize to something weird so the user knows an error has occurred.
    private boolean canBePlayed = false;

    public void playCard(GameManager gameManager) {}

    public void drawEffect(GameManager gameManager) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean canBePlayed() {
        return canBePlayed;
    }

    public void setCanBePlayed(boolean canBePlayed) {
        this.canBePlayed = canBePlayed;
    }
}
