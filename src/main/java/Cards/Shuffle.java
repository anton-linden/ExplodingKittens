package Cards;

import Game.GameManager;

public class Shuffle extends Card {

    public Shuffle() {
        setCanBePlayed(true);
        setName("Shuffle");
    }

    /**
     * When played, shuffle the games pile.
     * @param gameManager the current game's GameManger.
     */
    @Override
    public void playCard(GameManager gameManager) {
        gameManager.getPile().shuffle();
    }
}
