package Events;

import Game.GameManager;

public class AnnounceWinner extends Event {

    public void execute(GameManager gameManager) {
        gameManager.getPlayerManager().sendMessageToAllPlayersAndSpectators(
                "Player " + gameManager.getPlayerManager().getPlayers().get(0).playerID + " has won the game"
        );
    }

}
