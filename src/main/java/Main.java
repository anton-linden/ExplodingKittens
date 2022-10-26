import Cards.Card;
import Cards.CardFactory;
import Client.Client;
import Game.GameManager;

public class Main {

    public static int numberOfTurnsToTake = 1; //TODO: Remove this weirdness.
    public static GameManager gameManager = new GameManager();

    public static void main(String[] params) {  //TODO: Is this really correct? Maybe run server separate?
        if(params.length == 2) {
            try {
                gameManager.initGame(Integer.valueOf(params[0]).intValue(), Integer.valueOf(params[1]).intValue());
            } catch (Exception e) {
                return;
            }
            gameManager.game();
        } else if(params.length == 1) {
            Client c = new Client(params[0]);
        } else {
            System.out.println("Server syntax: java ExplodingKittens numPlayers numBots");
            System.out.println("Client syntax: IP");
        }
    }


}
