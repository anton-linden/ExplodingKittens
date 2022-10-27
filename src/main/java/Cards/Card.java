package Cards;

import Game.GameManager;

public abstract class Card {
    private String name = "Name missing";   //Initialize to something weird so the user knows an error has occurred.
    private boolean canBePlayed = false;

    /**
     * Execute specific card instructions when player plays the card.
     * @param gameManager the current game's GameManger.
     */
    public void playCard(GameManager gameManager) {}

    /**
     * Execute specific card instructions when player draws the card.
     * @param gameManager the current game's GameManger.
     */
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
