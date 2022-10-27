package Events;

import Game.GameManager;

public class AnnounceWinner extends Event {

    /**
     * Announces a winner of the current game.
     * @param gameManager the GameManager of the current game.
     */
    public void execute(GameManager gameManager) {
        gameManager.getPlayerManager().sendMessageToAllPlayersAndSpectators(
                "Player " + gameManager.getPlayerManager().getPlayers().get(0).playerID + " has won the game"
        );
    }

}
